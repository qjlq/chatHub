<template lang="html">
    <div class="video-player-container">
      <!-- 播放器容器 -->
      <video-player 
        ref="videoPlayer"
        class="vjs-custom-skin"
        :options="playerOptions"
        @ready="onPlayerReady"
        @play="onPlayerPlay"
      ></video-player>
  
      <!-- 视频信息 -->
      <div class="video-info">
        <h2>{{ videoDetails.title }}</h2>
        <p>{{ videoDetails.description }}</p>
      </div>
    </div>
  </template>
  
<script type="text/javascript">

  import { videoPlayer } from 'vue-video-player'
  
  export default {
    components: { videoPlayer },
    data() {
      return {
        videoDetails: {},  // 视频详细信息
        playerOptions: {
          playbackRates: [0.5, 1.0, 1.5, 2.0], // 播放速度
          autoplay: false, // 自动播放
          muted: false,    // 静音
          loop: false,     // 循环播放
          preload: 'auto', // 预加载
          language: 'zh-CN',
          aspectRatio: '16:9',
          fluid: true,    // 流体模式
          sources: [{
            type: 'video/mp4',
            src: '' // 视频源地址
          }],
          poster: '',     // 封面图
          notSupportedMessage: '此视频暂无法播放',
          controlBar: {
            timeDivider: true,
            durationDisplay: true,
            remainingTimeDisplay: false,
            fullscreenToggle: true // 全屏按钮
          }
        }
      }
    },
    mounted() {
      this.fetchVideoData()
    },
    methods: {
      async fetchVideoData() {
        try {
        //   const videoId = this.$route.params.id
        //   const response = await this.$axios.get(`/api/videos/${videoId}/`)
          const response = await this.$axios.get(`/api/videos/video`)

          this.videoDetails = response.data
          
          // 更新播放器配置
          this.playerOptions.sources[0].src = response.data.url
          this.playerOptions.poster = response.data.thumbnail
          
          // 如果有字幕
          if(response.data.subtitles) {
            this.playerOptions.tracks = response.data.subtitles.map(sub => ({
              src: sub.url,
              kind: 'subtitles',
              srclang: sub.language,
              label: sub.label,
              default: sub.default
            }))
          }
        } catch (error) {
          console.error('获取视频数据失败:', error)
        }
      },
  
      onPlayerReady(player) {
        console.log('播放器已就绪', player)
      },
  
      onPlayerPlay(player) {
        console.log('视频开始播放', player)
      }
    }
  }
  </script>
  
  <style scoped>
  .video-player-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .video-info {
    margin-top: 20px;
    padding: 15px;
    background: #f5f5f5;
    border-radius: 8px;
  }
  
  /* 自定义播放器样式 */
  .vjs-custom-skin {
    height: 0;
    padding-top: 56.25%;
  }
  
  .vjs-custom-skin >>> .video-js {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  </style>