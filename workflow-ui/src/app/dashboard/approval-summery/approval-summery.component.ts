import {Component, OnInit, Input} from '@angular/core';
import {DashboardData} from "../../commons/models/dashboard-data-models";
import {Router} from "@angular/router";

@Component({
    selector: 'app-approval-summery',
    templateUrl: './approval-summery.component.html',
    styleUrls: ['./approval-summery.component.scss'],
})
export class ApprovalSummeryComponent implements OnInit {

    @Input()
    private appDetailsSummery: DashboardData;

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    onCountClick(type: string) {
        if (type === 'APPLICATIONS') {
            this.router.navigate(['/approvals/applications'])
        } else if (type === 'SUBSCRIPTIONS') {
            this.router.navigate(['/approvals/subscriptions'])
        }
    }

}
