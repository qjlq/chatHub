// import { createApp } from 'vue'
// import App from './App.vue'  // 引入一个当前目录下的名字为App.vue的组件


// createApp(App).mount('#app')
import App from './App.vue' 
import { createApp } from 'vue'
// 引入路由
import router from './router.config.js'

const app = createApp(App)
// app.use(router).mount("#app")
app.use(router)
router.isReady().then(() => {
    app.mount("#app");
  });