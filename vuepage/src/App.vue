<template lang="">
<!-- <component :is='currentView'></component> -->
    <!-- <LoginPage/> -->
    <router-view />
</template>
<script>
    // import ChatRoom from './ChatRoom.vue'
    // import LoginPage from './LoginPage.vue'
    // export default {
    //     name: 'App',
    //     components: {
    //         LoginPage
    //     }
    // }
  
    // import NotFund from './NotFund.vue'
    // const routes ={
    //     '/':LoginPage,
    //     '/chat':ChatRoom,
    // }
    // export default {
    //     data() {
    //         return {
    //             currentPath:window.location.hash
    //         }
    //     },
    //     computed:{
    //         currentView() {
    //             return routes[this.currentPath.slice(1) || '/'] || NotFund
    //             // return routes[this.currentPath.slice(1) || '/']
    //         }
    //     },
    //     mounted() {
    //         window.addEventListener('hashchange',()=>{
    //             this.currentPath = window.location.hash
    //         })
    //     },
    // }
</script>
<style lang="">
    
</style>