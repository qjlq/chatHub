<template>
  <div class="container">
    <el-button type="primary" @click="sseSend">发送测试事件</el-button>
    <!-- <div>
      <h1>SSE实时数据接收</h1>
      <div v-if="messages.length === 0" class="empty-message">
        暂无接收到的消息
      </div>
      <div v-else class="message-list">
        <div v-for="(msg, index) in messages" :key="index" class="message-item">
          {{ msg }}
        </div>
      </div>
    </div> -->
  </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount } from 'vue'
import axios from 'axios'

// 响应式数据
// const messages = ref([])
let eventSource = null

// SSE初始化
const initSSE = () => {
  eventSource = new EventSource('http://localhost:2234/SSEs/sse')

  eventSource.onmessage = (event) => {
    try {
      // const data = JSON.parse(event.data)
      console.log('SSE数据:', event.data)
      // messages.value = [...messages.value, data]
    } catch (e) {
      console.error('SSE数据解析失败:', e)
    }
  }

  // eventSource.addEventListener('customEvent', (event) => {
  //   messages.value = [...messages.value, `自定义事件: ${event.data}`]
  // })

  eventSource.onerror = (error) => {
    console.error('SSE连接错误:', error)
    eventSource.close()
    // 5秒后重连
    setTimeout(initSSE, 5000)
  }
}

// 组件挂载时初始化SSE
onMounted(() => {
  initSSE()
})

// 组件卸载时关闭连接
onBeforeUnmount(() => {
  if (eventSource) {
    eventSource.close()
    eventSource = null
  }
})

// 发送测试事件
const sseSend = async () => {
  try {
    const response = await axios.post('/api/SSEs/SSEsend', {
      message: '测试事件',
      timestamp: Date.now()
    })
    console.log('事件发送成功:', response.data)
    console.log('事件发送成功:', response)

  } catch (error) {
    console.error('事件发送失败:', error)
  }
}
</script>

<style scoped>
.container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.empty-message {
  color: #666;
  font-style: italic;
  padding: 20px;
  text-align: center;
}

.message-list {
  margin-top: 20px;
  border: 1px solid #eee;
  border-radius: 4px;
}

.message-item {
  padding: 10px 15px;
  border-bottom: 1px solid #eee;
  font-family: monospace;
}

.message-item:last-child {
  border-bottom: none;
}
</style>