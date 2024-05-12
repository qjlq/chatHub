const ModuleUser = {
    state: {
        cid: "",
        username: "",
        cidMapTotallist: new Map(),
        cidMapGidinfo: new Map(),
        // GidinfoState: false,
        // test: "active",
        tabs:[
          {gid:'',groupname:''}
        ],
        chatRoom: new Map(),
        currentRoom: '',
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
      addTabs(state,[gid,groupname]){
        state.tabs.push({gid : gid, groupname : groupname})
      },
      changeCurrentRoom(state,gid){
        state.currentRoom = gid
      }

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