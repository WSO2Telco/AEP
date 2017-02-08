import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {ButtonsModule, PopoverModule, DropdownModule, TooltipModule, TypeaheadModule} from 'ng2-bootstrap'

import {AppComponent} from './app.component';
import {RootLevelRoutes} from './app.routes';
import {CommonsModule} from "./commons/commons.module";
import {AppGuard, LoginGuard} from "./app.guard";
import {HeaderComponent} from "./commons/components/header/header.component";
import {HamburgerMenuComponent} from "./commons/components/hamburger-menu/hamburger-menu.component";
import {UserAvatarComponent} from "./commons/components/user-avatar/user-avatar.component";
import {MainMenuComponent} from "./commons/components/main-menu/main-menu.component";
import {BreadcrumbsComponent} from "./commons/components/breadcrumbs/breadcrumbs.component";
import {ChartsModule} from "ng2-charts";
import {DataProvidersModule} from "./data-providers/data-providers.module";
import {ToastyModule} from "ng2-toasty";
import {ApprovalRemoteDataService} from "./data-providers/approval-remote-data.service";
import {DashboardRemoteDataService} from "./data-providers/dashboard-remote-data.service";
import {ApprovalHelperService} from "./approvals/approval-helper.service";
import {SharedModule} from "./shared/shared.module";
import {ReportingRemoteDataService} from "./data-providers/reporting-remote-data.service";

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        HamburgerMenuComponent,
        UserAvatarComponent,
        MainMenuComponent,
        BreadcrumbsComponent
    ],
    imports: [
        BrowserModule,
        RootLevelRoutes,
        FormsModule,
        HttpModule,
        CommonsModule,
        ChartsModule,
        SharedModule,
        DataProvidersModule,
        ButtonsModule.forRoot(),
        PopoverModule.forRoot(),
        DropdownModule.forRoot(),
        TooltipModule.forRoot(),
        ToastyModule.forRoot(),
        TypeaheadModule.forRoot()
    ],
    providers: [
        AppGuard,
        LoginGuard,
        ApprovalRemoteDataService,
        DashboardRemoteDataService,
        ReportingRemoteDataService,
        ApprovalHelperService,
        {
            provide : 'API_CONTEXT',
            useValue : 'api'
        }

    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
