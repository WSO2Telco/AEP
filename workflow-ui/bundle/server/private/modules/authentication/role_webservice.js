const Q = require('q');
const wreck = require('wreck');
const boom = require('boom');
const config = require('../../config/application_config');
const Messages = require('../common/messages');
const parseString = require('xml2js').parseString;

const getRolesRequest = function (username) {
  return '<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ser="http://service.ws.um.carbon.wso2.org">' +
    '<soap:Header/>' +
    '<soap:Body>' +
    '<ser:getRoleListOfUser>' +
    '<ser:userName>' + username + '</ser:userName>' +
    '</ser:getRoleListOfUser>' +
    '</soap:Body>' +
    '</soap:Envelope>';
};


function invokeRoleWebService (userName) {
  let deferred = Q.defer();

  const getRoleRequestOptions = {
    rejectUnauthorized: false,
    headers: {
      Authorization: 'Basic ' + new Buffer('admin:admin').toString('base64')
    },
    payload: getRolesRequest(userName)
  };

  wreck.post(config.authServerURL + '/RemoteUserStoreManagerService', getRoleRequestOptions, (error, res, payload) => {
    if (error) {
      deferred.reject(boom.serverUnavailable(Messages['SOAP_CALL_FAIL']));
    } else {

      parseString(payload, function (err, result) {
        if (!!err) {
          deferred.reject(boom.badImplementation(Messages['PARSER_FAILED']));
        }

        try {
          let roles = result['soapenv:Envelope']['soapenv:Body'][0]['ns:getRoleListOfUserResponse'][0]['ns:return']
          if(!!roles){
            deferred.resolve({roles:roles});
          }else{
            deferred.reject(boom.badImplementation(Messages['PARSER_FAILED']));
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
  Invoke : invokeRoleWebService
};
