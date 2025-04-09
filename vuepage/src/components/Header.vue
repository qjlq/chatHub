<template>
    <nav class="header-container">
      <!-- Logo 区域 -->
      <div class="function-container">
        <img src="..\assets\image1.jpg" alt="imgae"  class="logo-image" width="100" height="100"  >
        <el-upload
          ref="upload"
          :class ="['upload-demo',{'upload':this.uploading}]"
          :show-file-list = "listShow"
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
        <TaskQueueModal/>
      </div>
      <!-- 导航链接（桌面端） -->
      <ul class="nav-links" :class="{ 'active': isMenuOpen }">
        <li v-for="(link, index) in links" :key="index">
          <a 
            href="#" 
            class="nav-link"
            @click="handleNavClick"
          >
            {{ link.name }}
          </a>
        </li>
      </ul>
  
      <!-- 移动端汉堡菜单按钮 -->
      <div 
        class="hamburger"
        @click="toggleMenu"
        v-if="isMobile"
        :class="{ 'active': isMenuOpen }"
      >
        <span class="bar"></span>
        <span class="bar"></span>
        <span class="bar"></span>
      </div>
    </nav>
  </template>
  
  <script>

  import TaskQueueModal from './TaskQueue.vue'
  export default {
    components: { 
        TaskQueueModal
    },
    name: 'ResponsiveHeader',
    data() {
      return {
        isMenuOpen: false,
        uploading:false,
        listShow:true,
        fileList:[],      
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
        },
        // isMobile: false,
        tokens:{
          token: localStorage.getItem("token")
        },
        links: [
          { name: 'ChatRoom', path: '/chatroom' },
          { name: 'Sign Out', path: '/login' },
        ],
        screenWidth: window.innerWidth
      }
    },
    mounted() {
      // 监听窗口大小变化
      window.addEventListener('resize', this.handleResize)
    },
    beforeUnmount() {
      window.removeEventListener('resize', this.handleResize)
    },
    computed: {
      isMobile() {
        return this.screenWidth < 768
      }
    },
    methods: {
      handleResize() {
        this.screenWidth = window.innerWidth
        // 当窗口变大到桌面尺寸时自动关闭移动菜单
        if (this.screenWidth >= 768 && this.isMenuOpen) {
          this.isMenuOpen = false
        }
      },
      toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen
      },
      handleNavClick() {
        // 移动端点击链接后自动关闭菜单
        if (this.isMobile) {
          this.isMenuOpen = false
        }
      },
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
    }
  }
  </script>
  
  <style scoped>
  /* 基础布局样式 */
  .header-container {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    display: flex;
    flex: 0 0 auto;
    justify-content: space-between;
    align-items: center;
    /* padding: 1rem 5%; */
    padding: 0% 2%;
    background: rgba(255, 255, 255, 0.95);
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    height: 8vh;
    max-height: 12px;
    min-height: 10px;
  }
  .function-container{
    /* position: fixed; */

    display: flex;
    align-items: center;
    /* justify-content: space-between; */
    width: 50vw;
    /* padding: 0% 5%; */

  }
  /* Logo 响应式处理 */
  .logo-image {
    /* height: 40px; */
    /* width: auto; */
    transition: height 0.3s ease;
    width:45px; 
    height:45px; 
    border-radius:100%;
    padding: 0 3%;
    /* margin: 0% 0% 5% 5%; */
  }
  
  /* 导航链接样式 */
  .nav-links {
    display: flex;
    gap: 2rem;
    list-style: none;
    transition: all 0.3s ease;
  }
  
  .nav-link {
    color: #333;
    text-decoration: none;
    font-size: 1.1rem;
    padding: 0.5rem 1rem;
    border-radius: 4px;
    transition: all 0.2s ease;
  }
  
  .nav-link:hover {
    background: #f0f0f0;
    color: #007bff;
  }
  .upload{
    /* margin-bottom: 80%; */
    /* padding-left: 50%; */
  }
  .uploadButton {
    margin: 10% 10% 0% 0%;
    background: linear-gradient(135deg, #3b82f6, #6366f1);
    border: none;
    border-radius: 8px;
    /* padding: 0% 0% 0% 3%; */
    /* padding: 8px 16px; */
    box-shadow: 0 2px 4px rgba(59, 130, 246, 0.2);
  }
  /* 移动端响应式样式 */
  @media screen and (max-width: 768px) {
    .header-container {
      padding: 0.8rem 5%;
    }
  
    .logo-image {
      height: 35px;
    }
  
    .nav-links {
      position: fixed;
      top: 60px;
      right: -100%;
      flex-direction: column;
      background: white;
      width: 60%;
      text-align: center;
      padding: 2rem 0;
      box-shadow: -2px 0 10px rgba(0, 0, 0, 0.1);
      border-radius: 0 0 0 10px;
    }
  
    .nav-links.active {
      right: 0;
    }
  
    .hamburger {
      display: block;
      cursor: pointer;
      padding: 10px;
    }
  
    .hamburger.active .bar:nth-child(1) {
      transform: translateY(8px) rotate(45deg);
    }
  
    .hamburger.active .bar:nth-child(2) {
      opacity: 0;
    }
  
    .hamburger.active .bar:nth-child(3) {
      transform: translateY(-8px) rotate(-45deg);
    }
  }
  
  /* 汉堡菜单动画 */
  .bar {
    display: block;
    width: 25px;
    height: 3px;
    margin: 5px auto;
    background: #333;
    transition: all 0.3s ease;
  }
  
  /* 大屏幕适配 */
  @media screen and (min-width: 1200px) {
    .header-container {
      padding: 1.2rem 8%;
    }
  
    .logo-image {
      height: 45px;
    }
  
    .nav-link {
      font-size: 1.2rem;
    }
  }
  </style>