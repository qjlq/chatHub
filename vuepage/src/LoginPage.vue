<template lang="html">
    <body>
        <main>
            <div class="main-container">
                <div class="container-image">
                    <img src=".\assets\image1.jpg" alt="imgae" width="200" height="200" style="width:100px; height:100px; border-radius:100%;" >
                </div>
                <div class="container-header">
                    GymTest
                </div>
                <!-- <form novalidate> -->
                    <div class="container-input">
                        <!-- <form action="" accept-charset="UTF-8" data-turbo="false" method="post"> -->
                            <label for="login_field">Email</label>
                            <input @input="onInputid" type="email" name="cid" id="cid" 
                                    :class="['input-box',{'invalid':!this.checkemail}]" autocapitalize="off" autocorrect="off" 
                                    autocomplete="username" autofocus="autofocus" required="required">
                            <p class="reminder" v-if="!this.checkemail">please input an email</p>
                            <label for="login_field">Password</label>
                            <input @input="onInputpw" type="password" name="password" id="password"  @keydown.enter = "logins"
                                    :class="['input-box',{'invalid':!this.checkpass}]" autocapitalize="off" autocorrect="off" 
                                    autocomplete="password" autofocus="autofocus" required="required">
                            <p class="reminder" v-if="!this.checkpass">8-18个字符，可包含a-zA-Z0-9_+*-!@</p>
                            <button @click="logins"   class="commit-button">
                                Sign in
                            </button>
                            <a class="link" id="sign-up" href="/signup">sign-up</a>
                        <!-- </form> -->
                    </div>
                    <div class="failLoginMsg" v-if="failLogin">{{this.incorrectMsg}}</div>

                <!-- </form> -->
            </div>
        </main>
    </body>
</template>
<script type="text/javascript">

import axios from 'axios';
// import { useRouter } from 'vue-router'
// const router = useRouter();
// import router from './router.config.js'
// import store from './store/'
// import { useStore } from 'vuex'
// const store = useStore();

// var email = document.getElementById("email"); 
// email.addEventListener("input", function (event) {
//     if (email.validity.typeMismatch) {
//         email.setCustomValidity("please input an e-mail");
//         event
//     } else {
//         email.setCustomValidity(""); // 清除已设置的自定义错误
//     }
// });
// var email = document.getElementById("cid");
// var error = document.querySelector(".error");
export default {
    
    // setup() {
    //     //const router = useRouter();

    //     //const store = useStore();

    // },
    
    data() {
        return {
            token:'',
            cid:'',
            email:'',
            password:'',
            message:'',
            checkemail:false,
            checkpass:false,
            emailRegex: /^[a-zA-Z0-9+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/,
            passRegex:/^[a-zA-Z0-9_+*!@]{8,18}$/,
            failLogin:false,
            incorrectMsg:''
        }
    },methods: {
        onInputid(e){
            this.email = e.target.value
            if(this.emailRegex.test(this.email)){
                this.checkemail = true
            }
            else{
                this.checkemail = false
            }
            // if(email.validity.valid){
            //     console.log("y")
            //     // 如果校验通过，清除已显示的错误消息
            //     error.innerHTML = ""; // 重置消息的内容
            //     error.className = "error"; // 重置消息的显示状态

            // }
        },
        onInputpw(e){
            this.password = e.target.value
            if(this.passRegex.test(this.password) && this.password.length >= 8 && this.password.length <= 18){
                this.checkpass = true
            }
            else{
                this.checkpass = false
            }
        },
        logins(){
            // console.log("bf: " +  localStorage.getItem("token"))
            

            // if(!email.validity.valid){
            //     console.log("n")
            //     error.innerHTML = "please an e-mail";
            //     error.className = "error active";

            // }
            if(this.checkpass && this.checkemail){
                //console.log("y")
                var json = '{"email":"' + this.email + '","password":"' + this.password + '"}'
                axios({
                    method:"post",
                    url:"/api/login/logins",
                    params:{
                        json
                    },

                }).then((res)=>{
                    // console.log(res.data);
                    if (res.data != "400" && res.data !=500){
                        // console.log("AAAAAAAA");

                        localStorage.setItem("token",res.data)
                        var json2 = '{"email":"' + this.email + '"}'
                        axios({
                            method:"post",
                            url:"/api/login/getCid",
                            params:{
                                json2
                            },
                         }).then((res2)=>{
                            // console.log("nc: " + res2.data)
                            localStorage.setItem("cid",res2.data)
                            this.cid = res2.data
                            // console.log("a:" + this.cid)
                         });
                        this.$router.push({
                            // name:'chatroom',  //chatrom 原命令

                            // name:'video',
                            name:'test',
                            // path:'/chatroom',
                            //params: { cid: this.cid } 
                        });
                        console.log("test")
                        this.$store.commit('updateCid',this.cid)
                    }else if(res.data != "500"){
                        this.incorrectMsg = 'incorret password or username'
                        this.failLogin = true
                        // this.$router.push({
                        //     //name:'login'
                        //     path:'login'

                        // });
                    }else{
                        this.incorrectMsg = 'user does not exit'
                        this.failLogin = true
                    }
                })
            }
            else{
                this.incorrectMsg = 'invalid input'
                this.failLogin = true
            }
           
        },
        // login(){
        //     router.push({
        //                 path:'/signup',
        //                 //query:{cid:this.cid}
        //     })
        // },
        signUp(){
            this.$router.push({
                name:'signup'
                // path:'/signup',
            })
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
        font-size: 0.875rem, 14px;
        line-height: 1.5;
        color: #1f2328;
        background-color: #ffffff;
        display: flex;
        align-items: center;
        justify-content: center;

    }
    div {
        display: block;
        unicode-bidi: isolate;
    }
    .main-container{
        width: 320px;
        margin: 0 auto;
        margin-top: auto;
        
    }
    .container-image{
        margin-right: auto;
        margin-left: auto;
        text-align: center;
        width: 100% ;
        flex-direction: row;

    }
    .container-header{
        margin-bottom: 20px;
        color:#1f2328;
        text-align: center;
        text-shadow: none;
        background-color: transparent;
        border: 0;
        flex-direction: row;


    }
    .container-input{
        border-top: 2px #d0d7deb3;
        border-radius: 6px;
        padding: 16px;
        font-size: 14px;
        background-color: #f6f8fa;
        border: 1px solid #d0d7deb3;
        /* border-top: 0; */
        border-radius: 20px;
        margin-top: 20px;
        box-sizing: border-box;
        flex-direction: row;

    }
    .input-box{
        box-shadow: inset 0 0 0 32px #ffffff;
        -webkit-text-fill-color: #032247;
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
        border-radius: 15px;
    }
    .commit-button{
        padding: 5px 12px;
        margin-top: 4px;
        margin-bottom: 16px;
        border-radius: 10px;
        border: 1px solid #d0d7de;
        background-color: #238636;
        color: #fff;
        cursor: pointer;
        width: 100%
    }
    .link{
        align-items: center;
        padding: 5px 12px;
        font-size: 15px;
        margin-bottom: 16px;
    }

    .reminder{
        font-size: 12px;
        color: red;
        /* align-items: center; */
        margin-top: 1px;
        margin-bottom: 16px;
        margin-left: 5px;
    }
      /* input:hover, input:focus {
        background-color: #eee;
      } */

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