<template lang="">
    <div class="window-show" id = "window-show">
        <div v-for="tester in test[this.idMapArray.get(this.$store.state.user.currentRoom)]"
            :key = "tester.cid"    
        class="message-body"> 
          <!--message-body 弃用  -->
            <div v-if="tester.left && tester.msg !='' " class="message-container">
                <div class="message-container">
                    <div class="message-head">
                        <div class="message-image">
                            {{tester.name}}
                        </div>
                    </div>
                    <div class="message-text">
                        <p style="font-size: 14px; text-align:left ;" dir="auto">{{tester.msg}}</p>
                    </div>
                </div>
            </div>

            <div v-if = "!tester.left" class="message-user">
                <div :class="['message-container', { 'message-container-right': !tester.left}]">
                    <div class="message-head">
                        <div class="message-image">
                            {{tester.name}}
                        </div>
                    </div>
                    <div class="message-text">
                        <p style="font-size: 14px; text-align:left;" dir="auto">{{tester.msg}}</p>
                    </div>
                </div>
            </div>

            <!-- <p>test</p> -->

        </div>
    </div>
    <div class = "window-input" >
        <div class = "input-bar">
            <!-- <button @click="disconnect" class="send-button">
                <div class="button-image">
                </div>
                <div class="button-text">offLine</div>
            </button>
            <button @click="reconnect" class="send-button">
                <div class="button-image">
                </div>
                <div class="button-text">onLine</div>
            </button> -->
        </div>
        <label for="chat-input" class="input-label">
            <textarea :value='newMsg'  @keydown.enter = "sendMsg" @input="onInput" name="input-area" id="chat-input" cols="30" rows="3" style="font-size: 14px;" class="input-text"
            placeholder="Enter to send"></textarea>
            <button class="send-button" @click="sendMsg" >
                <div class="button-image">
                </div>
                <div class="button-text"> <svg t="1718034927159" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4386"><path d="M0 1024l106.496-474.112 588.8-36.864-588.8-39.936-106.496-473.088 1024 512z" p-id="4387" fill="#ffffff"></path></svg></div>
            </button>
        </label>
    </div>
</template>

<script>
// import MessageBlock from './MessageBlock.vue'
//let cid = 0
// var cid = Math.round(Math.random()*100);


// import { inject } from "vue";
// var {cidMapName,cidMapNameTrack} = inject("shareDate");

// var totallist = new Map();
// var gidmapginfo = new Map();

