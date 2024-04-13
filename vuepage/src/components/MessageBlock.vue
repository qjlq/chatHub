<template lang="html">
    <div class="message-body">
        <div :class="position">
            <div class="message-head">
                <div class="message-image">
                    {{cid}}
                </div>
            </div>
            <div class="message-text">
                <p style="font-size: 14px;" dir="auto">{{msg}}</p>
            </div>
        </div>
        
    </div>
</template>
<script>
export default {
    props:{
        msg:String,
        cid:Int16Array,
        state:String,
    },
    data() {
        return {
            position:'message-container-left'
        }

    },
    watch: {
            state(newState) {
                if (newState == 'right') {
                    this.position = 'message-container-right'
                }else{
                    this.position = 'message-container-left'
                }
            }
        }
}
</script>
<style lang="css">
    .message-body{
        /* animation-duration: 0.3s;
        animation-timing-function: ease;
        animation-delay: 0s;
        animation-iteration-count: 1;
        animation-direction: normal;
        animation-fill-mode: none;
        animation-play-state: running; */
        /* animation-name: chat_slide-in__nvZgA;
        animation-timeline: auto;
        animation-range-start: normal;
        animation-range-end: normal; */
        /* max-width: 80%; */
        display: flex;
    }
    .message-container-left{
        /* max-width: 80%; */
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        float: left;
    }
    .message-container-right{
        max-width: 80%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        float: right;
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
        -webkit-user-select: text;
        -moz-user-select: text;
        user-select: text;
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
    
</style>