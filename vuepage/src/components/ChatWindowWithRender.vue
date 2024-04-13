<template lang="">
    <div class="window-show" ref="msgContainer">

    </div>
    <div class = "window-input" >
        <div class = "input-bar">
            <button @click="receiveMsg">
                <div class="button-image">
                </div>
                <div class="button-text">receive</div>
            </button>
        </div>
        <label for="chat-input" class="input-label">
            <textarea name="input-area" id="chat-input" cols="30" rows="3" style="font-size: 14px;" class="input-text"></textarea>
            <button class="send-button" @click="sendMsg">
                <div class="button-image">
                </div>
                <div class="button-text">send</div>
            </button>
        </label>
    </div>
</template>

<script>
import MessageBlock from './MessageBlock.vue'
let cid = 0
export default {
    data() {
        return {
            newMsg:'',
            test: [
            {cid: cid++,msg: 'test message',state:'left'}
            ]
        }

    },
    component:{
        MessageBlock,
    },
    methods: {
        sendMsg(){
            this.test.push({cid : cid++,msg: this.newMsg,state:'right'})
            this.newMsg = ''
        },
        receiveMsg(){
            this.test.push({cid : cid++,msg: 'receive',state:'left'})
        }
    },
    mounted() {
        const MessageComponent = new Vue({
        render: h => h(MessageBlock)
        }).$mount()
        this.$refs.msgContainer.appendChild(MessageComponent.$el)
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
    }
    .window-show{
        flex: 1 1;
        overflow: auto;
        overflow-x: hidden;
        padding: 20px 20px 40px;
        position: relative;
        overscroll-behavior: none;
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
        border: none;
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
        background-color: #1d93ab;
        color: #fff;
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
</style>
