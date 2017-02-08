import {Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-approval-count',
  templateUrl: './approval-count.component.html',
  styleUrls: ['./approval-count.component.scss']
})
export class ApprovalCountComponent implements OnInit {

  @Input()
  private totalCount:number;

  @Input()
  private myCount:number;

  @Input()
  private groupCount:number;

  @Input()
  private name:string;

  @Input()
  private iconClass:string;

  constructor() { }

  ngOnInit() {
  }

}
