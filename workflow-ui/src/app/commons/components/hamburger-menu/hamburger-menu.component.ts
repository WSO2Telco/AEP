import {Component, OnInit} from '@angular/core';
import {AppCommonService} from "../../services/app-common.service";

@Component({
  selector: 'app-hamburger-menu',
  template: `
    <div class="hm-menu-container" (click)="onClick()">
      <div class="hamburger-menu" >
          <div class="bar" [ngClass]="{'animate':isClicked}"></div>	
      </div>
    </div>   
  `,
  styleUrls: ['./hamburger-menu.component.scss']
})
export class HamburgerMenuComponent implements OnInit {

  private isClicked:boolean = false;

  constructor(private _appCommonService:AppCommonService) {
  }

  ngOnInit() {
  }

  onClick(){
    this.isClicked = !this.isClicked;
    this._appCommonService.updateMenuToggleStream(this.isClicked);
  }
}
