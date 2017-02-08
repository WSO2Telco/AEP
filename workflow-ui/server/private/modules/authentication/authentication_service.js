'use strict';

const boom = require('boom');
const Messages = require('../common/messages');
const loginWebService = require('./login_webservice');
const roleWebService = require('./role_webservice');

const validateLoginRequest = function (request) {
  let param = request.payload;
  if (!!param && param.userName && param.password) {
    return true;
  }
  return false;
};

function authenticationService() {

  /**
   * Do Login Action
   * @param request
   * @param callback
   * @private
   */
  let _doLogin = function (request, callback) {
    request.server.log('info', 'LOGIN REQUEST : ' + request.payload && JSON.stringify(request.payload));
    let loginResult;

    let onRoleSuccess = function (roleResults) {
        callback(Object.assign({},loginResult,roleResults,{userName:request.payload.userName}));
    };

    let onRoleError = function (roleError) {
      callback(roleError);
    };

    let onLoginSuccess = function (_loginResult) {
      loginResult = _loginResult;
      roleWebService.Invoke(request.payload.userName).then(onRoleSuccess,onRoleError);
    };

    let onLoginFailed = function (loginError) {
      callback(loginError);
    };

    if (validateLoginRequest(request)) {
      loginWebService.Invoke(request).then(onLoginSuccess,onLoginFailed);
    } else {
      callback(boom.badRequest(Messages['BAD_REQUEST']));
    }
  };

  /**
   * Do Logout Action
   * @param request
   * @param callback
   * @private
   */
  let _doLogout = function (request, callback) {
    request.server.log('LOGIN REQUEST ' + request.payload);
    callback(_logoutLogic(request));
  };

  return {
    doLogin: _doLogin,
    doLogout: _doLogout
  };
}

module.exports = authenticationService();
