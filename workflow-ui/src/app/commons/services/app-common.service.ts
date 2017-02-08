import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable()
export class AppCommonService {

  menuToggleStream:Subject<boolean> = new Subject();

  constructor() { }

  updateMenuToggleStream(flag:boolean){
    this.menuToggleStream.next(flag);
  }

}
