<template lang="">

    <div v-if="this.state" class="message-container">
        <div class="message-container">
            <div class="message-head">
                <div class="message-image">
                    {{this.cid}}
                </div>
            </div>
            <div class="message-text">
                <p style="font-size: 14px;" dir="auto">{{this.msg}}</p>
            </div>
        </div>
    </div>

    <div v-else class="message-user">
        <div class="message-container">
            <div class="message-head">
                <div class="message-image">
                    {{this.cid}}
                </div>
            </div>
            <div class="message-text">
                <p style="font-size: 14px;" dir="auto">{{this.msg}}</p>
            </div>
        </div>
    </div>
        
</template>
<script>
export default {
    props:{
        msg:String,
        cid:String,
        state:Boolean,
    },
    // data() {
    //     return {
    //         position:'message-container-left'
    //     }

    // },
    // watch: {
    //         state(newState) {
    //             if (newState == 'right') {
    //                 this.position = 'message-container-right'
    //             }else{
    //                 this.position = 'message-container-left'
    //             }
    //         }
    //     }
}
</script>
<style lang="css">

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