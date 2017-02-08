import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges} from '@angular/core';
import {
    ApplicationTask, ApproveApplicationCreationTaskParam,
    ApproveSubscriptionCreationTaskParam, ApprovalEvent, ApplicationTaskFilter, ApplicationTaskResult, PaginationInfo
} from "../../models/application-data-models";
import {ApprovalRemoteDataService} from "../../../data-providers/approval-remote-data.service";
import {MessageService} from "../../services/message.service";
import {TableDataType} from "../../models/common-data-models";
import {Router} from "@angular/router";
import {TypeaheadMatch} from "ng2-bootstrap";

@Component({
    selector: 'application-data-table',
    templateUrl: './application-data-table.component.html',
    styleUrls: ['./application-data-table.component.scss']
})
export class ApplicationDataTableComponent implements OnInit {


    @Input()
    private tableTitle: string;

    @Input()
    private summeryModeRecordLimit: string;

    @Input()
    private dataSource: ApplicationTaskResult;

    @Input()
    private moreLinkPath: string;

    @Input()
    private filter: ApplicationTaskFilter;

    @Output()
    private onAssignTask: EventEmitter<ApprovalEvent> = new EventEmitter();

    @Output()
    private onApproveRejectTask: EventEmitter<ApprovalEvent> = new EventEmitter();

    @Output()
    private onFilterChange: EventEmitter<ApplicationTaskFilter> = new EventEmitter();

    private FilterFieldsDataSource: ApplicationTask[];

    private filterId: number;
    private filterAppName: string;
    private filterUser: string;
    private filterFromDate: string;
    private filterToDate: string;

    //Flag to determine whether to filtering is active or not
    private isFilterActivated: boolean = false;

    //Flag to determine whether to show or hide filtering panel
    private isFilterVisible: boolean = false;

    private currentPage: number = 1;

    constructor(private approvalService: ApprovalRemoteDataService,
                private message: MessageService,
                private _router: Router) {
    }

    ngOnInit() {

    }

    ngOnChanges(changeObj: SimpleChanges) {
        if (!this.isFilterActivated && changeObj && changeObj['dataSource'] && (changeObj['dataSource'].currentValue != changeObj['dataSource'].previousValue)) {
            let res: ApplicationTaskResult = changeObj['dataSource'].currentValue;
            this.FilterFieldsDataSource = (res && res.applicationTasks) || [];
        }
    }

    onViewAll(): void {
        if (!!this.moreLinkPath) {
            this._router.navigate([this.moreLinkPath]);
        }
    }

    onOptionChange(event, item) {
        item.tier = event.target.value;
    }

    onToggleFilter() {
        this.isFilterVisible = !this.isFilterVisible;
        if (!this.isFilterVisible) {
            this.onClear('ALL');
        }
    }

    onAction(actionType: string, appTask: ApplicationTask, typeInfo: TableDataType): void {
        switch (actionType) {
            case 'ASSIGN' : {
                this.onAssignTask.emit(new ApprovalEvent(appTask, typeInfo));
                break;
            }

            case 'APPROVE' : {
                this.onApproveRejectTask.emit(new ApprovalEvent(appTask, typeInfo, 'APPROVED'));
                break;
            }

            case 'REJECT' : {
                this.onApproveRejectTask.emit(new ApprovalEvent(appTask, typeInfo, 'REJECTED'));
                break;
            }
        }
    }

    onFilterItemAdded(event: TypeaheadMatch, type: string) {
        let task: ApplicationTask = <ApplicationTask>event.item;
        this.isFilterActivated = true;

        switch (type) {
            case 'ID' : {
                if (this.filter.ids.indexOf(task.id) < 0) {
                    this.filter.ids.push(task.id);
                }
                this.filterId = null;
                break;
            }

            case 'APP_NAME' : {
                if (this.filter.appNames.indexOf(task.applicationName) < 0) {
                    this.filter.appNames.push(task.applicationName);
                }
                this.filterAppName = null;
                break;
            }

            case 'USER' : {
                if (this.filter.users.indexOf(task.userName) < 0) {
                    this.filter.users.push(task.userName);
                }
                this.filterUser = null;
                break;
            }
        }
        this.onFilterChange.emit(this.filter);
    }

    onClear(type: string) {
        switch (type) {
            case 'ID': {
                this.filter.ids.length = 0;
                this.filterId = null;
                break;
            }
            case 'NAME': {
                this.filter.appNames.length = 0;
                this.filterAppName = null;
                break;
            }
            case 'USER': {
                this.filter.users.length = 0;
                this.filterUser = null;
                break;
            }
            case 'ALL': {
                this.filter.ids.length = 0;
                this.filter.appNames.length = 0;
                this.filter.users.length = 0;
                this.filterId = null;
                this.filterAppName = null;
                this.filterUser = null;
                break;
            }
        }

        if (this.filter.ids.length == 0 || this.filter.appNames.length == 0 || this.filter.users.length == 0) {
            this.isFilterActivated = false;
        }

        this.onFilterChange.emit(this.filter);
    }

    onPageChanged(event) {
        this.filter.startRecordNumber = (<number>event.page - 1) * (this.filter.numberOfRecordsPerPage || 0);
        this.onFilterChange.emit(this.filter);
    }

}
