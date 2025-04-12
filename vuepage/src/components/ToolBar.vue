<template>
    <div class="button-group" v-if="this.$store.state.user.currentVideo !== null">
      <el-button 
        :type="buttonType('keypoint_task')"
        :disabled="isButtonDisabled('keypoint_task')"
        @click="regTask('keypoint_task')">test</el-button>
      <el-button 
        :type="buttonType('keypoint_task')"
        :disabled="isButtonDisabled('keypoint_task')"
        @click="getProgress">关键点检测</el-button>
      <el-button 
        :type="buttonType('recognize_task')"
        :disabled="isButtonDisabled('recognize_task')"
        @click="getVideoList">动作识别</el-button>
      <el-button
        :type="buttonType('separate_task')"
        :disabled="isButtonDisabled('separate_task')"
        @click="getVideoList">时序动作识别</el-button>

      <el-input placeholder="Please input" v-model="input"></el-input>
    </div>
  </template>
<script>
  import axios from 'axios';
  export default {
    data() {
        return {
            input: '',
        }
    },
    computed: {
      // 通用状态获取方法
      getTaskState() {
        return (taskType) => {
          const videoState = this.$store.state.user.videoMapState.get(
            this.$store.state.user.currentVideo
          );
          return videoState?.[taskType] || 'NONE'; // 默认返回'NONE'
        };
      },

      // 按钮类型映射
      buttonType() {
        return (taskType) => {
          if (this.$store.state.user.currentVideo !== null) {
            const taskState = this.getTaskState(taskType);
            const typeMap = {
              QUEUED: 'primary',
              COMPLETED: 'success',
              NONE: 'info',
              FAILED: 'danger'
            };
            return typeMap[taskState] || 'primary';
          }
          else {
            return 'info'
          }
        };
      },

      // 禁用状态判断
      isButtonDisabled() {
        return (taskType) => {
          if (this.$store.state.user.currentVideo !== null) {
            return this.getTaskState(taskType) === 'QUEUED';
          }
          else {
            return 'info'
          }
        };
      }
    },
    methods: {

      regTask(taskTypeP) {
        // console.log(this.input)
        const fileName = this.$store.state.user.currentVideo
        const taskType = taskTypeP
        axios({
            method:"post",
            url:"/api/tasks/submit",
            params:{
                fileName:fileName,
                token:localStorage.getItem("token"),
                type:taskType
            },

        }).then((res)=>{
            if(res.data === 'success'){
              // this.$store.state.user.videoMapState.get(fileName)?.[taskType] = 'QUEUED'
              const videoState = this.$store.state.user.videoMapState.get(fileName)
              this.$store.commit('changeVideoMapState',[fileName,videoState,taskType,'QUEUED'])
              this.$store.state.user.videoMapState.forEach((element,key) => {
                console.log("name "+key);        // 正确
                console.log("state "+element.keypoint_task);   // 正确（与后端一致）
              })
              console.log('submit ' + fileName + ' ' + taskType + ' success')
            }else{
              console.log('submit ' + fileName + ' ' + taskType + ' fail')
            }
        })
      },
      getProgress() {
        axios({
              method:"post",
              url:"/api/tasks/getProgress",
              params:{
                token:localStorage.getItem("token"),
              },

          }).then((res)=>{
              console.log(res)
          })
      },
      getVideoList() {
        var token = localStorage.getItem("token")
        axios({
          method:"post",
          url:"/api/videos/videoList",
          params:{
            token
          },
        }).then((res2)=>{

          const videoList = res2.data;
          this.$store.commit('initVideoPage', videoList);

          this.$store.state.user.videoMapState.forEach((element,key) => {
            console.log("name "+key);        // 正确
            console.log("state "+element.keypoint_task);   // 正确（与后端一致）
          });
        });
      },
      getTaskList() {
        var token = localStorage.getItem("token")
        axios({
          method:"post",
          url:"/api/tasks/taskList",
          params:{
            token
          },
        }).then((res2)=>{
          this.$store.commit('initTaskPage',res2.data)
          this.$store.state.user.idMapTask.forEach((element,key) => {
            console.log("name "+key);        // 正确
            console.log("state "+JSON.stringify(element, null, 2));   // 正确（与后端一致）
          });
        });
      },

    }
  }
</script>
  
  <style scoped>
  .button-group {
    display: flex;
    gap: 12px;
    padding: 12px;
    background-color: #f5f5f5;
    border-radius: 8px;
    overflow-x: auto;
  }
  .button-group {
    display: flex;
    gap: 12px; /* 按钮间距 */
    padding: 12px;
    background-color: #f5f5f5;
    border-radius: 8px;
    overflow-x: auto; /* 内容过多时允许横向滚动 */
    /* flex: 1; */
  }
  
  .group-button {
    flex: 0 0 auto;
    padding: 8px 16px;
    border: none;
    border-radius: 6px;
    background-color: #409eff;
    color: white;
    font-size: 14px;
    cursor: pointer;
    transition: 
      background-color 0.2s ease,
      transform 0.1s ease;
  }
  
  .group-button:hover {
    background-color: #66b1ff;
  }
  
  .group-button:active {
    transform: scale(0.98);
  }
  
  .group-button:disabled {
    background-color: #c0c4cc;
    cursor: not-allowed;
    opacity: 0.7;
  }
  
  /* 可选：为滚动条添加样式（如果需要） */
  .button-group::-webkit-scrollbar {
    height: 6px;
  }
  
  .button-group::-webkit-scrollbar-thumb {
    background-color: #ddd;
    border-radius: 3px;
  }
  </style>


