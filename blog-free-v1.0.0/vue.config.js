const path = require('path')
// eslint-disable-next-line no-unused-vars
function resolve (dir) {
  return path.join(__dirname, dir)
}
// eslint-disable-next-line no-unused-vars
const webpack = require('webpack')
module.exports = {
  chainWebpack: config => {
    config.resolve.alias
      .set('assets', resolve('src/assets'))
  },
  devServer: {
    disableHostCheck: true,
    proxy: {
      '/register': {
        target: 'http://192.168.189.130:8088/thymeleaf/register', // 目标接口域名
        changeOrigin: true, // 是否跨域
        // pathRewrite: {
        //   '^/register': '/register', // 重写接口
        // },
      },
    },
  },
  transpileDependencies: ['vuetify'],
}
