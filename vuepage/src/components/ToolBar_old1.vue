<template>
    <div class="button-group">
      <!-- <button
        v-for="(button, index) in buttons"
        :key="index"
        class="group-button"
        :disabled="button.disabled"
        @click="button.handler"
        :style="{ backgroundColor: button.color }"
      >
        {{ button.label }}
      </button> -->
      <el-button type="primary" @click="regTask">test</el-button>
      <el-button type="primary" @click="getProgress">test2</el-button>
      <el-button type="primary" @click="getVideoList">VidoeState</el-button>
      <el-button type="primary" @click="getTaskList">TaskList</el-button>

      <el-input placeholder="Please input" v-model="input"></el-input>
    </div>
  </template>
  
  <!-- <script setup>
  const props = defineProps({
    buttons: {
      type: Array,
      required: true,
      validator: (value) => {
        return value.every(item => 
          'label' in item && 
          'handler' in item &&
          typeof item.handler === 'function'
        )
      }
    }
  })
    </script> -->
<script>
  import axios from 'axios';

  export default {
    data() {
        return {
            input: '',
            // buttons : [
            //     {
            //         label: '新建',
            //         handler: () => {
            //             console.log('新建操作')
            //         },
            //         color: '#67c23a' // 可选自定义颜色
            //     },
            //     {
            //         label: '编辑',
            //         handler: () => console.log('编辑操作')
            //     },
            //     {
            //         label: '删除',
            //         handler: () => console.log('删除操作'),
            //         color: '#f56c6c'
            //     },
            //     {
            //         label: '禁用按钮',
            //         handler: () => console.log('不会触发'),
            //         disabled: true
            //     }
            // ]
        }
    },
    methods: {
        regTask() {
          // console.log(this.input)
          axios({
              method:"post",
              url:"/api/tasks/submit",
              params:{
                  fileName:this.input,
                  token:localStorage.getItem("token"),
                  type:'PROCESS_DOCUMENT'
              },

          }).then((res)=>{
              console.log(res)
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
            // this.videoLsit = res2.data;
            // console.log("get videoState: " + res2.data)
            const videoList = res2.data;
            // console.log("get video list: " + videoLsit)
            // console.log(JSON.stringify(videoList, null, 2));

            // this.$store.commit('setVideoState', videoList);
            // this.$store.commit('setVideoList');
            // this.$store.commit('setVideoMapState');

            this.$store.commit('initVideoPage', videoList);


            // this.$store.state.user.videoState.forEach(video => {
            //   console.log("name "+video.fileName);        // 正确
            //   console.log("state "+video.keypoint_task);   // 正确（与后端一致）
            //   // console.log(video.keypointTask); // 错误（驼峰命名会导致 undefined）
            // });

            this.$store.state.user.videoMapState.forEach((element,key) => {
              console.log("name "+key);        // 正确
              console.log("state "+element.keypoint_task);   // 正确（与后端一致）
              // console.log(video.keypointTask); // 错误（驼峰命名会导致 undefined）
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
            // console.log("get taskList: " + res2.data)
            this.$store.commit('initTaskPage',res2.data)
            this.$store.state.user.idMapTask.forEach((element,key) => {
              console.log("name "+key);        // 正确
              console.log("state "+JSON.stringify(element, null, 2));   // 正确（与后端一致）
              // console.log(video.keypointTask); // 错误（驼峰命名会导致 undefined）
            });
            // const taskList = res2.data;
          });
        },

    }
  }
</script>
  
  <style scoped>
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