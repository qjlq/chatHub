<template>
  <div>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-else>
      <video-player ref="videoPlayer" :options="playerOptions" @ready="onPlayerReady"></video-player>
    </div>
  </div>
</template>

<script>
import { VideoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
import axios from 'axios'

export default {
  components: {
    VideoPlayer
  },
  data() {
    return {
      playerOptions: {
        autoplay: false,
        controls: true,
        sources: [],
        fluid: true // 自适应容器
      },
      error: null,
      // player: null
    }
  },
  methods: {
    // onPlayerReady(player) {
    onPlayerReady() {
      // this.player = player;
      this.fetchVideoUrl()
    },
    async fetchVideoUrl() {
      try {
        // 调用后端获取视频URL（此处直接使用已知URL，实际可能需要动态参数）
        // const url = 'http://localhost:2234/videos/video'
        const url = '/api/videos/video'

        // 验证资源是否存在（可选）
        await axios.head(url)
        this.playerOptions.sources = [{
        // this.player.src = [{

          src: url,
          type: 'video/mp4'
        }]
        // this.player.load();
        // this.$refs.videoPlayer.player.src({ src: url, type: 'video/mp4' })
      } catch (err) {
        this.error = '视频加载失败，请检查网络或文件是否存在。'
        console.error('视频加载错误:', err)
      }
    }
  }
}
</script>

<style>
.error {
  color: red;
  padding: 20px;
}
</style>