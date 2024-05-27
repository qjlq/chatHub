// import { createApp } from 'vue'
// import App from './App.vue'  // 引入一个当前目录下的名字为App.vue的组件


// createApp(App).mount('#app')
import App from './App.vue' 
import { createApp } from 'vue'
// import { createStore } from 'vuex'
// 引入路由
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router.config.js'
import store from './store'


// const store = createStore({
//   modules: {
//       homeStore: homePage,
//       homePanelStore: homePanelPage,
//       messageBoardStore:messageBoardPage
//   }
// });
const app = createApp(App)
// app.use(router).mount("#app")
//router.use(ElementPlus)
app.use(ElementPlus)
app.use(router).use(store)
router.isReady().then(() => {
    app.mount("#app");
  });