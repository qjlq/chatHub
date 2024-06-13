<template lang="html">
    <html lang="en-US" style="--sidebar-width: 25%;">
        <head> 
            <title>邱嘉乐2021153007</title>
            <meta http-equiv="Content-Type" content="text/html" charset="utf-8"> 
            <!-- 向浏览器声明字符为utf8，防止乱码-->
        </head>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" router>
            <el-menu-item index="/chatroom">ChatRoom</el-menu-item>
            <el-sub-menu index="3">
              <template #title>Note</template>
              <el-menu-item index="/note">WebNote</el-menu-item>
              <el-menu-item index="/labOne">lab1</el-menu-item>
            </el-sub-menu>
        </el-menu>
        <body style="background-color: powderblue;"> 
            <p style="border: 2px solid Tomato; text-align:center; font-size:60px ;"> 我的前半生 <span style="font-family: Arial, Helvetica, sans-serif; font-size: 18px;">邱嘉乐 2021153007</span></p>
            
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>0-3</b></summary>
                <div style="background-image: url('https://www.bing.com/th?id=OIP.FsZAoapn_OMtVzy-A_BTQQHaE7&w=162&h=106&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;"> 
                    <p style="font-size: 75px; text-align: center;" > 一开始啥也不会<br>没啥印象<br>应该会说话 </p>
                </div>
            </details>
        
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>4-5</b></summary>
                <div style="background-image: url('https://zhizimao.com/uploads/article/2018/10/_15391525496774.png');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;"> 
                    <pre style="text-align: center; font-size: 35px; ">
                         有一点印象
                         交到了朋友
                        去了一趟北京
                        好像一直在玩
                         上了幼儿园
                          无忧无虑
                    </pre>
                </div>
            </details>
        
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>6-12</b></summary>
                <div style="background-image: url('https://p8.itc.cn/images01/20211031/97dc34ec690049798566e4db8590d044.png');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;"> 
                <p style="font-size: 35px; text-align: center;">
                    开始上小学<br>
                    有了各种各样的烦恼<br>
                    怕成绩不好<br>
                    怕写作文<br>
                    怕英语听写<br>
                    <mark>但没有担心过饿肚子</mark><br>
                    学了很多东西<br>
                    学了拼音，数学，英语，语文<br><sub>还有老是生病的体育老师</sub><br>
                </p>
                </div>
            </details>
        
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>13-17</b></summary>
                <div style="background-image: url('https://th.bing.com/th/id/OIP.79e9qF0sgpEUBPYa3UXSIQAAAA?rs=1&pid=ImgDetMain');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;">
                <p style="font-size: 75px; text-align: center;">
                    移居香港<br>
                    移民留一级<br>
                    找学校被拒绝了几次<br>
                    开始适应新环境<br>
                    家里没有学习桌<br>
                    英语跟不上<br>
                </p>
                </div>
            </details>
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>16-20</b></summary>
                <div style="background-image: url('https://pic1.zhimg.com/70/v2-b157c99a73ec74c7d18d218e70a22640_1440w.avis?source=172ae18b&biz_tag=Posts');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;">
                <p style="font-size: 75px; text-align: center;">
                    升上中4 <sub>香港中学读六年</sub><br>
                    选修了 物理 化学 <br>
                    对未来感到担忧 <br>
                    参加了香港高考DSE <br>
                </p>
                </div>
            </details>
            <details>
                <summary style="font-size: 45px; text-align: center;"><b>21-现在</b></summary>
                <div>
                <p style="font-size: 35px; text-align: center;">
                    开始了住宿生活<br>
                    <img src="MVIMG_20240124_131623.jpg" alt="宿舍照片" style="width: 250px; height: 350px;"><br>
                    认识了来自五湖四海的同学<br>
                    <img src="IMG_20231010_224607.jpg" alt="合照" style="width: 250px; height: 350px;"><br>
                    跟学校的活动，游览祖国山水<br>
                    <img src="MVIMG_20231126_131956.jpg" alt="风景照" style="width: 250px; height: 350px;"><br>
                    现在在N116写html代码<br>
                    <img src="WIN_20240402_14_54_21_Pro.jpg" alt="116现场照片" style="width: 250px; height: 350px;"><br>
                </p>
                </div>
            </details>
        </body>
        
        </html>
</template>
<script>
export default {
    
}
</script>
<style lang="css">
     body {
        background-color: #fafafa;
        color: #303030;
        margin: 0;
        padding: 0;
        /* width: 100rem; */
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        user-select: none;
        touch-action: pan-x pan-y;
        /* overflow: hidden; */
    }
    #app {
        font-family: Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 5vh;
    }
</style>