<template>
  <html style="--sidebar-width: 25%;" lang="en">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
      <el-menu-item index="/chatroom">ChatRoom</el-menu-item>
      <el-sub-menu index="2">
        <template #title>Profile</template>
        <el-menu-item index="/login" @click="signOut">Sign out</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>Note</template>
        <el-menu-item index="/note">WebNote</el-menu-item>
        <el-menu-item index="/labOne">lab1</el-menu-item>
      </el-sub-menu>
    </el-menu>
<body>

    <div class="window">
      <div class="left-bar">
        <div class="left-bar-tittle">上线用户</div>
        <div class="left-bar-partial">
          <div v-for="tab in this.$store.state.user.tabs" :key="tab.gid">
            <el-button v-if="tab.type == 'cid' || tab.type == 'ALL'" 
            :class="['left-button', { 'left-button-active': this.$store.state.user.currentRoom == tab.gid ,'left-button-blink': tab.umsg !== 0}]" 
            @click="currentTab(tab.gid,tab.groupname,tab.type,tab.msg)"
            text
            >
            {{ tab.groupname }}
            </el-button>
          </div>
        </div>
        <div class="left-bar-tittle">聊天群组</div>
        <div class="left-bar-partial">
          <div v-for="tab in this.$store.state.user.tabs" :key="tab.gid">
            <el-button v-if="tab.type == 'gid'" 
            :class="['left-button', { 'left-button-active': this.$store.state.user.currentRoom == tab.gid ,'left-button-blink': tab.umsg !== 0}]"
            @click="currentTab(tab.gid,tab.groupname,tab.type,tab.msg)"
            text>
            {{ tab.groupname }}
            </el-button>
          </div>
        </div>
        <!-- <el-button v-for="tab in this.$store.state.user.tabs" :key="tab.gid"
        :class="['tab-button', { active: currentTab === tab.groupname }]" @click="currentTab(tab.gid,tab.type)"
        text>
        {{ tab.groupname }}
        </el-button> -->

      </div>
      <div class="chat">
        <myHeader />
        <ChatWindow ref="windowRef"/>
      </div>


    </div>

</body>

  </html>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <!-- <HelloWorld msg="Welcome to Your Vue.js App"/> -->


</template>

<script>
  // import HelloWorld from './components/HelloWorld.vue'
  import myHeader from './components/myHeader.vue'
  import ChatWindow from './components/ChatWindow.vue'
  //import {ELMenu} from 'element-plus'
  // import { useRouter } from "vue-router";
  // const router = useRouter()
  // const cid = router.currentRoute.value.query.cid;
  // import router from './router.config.js'
  //import {computed} from 'vue'
  // import { ref } from "vue";

  // var cidMapName = ref(new Map());
  // var cidMApNameTrack = ref(0);
  // var tabs = [
  //         {gid:'',groupname:''}
  //       ]
  export default {
    // name: 'App',
    // setup() {
    //   // provide("ShareDate",{cidMapName,cidMApNameTrack})
    //   const myref = ref(null);
    // },
    data() {
      return {
        // cid: router.currentRoute.value.query.cid,
        // cid: this.$store.state.user.cid
        // myref:ref(null)
        // tabs:[
        //   {gid:'',groupname:''}
        // ],
      }
    },
    components: {
      // HelloWorld
      myHeader,
      ChatWindow,
      
    },
    methods: {
      currentTab(id,name,type,msg) {
        // console.log("c: "+this.cid+"dd"+this.$store.state.user.cid+"dd:"+localStorage.getItem("cid"))
        this.$store.commit('changeCurrentRoom', [id,name,type,msg])
        //this.$store.commit('changeName', name)
      },
      signOut(){
        this.$refs.windowRef.Onclose();
        // localStorage.clear();
      },
    },
    //   methods: {
    //     // test(){
    //     //   var x = cidMApNameTrack
    //     //   console.log(x)
    //     //   cidMapName.value.forEach(function(value, key) {
    //     //       console.log(key, value);
    //     //   });
    //     //}
    //     updatedGroup(){
    //           //var gidinfo = computed(() => this.$store.user.cidMapGidinfo)
    //           console.log("#####test#####")
    //           //this.tabs.put({gid:'123',groupname:'test'})

    //           this.$store.state.user.cidMapGidinfo.forEach(function(value, key) {
    //             //this.tabs.push({ gid : key, groupname : value.groupname})
    //             console.log(value, key)
    //           });
    //           this.tabs.push({ gid : "1", groupname : "value.groupname"})
    //     }
    //   },
      // computed: {  
      //   Obj() {  //  计算属性
      //     try {
      //       if(this.$store.user.cidMapGidinfo != null)
      //       return 1; //  Vuex 中定义的属性
      //       return 0;
      //     } catch (error) {
      //       console.log(error);
      //       return 0;
      //     }
      //     console.log("state")
      //     return this.$store.state.user.GidinfoState
      //     return this.$store.state.user.cidMapGidinfo 
      //     return this.$store.state.user.test
      //   }
      // },
      // watch:{
      //   Obj(newVal,oldVal) {
      //     console.log("test")

      //     console.log(newVal,oldVal)
      //     this.updatedGroup();  //   需要调用的方法
      //     this.$store.commit('updateGidState',false);
      //   }
      // },
  }

</script>

<style>

  #app {
    font-family: Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 5vh;
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

  .chat {
    width: calc(100% - var(--sidebar-width));
    display: flex;
    flex-direction: column;
    height: 100%;
    position: relative;
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

  }

  .left-bar-partial{
    overflow: auto;
    overflow-x: hidden;
    height: 40vh;
    margin: 10px 0;
    border: 1px solid #dedede;
    border-radius: 20px;
    box-shadow: 50px 50px 100px 10px rgba(75, 75, 75, 0.1);
    box-sizing: border-box;
  }

  .left-button {
    font-size: large;
    background-color: #2c3e50;
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