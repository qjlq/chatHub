<template>

  <html style="--sidebar-width: 300px;" lang="en">
    <body>
      <div class="window">
                  <div class="left-bar">
                    <button
                    v-for="tab in this.$store.state.user.tabs"
                    :key="tab.gid"
                    :class="['tab-button', { active: currentTab === tab.groupname }]"
                    @click="currentTab(tab.gid)"
                    >
                    {{ tab.groupname }}
                    </button>
                  </div>    
        <div class="chat">
          <myHeader/>
          <ChatWindow :cid="cid"/>
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
  // import { useRouter } from "vue-router";
  // const router = useRouter()
  // const cid = router.currentRoute.value.query.cid;
  import router from './router.config.js'
  //import {computed} from 'vue'
  // import { ref, provide } from "vue";
  
  // var cidMapName = ref(new Map());
  // var cidMApNameTrack = ref(0);
  // var tabs = [
  //         {gid:'',groupname:''}
  //       ]
  export default {
    // name: 'App',
    // setup() {
    //   provide("ShareDate",{cidMapName,cidMApNameTrack})
    // },
    data() {
      return {
        cid:router.currentRoute.value.query.cid,
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
      currentTab(gid){
        this.$store.commit('changeCurrentRoom',gid)
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
  //   computed: {  
  //     Obj() {  //  计算属性
  //       // try {
  //       //   if(this.$store.user.cidMapGidinfo != null)
  //       //   return 1; //  Vuex 中定义的属性
  //       //   return 0;
  //       // } catch (error) {
  //       //   console.log(error);
  //       //   return 0;
  //       // }
  //       // console.log("state")
  //       // return this.$store.state.user.GidinfoState
  //       //return this.$store.state.user.cidMapGidinfo 
  //       return this.$store.state.user.test
  //     }
  //   },
  //   watch:{
  //     Obj(newVal,oldVal) {
  //       console.log("test")

  //       console.log(newVal,oldVal)
  //       this.updatedGroup();  //   需要调用的方法
  //       this.$store.commit('updateGidState',false);
  //     }
  //   },
  }
  
</script>

<style>
#app {
  font-family: Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body{
    background-color: #fafafa;
    color: #303030;
    margin: 0;
    padding: 0;
    width: 100vw;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    touch-action: pan-x pan-y;
    overflow: hidden;
}
.window{
  border: 1px solid #dedede;
  border-radius: 20px;
  box-shadow: 50px 50px 100px 10px rgba(0, 0, 0, .1);;
  color: #303030;
  background-color: #fff;
  min-width: 600px;
  min-height: 370px;
  max-width: 1200px;
  display: flex;
  overflow: hidden;
  box-sizing: border-box;
  width: 90vw;
  height: 90vh;
}
.chat{
  width: calc(100% - var(--sidebar-width));
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
}
.left-bar{
  top: 0;
  width: 300px;;
  box-sizing: border-box;
  padding: 20px;
  background-color: #e7f8ff;
  display: flex;
  flex-direction: column;
  box-shadow: inset -2px 0 2px 0 rgba(0,0,0,.05);
  position: relative;
  transition: width .05s;
  overflow-y: auto;
}
.left-button{

}

</style>
