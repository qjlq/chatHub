<template>
  <html style="--sidebar-width: 25%;" lang="en">
      <!-- <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
        <el-menu-item index="/chatroom">ChatRoom</el-menu-item>
        <el-menu-item index="/login" @click="signOut">Sign out</el-menu-item>
        <el-menu-item>
          
        </el-menu-item>
        <el-menu-item>
        </el-menu-item>

      </el-menu> -->
    <Header></Header>
  <body>
    <div class="window">
      <div class="left-bar">
        <div class="left-bar-tittle">视频列表</div>
        <div class="left-bar-partial">
          <div v-for="fileName in this.$store.state.user.videoList" :key="fileName">
            <el-button 
            :class="['left-button' ,  { 'left-button-active': this.$store.state.user.currentVideo === fileName }]" 
            @click="getVideo(fileName)"
            text>
            {{ fileName }}
            </el-button>
          </div>
        </div>
      </div>
      <!-- <div class = "RoomName-header">
      </div> -->
      <div class="middle-container">
        <toolbar/>
        <div class="video-container">

          <!-- 视频播放器 -->
          <!-- <video-player 
            ref="videoPlayer"
            class="vjs-custom-skin"
            :options="playerOptions"
            :videoSource = "videoSource"
            @ready="onPlayerReady"
          ></video-player> -->
          <video-player 
          ref="videoPlayer"
          class="['vide-js']"
          :options="playerOptions"
          :src = "videoSource"
          :controls="videoControls"
          @ready="onPlayerReady"
          />
          <!-- 加载状态提示 -->
          <!-- <div v-if="isLoading" class="loading">加载中...</div> -->
        </div>
        <div class="left-bar" v-if="locationList">

          <div class="left-bar-partial">
            <!-- <TaskQueueModal/> -->

          </div>
        </div>
      </div>

      


    </div>

</body>
</html>
</template>

<script>
import axios from 'axios';

import Header from './components/Header.vue'
import toolbar from './components/ToolBar.vue'

import { defineComponent } from 'vue';

import { VideoPlayer } from '@videojs-player/vue';
import 'video.js/dist/video-js.css';

export default defineComponent({
  components: { 
    Header,
    VideoPlayer,
    toolbar,
    // TaskQueueModal
  },
  created(){
    this.initWebSocket();
    this.getVideoList();
  },
  data() {
    return {
      locationList:false,
      isLoading:true,
      // isReady:false,
      videoControls:false,
      // uploading:false,
      videoSource:null, //视频地址
      tokens:{
        token: localStorage.getItem("token")
      },
      playerOptions: {
        // autoplay: false,   // 是否自动播放
        autoplay: true,   // 是否自动播放
        controls: true,    // 显示控制条
        fluid: true,       // 自适应容器
        // sources: [{        // 视频源配置
        //   type: 'video/mp4',
        //   src: ''         // 动态填充视频URL
        // }],
        // sources: [],       // 视频源配置
        // poster: '',        // 可选封面图
        techOrder: ['html5'] // 强制使用HTML5播放
      }
    };
  },
  computed: {
  },
  methods: {
    initWebSocket() {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      this.cid = localStorage.getItem("cid")
      var reqUrl = "http://chatroom.qjlkalok.xyz/api/VedioWebsocket/";
      // var reqUrl = "http://gymtest.qjlkalok.xyz/api/websocket/";
      // var reqUrl = "http://127.0.0.1/api/VedioWebsocket/";
      var protacal = "wss"
      // var protacal = "ws"


      try {
          this.socket = new WebSocket(reqUrl.replace("http", protacal),localStorage.getItem("token"));
          // 0 —— “CONNECTING”：连接还未建立，
          // 1 —— “OPEN”：通信中，
          // 2 —— “CLOSING”：连接关闭中，
          // 3 —— “CLOSED”：连接已关闭。
          if (this.socket.readyState == 3){
            console.log("WebSocket 已关闭");
            localStorage.removeItem('token')
            alert('登陆超时')
            this.signOut()

          }
      } catch (error) {
          console.log(error)
      }
      this.socket.onopen = this.Onopen;
      this.socket.onerror = this.Onerror;
      this.socket.onmessage = this.receiveMsg;
      this.socket.onclose = this.Onclose;
  },
  Onopen(){
      console.log("VideoPageSocket 已打开");
  },
  sendMsg(){
      if (this.newMsg != '' && this.newMsg != null){
          // this.test.push({cid : cid++,msg: this.newMsg,left:false})
          // this.newMsg = ''
          //this.test.push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})


          //this.$store.commit("showmessage",[this.$store.state.user.currentRoom,{cid : this.cid,name : this.name ,msg: this.newMsg,left:false}])
          //this.idMapArray[this.cid] = this.idMapArray.get(this.cid).push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})
          this.test[this.idMapArray.get(this.$store.state.user.currentRoom)].push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})
          this.scollToButtom()
          var tocid = '';
          var togid = '';
          if(this.$store.state.user.IDtype == 'gid'){
              togid = this.$store.state.user.currentRoom
          }else if(this.$store.state.user.IDtype == 'cid'){
              tocid = this.$store.state.user.currentRoom
          }
          var msg = '{"cid":"' + tocid + '","gid":"' + togid + '","message":"' + this.newMsg + '"}'
          this.socket.send(msg)
          this.newMsg = ''
          this.changeTabsNumber(this.$store.state.user.currentRoom)  //增加消息数及未读消息
      
      }else{
          alert("不可发送空消息")
      }
  },
  changeTabsNumber(id){
      this.$store.commit('changeMsg', id)
  },
  
  receiveMsg(msg){
    console.log("receiveMsg"+msg)
  },
  onInput(e){
      this.newMsg = e.target.value
  },
  Onerror(){

  },
  Onclose(){
      //localStorage.removeItem('token')
      this.socket.close();
      localStorage.clear();
      
  },
  reconnect(){
      this.initWebSocket()
  },
  disconnect(){
      this.socket.close()
  },
    // // 播放器准备就绪
    getVideoList() {
      var token = localStorage.getItem("token")
      axios({
        method:"post",
        url:"/api/videos/videoList",
        params:{
          token
        },
      }).then((res2)=>{
        //还没写token过期等返回值处理逻辑
        this.$store.commit('initVideoPage', res2.data);
      });
    },
    onPlayerReady() {
      this.isLoading = false;
    },
    getVideo(fileName){
      this.$store.commit('setCurrentVideo', fileName);
      // this.isReady = true;
      // this.videoControls = true;
      if(!this.isLoading){
        this.videoSource = `/api/videos/video/${fileName}`;
        // console.log(fileName);
      }
    },
    // 退出登录
    signOut(){
      // localStorage.getItem("socket").close();
      localStorage.clear();
      this.$router.push('/login');
    },
  },

});
</script>

