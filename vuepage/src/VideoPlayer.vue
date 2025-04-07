<template>
  <html style="--sidebar-width: 25%;" lang="en">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
        <el-menu-item index="/chatroom">ChatRoom</el-menu-item>
        <el-menu-item index="/login" @click="signOut">Sign out</el-menu-item>
        <el-menu-item>
          
        </el-menu-item>
        <!-- <el-menu-item>
        </el-menu-item> -->

      </el-menu>

  <body>
    <div class="window">
      <div class="left-bar">
        <div class="left-bar-tittle">视频列表</div>
        <div class="left-bar-partial">
          <div v-for="tab in this.videoLsit" :key="tab">
            <el-button 
            :class="['left-button' ,  { 'left-button-active': this.videoChoosed === tab }]" 
            @click="videoTab(tab)"
            text>
            {{ tab }}
            </el-button>
          </div>
        </div>
      </div>
      <div class = "RoomName-header">

      </div>
      <div class="video-container">
        <!-- 视频播放器 -->
        <!-- <video-player 
          ref="videoPlayer"
          class="vjs-custom-skin"
          :options="playerOptions"
          :src = "src"
          @ready="onPlayerReady"
        ></video-player> -->
        <video-player 
        ref="videoPlayer"
        class="['vide-js']"
        :options="playerOptions"
        :src = "src"
        :controls="videoControls"
        @ready="onPlayerReady"
        />
        <!-- 加载状态提示 -->
        <!-- <div v-if="isLoading" class="loading">加载中...</div> -->
      </div>
      <div class="left-bar" v-if="locationList">

        <div class="left-bar-partial">
          <TaskQueueModal/>

        </div>
      </div>
      


    </div>

</body>
</html>
</template>

<script>
import axios from 'axios';
import TaskQueueModal from './components/TaskQueue.vue'

import { defineComponent } from 'vue';
// import videojs from 'video.js';
import { VideoPlayer } from '@videojs-player/vue';
import 'video.js/dist/video-js.css';

export default defineComponent({
  components: { 
    VideoPlayer,
    TaskQueueModal
  },
  created(){
    this.getVideoList();
  },
  data() {
    return {
      locationList:false,
      isLoading:true,
      isReady:false,
      videoControls:false,
      // uploading:false,
      src:null,
      tokens:{
        token: localStorage.getItem("token")
      },
      // fileList:[],
      // player: null,
      videoLsit: ['bandicam 2022-03-26 19-07-15-079.mp4','bandicam 2022-03-26 19-36-15-867.mp4','脏花.mp4'],
      videoChoosed: null,

      tasks: [
        { 
          id: 1, 
          name: '用户数据导出', 
          user: '张三', 
          time: new Date('2023-05-15 09:30:00'), 
          type: 'process', 
          progress: 75 
        },
        { 
          id: 2, 
          name: '系统备份', 
          user: '李四', 
          time: '2023-05-15 10:15:00', 
          type: 'backup', 
          progress: 30 
        },
        { 
          id: 3, 
          name: '图片上传', 
          user: '王五', 
          time: new Date(), 
          type: 'upload', 
          progress: 100 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        }
      ]
    };
  },
  computed: {
    // reToken(){
    //   return {
    //     token: localStorage.getItem("token")
    //   }
    // }
  },
  methods: {
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
          this.videoLsit = res2.data;
          console.log("get video list: " + res2.data)

      });
    },
    onPlayerReady() {
      this.isLoading = false;
    },
    videoTab(tab){
      this.videoChoosed = tab;
      this.isReady = true;
      this.videoControls = true;
      if(!this.isLoading){
        this.src = `/api/videos/video/${tab}`;
        console.log(tab);
      }
    },
    // uploadRequest(params){
    //   return new Promise((resolve, reject) => {
    //     //通过 FormData 对象上传文件
    //     var formData = new FormData()
    //     formData.append(params.file)
    //     axios({url: './api/upload', method: 'post'}).Upload(formData,this.tokens).then((res) => {
    //       if (res.code === 200) {
    //         resolve(res)
    //       } else {
    //         reject(res)
    //       }
    //     }).catch((res) => {
    //       console.log(res)
    //       reject(res)
    //     })
    //   })
    // },
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
    height: 98vh;
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

