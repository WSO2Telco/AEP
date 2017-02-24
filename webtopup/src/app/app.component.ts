import {Component} from '@angular/core';
import {Http, RequestOptions,Response,Headers} from "@angular/http";
import 'rxjs/Rx';
import 'rxjs/operator/map';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  private headers = new Headers({
    'Content-Type' : 'application/json',
    'Authorization' : 'Bearer 922f38fe-d4a7-3872-b995-83886ef7c00f'
  });

  private reqOptions:RequestOptions = new RequestOptions({headers : this.headers});

  constructor(private  http:Http) {

  }

  private isError:boolean = false;
  private result:any = '';

  private onTopUp(msisdn,amount) {

    let param = {
      "rechargeAmount" : amount
    };

    this.http.post('/backend/accountbalance/v1/'+msisdn,param,this.reqOptions)
      .map((res:Response)=>res.json())
      .subscribe((result)=>{
        this.isError= false;
        this.result = result;
      },(error:Response)=>{
        this.isError= true;
        this.result = error.json();
      });
  }

}
