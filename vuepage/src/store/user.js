const ModuleUser = {
    state: {
        cid: "",
        username: "",
        cidMapTotallist: new Map(),
        cidMapGidinfo: new Map(),
        // GidinfoState: false,
        // test: "active",
        tabs:[
          {id:'',groupname:'',type:''}
        ],
        chatRoom: new Map(),
        currentRoom: 'ALL',
        // array : [{cid: "test",name: 'name',msg: 'test message',left:true}],
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
      addTabs(state,[gid,groupname,type]){
        state.tabs.push({gid : gid, groupname : groupname,type : type})
      },
      changeCurrentRoom(state,id){
        state.currentRoom = id
      },
      changeType(state,type){
        state.type = type
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