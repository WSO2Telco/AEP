const Q = require('q');
const boom = require('boom');
const Messages = require('../common/messages');
const config = require('../../config/application_config');
const wreck = require('wreck');

function invokeApplicationRest(params) {
    let deferred = Q.defer();

    let getEndpointUrl = function (params) {
        let path = config.businessProcessEngineBaseUrl + '/runtime/tasks?';
        let isSearchForUser = false;

        if (!!params.assignee) {
            isSearchForUser = true;
            path += ('assignee=' + params.assignee);
        }

        if (!isSearchForUser && !!params.candidateGroups) {
            path += ('candidateGroups=' + params.candidateGroups);
        }

        path += ( (!!params.size) ? '&size=' + params.size : '&size=100');
        path += ( (!!params.start) ? '&start=' + params.start : '&size=0');
        path += '&order=desc';
        path +='&sort=createTime';

        if (params.processType == 'APPLICATION_CREATION') {
            path += '&processDefinitionKey=application_creation_approval_process';
        } else if (params.processType == 'SUBSCRIPTION_CREATION') {
            path += '&processDefinitionKey=subscription_approval_process';
        }
        return path;
    };

    let getRequestOptions = function () {
        return {
            rejectUnauthorized: false,
            json: true,
            headers: {
                Authorization: 'Basic ' + new Buffer(config.businessProcessEngineUserName+':'+ config.businessProcessEnginePassword).toString('base64')
            },
        };
    };

    wreck.get(getEndpointUrl(params), getRequestOptions(), (error, res, payload) => {
        if (error) {
            deferred.reject(boom.serverUnavailable(Messages['SERVER_FAILED']));
        } else {
            deferred.resolve(payload);
        }
    });

    return deferred.promise;
}

module.exports = {
    Invoke: invokeApplicationRest
};