export default {
    // setup() {
    //     var {cidMapName,cidMapNameTrack} = inject("shareDate")
    // },

    props:{
        // cid : String,
        // name:String,
        // gid:String,
        // socket:socket
    },

    data() {
        return {
            cid: '',
            newMsg:'',
            test: [[{cid: this.cid,name: 'name',msg: '',left:true}]],  //对像数组的数组，存对话记录
            socket:null,
            name:null,
            idMapArray:new Map(), //绑定gid/cid 和 对象数组的数组（stats.tabs）的下标


        }

    },
    component:{
        // MessageBlock,
    },
    created() {
        this.initWebSocket();
    },
    methods: {
        initWebSocket() {
        // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
            this.cid = localStorage.getItem("cid")
 
            console.log("调用了链接websock  ，用户id为   ："+this.cid + "aaa:" + localStorage.getItem("cid"))
            //var reqUrl = "http://localhost:2234/websocket/" + this.cid;
            // var reqUrl = "http://localhost:2234/websocket/" ;  //local
            //var reqUrl = "http://chat.qjlkalok.xyz:2234/websocket/";
            // var reqUrl = "http://chatroom.qjlkalok.xyz/websocket/";
            var reqUrl = "http://chatroom.qjlkalok.xyz/api/websocket/";
            // var reqUrl = "http://gymtest.qjlkalok.xyz/api/websocket/";
            // var reqUrl = "http://127.0.0.1/api/websocket/";
            var protacal = "wss"
            // var protacal = "ws"



            try {
                this.socket = new WebSocket(reqUrl.replace("http", protacal),localStorage.getItem("token"));

                // localStorage.setItem("socket", this.socket)
                
                // this.socket = new WebSocket(reqUrl.replace("http", "wss"),localStorage.getItem("token"));

                //this.socket = new WebSocket(reqUrl.replace("http", "ws"),localStorage.getItem("token"));
                // 要获取连接状态，可以通过带有值的 socket.readyState 属性：

                // 0 —— “CONNECTING”：连接还未建立，
                // 1 —— “OPEN”：通信中，
                // 2 —— “CLOSING”：连接关闭中，
                // 3 —— “CLOSED”：连接已关闭。
                if (this.socket.readyState == 3) localStorage.removeItem('token')
            } catch (error) {
                //localStorage.removeItem('token')
                console.log(error)
            }

            console.log(reqUrl.replace("http", "ws"));
            this.socket.onopen = this.Onopen;
            this.socket.onerror = this.Onerror;
            this.socket.onmessage = this.receiveMsg;
            this.socket.onclose = this.Onclose;
            //this.$store.commit('updateChatRoom',[this.cid,test.push({cid: this.cid ,name: 'name',msg: 'test message',left:true})])
            this.$store.commit('addTabs',["ALL","ALL",'ALL',0,0])
            //console.log("room: " + this.$store.state.user.chatRoom[this.cid])
            this.idMapArray.set("ALL",0)

        },
        Onopen(){

            console.log("Socket 已打开");
            //this.$store.commit('updateChatRoom',[this.cid,test.push({cid: this.cid ,name: 'name',msg: 'test message',left:true})])
            //this.$store.commit('addTabs',[this.cid,"ALL"])
            //console.log("room: " + this.$store.state.user.chatRoom[this.cid])


        },
        sendMsg(){
            if (this.newMsg != '' && this.newMsg != null){
                // this.test.push({cid : cid++,msg: this.newMsg,left:false})
                // this.newMsg = ''
                //this.test.push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})


                //this.$store.commit("showmessage",[this.$store.state.user.currentRoom,{cid : this.cid,name : this.name ,msg: this.newMsg,left:false}])
                //this.idMapArray[this.cid] = this.idMapArray.get(this.cid).push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})
                this.test[this.idMapArray.get(this.$store.state.user.currentRoom)].push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})
                this.scollToButtom()
                var tocid = '';
                var togid = '';
                if(this.$store.state.user.IDtype == 'gid'){
                    togid = this.$store.state.user.currentRoom
                }else if(this.$store.state.user.IDtype == 'cid'){
                    tocid = this.$store.state.user.currentRoom
                }
                var msg = '{"cid":"' + tocid + '","gid":"' + togid + '","message":"' + this.newMsg + '"}'
                this.socket.send(msg)
                this.newMsg = ''
                this.changeTabsNumber(this.$store.state.user.currentRoom)  //增加消息数及未读消息
            
            }else{
                alert("不可发送空消息")
            }
        },
        changeTabsNumber(id){
            this.$store.commit('changeMsg', id)
        },
        
        receiveMsg(msg){
            // this.test.push({cid : cid++,msg: 'receive',left:true})
            var message = JSON.parse(msg.data);
            console.log(msg)
            // if(message.cid == this.cid.toString()){
            //     message.cid = "系统信息"
            // }
            var code = parseInt(message.code)
            var id = "ALL"
            if(code != 3){
                console.log("code0: " + message.code)
                console.log("store cid = " + this.$store.state.user.cid)
                console.log("cid = " + this.cid)

                
                    if(code == 1){    //普通消息
                        console.log("get a message")
                        id = "ALL"
                        if(message.gid != null && message.gid != undefined && message.gid != ''){
                            id = message.gid
                        }
                        console.log("re: " + id)
                    }else if(code == 2){  //下线消息
                        message.name = "系统信息"
                        this.$store.commit('deleteTabs',message.cid)
                        
                    }else if(code == 0){ //上线消息（在你登录后上线的）
                        console.log(message.code)
                        if(message.cid == this.cid){
                            this.name = message.name;
                        }else if(!this.idMapArray.has(message.cid)){ //设置单对单按钮
                            this.test.push([{cid: message.cid,name: message.name, msg: '',left:true}])
                            this.idMapArray.set(message.cid,this.idMapArray.size)
                        }
                        if(message.cid != this.cid){
                            this.$store.commit('addTabs',[message.cid,message.name,'cid',0,0])
                        }
                        message.name = "系统信息"
                    }else if (code == 4 && message.cid != null && message.cid != undefined && message.cid != ''){ //1 对 1聊天
                        id = message.cid 
                    }else if (code == 5){  // 群解散消息，删除群按钮
                        this.$store.commit('deleteTabs',message.gid)
                    }
                    if(message.msg != null && message.msg != undefined && message.msg != ''){ //msg不为空时才存入对象数组的数组
                        this.test[this.idMapArray.get(id)].push({cid : message.cid,name : message.name, msg: message.msg,left:true});
                        this.changeTabsNumber(id)  //增加消息数及未读消息
                    }
                
                // else if(code == 0){  //在你登录前在线的
                //         this.test.push([{cid: message.cid,name: message.name, msg: 'hi',left:true}])
                //         this.idMapArray.set(message.cid,this.idMapArray.size)
                //         this.$store.commit('addTabs',[message.cid,message.name,'cid',true])

                // }
                //this.test.push({cid : message.cid,name : message.name, msg: message.msg,left:true});
                //this.idMapArray[this.$store.state.user.currentRoom] = this.idMapArray.get(this.$store.state.user.currentRoom).push({cid : message.cid,name : message.name, msg: message.msg,left:true})
                
                //this.$store.commit("showmessage",[this.$store.state.user.currentRoom,{cid : message.cid,name : message.name, msg: message.msg,left:true}])
                
                this.scollToButtom();
            }else {  //code 为 3的情况
                //console.log("totallist: " + message.totallist)
                console.log("code3: " + code)
                for(var val in message.totallist){ //val = gid
                    // console.log(val + " "+message.totallist[val])
                     
                    //console.log("index: "+message.totallist[val][2])
                    //var list = message.totallist[val].replace("[","").replace("]","").split(",")
                    // list.forEach(function(i){
                    //     console.log(i)
                    // })

                    //totallist.set(val,message.totallist[val])
                    this.$store.commit('updatecidMapTotallist', [val,message.totallist[val].replace("[","").replace("]","").split(",")])

                }
                
                var gidinfo = JSON.parse(message.gidinfo)

                for(var val2 in gidinfo){
                    var info = {
                        groupname : gidinfo[val2].groupname,
                        cid : gidinfo[val2].cid,
                        number : gidinfo[val2].number
                    }
                    // console.log("name: "+gidinfo[val2].groupname)
                    // console.log("name: "+gidinfo[val2].cid + " : " + info.cid)
                    
                    //gidmapginfo.set(val2,info)
                    //if( !this.idMapArray.has(gidinfo[val2].cid) ){

                    // console.log("add: " + gidinfo[val2].cid )
                    // console.log("size: " + this.idMapArray.size )
                    // var index = this.idMapArray.size
                    // console.log("index: " + index )

                    this.test.push([{cid: this.cid,name: 'name',msg: '',left:true}]) //重要，第一条插入对象数组的数组中的对象的元素，必须含数组格式（创建该群的第一条空消息）
                    this.idMapArray.set(val2,this.idMapArray.size)  //gid 匹配 test[i][] i下标
                    this.$store.commit('updatecidMapGidinfo',[val2,info])
                    this.$store.commit('addTabs',[val2,info.groupname,'gid',0,0])
                    
                }

                // this.$store.commit('updateGidState',true)
                // this.$store.commit('test',"testchange")


                console.log("commit good info")
                // this.$store.state.user.tabs.forEach(function(value){
                //     console.log( value.gid + value.groupname)
                // })
                // this.$store.commit('updatecidMapTotallist',totallist)
                // this.$store.commit('updatecidMapGidinfo',gidmapginfo)

                // JSON.parse(message.totallist).forEach(element => {
                //     console.log(element)
                // });
                
                // this.$store.state.user.cidMapTotallist.forEach(function(value, key) {
                //     console.log(key, value);

                //     value.forEach(function(list){
                //         console.log(list)
                //     })
                // });
            }

        },
        onInput(e){
            this.newMsg = e.target.value
        },
        Onerror(){

        },
        Onclose(){
            //localStorage.removeItem('token')
            this.socket.close();
            this.test.push({cid : "系统信息",msg: "你已下线",left:false})
            this.$store.commit('resetTabs')
            localStorage.clear();
            
        },
        reconnect(){
            this.initWebSocket()
        },
        disconnect(){
            this.socket.close()
        },
        scollToButtom(){
            this.$nextTick(() => {
            let chatform= document.getElementById('window-show') // 获取对象
            chatform.scrollTop = chatform.scrollHeight // 滚动高度
            })
        },

    }
    
}
  
