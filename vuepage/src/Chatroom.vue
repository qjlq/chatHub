<template>
  <html style="--sidebar-width: 300px;" lang="en">
    <body>
      <div class="window">
                  <div class="left-bar">tmp</div>
        <div class="chat">

          <myHeader/>
          <ChatWindow/>
        </div>


      </div>
      
    </body>
  </html>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->
  

</template>

<script>
  // import HelloWorld from './components/HelloWorld.vue'
  import myHeader from './components/myHeader.vue'
  import ChatWindow from './components/ChatWindow.vue'
  export default {
    name: 'App',
    components: {
      // HelloWorld
      myHeader,
      ChatWindow
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
    background-color: #fafafa;
    color: #303030;
    margin: 0;
    padding: 0;
    width: 100vw;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    touch-action: pan-x pan-y;
    overflow: hidden;
}
.window{
  border: 1px solid #dedede;
  border-radius: 20px;
  box-shadow: 50px 50px 100px 10px rgba(0, 0, 0, .1);;
  color: #303030;
  background-color: #fff;
  min-width: 600px;
  min-height: 370px;
  max-width: 1200px;
  display: flex;
  overflow: hidden;
  box-sizing: border-box;
  width: 90vw;
  height: 90vh;
}
.chat{
  width: calc(100% - var(--sidebar-width));
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
}
.left-bar{
  top: 0;
  width: 300px;;
  box-sizing: border-box;
  padding: 20px;
  background-color: #e7f8ff;
  display: flex;
  flex-direction: column;
  box-shadow: inset -2px 0 2px 0 rgba(0,0,0,.05);
  position: relative;
  transition: width .05s
}

</style>
