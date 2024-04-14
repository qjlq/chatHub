<template lang="html">
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
                    <form action="" accept-charset="UTF-8" data-turbo="false" method="post">
                        <label for="login_field">Username</label>
                        <input type="text" name="login" id="login_field" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <label for="login_field">Password</label>
                        <input type="password" name="password" id="password" class="input-box" autocapitalize="off" autocorrect="off" autocomplete="username" autofocus="autofocus" required="required">
                        <button class="commit-button">
                            Sign in
                        </button>
                    </form>
                </div>
            </div>
        </main>
    </body>
</template>
<script>

export default {
    
}
</script>
<style lang="css">
    *{
        box-sizing: border-box;
    }
    body{
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Noto Sans", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji";
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
        width: 320px;
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
        margin-bottom: 16px;
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
        margin-top: 4px;
        margin-bottom: 16px;
        border-radius: 10px;
        background-color: #238636;
        color: #fff;
        cursor: pointer;
        width: 100%
    }
    
</style>