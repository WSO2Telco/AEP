'use strict';
const config = require('./application_config.js');
const goodConsole = require('good-console');

const getOptions = function (opt, addPrefix) {
    var pluginOptions = opt || {};

    if (!!addPrefix && !!config.applicationContext) {
        pluginOptions.routes = {
            "prefix": "/" + config.applicationContext
        }
    }
    return pluginOptions;
};


const goodOptions = {
    ops: {
        interval: 50000
    },
    reporters: {
        console: [
            {
                module: 'good-console',
                args: [{log: ['error', 'info', 'debug'], response: '*'}]
            },
            'stdout'
        ]
    }
};


module.exports = {
    "server": {
        "app": {
            "name": "workflow-manager"
        }
    },
    "connections": [
        {
            "port": config.serverPort,
            "labels": [
                "workflow-manager"
            ]
        }
    ],
    "registrations": [
        {
            "plugin": {
                "register": "inert"
            }
        },
        {
            "plugin": {
                "register": "h2o2"
            }
        },
        {
            "plugin": {
                "register": "good",
                "options": goodOptions
            }
        },
        {
            "plugin": {
                "register": "./authentication"
            },
            "options": getOptions({}, true)
        },
        {
            "plugin": {
                "register": "./common"
            },
            "options": getOptions({}, true)
        },
        {
            "plugin": {
                "register": "./applications"
            },
            "options": getOptions({}, true)
        },
        {
            "plugin": {
                "register": "./reports"
            },
            "options": getOptions({}, true)
        }
    ]
};
