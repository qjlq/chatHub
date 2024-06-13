<template lang="html">
    <html style="--sidebar-width: 25%;">
    <head> 
        <title>QJL2021153007</title>
        <meta http-equiv="Content-Type" content="text/html" charset="utf-8"> 
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
        <p style="border: 2px solid Tomato; text-align:center; font-size:60px ;"> HTML web note <span style="font-family: Arial, Helvetica, sans-serif; font-size: 18px;">by 2021153007</span></p>
        
        <details>
            <summary style="font-size: 45px; "><b>Head<span style="font-family: Arial; font-size: 18px;">(where code did not use &lt;style&gt; or CSS, that make the code look complicate)</span></b></summary>
            <div style="text-indent:5em; background-color:rgb(220, 230, 176);"> 
                <p style="font-size: 26px;">1.&lt;h1&gt; defines a large heading :</p>
                <h1 style="text-indent: 150px;">This is a Heading</h1> 
                <p style="font-size: 26px;">2.&lt;h6&gt; defines a smaller heading :</p>
                <h6 style="text-indent: 150px;"> defines the least important heading </h6>
                <p style="font-size: 26px;">3.&lt;b&gt; make the text <strong>STRONG</strong>, &lt;strong&gt; make partial text <strong>STRONG</strong>:</p>
                <p style="font-size: 26px;">4.&lt;body style="background-color:powderblue;"&gt; the style inside the &lt;&gt; make the element have future,this one make the background blue:</p>
                <p style="font-size: 26px;">5.&lt;span&gt; defines a Different character sentence in a paragraph  :</p>
                    <p style="font-size: 20; text-indent: 150px;">sencten <span style="font-size: 10;">(sentence with 10 inside a sentence)</span>with 20 </p>
                <p style="font-size: 26px;">6.&lt;details&gt; 实现内容收合的效果，&lt;summary&gt; 为设定details 设定标题：</p>
                    <details style="text-indent: 150px;">
                        <summary>test</summary>
                        <p>details information</p>
                    </details>
                <p style="font-size: 26px;">7.&lt;mark&gt; 高亮范围内的文本内容：</p>
                    <p style="font-size: 26px; text-indent: 150px;">Do not forget to buy <mark>milk</mark> today.</p>
            </div>
        </details>
    
        
    
        <details>
            <summary id="title1"><b>Different between #id and .class in CSS<span class="span">(where using the inter CSS from)</span></b></summary>
            <div id="div" style="background-color:rgb(197, 176, 230);">
                <pre id="para1">
                1.id是一个标签，用于区分不同的结构和内容；
                    id是先找到结构/内容，再给它定义样式；
                    class是一个样式，可以套在任何结构和内容上；
                    class是先定义好一种样式，再套给多个结构/内容。
                2.id是元素的名称，可以供js或其它脚本程序来访问该元素对象，而
                    class是该元素的css类名。
                3.定义样式时，id="aa"的用#aa{...}，class="bb"的用.bb{...}。
                4.class是全局属性，id是局部属性。
                </pre>
                <p id="para1">引用外部文件的CSS <b>&lt;link rel="stylesheet" href="learn.css"&gt;</b> 其中rel="stylesheet"是定死的</p>
            </div>
        </details>
    
        <details>
            <summary id="title1"><b>Link/url</b></summary>
            <div id="div" style="background-color:rgb(176, 230, 214);">
                <p id="para1">0.use the id attribute to create a bookmark:</p>
                <h2 id="C4" class="para2">Chapter 4</h2>
                <p id="para1">1.HTML links are defined with the &lt;a&gt; tag</p>
                <p id="para1">2.this is <a href="qjlkalok.xyz">my web url</a> </p>
                <p id="para1">3.this <a href="qjlkalok.xyz" target="_blank">url</a> will open in a new page by adding "target = _blank"</p>
                <p id="para1">4.this is a image, Use an Image as a Link <a href="qjlkalok.xyz"><img src="https://photosku.com/photo/images/s/p07/07755.jpg" alt="HTML tutorial" style="width:42px;height:42px;"></a> url</p>
                <p id="para1"><a href="https://photosku.com/photo/images/s/p07/07755.jpg">5.链接跳转 图片 ，可以改为本地路径</a></p>
                <p id="para1"><a href="https://www.w3schools.com/html/" title="Go to W3Schools HTML section">6.鼠标指到该链接会显示详细信息</a></p>
                <p id="para1"> 7.可以用 &lt;button&gt; 设置可互动按钮</p>
                <p id="para1">8.&lt;img&gt; use to import a image</p>
                    <p id="para2"><img src="https://photosku.com/photo/images/s/p07/07756.jpg" alt="this is a image" id="para1"></p>
                <p id="para1">9.Link to an Email Address</p>
                    <p id="para2"><a href="mailto:someone@example.com">Send email</a></p>
                <p id="para1">10.(本页面跳转)add a link to the bookmark ("Jump to Chapter 4"), from within the same page:</p>
                <p class="para2"><a href="#C4">Jump to Chapter 4</a></p>
            </div>
        </details>
    
        <details>
            <summary id="title1"><b>引用 HTML Quotation and Citation Elements</b></summary>
            <div id="div" style="background-color: bisque;">
                <p id="para1">1.Here is a quote from WWF's website:</p>
                    <div id="div" style="background-color: aquamarine;">
                        <blockquote cite="http://www.worldwildlife.org/who/index.html">
                            For 60 years, WWF has worked to help people and nature thrive. As the world's leading conservation organization, WWF works in nearly 100 countries. At every level, we collaborate with people around the world to develop and deliver innovative solutions that protect communities, wildlife, and the places in which they live.
                        </blockquote>
                    </div>
                <p id="para1">2.&lt;q&gt; for Short Quotations</p>
                    <p id="para2">WWF's goal is to: <q>Build a future where people live in harmony with nature.</q></p>
                <p id="para1">3.&lt;abbr&gt; for Abbreviations</p>
                    <p id="para2">The <abbr title="World Health Organization">WHO</abbr> was founded in 1948.</p>
                <p id="para1">4.&lt;address&gt; for Contact Information</p>
                    <address>
                        Written by John Doe.<br>
                        Visit us at:<br>
                        Example.com<br>
                        Box 564, Disneyland<br>
                        USA
                    </address>
                <p id="para1">5.&lt;cite&gt; for Work Title,告诉爬虫这是个引用</p>
                    <p id="para2"><cite>The Scream</cite> by Edvard Munch. Painted in 1893.</p>
                <p id="para1">6.&lt;bdo&gt; for Bi-Directional Override</p>
                <bdo dir="rtl">This text will be written from right to left</bdo>
            </div>
        </details>
        <details>
            <summary id="title1"><b>Comment</b></summary>
            <div id="div" style="background-color: chocolate;">
                <p id="para1">1.use <b> &lt;!--comment--&gt;</b>as a comment in html </p>
                <p id="para1">2.use /* comment */ in CSS or inter CSS</p>
            </div>
        </details>
    
        <details>
            <summary id="title1"><b>element</b></summary>
            <div id="div" style="background-color: azure;">
                <p id="para1">1.&lt;p&gt; defines a paragraph</p>
                <p id="para1">2.&lt;br&gt; defines a line break</p>
                <p id="para1">3.&lt;pre&gt; displayed in a fixed-width font (usually Courier), and it preserves both spaces and line breaks</p>
                    <pre>
                        1 test 1
                            2 test 12
                                3 test 123
                    </pre>
            </div>
        </details>
    
        <details>
            <summary id="title1"><b>image</b></summary>
            <div id="div" style="background-color: aqua;">
                <p id="para1">1. user &lt;img&gt; to involve a image, the alt - Specifies an alternate text for the image to prevent the user for some reason cannot view it</p>
                    <img src="https://photosku.com/photo/images/s/p07/07753.jpg" alt="a involve image">
                <p id="para1">2.[style] attribute to specify the fixing width and height of an image</p>
                    <img src="https://photosku.com/photo/images/s/p07/07753.jpg" alt="a smaller image fix with style" style="width: 250px; height: 250px;">
                    <br>
                    <img src="https://photosku.com/photo/images/s/p07/07753.jpg" alt="a image fix with width and height" width="250px" height="250px">
                <pre>
                    The width, height, and style attributes are all valid in HTML.
                    Suggest using the style attribute. It prevents styles sheets from changing the size of images.
                </pre>
                <p id="para1">3.HTML allows animated <b>gifs</b></p>
                    <img src="https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExdzRic2FjMnR6dHFxdXppeHJseWl6MndyNnhkeHNmNHN0YndteHhhdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/sJWNLTclcvVmw/giphy.gif" alt="a gif">
                <p id="para1">4.(float) property to let the image float to the right or to the left of a text</p>
                <p id="para2"><img src="https://photosku.com/photo/images/s/p07/07753.jpg" alt="Smiley face" style="float:right;width:42px;height:42px;">
                    The image will float to the right of the text.</p>
                <p id="para2"><img src="https://photosku.com/photo/images/s/p07/07753.jpg" alt="Smiley face" style="float:left;width:42px;height:42px;">
                        The image will float to the left of the text.</p>
                <p id="para1">5.HTML Image Tag <br><pre style="font-size: 24px;">
                    
            Tag	Description
                &lt;img&gt;	Defines an image
                &lt;map&gt;	Defines an image map
                &lt;area&gt;	Defines a clickable area inside an image map
                &lt;picture&gt;	Defines a container for multiple image resources
                </pre></p>
                <p id="para1">6.&lt;map&gt; defines an image map. An image map is an image with clickable areas. The areas are defined with one or more &lt;;area&gt;; tags.</p>
                    <img src="https://www.w3schools.com/html/workplace.jpg" alt="Workplace" usemap="#workmap">
    
                    <map name="workmap">
                    <area shape="rect" coords="34,44,270,350" alt="Computer" href="https://www.w3schools.com/html/computer.htm">
                    <area shape="rect" coords="290,172,333,250" alt="Phone" href="https://www.w3schools.com/html/phone.htm">
                    <area shape="circle" coords="337,300,44" alt="Coffee" href="https://www.w3schools.com/html/coffee.htm">
                    </map>
                <p id="para1"><pre>
                    7.(background-image) property add a background image on a HTML element:
                    8. background-repeat property to no-repeat
                    9.If you want the background image to cover the entire element, you can set the <b>(background-size)</b> property to cover.
                        Also, to make sure the entire element is always covered, set the <b>background-attachment</b> property to fixed:
                        This way, the background image will cover the entire element, with no stretching (the image will keep its original proportions):
                
    
                </pre></p>
                    <p style="background-image: url('https://photosku.com/photo/images/s/p07/07753.jpg'); font-size: 70px">repeat background</p>
                    <p style="background-image: url('https://photosku.com/photo/images/s/p07/07753.jpg'); font-size: 70px;
                              background-repeat: no-repeat; ">no-repeat background</p>
                    <p style="background-image: url('https://photosku.com/photo/images/s/p07/07753.jpg'); font-size: 70px;
                    background-repeat: no-repeat;
                    background-attachment: fixed;
                    background-size: cover "
                    >fix cover background</p>
            </div>
        </details>
        <!-- <script>
        var details = Array.from(document.querySelectorAll('.details'));  
        details.forEach((detail) => {
            detail.addEventListener('click', (e) => {
            var active = details.find(d => d.open);
            if (!e.currentTarget.open && active) {
                active.open = false;
            }
            });
        });
        </script> -->
    </body>
    </html>
</template>
<script>
export default {
    
}
</script>
<style lang="css">

/* 这里是文本内css */
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
    .span{ /*where is class with .*/
        font-family: Arial; 
        font-size: 18px;
    }

    #title1{ /*where is id with #*/
        font-size: 45px;
    }
    #para1{
        font-size: 26px;
        font-family: Arial, Helvetica, sans-serif;
        TEXT-ALIGN:justify;
    }
    #para2{
        font-size: 26px;
        text-indent: 150px;
        TEXT-ALIGN:justify;
    }
    .para2{
        font-size: 26px;
        text-indent: 150px;
        TEXT-ALIGN:justify;
    }
    #div{
        text-indent: 5em;
        TEXT-ALIGN:justify;
        /* display:flex; */
        /* flex:1; */
        /* display: -webkit-flex;
        display: flex; */
    }
    #img{
        width: 128;
        height: 128;
    }
</style>