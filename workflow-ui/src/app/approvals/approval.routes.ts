import {RouterModule} from "@angular/router";
import {ApplicationsComponent} from "./applications/applications.component";
import {SubscriptionsComponent} from "./subscriptions/subscriptions.component";
import {ApprovalMainComponent} from "./approval-main/approval-main.component";

const routes = [
  {
    path : '',
    component : ApprovalMainComponent
  },
  {
    path : 'applications',
    component : ApplicationsComponent
  },
  {
    path : 'subscriptions',
    component : SubscriptionsComponent
  }
];


export const ApprovalRoutes = RouterModule.forChild(routes);
