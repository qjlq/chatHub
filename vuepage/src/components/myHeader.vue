<template lang="">
    <div>
        <div class = "chatRoomName" data-tauri-drag-region="true">
            <div class = "RoomName-header">
                <div class = "header-text">{{mainHead}}</div>
                <div class = "sub-header-text">{{Number}} {{subHead}}</div>
            </div>
            <div class = "window-action"> 
                <button v-on:click="test1" class="button"> <div class="button-image">
                    test
                </div> </button>
                <button v-on:click="test2" class="button"> <div class="button-image">
                    test
                </div></button>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            mainHead: '',
            subHead: 'message',
            Number: 0,
        }
    },
    methods: {
        test1() {
            this.mainHead = 'test head',
            this.Number = 9999
        },
        test2() {
            this.mainHead = '',
            this.Number = 0
        }
    },
}
</script>

<style lang="css" scoped>
    div {
        display: block;
        unicode-bidi: isolate;
    }

    .chatRoomName {
        padding: 14px 20px;
        border-bottom: 1px solid rgba(0, 0, 0, .1);
        position: relative;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .RoomName-heard {
        max-width: calc(100% - 100px);
        overflow: hidden;
    }

    .header-text {
        font-size: 20px;
        font-weight: bolder;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        display: block;
        max-width: 50vw;
    }

    .sub-header-text {
        font-size: 14px;
    }

    .window-action {
        display: inline-flex;
    }
    
    .button{
        background-color: wheat;
        border-radius: 10px;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 10px;
            padding-top: 10px;
            padding-right: 10px;
            padding-bottom: 10px;
            padding-left: 10px;
        border: darkgrey;
        color: black;
        cursor: pointer;
        /* transition: all .3s ease;
            transition-behavior: normal;
            transition-duration: 0.3s;
            transition-timing-function: ease;
            transition-delay: 0s;
            transition-property: all; */
        /* overflow: hidden;
            overflow-x: hidden;
            overflow-y: hidden;
        user-select: none;
        outline: none; */
    }

    .button-image{
        width: 16px;
        height: 16px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    /* .home_container__4PEJZ {
    border: var(--border-in-light);
    border-radius: 20px;
    box-shadow: var(--shadow);
    color: var(--black);
    background-color: var(--white);
    min-width: 600px;
    min-height: 370px;
    max-width: 1200px;
    display: flex;
    overflow: hidden;
    box-sizing: border-box;
    width: var(--window-width);
    height: var(--window-height);
    } */
</style>