</script>

<style lang="css">
    .window-input {
        position: relative;
        width: 100%;
        padding: 10px 20px 20px;
        box-sizing: border-box;
        flex-direction: column;
        border-top: #dedede;
        box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, .05);
        /* display:block */
    }
    .window-show{
        flex: 1 1;
        overflow: auto;
        overflow-x: hidden;
        padding: 20px 20px 40px;
        position: relative;
        /* overscroll-behavior: none; */
    }
    .input-bar{
        display: flex;
        flex-wrap: wrap;
    }
    .input-label{
        cursor: text;
        display: flex;
        flex: 1 1;
        border-radius: 10px;
        border: #dedede;
    }
    .input-text{
        height: 100%;
        width: 100%;
        border-radius: 10px;
        /* border: none; */
        box-shadow: 0 -2px 5px rgba(0,0,0,.03);
        background-color: #fff;
        color: #303030;
        font-family: inherit;
        padding: 10px 90px 10px 14px;
        resize: none;
        outline: none;
        box-sizing: border-box;
        min-height: 68px;
    }
    .send-button{
        border-radius: 10px;
        border: 1px solid #dedede;
        background-color: #1d93ab;
        color: #fff;
        cursor: pointer;
        margin-right: 16px;
        /* text-align: center; */
        line-height: 50px;
        justify-content: center;
        align-items: center;
    }
    .button-image{
        width: 16px;
        height: 16px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .button-text{
        font-size: 12px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    /* .message-body{
        width: var(--bar-width);
        height: var(--bar-width);
        animation-duration: 0.3s;
        animation-timing-function: ease;
        animation-delay: 0s;
        animation-iteration-count: 1;
        animation-direction: normal;
        animation-fill-mode: none;
        animation-play-state: running;
        animation-name: chat_slide-in__nvZgA;
        animation-timeline: auto;
        animation-range-start: normal;
        animation-range-end: normal;
        max-width: 80%;
        display: flex;
    } */
    .message-container{
        max-width: 80%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }

    .message-container-right{
        /* text-align: right; */
        /* justify-content: flex-end; */
        align-items: flex-end;
        /* justify-content: right; */
    }

    .message-head{
        margin-top: 20px;
        display: flex;
        align-items: center;
    }
    .message-text{
        box-sizing: border-box;
        max-width: 100%;
        margin-top: 10px;
        border-radius: 10px;
        background-color: rgba(0, 0, 0, .05);
        padding: 10px;
        font-size: 14px;
        /* user-select: text; */
        word-break: break-word;
        border: 1px solid #dedede;
        position: relative;
        transition: all .3s ease;

    }
    .message-image{
        width: 30;
        height: 30;
        fill: none;
        /* display: block; */
        /* align-items: right; */
        /* justify-content: right; */
        unicode-bidi: isolate;
        
    }
    .message-user{
        display: flex;
        flex-direction: row-reverse;
    }
</style>
