const Q = require('q');
const boom = require('boom');
const Messages = require('../common/messages');
const config = require('../../config/application_config');
const wreck = require('wreck');

function invokeApplicationDetailRest(taskId){
  let deferred = Q.defer();

  let getEndpointUrl = function (taskId) {
     return config.businessProcessEngineBaseUrl + '/runtime/tasks/'+taskId+'/variables';
  };

  let getRequestOptions = function () {
    return {
      rejectUnauthorized: false,
      json : true,
      headers: {
          Authorization: 'Basic ' + new Buffer(config.businessProcessEngineUserName+':'+ config.businessProcessEnginePassword).toString('base64')
      },
    };
  };

  wreck.get(getEndpointUrl(taskId), getRequestOptions(), (error, res, payload) => {
    if(error){
      deferred.reject(boom.serverUnavailable(Messages['SERVER_FAILED']));
    }else{
      deferred.resolve(payload);
    }
  });
  return deferred.promise;
}

module.exports = {
  Invoke : invokeApplicationDetailRest
};
