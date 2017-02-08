const wreck = require('wreck');
const parseString = require('xml2js').parseString;
const boom = require('boom');
const config = require('../../config/application_config');
const Messages = require('../common/messages');
const Q = require('q');

const getLoginRequest = function (params) {
  return '<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" x' +
    'mlns:aut="http://authentication.services.core.carbon.wso2.org"><soap:Header/><soap:Body><aut:login>' +
    '<aut:username>' + params.userName + '</aut:username>' +
    '<aut:password>' + params.password + '</aut:password>' +
    '<aut:remoteAddress>localhost</aut:remoteAddress>' +
    '</aut:login></soap:Body></soap:Envelope>';
};


const invokeLoginWebService = function (request) {
  let deferred = Q.defer();

  let authRequestOptions = {
    rejectUnauthorized: false,
    payload: getLoginRequest(request.payload)
  };

  wreck.post(config.authServerURL+'/AuthenticationAdmin', authRequestOptions, (error, res, payload) => {
    if (error) {
      deferred.reject(boom.serverUnavailable(Messages['SOAP_CALL_FAIL']));
    } else {

      parseString(payload, function (err, result) {
        if (!!err) {
          deferred.reject(boom.unauthorized(Messages['INVALID_LOGIN']));
        }

        try {
          let isSuccess = result['soapenv:Envelope']['soapenv:Body'][0]['ns:loginResponse'][0]['ns:return'][0];
          if (JSON.parse(isSuccess)) {
            deferred.resolve({isLoggedIn: true});
          } else {
            deferred.reject(boom.unauthorized(Messages['INVALID_LOGIN']));
          }
        } catch (e) {
          deferred.reject(boom.unauthorized(Messages['INVALID_LOGIN']));
        }
      });
    }
  });

  return deferred.promise;
};

module.exports = {
  Invoke  : invokeLoginWebService
};
