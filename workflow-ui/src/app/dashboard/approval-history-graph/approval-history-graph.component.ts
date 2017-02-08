import {Component, OnInit, Input} from '@angular/core';
import {DashboardRemoteDataService} from "../../data-providers/dashboard-remote-data.service";
import {HistoryBarGraphData} from "../../commons/models/dashboard-data-models";
import {MessageService} from "../../commons/services/message.service";

@Component({
    selector: 'app-approval-history-graph',
    templateUrl: './approval-history-graph.component.html',
    styleUrls: ['./approval-history-graph.component.scss'],
})
export class ApprovalHistoryGraphComponent implements OnInit {

    public barChartOptionsSubscriptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            display: true,
            text: 'Subscription Creations'
        }
    };

    public barChartOptionsApplications: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            display: true,
            text: 'Application Creations'
        }
    };

    public chartType: string = 'bar';
    public barChartLegend: boolean = false;

    private appCreationHistoryDataSet: any[] = [{data: []}];
    private appCreationHistoryLabels: string[] = [];
    public chartColors: Array<any> = [
        {
            backgroundColor: 'rgba(53,152,220,0.5)'
        }];

    private subscriptionHistoryDataSet: any[] = [{data: []}];
    private subscriptionHistoryLabels: string[] = [];

    constructor(private dashboardService: DashboardRemoteDataService,
                private message: MessageService) {
    }

    ngOnInit() {
        this.dashboardService.getCreationHistoryGraphData('applications');
        this.dashboardService.getCreationHistoryGraphData('subscriptions');

        this.dashboardService.ApplicationCreationHistoryDataProvider.subscribe(
            (historyData: any) => {
                if (historyData && historyData.xAxisLabels) {
                    this.appCreationHistoryLabels.length = 0;
                    historyData.xAxisLabels.forEach((lbl, index) => {
                        this.appCreationHistoryLabels[index] = lbl;
                    });
                }

                if (historyData && historyData.graphData && historyData.graphData.length > 0) {
                    this.appCreationHistoryDataSet = historyData.graphData;
                }
            },
            (error) => {
                this.message.error(error);
            }
        );

        this.dashboardService.SubscriptionCreationHistoryDataProvider.subscribe(
            (historyData: any) => {
                if (historyData && historyData.xAxisLabels) {
                    this.subscriptionHistoryLabels.length = 0;
                    historyData.xAxisLabels.forEach((lbl, index) => {
                        this.subscriptionHistoryLabels[index] = lbl;
                    });
                }

                if (historyData && historyData.graphData && historyData.graphData.length > 0) {
                    this.subscriptionHistoryDataSet = historyData.graphData;
                }
            },
            (error) => {
                this.message.error(error);
            }
        );


    }

}