<style scoped>

  #app {
    /* font-family: Helvetica, Arial, sans-serif; */
    font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 5vh;
  }

  .el-menu-demo {
    padding: 0 24px;
    background: #f8f9fa;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    border-radius: 12px;
    margin: 12px;
  }

  .el-menu-item {
    height: 56px;
    font-weight: 500;
    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .el-menu-item:hover {
    background: rgba(59, 130, 246, 0.1);
    transform: translateY(-1px);
  }


  .RoomName-heard {
    max-width: calc(100% - 100px);
    overflow: hidden;
    border-radius: 12px;
    margin: 1%;
    min-width: 5vh;
    min-height: 5vh;
  }
  .middle-container {
    display: flex;
    flex-direction: column;
    height: 95vh;
    width: 100%;
    /* height: 100%; */
  }
  .video-container {
    /* max-width: 800px;
    margin: 20px auto; */
    /* flex-direction: column; */
    /* position: relative; */
    /* width: calc(100% - var(--sidebar-width));
    display: flex;
    flex-direction: column;
    height: 100%;
    position: relative; */

    background: #000;
    border-radius: 12px;
    overflow: hidden;
    margin: 1%;
    aspect-ratio: 16/9;
    max-height: 95vh;
  }

  .loading {
    text-align: center;
    padding: 20px;
    color: #666;
  }

  body {
    background-color: #fafafa;
    color: #303030;
    margin: 0;
    padding: 0;
    /* width: 100rem; */
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    touch-action: pan-x pan-y;
    /* overflow: hidden; */
    overflow-y: auto;
  }

  .window {
    border: 1px solid #dedede;
    border-radius: 16px;
    /* box-shadow: 50px 50px 100px 10px rgba(0, 0, 0, .1); */
    box-shadow: 0 12px 24px -4px rgba(0, 0, 0, 0.08);
    flex-direction: row;
    color: #303030;
    background-color: #fff;
    transition: all 0.3s ease;
    /* min-width: 600px;
    min-height: 370px;
    max-width: 1200px; */
    /* min-width: 600rem;
    min-height: 370rem;
    max-width: 1200rem; */
    min-width: 300px;
    min-height: 175px;
    /* max-width: 1200px; */
    display: flex;
    overflow: hidden;
    box-sizing: border-box;
    width: 98vw;
    height: 90vh;
    /* width: 90vmax;
    height: 90vmin; */
    /* width: 90vmin;
    height: 90vmax; */
  }


  .left-bar {
    top: 0;
    /* width: 300px; */
    width:var(--sidebar-width);
    box-sizing: border-box;
    padding: 20px;
    background-color: #e7f8ff;
    display: flex;
    flex-direction: column;
    /* box-shadow: inset -2px 0 2px 0 rgba(0, 0, 0, .05); */
    box-shadow: 0 12px 24px -4px rgba(0, 0, 0, 0.08);
    position: relative;
    /* transition: width .05s; */
    /* transition: all 0.3s ease; */
    overflow-y: auto;
  }
  .left-bar-tittle{
    font-weight: bold;
    text-align:left;

  }

  .left-bar-partial{
    overflow: auto;
    /* overflow-x: hidden; */

    height: 90vh;
    /* height: 40vh; */
    margin: 10px 0;
    border: 1px solid #dedede;
    border-radius: 20px;
    box-shadow: 50px 50px 100px 10px rgba(75, 75, 75, 0.1);
    box-sizing: border-box;
  }

  .left-button {
    font-size: large;
    background-color: #cee5fd;
    width: 100%;
    color: black;
    text-align: center;
    padding: 10px;
    margin: 1px 3px;
    border-radius: 10px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .left-button-blink {
    animation: blink-animation 1s infinite alternate;
  }
  .left-button-active{
    animation: button-active 1s infinite alternate;
  }
  @keyframes blink-animation {
    0%   {color: red; opacity: 1;}
    25%  {color: yellow; opacity: 0.5;}
    50%  {color: blue; opacity: 0.25;}
    100% {color: green; opacity: 0.7;}
  }
  @keyframes button-active {
    0%   {background-color: #858b91;}
    100% {background-color: #858b91;}
  }
</style>

