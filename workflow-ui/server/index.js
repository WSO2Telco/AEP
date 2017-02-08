const Glue = require('glue');

const Manifest = require('./private/config/module_config.js');
const config = require('./private/config/application_config.js');

const options = {
    relativeTo: __dirname + '/private/modules'
};

Glue.compose(Manifest, options, function (err, server) {
    if (err) {
        throw err;
    }

    server.route({
        method: 'GET',
        path: config.applicationContext ? ('/' + config.applicationContext + '/{param*}') : '/{param*}',
        handler: {
            directory: {
                path: 'server/public',
                listing: false,
                index: true
            }
        }
    });

    server.start(function (err) {
        if (err) {
            throw err;
        }
        server.log('info', 'server running on  ' + server.info.uri + '/' + config.applicationContext);
    });
});
