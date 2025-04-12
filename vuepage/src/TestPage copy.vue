<template>
  <div class="container">
    <el-button
    type="primary"
    @click="sseSend">SSE</el-button>
    <!-- <Header/>
    <toolbar/> -->
    <div>
      <h1>测试页面</h1>
    </div>
  </div>


</template>

<script>
// import TaskQueueModal from './components/TaskQueue.vue'
// import Header from './components/Header.vue'
// import toolbar from './components/ToolBar.vue'
// import { onMounted, onBeforeUnmount } from 'vue';
// import axios from 'axios';

// let eventSource = null;

// onMounted(() => {
//     // 连接后端SSE端点
//     eventSource = new EventSource('http://localhost:8080/SSEs/sse');

//     // 监听默认消息事件
//     eventSource.onmessage = (event) => {
//         console.log('Received data:', event.data);
//         // 更新Vue响应式状态（如data变量）
//     };

//     // 监听自定义事件（需后端指定事件名称）
//     eventSource.addEventListener('customEvent', (event) => {
//         console.log('Custom event:', event.data);
//     });

//     // 错误处理
//     eventSource.onerror = (error) => {
//         console.error('SSE Error:', error);
//         // 重连逻辑（例如：延迟5秒后重新连接）
//         eventSource.close();
//         setTimeout(() => {
//             eventSource = new EventSource('http://localhost:8080/sse');
//         }, 5000);
//     };
// });

// // 组件销毁时关闭连接
// onBeforeUnmount(() => {
//     if (eventSource) {
//         eventSource.close();
//     }
// });

// export default {
//   components: {
//     // TaskQueueModal,
//     // Header,
//     // toolbar,
//   },
//   data() {
//     return {

//     }
//   },methods: {
//     sseSend() {
//       axios({
//             method:"post",
//             url:"/api/SSE/SSEsend",

//         }).then((res)=>{
//             console.log('sse: '+res.data)
//         })
//     }
//   }
// }
</script>

<style>
.container {
  margin-top: 5%;
  /* margin-top: 8vh; */

  display: flex;
  min-height: 100vh;
  flex-direction: column;
}
</style>
