import {Component, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-responsive-table',
  templateUrl: './responsive-table.component.html',
  styleUrls: ['./responsive-table.component.scss']
})
export class ResponsiveTableComponent implements OnInit {

  @Input()
  private tableHeader:string;

  @Input()
  private dataSource:any[];

  @Input()
  private fieldSet:string[];

  constructor() { }

  ngOnInit() {
  }

}
