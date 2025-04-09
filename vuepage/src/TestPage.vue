<template>
  <div class="container">
    <Header/>
    <toolbar/>
    <div>
      <h1>测试页面</h1>
    </div>
  </div>


</template>

<script>
// import TaskQueueModal from './components/TaskQueue.vue'
import Header from './components/Header.vue'
import toolbar from './components/ToolBar.vue'

export default {
  components: {
    // TaskQueueModal,
    Header,
    toolbar,
  },
  data() {
    return {
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
    }
  }
}
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
