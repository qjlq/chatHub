const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
//   base: '/chat/'
})
// module.exports = {  //unplugin-element-plus
//     configureWebpack: {
//       plugins: [
//         require('unplugin-element-plus/webpack')({
//           // options
//         }),
//       ],
//     },
// };
// const proxy = require('http-proxy-middleware');

//跨域部分代码 使以api开头的为代理请求
module.exports = {
    devServer: {
        // host: 'localhost',
        // open: true, // 自动打开浏览器
        // 代理配置表，在这里可以配置特定的请求代理到对应的API接口
        // 例如将'localhost:8080/api/xxx'代理到'www.example.com/api/xxx'
        
        proxy: {
            '/api': {
                // target: 'http://localhost:2234', // 指定Spring Boot后端的地址和端口
                // target: 'http://chat.qjlkalok.xyz:2234', // 指定Spring Boot后端的地址和端口
                target: 'http://chatroom.qjlkalok.xyz:2234', // 指定Spring Boot后端的地址和端口

                //secure: false,  // 如果是https接口，需要配置这个参数
                wss: true,    //是否代理 websockets
                changeOrigin: true, // 支持跨域
                pathRewrite: {
                    '^/api': ''
                    /*pathRewrite: {'^/api': ''}表示将
                    请求路径中的'/api'替换为空字符串，这样在
                    发送请求时，不会包含'/api'。 */
                }
            }
        }
    }
};
