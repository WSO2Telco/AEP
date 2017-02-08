import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {DashboardRoutes} from "./dashboard.routes";
import {ApprovalSummeryComponent} from './approval-summery/approval-summery.component';
import {ApprovalCountComponent} from './approval-count/approval-count.component';
import {ApprovalHistoryGraphComponent} from './approval-history-graph/approval-history-graph.component';
import {ChartsModule} from "ng2-charts";
import {DashboardHelperService} from "./dashboard-helper.service";
import {SharedModule} from "../shared/shared.module";


@NgModule({
    imports: [
        CommonModule,
        DashboardRoutes,
        ChartsModule,
        SharedModule
    ],
    providers: [
        DashboardHelperService],
    declarations: [
        HomeComponent,
        ApprovalSummeryComponent,
        ApprovalCountComponent,
        ApprovalHistoryGraphComponent ]
})
export class DashboardModule {
}
