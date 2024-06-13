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
                        <input @input="onEmail" type="text" name="login" id="login_field" 
                            :class="['input-box',{'invalid':!this.checkemail}]" autocapitalize="off" autocorrect="off" 
                            autocomplete="username" autofocus="autofocus" required="required">
                        <p class="reminder" v-if="!this.checkemail">please input an email</p>
                        <label for="login_field">Username:</label>
                        <input @input="onName" type="text" name="password" id="password" 
                            :class="['input-box',{'invalid':!this.checkname}]" autocapitalize="off" autocorrect="off" 
                            autocomplete="username" autofocus="autofocus" required="required">
                        <p class="reminder" v-if="!this.checkname">e.g. Yau ka lok或 kevin</p>
                        <label for="login_field">password:</label>
                        <input @input="onPwd" type="password" name="password" id="password" 
                            :class="['input-box',{'invalid':!this.checkpass}]" autocapitalize="off" autocorrect="off" 
                            autocomplete="username" autofocus="autofocus" required="required">
                        <p class="reminder" v-if="!this.checkpass">8-18个字符，可包含a-zA-Z0-9_+*-!@</p>
                        <label for="login_field">re-password:</label>
                        <input @input="onRePwd" type="password" name="password" id="password" 
                            :class="['input-box',{'invalid':!this.checkrepass}]" autocapitalize="off" autocorrect="off" 
                            autocomplete="username" autofocus="autofocus" required="required">
                        <p class="reminder" v-if="!this.checkrepass">密码不一致</p>
                        <button @click="sign"   class="commit-button">
                            Sign up
                        </button>

                    <!-- </form> -->
                </div>
                <div class="failLoginMsg" v-if="signfail">{{this.remindMsg}}</div>
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
            checkemail:false,
            checkpass:false,
            checkrepass:false,
            checkname:false,
            emailRegex: /^[a-zA-Z0-9+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/,
            passRegex:/^[a-zA-Z0-9_+*!@]{8,18}$/,
            nameRegex:/^[a-zA-Z0-9]+(?:[ ]{0,1}[a-zA-Z0-9]+){0,5}$/,
            signfail:false,
            remindMsg:''
        }
    },
    methods: {
        onEmail(e){
            this.cid = e.target.value 
            if(this.emailRegex.test(this.cid)){
                this.checkemail = true
            }
            else{
                this.checkemail = false
            }
        },
        onName(e){
            this.username = e.target.value
            if(this.nameRegex.test(this.username) && this.username.length >= 1 && this.username.length <=24){
                this.checkname = true
            }
            else{
                this.checkname = false
            }
        },
        onPwd(e){
            this.password = e.target.value
            if(this.passRegex.test(this.password) && this.password.length >= 8 && this.password.length <= 18){
                this.checkpass = true
            }
            else{
                this.checkpass = false
            }
        },
        onRePwd(e){
            this.repwd = e.target.value
            if(this.repwd == this.password && this.checkpass){
                this.checkrepass = true
            }
            else{
                this.checkrepass = false
            }
        },
        sign(){
            if(this.checkemail && this.checkpass && this.checkrepass && this.checkname){
                var json = '{"email":"' + this.cid + '","password":"' + this.password + '","username":"' + this.username + '"}'
                axios({
                    method:"post",
                    url:"/api/login/signup",
                    params:{
                        json
                    },
                }).then((res)=>{
                    if (res.data == "200"){
                        alert("注册成功")
                        router.push({
                            name:'login'
                        });
                        
                    }else {
                        console.log('sign upload error')
                        this.remindMsg = 'user exit'
                        this.signfail = true
                    }
                })
            }else{
                this.remindMsg = 'incorret input'
                this.signfail = true
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
        /* border-top: 0; */
        border-radius: 6px 6px 6px 6px;
        margin-top: 20px;
        box-sizing: border-box;
    }
    .input-box{
        box-shadow: inset 0 0 0 32px #ffffff;
        -webkit-text-fill-color: #1f2328;
        margin-top: 4px;
        margin-bottom: 1px;
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
        border-radius: 20px;
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
    .reminder{
        font-size: 12px;
        color: red;
        /* align-items: center; */
        margin-top: 1px;
        margin-bottom: 16px;
        margin-left: 5px;
    }

    .invalid {
        border: 2px solid red;
    }
    .failLoginMsg{
        padding: 1rem 1rem;
        text-align: center;
        background-color: #ffebe9;
        /* background-image: linear-gradient(var(#ffebe9), var(#ffebe9));s */
        border-color: var(#ff818266);
        margin-top: 4px;
        border-radius: 20px;
    }

</style>