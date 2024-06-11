<template lang="">
    <div>
        <div class = "chatRoomName" data-tauri-drag-region="true">
            <div class = "RoomName-header">
                <div class = "header-text">{{this.$store.state.user.groupname}}</div>
                <div class = "sub-header-text">{{this.$store.state.user.currentRoomMsg}} {{subHead}}</div>
            </div>
            <div class = "window-action"> 
                <button v-on:click="test1" class="button" @click="openDialog"> 
                    <svg t="1718035492516" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5419" width="16" height="16"><path d="M925.696 384q19.456 0 37.376 7.68t30.72 20.48 20.48 30.72 7.68 37.376q0 20.48-7.68 37.888t-20.48 30.208-30.72 20.48-37.376 7.68l-287.744 0 0 287.744q0 20.48-7.68 37.888t-20.48 30.208-30.72 20.48-37.376 7.68q-20.48 0-37.888-7.68t-30.208-20.48-20.48-30.208-7.68-37.888l0-287.744-287.744 0q-20.48 0-37.888-7.68t-30.208-20.48-20.48-30.208-7.68-37.888q0-19.456 7.68-37.376t20.48-30.72 30.208-20.48 37.888-7.68l287.744 0 0-287.744q0-19.456 7.68-37.376t20.48-30.72 30.208-20.48 37.888-7.68q39.936 0 68.096 28.16t28.16 68.096l0 287.744 287.744 0z" p-id="5420" fill="#ffffff"></path></svg>
                </button>
                <el-dialog v-model="showDialog" title="创建新群" width="500">
                    <el-form>
                        <el-form-item label="群名">
                            <el-input v-model="this.groupName" autocomplete="off" @input="onName"></el-input>
                        </el-form-item>
                        <el-form-item label="选项">
                            <el-checkbox-group v-model="selectedOptions">
                                <div v-for="tab in this.$store.state.user.tabs" :key="tab.gid" style="display: flex; align-items:flex-start; overflow: auto; overflow-x: hidden;">
                                    <el-checkbox v-if="tab.type == 'cid'" :label="tab.gid" style="margin-left: 130px;" >{{ tab.groupname }}</el-checkbox>
                                </div>
                            </el-checkbox-group>
                      </el-form-item>
                    </el-form>
                    <template #footer>
                      <el-button @click="submitAddgroup">Submit</el-button>
                      <el-button @click="closeDialog">关闭</el-button>
                    </template>
                </el-dialog>
                <button v-on:click="test2" class="button" v-if="this.$store.state.user.IDtype == 'gid'"> <div class="button-image">
                    <svg t="1718035739017" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6487" width="16" height="16"><path d="M750.933333 573.44H389.12V457.386667H750.933333L641.706667 341.333333l75.093333-81.92L955.733333 512l-238.933333 252.586667L641.706667 682.666667l13.653333-13.653334z" fill="#ffffff" p-id="6488"></path><path d="M245.76 839.68h211.626667V955.733333H136.533333V68.266667h320.853334v116.053333H245.76z" fill="#ffffff" p-id="6489"></path></svg>
                </div></button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            mainHead: '',
            subHead: 'message',
            Number: 0,


            showDialog: false,
            groupName: '',
            selectedOptions: [],
            nameRegex:/^[a-zA-Z0-9]+(?:[ ]{0,1}[a-zA-Z0-9]+){0,5}$/,
            checkname:false,
        }
    },
    methods: {
        test1() {
            this.mainHead = 'test head',
            this.Number = 66666
        },
        test2() {
            var json = '{"ccid":"' + localStorage.getItem("cid") + '","gid":"' + this.$store.state.user.currentRoom + '"}'
            axios({
                method:"post",
                url:"/api/chatcontroller/leaveGroup",
                headers: {
                    'token' : localStorage.getItem("token"),
                },
                params:{
                    json
                },
                }).then((res2)=>{
                // console.log("nc: " + res2.data)
                // localStorage.setItem("cid",res2.data)
                    console.log("leave group: " + this.$store.state.user.currentRoom + " state: " + res2)
                    this.$store.commit('deleteTabs',this.$store.state.user.currentRoom)
                    this.$store.commit('changeToALLroom')
                    alert('你已离开群：'+ this.$store.state.user.currentRoom );
                // console.log("a:" + this.cid)
            });
        },
        openDialog() {
            this.showDialog = true;
        },
        closeDialog() {
            this.showDialog = false;
        },
        onName(){
            if(this.nameRegex.test(this.groupName) && this.groupName.length >= 1 && this.groupName.length <=24){
                this.checkname = true
            }
            else{
                this.checkname = false
            }
        },
        submitAddgroup(){
            // if (this.checkname && this.selectedOptions.length != 0){
            //     this.selectedOptions.forEach(element => {
            //     console.log(element)
            //     });
            // }
            if (!this.checkname){
                alert('请输入群名！');
            }else if(this.selectedOptions.length === 0){
                alert('选择至少一个成员');
            }else{
                var json = '{"ccid":"' + localStorage.getItem("cid") + '","groupname":"' + this.groupName + '","number":"' + this.selectedOptions.length + '","member":"' + this.selectedOptions + '"}'
                // this.selectedOptions.forEach(element => {
                //     console.log(element)
                // });
                axios({
                    method:"post",
                    url:"/api/chatcontroller/addGroup",
                    headers: {
                        'token' : localStorage.getItem("token"),
                    },
                    params:{
                        json
                    },
                    }).then((res2)=>{
                    // console.log("nc: " + res2.data)
                    // localStorage.setItem("cid",res2.data)
                        console.log("add group: " + this.groupName  + " state: " + res2)
                        this.groupName = ''
                        this.selectedOptions = []
                        this.showDialog = false;
                        alert('创群成功');
                        // this.$store.commit('deleteTabs',this.$store.state.user.currentRoom)
                        // this.$store.commit('changeToALLroom')

                    // console.log("a:" + this.cid)
                });
            }

            
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
        margin-right: 16px;
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