import {RouterModule} from "@angular/router";
import {AppGuard, LoginGuard} from "./app.guard";

const routes = [
  {
    path: 'login',
    loadChildren: 'app/login/login.module#LoginModule',
    canActivate : [LoginGuard]
  },
  {
    path: 'home',
    loadChildren: 'app/dashboard/dashboard.module#DashboardModule',
    canActivate : [AppGuard]
  },
  {
    path: 'history',
    loadChildren: 'app/history/history.module#HistoryModule',
    canActivate : [AppGuard]
  },
  {
    path: 'approvals',
    loadChildren: 'app/approvals/approvals.module#ApprovalsModule',
    canActivate : [AppGuard]
  },
  {
    path : '',
    redirectTo : '/home',
    pathMatch : 'full'
  },
  {
    path : '**',
    redirectTo : '/home'
  }
];

export const RootLevelRoutes = RouterModule.forRoot(routes,{ useHash: true });
