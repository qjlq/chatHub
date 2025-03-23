<template>
  <div class="video-container">
    <!-- 视频播放器 -->
    <video-player 
      ref="videoPlayer"
      class="vjs-custom-skin"
      :options="playerOptions"
      @ready="onPlayerReady"
    ></video-player>
    
    <!-- 加载状态提示 -->
    <div v-if="isLoading" class="loading">加载中...</div>
  </div>
</template>

<script>
// import axios from 'axios';
import { VideoPlayer } from 'vue-video-player';
import 'video.js/dist/video-js.css';

export default {
  components: { VideoPlayer },
  data() {
    return {
      isLoading: true,
      playerOptions: {
        autoplay: false,   // 是否自动播放
        controls: true,    // 显示控制条
        fluid: true,       // 自适应容器
        sources: [{        // 视频源配置
          type: 'video/mp4',
          src: '/api/videos/video'          // 动态填充视频URL
        }],
        // sources: [],       // 视频源配置
        poster: '',        // 可选封面图
        techOrder: ['html5'] // 强制使用HTML5播放
      }
    };
  },
  methods: {
    // // 播放器准备就绪
    onPlayerReady() {
      this.isLoading = false;
    },
    // // 获取视频数据
    // async fetchVideo() {
    //   try {
    //     // const videoName = 'sample.mp4'; // 替换为实际视频名称
    //     // console.log ()
    //     const response = await axios.get(`/api/videos/video`, {
    //       responseType: 'blob' // 重要：指定响应类型为二进制流
    //     });

    //     // 将Blob转换为可播放的URL
    //     const blob = new Blob([response.data], { type: 'video/mp4' });
    //     const videoUrl = URL.createObjectURL(blob);
        
    //     // 更新播放器源
    //     this.playerOptions.sources = videoUrl;
    //   } catch (error) {
    //     console.error('视频加载失败:', error);
    //     this.isLoading = false;
    //   }
    // }
  },
  async mounted() {
    await this.fetchVideo();
  },
  beforeUnmount() {
    // 组件销毁时释放Blob URL
    if (this.playerOptions.sources[0].src) {
      URL.revokeObjectURL(this.playerOptions.sources[0].src);
    }
  }
};
</script>

<style scoped>
.video-container {
  max-width: 800px;
  margin: 20px auto;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}
</style>