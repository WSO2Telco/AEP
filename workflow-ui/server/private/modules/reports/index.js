'use strict';

const routes = require('./routes.js');

exports.register = function (server, options, next) {
    server.route(routes);
    return next();
};

exports.register.attributes = {
    name: 'reports',
    version: '1.0.0'
};
