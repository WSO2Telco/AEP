import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ApplicationsComponent} from './applications/applications.component';
import {SubscriptionsComponent} from './subscriptions/subscriptions.component';
import {ApprovalRoutes} from "./approval.routes";
import {ApprovalMainComponent} from './approval-main/approval-main.component';
import {ApplicationDataTableComponent} from "../commons/components/application-data-table/application-data-table.component";
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        ApprovalRoutes,
        SharedModule
    ],
    declarations: [ApplicationsComponent, SubscriptionsComponent, ApprovalMainComponent]
})
export class ApprovalsModule {
}
