'use strict';
const _messages = require('./messages.js');
const _commonService = require('./common_helper_service.js');

exports.register = function(server,options,next){
    server.expose({
        messages  : _messages,
        common : _commonService
    });
    return next();
};

exports.register.attributes = {
    name  : 'commonService',
    version : '1.0.0'
};
