'use strict';
const authService = require('./authentication_service');

module.exports = [
  {
    method: 'POST',
    path: '/api/authentication/login',
    handler: authService.doLogin
  },
  {
    method: 'GET',
    path: '/api/authentication/logout/{userId}',
    handler: authService.doLogout
  }
];
