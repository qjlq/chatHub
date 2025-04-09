
const ModuleUser = {
    state: {
        cid: "",
        username: "",
        cidMapTotallist: new Map(),  
        cidMapGidinfo: new Map(),
        // GidinfoState: false,
        // test: "active",
        tabs:[
          {gid:'',groupname:'',type:'',msg:0,umsg:0}
        ],    //存群组名/人名 type = cid/gid 
        chatRoom: new Map(),
        currentRoom: 'ALL',
        IDtype:'ALL', 
        // array : [{cid: "test",name: 'name',msg: 'test message',left:true}],
        groupname:'ALL',
        currentRoomMsg:0, //当前房间信息数量
        socket:null,

        //以下为视频相关
        videoState: [{fileName:'',keypoint_task:false,recognize_task:false,separate_task:false}], //容器
        videoList: [], //用来构建文件名列表
        videoMapState: new Map(), //用来控制按钮样式

        taskList: [{id:0,fileName:'',taskIdentifier:'',taskType:'',taskStatus:'',creator:'',createTime:'',startTime:'',endTime:''}], //容器
        // task:{id:0,fileName:'',creator:'',createTime:'',taskType:'',taskStatus:''}, //容器 用于用户在查看任务详情时有其他用户新增任务的情况
        // taskProgress: {id:'',progress:0}, //容器//在界面设计时设置progress default value为0，在后端返回taskProgress时更新
        idMapTask: new Map(), //用来构建任务列表


    },
   
    getters: {
    },
   
    mutations: { 
      updateUser(state,user){
        state.cid = user.cid;
        state.username = user.username;
      }, 
      updateCid(state, cid) {
        state.cid = cid;
      },
      updatecidMapTotallist(state,[key,value]){
        state.cidMapTotallist.set(key,value)
        //console(value)
      },
      updatecidMapGidinfo(state,[key,value]){
        state.cidMapGidinfo.set(key,value)
      },
      // updateGidState(state,value){
      //   state.GidinfoState = value
      // },
      // test(state,value){
      //   state.test = value
      // },
      addTabs(state,[gid,groupname,type,msg,umsg]){
        state.tabs.push({gid : gid, groupname : groupname,type : type,msg : msg, umsg : umsg})
      },
      deleteTabs(state,gid){ 
        var index = 0;
        for(var i in state.tabs){
          if(state.tabs[i].gid == gid){
            index = i;
            state.tabs.splice(index,1);
            break;
          }
        }
      },
      resetTabs(state){
        state.tabs = []
      },
      // changeTabColor(state,[gid,color]){
      //   state.tabs.forEach(element => {
      //     if(element.gid == gid){
      //       element.color = color
      //     }
      //   });
      // },
      changeCurrentRoom(state,[id,name,type,msg]){
        state.currentRoom = id
        state.groupname = name
        state.currentRoomMsg = msg
        state.IDtype = type
        for(var i = 0; i < state.tabs.length; i++){
          if(state.tabs[i].gid == id){
            state.tabs[i].umsg = 0
          }
        }
      },
      changeMsg(state,id){
        for(var i = 0; i < state.tabs.length; i++){
          if(state.tabs[i].gid == id){
            console.log(state.tabs[i].msg)
            state.tabs[i].msg += 1;
            console.log(state.tabs[i].msg)
              if(state.tabs[i].gid != state.currentRoom){
                state.tabs[i].umsg += 1
              }else{
                state.currentRoomMsg = state.tabs[i].msg
              }
          }
        }
      },
      changeToALLroom(state){
        state.currentRoom = state.tabs[0].gid
        state.groupname = state.tabs[0].groupname
        state.currentRoomMsg = state.tabs[0].msg
        state.IDtype = state.tabs[0].type
        state.tabs[0].umsg = 0
      },
      setVideoState(state,value){
        //要通过javaScript的自动匹配机制json来更新state.videoState，字典的关键字必须与后端的完全相同
        state.videoState = value
      },

      //以下方法需要在setVideoState之后调用
      setVideoList(state){
        state.videoState.forEach(element => {
          state.videoList.push(element.fileName)
        });
      },
      //以下方法需要在setVideoState之后调用
      setVideoMapState(state){
        state.videoState.forEach(element => {
          const tmpobj = {keypoint_task:element.keypoint_task,recognize_task:element.recognize_task,separate_task:element.separate_task}
          state.videoMapState.set(element.fileName,tmpobj)
        });
      },

      initVideoPage(state,value){
        // this.setVideoState(state,value)
        // this.setVideoList(state)
        // this.setVideoMapState(state)
        state.videoState = value
        state.videoState.forEach(element => {
          state.videoList.push(element.fileName)
          const tmpobj = {keypoint_task:element.keypoint_task,recognize_task:element.recognize_task,separate_task:element.separate_task}
          state.videoMapState.set(element.fileName,tmpobj)
        });
      },

      //任务列表相关
      setTaskList(state,value){
        state.taskList = value
      },
      //以下方法需要在setTaskList之后调用
      // setTaskProgress(state,value){
      //   state.taskProgress = value
      // },
      //以下方法需要在setTaskList之后调用
      setIdMapTask(state){
        state.taskList.forEach(element => {
          //progress初始设置为0，在后端返回taskProgress时更新
          const task = {fileName:element.fileName,creator:element.creator,createTime: element.createTime,taskType:element.taskType,taskStatus:element.taskStatus,progress:0}
          state.idMapTask.set(element.id,task)
        });
      },
      initTaskPage(state,value){
        // this.mutations.setTaskList(state,value)
        // this.mutations.setIdMapTask(state)
        state.taskList = value
        state.taskList.forEach(element => {
          //progress初始设置为0，在后端返回taskProgress时更新
          const task = {fileName:element.fileName,creator:element.creator,createTime: element.createTime,taskType:element.taskType,taskStatus:element.taskStatus,progress:0}
          state.idMapTask.set(element.id,task)
        });
      },
      addTask(state,value){
        const task = {fileName:value.fileName,creator:value.creator,createTime: value.createTime,taskType:value.taskType,taskStatus:value.taskStatus,progress:0}
        state.idMapTask.set(value.id,task)
      },
      updateProgress(state,value){
        const task = state.idMapTask.get(value.id)
        task.progress = value.progress
        state.idMapTask.set(value.id,task)
      },
      deleteTask(state,id){
        state.idMapTask.delete(id)
      },


      // updateChatRoom(state,[key,value]){
      //   state.Array.push(value)
      //   state.chatRoom.set(key,state.Array)
      //   console.log("array1: " + state.Array)
      //   state.Array.put()
      //   console.log("array2: " + state.Array)

      // },
      // showmessage(state,[id,msg]){
      //   console.log(state.chatRoom[id])
      //   // state.array = state.chatRoom.get(id)
      //   // state.array.push(msg)
      //   state.chatRoom[id] = state.chatRoom[id].push(msg)
      // }

    },
   
    actions: {
        changeCid(store,cid){
            // 通过commit触发mutation
            setTimeout(() => {
                store.commit('updateCid', cid)
            }, 1000);
        }
    },
   
    modules: {
    }

   };
   
   export default ModuleUser;