import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ApplicationDataTableComponent} from "../commons/components/application-data-table/application-data-table.component";
import {TooltipModule, TypeaheadModule, PaginationModule} from "ng2-bootstrap";
import {FormsModule} from "@angular/forms";
import {SlimLoadingBarModule} from "ng2-slim-loading-bar";
import {ResponsiveTableComponent} from "../commons/components/responsive-table/responsive-table.component";

@NgModule({
    imports: [
        CommonModule,
        TooltipModule,
        TypeaheadModule,
        FormsModule,
        SlimLoadingBarModule.forRoot(),
        PaginationModule.forRoot()
    ],
    declarations: [
        ApplicationDataTableComponent,
        ResponsiveTableComponent],
    exports: [
        ApplicationDataTableComponent,
        SlimLoadingBarModule,
        ResponsiveTableComponent,
        FormsModule,
        TypeaheadModule,
        PaginationModule]
})
export class SharedModule {
}
