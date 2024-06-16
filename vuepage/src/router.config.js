//import { createRouter, createWebHashHistory } from 'vue-router'
import { createRouter, createWebHistory } from 'vue-router'

import routes from './router/router.js'
import {ElNotification} from "element-plus"
// import ElNotification from "element-plus"
import 'element-plus/dist/index.css'

const router = createRouter({
// 这里使用hash模式路由
  // history: createWebHashHistory(), 
  // history: createWebHistory('/chat/'),  
  history: createWebHistory(),  
  routes,
})

//   导航守卫，前置处理
// router.beforeEach((to, from, next) => {
//   var isAuthenticated = localStorage.getItem('token')
//   // 如果路由要跳转到除了登录和注册的界面的话就判断是否已经登录，如果没有登录就强制跳到登录界面
//   if (to.path !== '/login'&& to.path !== '/signup'&&to.path !== '/notFund' && isAuthenticated == null) {
//       next({ path: '/login' })
//       ElNotification({
//           message: '请先登录！',
//           type: "warning",
//       });
//   } else next()
// })

// var isAuthenticated = null
router.beforeEach(async (to, from) => {
  from.fullPath
  // console.log("r1: " + localStorage.getItem('token') +" cid: " +localStorage.getItem('cid'))
  // 防止登录后登录
  if((to.name == 'login' || to.name == 'signup') && localStorage.getItem('token') != null && localStorage.getItem('cid') != null){
    return{name:'chatroom', params:{cid:localStorage.getItem('cid')} }
  }
  
  if (
    // 检查用户是否已登录
    localStorage.getItem('token') == null &&
    // ❗️ 避免无限重定向
    to.name !== 'login' &&
    to.name !== 'signup' &&
    to.name !== 'notFund' 

  ) {
    // console.log("r2: " + localStorage.getItem('token'))

    ElNotification({
      message: '请先登录！',
      type: "warning",
    });
    //将用户重定向到登录页面
    return { name: 'login' }
  }
  // else{
  //   // 禁止浏览器后退
  //   history.pushState(null, null, to.path);
  // }
})



export default router
