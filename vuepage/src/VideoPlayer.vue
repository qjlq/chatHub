<template>
  <html style="--sidebar-width: 25%;" lang="en">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
        <el-menu-item index="/chatroom">ChatRoom</el-menu-item>
        <el-menu-item index="/login" @click="signOut">Sign out</el-menu-item>
        <el-menu-item>
          <el-upload
            ref="upload"
            :class ="['upload-demo',{'upload':this.uploading}]"
            action="/api/upload"
            :data= "tokens"
            :on-preview="handlePreview"
            :before-remove="beforeRemove"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            :before-upload="beforeUpload"
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-progress="handleProgress"
            >
            <!-- <el-tooltip placement="bottom" effect="light" > -->
              <!-- <div slot="content" v-html="'mp4 file only,Max size: 5GB'"></div> -->
              <el-button size="small" type="primary" :class ="[{'uploadButton':!this.uploading}]" v-if="!this.uploading">Click to upload</el-button>
            <!-- </el-tooltip> -->

          </el-upload>
        </el-menu-item>
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


    </div>

</body>
</html>
</template>

<script>
import axios from 'axios';

import { defineComponent } from 'vue';
// import videojs from 'video.js';
import { VideoPlayer } from '@videojs-player/vue';
import 'video.js/dist/video-js.css';

export default defineComponent({
  components: { VideoPlayer },
  created(){
    this.getVideoList();
  },
  data() {
    return {
      isLoading:true,
      isReady:false,
      videoControls:false,
      uploading:false,
      src:null,
      tokens:{
        token: localStorage.getItem("token")
      },
      fileList:[],
      // player: null,
      videoLsit: ['bandicam 2022-03-26 19-07-15-079.mp4','bandicam 2022-03-26 19-36-15-867.mp4','脏花.mp4'],
      videoChoosed: null,
      playerOptions: {
        // autoplay: false,   // 是否自动播放
        autoplay: true,   // 是否自动播放
        // controls: true,    // 显示控制条
        fluid: true,       // 自适应容器
        // sources: [{        // 视频源配置
        //   type: 'video/mp4',
        //   src: ''         // 动态填充视频URL
        // }],
        sources: [],       // 视频源配置
        poster: '',        // 可选封面图
        techOrder: ['html5'] // 强制使用HTML5播放
      }
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
    handleRemove(file, fileList) {
      this.fileList = fileList
      console.log("handleRemove", file, fileList);

    },
    handlePreview(file) {
      console.log("handlePreview", file.name);
    },
    beforeRemove(file) {
      return this.$confirm(`Cancel the transfert of ${ file.name } ?`);
    },
    signOut(){
      localStorage.clear();
      this.$router.push('/login');
    },
    handleSuccess(res, file, fileList) {
      console.log("success"+res+file.name+fileList);
      this.$refs.upload.clearFiles();
      this.uploading = false;
      this.videoLsit.push(file.name);
    },
    handleExceed(files, fileList){
      console.log("handle Exceed"+files.name+fileList);

    },
    handleError(error, file, fileList){
      console.log("handle Error"+error+file.name+fileList);
      // this.$refs.upload.clearFiles();
      this.uploading = false;
      if (error.code === '401') {
        this.$message.error('请先登录！');
      }else{
        alert(error);
      }
    },
    handleProgress(event, file, fileList){
      console.log("handleProgress"+event+file.name+fileList);
    },
    beforeUpload(file){
      // const suffix = file.name.split('.').pop()
      // const list = ['mp4']
      // if (list.indexOf(suffix) < 0) {
      //   this.$message.error('上传的文件只能是 mp4 格式!')
      //   return false
      // }
      // const isLt2M = file.size / 1024 / 1024 /1024 < 5;
      // if (!isLt2M) {
      //   this.$message.error('上传文件大小不能超过 5GB!')
      //   return false
      // }
      this.uploading = true;
      console.log("beforeUpload" + file.name);
      // var token = localStorage.getItem("token");
      // // console.log("token: " + token);
      // var Tokenstate = axios({
      //   method:"post",
      //   url:"/api/valid",
      //   params:{
      //     token
      //   }
      // }).then(res => {
      //   console.log(res.data);
      // })
      // if (Tokenstate != "valid"){
      //   console.log("start upload： " + file.name + "state:" +Tokenstate);
      //   return true;
      // }else{
      //   console.log("token is invalid" + "state:" +Tokenstate);
      //   this.uploading = false;
      //   return false;
      // }
      // try {
      //   const res = await axios.post("/api/valid", { params: token});
      //   return res.data === "valid"; // 正确阻断上传
      // } catch {
      //   this.uploading = false;
      //   return false;
      // }
    }

  },

});
</script>

<style scoped>

  #app {
    font-family: Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 5vh;
  }

.upload{
  margin-bottom: 80%;
  /* padding-left: 50%; */
}

.video-container {
  max-width: 800px;
  margin: 20px auto;
  /* flex-direction: column; */
  /* position: relative; */
  width: calc(100% - var(--sidebar-width));
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
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
    border-radius: 20px;
    box-shadow: 50px 50px 100px 10px rgba(0, 0, 0, .1);
    flex-direction: row;
    color: #303030;
    background-color: #fff;
    /* min-width: 600px;
    min-height: 370px;
    max-width: 1200px; */
    /* min-width: 600rem;
    min-height: 370rem;
    max-width: 1200rem; */
    min-width: 300px;
    min-height: 175px;
    max-width: 1200px;
    display: flex;
    overflow: hidden;
    box-sizing: border-box;
    width: 90vw;
    height: 90vh;
    /* width: 90vmax;
    height: 90vmin; */
    /* width: 90vmin;
    height: 90vmax; */
  }
  .uploadButton {
    margin: 10% 0;
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
    box-shadow: inset -2px 0 2px 0 rgba(0, 0, 0, .05);
    position: relative;
    transition: width .05s;
    overflow-y: auto;
  }
  .left-bar-tittle{
    font-weight: bold;
    text-align:left;

  }

  .left-bar-partial{
    overflow: auto;
    overflow-x: hidden;
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