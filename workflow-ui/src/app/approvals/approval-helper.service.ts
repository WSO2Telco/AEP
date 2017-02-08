import {Injectable} from '@angular/core';
import {ApprovalRemoteDataService} from "../data-providers/approval-remote-data.service";
import {MessageService} from "../commons/services/message.service";
import {
    ApproveApplicationCreationTaskParam,
    ApproveSubscriptionCreationTaskParam, ApplicationTask
} from "../commons/models/application-data-models";
import {TableDataType} from "../commons/models/common-data-models";
import {SlimLoadingBarService} from "ng2-slim-loading-bar";

@Injectable()
export class ApprovalHelperService {

    constructor(private approvalService: ApprovalRemoteDataService,
                private message: MessageService,
                private slimLoadingBarService: SlimLoadingBarService) {
    }


    assignApplicationTask(dataType: string, taskId: number,callBack):void {

        this.slimLoadingBarService.start();

        this.approvalService.assignApplicationTaskToUser(taskId).subscribe(
            () => {
                if (dataType == "APPLICATION") {
                    this.message.success(this.message.APPROVAL_MESSAGES.APPLICATION_CREATION_ASSIGN_SUCCESS);
                } else if (dataType == "SUBSCRIPTION") {
                    this.message.success(this.message.APPROVAL_MESSAGES.SUBSCRIPTION_CREATION_ASSIGN_SUCCESS);
                }
                callBack();
            },
            (error) => {
                this.message.error(error);
            },
            ()=>{
                this.slimLoadingBarService.complete();
            }
        );
    }

    approveRejectTask(dataType:TableDataType,appTask:ApplicationTask,status):void{

        this.slimLoadingBarService.start();

        let applicationActions = (status:'APPROVED' | 'REJECTED') => {

            let param: ApproveApplicationCreationTaskParam = new ApproveApplicationCreationTaskParam();
            param.taskId = appTask.id;
            param.description = appTask.toString();
            param.selectedTier = appTask.tier;
            param.status = status;
            param.user = 'admin';
            param.taskType = "application";

            this.approvalService.approveApplicationCreationTask(param).subscribe(
                () => {

                    if(status == 'APPROVED'){
                        this.message.success(this.message.APPROVAL_MESSAGES.APP_CREATION_APPROVE_SUCCESS);
                    }else{
                        this.message.info(this.message.APPROVAL_MESSAGES.APP_CREATION_REJECT_SUCCESS);
                    }

                    this.approvalService.getAllTasks();
                },
                (error) => {
                    this.message.error(error);
                },
                ()=>{
                    this.slimLoadingBarService.complete();
                }
            );
        };

        let subscriptionActions = (status:'APPROVED' | 'REJECTED') => {
            let param: ApproveSubscriptionCreationTaskParam = new ApproveSubscriptionCreationTaskParam();
            param.taskId = appTask.id;
            param.description = appTask.toString();
            param.selectedTier = appTask.tier;
            param.status = status;
            param.user = 'admin';
            param.taskType = "subscription";

            this.approvalService.approveSubscriptionCreationTask(param).subscribe(
                () => {
                    if(status == 'APPROVED'){
                        this.message.success(this.message.APPROVAL_MESSAGES.APP_SUBSCRIPTION_APPROVE_SUCCESS);
                    }else{
                        this.message.info(this.message.APPROVAL_MESSAGES.APP_SUBSCRIPTION_REJECT_SUCCESS);
                    }

                    this.approvalService.getAllTasks();
                },
                (error) => {
                    this.message.error(error);
                },
                ()=>{
                    this.slimLoadingBarService.complete();
                }
            );
        };

        let approveActions = {};
        approveActions['APPLICATION'] = applicationActions;
        approveActions['SUBSCRIPTION'] = subscriptionActions;

        approveActions[dataType.dataType] && approveActions[dataType.dataType](status);
    }

}
