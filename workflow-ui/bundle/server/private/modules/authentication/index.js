'use strict';

const authenticationRoutes = require('./routes.js');

exports.register = function (server, options, next) {
    server.route(authenticationRoutes);
    return next();
};

exports.register.attributes = {
    name: 'authentication',
    version: '1.0.0'
};