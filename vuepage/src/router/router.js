const routes = [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/chatroom',
      name: 'chatroom',
      // 懒加载路由
      component: () => import('../ChatRoom.vue'), 
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../LoginPage.vue'),  
    },
    {
        path: '/notFund',
        name: 'notFund',
        component: () => import('../NotFund.vue'),  
    }
  ]
  
  export default routes
 