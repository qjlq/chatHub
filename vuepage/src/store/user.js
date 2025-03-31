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
      }

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