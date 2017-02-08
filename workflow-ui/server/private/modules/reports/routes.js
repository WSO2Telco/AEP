const reportService = require('./report_service');
const config = require('../../config/application_config');

module.exports = [
    {
        method: [ 'GET', 'POST' ],
        path: '/api/reports/{serviceName*}',
        handler: {
            proxy: {
                rejectUnauthorized:false,
                uri: config.reportingUrl+'/{serviceName}'
            }
        }
    }
];
