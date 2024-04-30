<template lang="">
    <div class="window-show" id = "window-show">
        <div v-for="tester in test"
            :key = "tester.cid"    
        class="message-body">   
            <div v-if="tester.left" class="message-container">
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

            <div v-else class="message-user">
                <div class="message-container">
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
            <button @click="disconnect" class="send-button">
                <div class="button-image">
                </div>
                <div class="button-text">offLine</div>
            </button>
            <button @click="reconnect" class="send-button">
                <div class="button-image">
                </div>
                <div class="button-text">onLine</div>
            </button>
        </div>
        <label for="chat-input" class="input-label">
            <textarea :value='newMsg' @input="onInput" name="input-area" id="chat-input" cols="30" rows="3" style="font-size: 14px;" class="input-text"
            placeholder="Enter to send, Shift + Enter to wrap, / to search prompts, : to use commands"></textarea>
            <button class="send-button" @click="sendMsg">
                <div class="button-image">
                </div>
                <div class="button-text">send</div>
            </button>
        </label>
    </div>
</template>

<script>
// import MessageBlock from './MessageBlock.vue'
//let cid = 0
// var cid = Math.round(Math.random()*100);
var scid = '1';
var togid = '';
export default {
    props:{
        cid:String
        
    },
    data() {
        return {
            newMsg:'',
            test: [
            {cid: this.cid,name: 'name',msg: 'test message',left:true}
            ],
            socket:null,
            name:null,
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
            console.log("调用了链接websock  ，用户id为   ："+this.cid)
            var reqUrl = "http://localhost:2234/websocket/" + this.cid;
            this.socket = new WebSocket(reqUrl.replace("http", "ws"));
            console.log(reqUrl.replace("http", "ws"));
            this.socket.onopen = this.Onopen;
            this.socket.onerror = this.Onerror;
            this.socket.onmessage = this.receiveMsg;
            this.socket.onclose = this.Onclose;
        },
        Onopen(){
            console.log("Socket 已打开");
        },
        sendMsg(){
            if (this.newMsg != ''){
                // this.test.push({cid : cid++,msg: this.newMsg,left:false})
                // this.newMsg = ''
                this.test.push({cid : this.cid,name : this.name ,msg: this.newMsg,left:false})
                this.scollToButtom()
                var msg = '{"cid":"' + scid + '","gid":"' + togid + '","message":"' + this.newMsg + '"}'
                this.socket.send(msg)
                this.newMsg = ''
            
            }
        },
        receiveMsg(msg){
            // this.test.push({cid : cid++,msg: 'receive',left:true})
            var message = JSON.parse(msg.data);
            console.log(msg)
            // if(message.cid == this.cid.toString()){
            //     message.cid = "系统信息"
            // }
            if(message.code == 1){
                console.log("get a message")
            }else if(message.code == 0){
                if(message.cid == this.cid.toString()){
                    this.name = message.name;
                }
                message.name = "系统信息"
            }else if(message.code == 2){
                message.name = "系统信息"
            }
            this.test.push({cid : message.cid,name : message.name, msg: message.msg,left:true});
            this.scollToButtom();
        },
        onInput(e){
            this.newMsg = e.target.value
        },
        Onerror(){

        },
        Onclose(){
            this.test.push({cid : "系统信息",msg: "你已下线",left:false})

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
        background-color: #1d93ab;
        color: #fff;
        cursor: pointer;
        margin-right: 16px;
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
        display: block;
        unicode-bidi: isolate;
        
    }
    .message-user{
        display: flex;
        flex-direction: row-reverse;
    }
</style>
