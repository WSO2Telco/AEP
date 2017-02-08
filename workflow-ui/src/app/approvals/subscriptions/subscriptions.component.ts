import {Component, OnInit} from '@angular/core';
import {
    ApplicationTask, ApprovalEvent, ApplicationTaskFilter,
    ApplicationTaskResult
} from "../../commons/models/application-data-models";
import {ApprovalRemoteDataService} from "../../data-providers/approval-remote-data.service";
import {MessageService} from "../../commons/services/message.service";
import {ApprovalHelperService} from "../approval-helper.service";
import {TableDataType} from "../../commons/models/common-data-models";

@Component({
    selector: 'app-subscriptions',
    templateUrl: './subscriptions.component.html',
    styleUrls: ['./subscriptions.component.scss']
})
export class SubscriptionsComponent implements OnInit {

    private mySubscriptions: ApplicationTaskResult;
    private allSubscriptions: ApplicationTaskResult;

    private mySubscriptionFilter: ApplicationTaskFilter;
    private groupSubscriptionFilter: ApplicationTaskFilter;

    constructor(private message: MessageService,
                private approvalHelperService: ApprovalHelperService,
                private approvalService: ApprovalRemoteDataService) {
    }

    ngOnInit() {

        this.mySubscriptionFilter = new ApplicationTaskFilter(new TableDataType('USER', 'SUBSCRIPTION'), 10);

        this.groupSubscriptionFilter = new ApplicationTaskFilter(new TableDataType('GROUP', 'SUBSCRIPTION'), 10);

        this.approvalService.MySubscriptionTasksProvider.subscribe(
            (subs: ApplicationTaskResult) => {
                this.mySubscriptions = subs;
            },
            (error) => {
                this.message.error(error)
            }
        );

        this.approvalService.GroupSubscriptionTasksProvider.subscribe(
            (subs: ApplicationTaskResult) => {
                this.allSubscriptions = subs;
            },
            (error) => {
                this.message.error(error)
            }
        );

        this.getData();
    }

    private getData() {
        this.approvalService.getUserAppSubscriptionTasks(this.mySubscriptionFilter);
        this.approvalService.getUserGroupAppSubscriptionTask(this.groupSubscriptionFilter);
    }

    onAssignTaskHandler(event: ApprovalEvent): void {
        this.approvalHelperService.assignApplicationTask(event.dataType.dataType, event.task.id, () => {
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
