import {RouterModule} from "@angular/router";
import {UserLoginComponent} from "./user-login/user-login.component";

const routes = [
  {
    path: '',
    component: UserLoginComponent
  }
];

export const LoginRoutes =  RouterModule.forChild(routes);
