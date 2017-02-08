import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginRemoteDataService} from "./login_remote-data.service";
import {DashboardRemoteDataService} from "./dashboard-remote-data.service";

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [],
    providers: [LoginRemoteDataService, DashboardRemoteDataService]
})
export class DataProvidersModule {
}
