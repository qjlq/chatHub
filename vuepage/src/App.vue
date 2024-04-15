<template lang="">
<component :is='currentView'></component>
</template>
<script>
import Chatroom from './Chatroom.vue'
import LoginPage from './LoginPage.vue'
import NotFund from './NotFund.vue'
const routes ={
    '/':LoginPage,
    '/chat':Chatroom,
}
export default {
    data() {
        return {
            currentPath:window.location.hash
        }
    },
    computed:{
        currentView() {
            return routes[this.currentPath.slice(1) || '/'] || NotFund
        }
    },
    mounted() {
        window.addEventListener('hashchange',()=>{
            this.currentPath = window.location.hash
        })
    },
}
</script>
<style lang="">
    
</style>