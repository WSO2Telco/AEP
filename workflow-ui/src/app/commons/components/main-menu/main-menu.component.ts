import {Component, OnInit} from '@angular/core';
import {MenuItem} from '../../models/common-data-models';
import {AppCommonService} from "../../services/app-common.service";
import {Router, NavigationEnd} from "@angular/router";

@Component({
    selector: 'app-main-menu',
    templateUrl: './main-menu.component.html',
    styleUrls: ['./main-menu.component.scss']
})
export class MainMenuComponent implements OnInit {

    private selectedMenu: MenuItem;
    private isExpand: boolean = false;

    private menuSource: MenuItem[] = [
        {id: 1, route: '/home', name: 'Home', iconName: 'home'},
        {id: 2, route: '/approvals/applications', name: 'Approve Applications', iconName: 'apps'},
        {id: 3, route: '/approvals/subscriptions', name: 'Approve Subscriptions', iconName: 'subscriptions'},
        {id: 4, route: '/history', name: 'Approval History', iconName: 'history'}
    ];

    constructor(private _appCommonService: AppCommonService,
                private _router: Router) {
    }

    ngOnInit() {
        this._router.events.subscribe((event) => {
            if(event instanceof NavigationEnd){
                this.selectedMenu = this.menuSource.filter((menu)=>menu.route == event.url)[0];
            }
        });

        this.selectedMenu = this.menuSource[0];
        this._appCommonService.menuToggleStream.subscribe((flag) => this.isExpand = flag);
    }

    onClick(menu: any) {
        this.selectedMenu = menu;
        this._router.navigate([menu.route]);
    }
}
