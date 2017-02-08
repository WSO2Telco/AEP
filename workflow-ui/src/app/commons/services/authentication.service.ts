import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {LoginRemoteDataService} from "../../data-providers/login_remote-data.service";
import {User, LoginResponse} from "../models/common-data-models";


@Injectable()
export class AuthenticationService {

  loginUserInfo: BehaviorSubject<LoginResponse> = new BehaviorSubject(null);

  constructor(private _router: Router, private _remoteService: LoginRemoteDataService) {
    let _loginUserInfo = JSON.parse(sessionStorage.getItem('loginUserInfo'));
    this.loginUserInfo.next(_loginUserInfo);
  }

  doLogin(userName: string, password: string,callback:Function) {
    let user: User = new User();
    user.userName = userName;
    user.password = password;

    this._remoteService.login(user)
      .subscribe(
        (loginInfo: LoginResponse) => {
          this.loginUserInfo.next(loginInfo);
          sessionStorage.setItem('loginUserInfo', JSON.stringify(loginInfo));
          this._router.navigate(['home']);
        },
        (error: string) => {
          callback(error)
        }
      );
  }

  doLogout() {
    let user = JSON.parse(sessionStorage.getItem('loginUserInfo'));
    if (!!user) {
      this._remoteService.logout(user.userName);
      sessionStorage.setItem('loginUserInfo', null);
      this.loginUserInfo.next(null);
      this._router.navigate(['login']);

    } else {
      this._router.navigate(['login']);
    }

  }

  isLoggedIn() {
    let loginInfo =  this.loginUserInfo && this.loginUserInfo.getValue();
    return !!loginInfo;
  }

}
