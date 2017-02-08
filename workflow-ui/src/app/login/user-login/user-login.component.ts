import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../commons/services/authentication.service";

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  userName:string;
  password:string;
  isSubmitted : boolean;
  loginError:string;

  constructor(private _authenticationService:AuthenticationService) { }

  ngOnInit() {
  }

  onLoginClick(loginForm){
      this.isSubmitted = true;
      if(loginForm.valid){
        this._authenticationService.doLogin(this.userName,this.password,(errorMsg)=> {
          this.loginError = errorMsg;
          setTimeout(()=>{this.loginError=null},5000);
        });
      }

  }

}
