const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {

    //golang backend
    app.use(
        '/go/*',
        createProxyMiddleware({
            target: 'http://localhost:8081',
            changeOrigin: true,
            pathRewrite: {
                '^/go/': '/'
            },
        })
    );

    //Java backend api
    app.use(
        '/java/*',
        createProxyMiddleware({
            target: 'http://localhost:8085',
            changeOrigin: true,
            pathRewrite: {
                '^/java/': '/'
            },
        })
    );

    //cdn backend api
    app.use(
        '/cdn/*',
        createProxyMiddleware({
            target: 'http://localhost:8082',
            changeOrigin: true,
            pathRewrite: {
                '^/cdn/': '/'
            },
        })
    );

    //nodejs backend
    app.use(
        '/node/*',
        createProxyMiddleware({
            target: 'http://localhost:8086',
            changeOrigin: true,
            pathRewrite: {
                '^/node/': '/'
            },
        })
    );
};