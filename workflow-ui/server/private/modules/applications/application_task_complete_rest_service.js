const Q = require('q');
const boom = require('boom');
const Messages = require('../common/messages');
const config = require('../../config/application_config');
const wreck = require('wreck');

function invokeApplicationCompleteTask(params) {
    let deferred = Q.defer();

    let getEndpointUrl = function (params) {
        return config.businessProcessEngineBaseUrl + '/runtime/tasks/' + params.taskId;
    };

    let getPayload = function (params) {
        return {
            action: "complete",
            variables: [
                {
                    name: params.adminApprovalLevel,
                    value: params.status,
                    type: "string"
                },
                {
                    name: params.selectedTier,
                    value: params.selectedTier,
                    type: "string"
                },
                {
                    name: 'completedByUser',
                    value: params.user,
                    type: "string"
                },
                {
                    name: 'status',
                    value: params.status,
                    type: "string"
                },
                {
                    name: 'completedOn',
                    value: new Date(),
                    type: "string"
                },
                {
                    name: 'description',
                    value: params.description,
                    type: "string"
                },
                {
                    name: 'selectedTier',
                    value: params.selectedTier,
                    type: "string"
                }
            ]
        }
    };


    let getRequestOptions = function (params) {
        return {
            rejectUnauthorized: false,
            json: true,
            payload: getPayload(params),
            headers: {
                Authorization: 'Basic ' + new Buffer(config.businessProcessEngineUserName + ':' + config.businessProcessEnginePassword).toString('base64')
            },
        };
    };

    wreck.post(getEndpointUrl(params), getRequestOptions(params), (error, res, payload) => {
        if (error) {
            deferred.reject(boom.serverUnavailable(Messages['SERVER_FAILED']));
        } else {
            if(payload && payload.exception){
                deferred.reject(boom.serverUnavailable(Messages['SERVER_FAILED']));
            }else{
                deferred.resolve({
                    success: true,
                    error: null
                });
            }
        }
    });

    return deferred.promise;
}

module.exports = {
    Invoke: invokeApplicationCompleteTask
};