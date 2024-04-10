<template>
  <html style="--sidebar-width: 300px;" lang="en">
    <body>
      <div class="right-body">
        <myHeader/>
        

      </div>
      
    </body>
  </html>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
  

</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import myHeader from './components/myHeader.vue'

export default {
  name: 'App',
  components: {
    // HelloWorld
    myHeader
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body{
    background-color: var(--gray);
    color: var(--black);
    margin: 0;
    padding: 0;
    width: 100vw;
    /* display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    touch-action: pan-x pan-y;
    overflow: hidden; */
}
.right-body{
  display: flex;
  flex-direction: column;
  position: relative;
  height: 100%;
}

</style>
