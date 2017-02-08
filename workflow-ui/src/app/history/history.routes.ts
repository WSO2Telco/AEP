import {RouterModule} from "@angular/router";
import {HistoryMainComponent} from "./history-main/history-main.component";

const routes =[{
  path : '',
  component : HistoryMainComponent
}];

export const HistoryRoutes = RouterModule.forChild(routes);
