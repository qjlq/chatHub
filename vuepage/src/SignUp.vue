<template lang="">
    <body>
        <main>
            <div class="main-container">
                <div class="container-image">
                    <img src=".\assets\image1.jpg" alt="imgae" width="200" height="200" style="width:100px; height:100px; border-radius:100%;" >
                </div>
                <div class="container-header">
                    tmps
                </div>
                <div class="container-input">
                    <!-- <form action="" accept-charset="UTF-8" data-turbo="false" method="post"> -->
                        <label for="login_field">email:</label>
                        <input @input="onEmail" type="text" name="login" id="login_field" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <label for="login_field">Username:</label>
                        <input @input="onName" type="text" name="password" id="password" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <label for="login_field">password:</label>
                        <input @input="onPwd" type="password" name="password" id="password" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <label for="login_field">re-password:</label>
                        <input @input="onRePwd" type="password" name="password" id="password" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <button @click="sign"   class="commit-button">
                            Sign up
                        </button>

                    <!-- </form> -->
                </div>
            </div>
        </main>
    </body>
</template>
<script>
import axios from 'axios';

import router from './router.config.js';
export default {
    data() {
        return {
            cid:'',
            username:'',
            password:'',
            repwd:'',
        }
    },
    methods: {
        onEmail(e){
            this.cid = e.target.value 
        },
        onName(e){
            this.username = e.target.value
        },
        onPwd(e){
            this.password = e.target.value
        },
        onRePwd(e){
            this.repwd = e.target.value
        },
        sign(){
            if(this.password == this.repwd){
                var json = '{"cid":"' + this.cid + '","password":"' + this.password + '","username":"' + this.username + '"}'
                axios({
                    method:"post",
                    url:"/api/login/signup",
                    params:{
                        json
                    },
                }).then((res)=>{
                    if (res.data == "y"){
                        router.push({
                            path:'/',
                        });
                    }else {
                        console.log('eeeee')
                    }
                })
            }else{
                console.log('eeeee')
            }
        }
    },
    
}
</script>
<style lang="css">
     *{
        box-sizing: border-box;
    }
    body{
        font-family: "Segoe UI", "Noto Sans", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji";
        font-size: var(0.875rem, 14px);
        line-height: 1.5;
        color: #1f2328;
        background-color: #ffffff;
    }
    div {
        display: block;
        unicode-bidi: isolate;
    }
    .main-container{
        width: 520px;
        margin: 0 auto;
        margin-top: auto;
        
    }
    .container-image{
        margin-right: auto;
        margin-left: auto;
        text-align: center;
        width: 100% ;
    }
    .container-header{
        margin-bottom: 20px;
        color:#1f2328;
        text-align: center;
        text-shadow: none;
        background-color: transparent;
        border: 0;

    }
    .container-input{
        border-top: 2px #d0d7deb3;
        border-radius: 6px;
        padding: 16px;
        font-size: 14px;
        background-color: #f6f8fa;
        border: 1px solid #d0d7deb3;
        border-top: 0;
        border-radius: 0 0 6px 6px;
        margin-top: 20px;
        box-sizing: border-box;
    }
    .input-box{
        box-shadow: inset 0 0 0 32px #ffffff;
        -webkit-text-fill-color: #1f2328;
        margin-top: 4px;
        margin-bottom: 16px;
        display: block;
        width: 100%;
        transition: 80ms cubic-bezier(0.33, 1, 0.68, 1);
        border: 1px solid #d0d7de;
        background-position: right 8px center;
        background-repeat: no-repeat;
        /* vertical-align: middle; */
        padding: 5px 12px;
        font-size: 14px;
        line-height: 20px;
    }
    .commit-button{
        padding: 5px 12px;
        margin-top: 4px;
        margin-bottom: 16px;
        border-radius: 10px;
        background-color: #238636;
        color: #fff;
        cursor: pointer;
        width: 100%
    }

</style>