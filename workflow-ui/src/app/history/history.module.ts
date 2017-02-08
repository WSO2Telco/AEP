import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SearchPanelComponent} from './search-panel/search-panel.component';
import {SearchResultsComponent} from './search-results/search-results.component';
import {HistoryMainComponent} from './history-main/history-main.component';
import {HistoryRoutes} from "./history.routes";
import {HistoryFilterComponent} from './history-filter/history-filter.component';
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        HistoryRoutes,
        SharedModule
    ],
    declarations: [SearchPanelComponent, SearchResultsComponent, HistoryMainComponent, HistoryFilterComponent]
})
export class HistoryModule {
}
