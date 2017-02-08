import {Component, OnInit} from '@angular/core';
import {
    ApplicationTask, ApprovalEvent, ApplicationTaskFilter,
    ApplicationTaskResult, PaginationInfo
} from "../../commons/models/application-data-models";
import {ApprovalRemoteDataService} from "../../data-providers/approval-remote-data.service";
import {MessageService} from "../../commons/services/message.service";
import {ApprovalHelperService} from "../approval-helper.service";
import {TableDataType} from "../../commons/models/common-data-models";

@Component({
    selector: 'app-applications',
    templateUrl: './applications.component.html',
    styleUrls: ['./applications.component.scss']
})
export class ApplicationsComponent implements OnInit {

    private myApplications: ApplicationTaskResult;

    private allApplications: ApplicationTaskResult;

    private userApplicationFilter: ApplicationTaskFilter;

    private groupApplicationFilter: ApplicationTaskFilter;

    constructor(private message: MessageService,
                private approvalHelperService: ApprovalHelperService,
                private approvalService: ApprovalRemoteDataService) {
    }

    ngOnInit() {
        this.userApplicationFilter = new ApplicationTaskFilter(new TableDataType('USER', 'APPLICATION'),10);

        this.groupApplicationFilter = new ApplicationTaskFilter(new TableDataType('GROUP', 'APPLICATION'),10);

        this.approvalService.MyApplicationCreationTasksProvider.subscribe(
            (apps: ApplicationTaskResult) => {
                this.myApplications = apps;
            },
            (error) => {
                this.message.error(error);
            }
        );

        this.approvalService.GroupApplicationCreationTasksProvider.subscribe(
            (apps: ApplicationTaskResult) => {
                this.allApplications = apps;
            },
            (error) => {
                this.message.error(error)
            }
        );

        this.getData();
    }

    private getData(){
        this.approvalService.getFilteredResult(this.userApplicationFilter);
        this.approvalService.getUserGroupApplicationTasks(this.groupApplicationFilter);
    }

    onAssignTaskHandler(event: ApprovalEvent): void {
        this.approvalHelperService.assignApplicationTask(event.dataType.dataType, event.task.id,()=>{
            this.getData();
        });
    }

    onApproveRejectHandler(event: ApprovalEvent): void {
        this.approvalHelperService.approveRejectTask(event.dataType, event.task, event.status);
    }

    onFilterChangeHandler(event: ApplicationTaskFilter): void {
        this.approvalService.getFilteredResult(event);
    }

}
