package com.example.hello_world_with_mvc.controller;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.hello_world_with_mvc.entity.VideoState;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.utils.TokenUtil;
import com.example.hello_world_with_mvc.utils.VideoHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private VideoHandler VideoHandler;
    @Autowired
    private DatabaseService database;
    private static VideoController serverHandler;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;        
        // 初使化时将已静态化的testService实例化
    }  

    @PostMapping(value = "/videoList")
    public Set<VideoState> videoList(String token) {
        if (TokenUtil.verify(token) != null){

            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            Set<VideoState> videoList = new HashSet<VideoState>(serverHandler.database.getVideoStateByCid(cid));

            // log.info("videoList request frome [cid]: " + cid + " videoList: " + videoList.toString());
            log.info("videoList request frome [cid]: " + cid );

            // return true;

            /*  Controller 返回 Set<VideoState> 时，Spring Boot 会自动调用 Jackson 库将对象序列化为 JSON*/
            return videoList;
        }
        else{
            log.info("videoList request frome with fail token ");
            // return false;
            return null;
        }
    }
   
    @RequestMapping(value = "/video/{filename}", method = RequestMethod.GET)
    public void video(
            // @RequestParam String filename, 
            @PathVariable String filename, 
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // Path path = Paths.get("/media/SSD/DataDisk/gym/events/");
            String RootPath = "/home/yaukalok/myweb/UserVideo/";
            // String RootPath = "E:\\kaf\\";
            // String filename = request.getParameter("filename");
            log.info("filename: " + filename);
// String filename = "zNL3kn3UBmg.mp4";
            // String filename = "tQLr-oGPO_I_E_004996_005065.mp4";
            // Path path = Paths.get("E:\\kaf\\【239】[feat. 花譜, ツミキ] トウキョウ・シャンディ・ランデヴ ⧸ MAISONdes.mp4");
            // Path path = Paths.get("E:\\kaf\\bandicam 2022-03-26 19-07-15-079.mp4");

            Path path = Paths.get(RootPath + filename);

            // File file = new File(path);
            // filename = "zNL3kn3UBmg.mp4";
            if (Files.exists(path)) {
                // request.setAttribute(VideoHandler.ATTR_FILE, path.toString());
                // request.setAttribute(VideoHandler.ATTR_FILE, "/media/SSD/DataDisk/gym/events/tQLr-oGPO_I_E_004996_005065.mp4");

                request.setAttribute(VideoHandler.ATTR_FILE, RootPath + filename);
                // request.setAttribute(VideoHandler.ATTR_FILE, "E:\\kaf\\bandicam 2022-03-26 19-07-15-079.mp4");


                VideoHandler.handleRequest(request, response);
                // request.getRequestDispatcher("/video/" + filename).forward(request, response);
                // request.getRequestDispatcher("/video/stream.mp4").forward(request, response);
                //显式设置 Content-Type：
                // String mimeType = Files.probeContentType(path);
                // response.setContentType(mimeType != null ? mimeType : "video/mp4");
                log.info("video found");  
            } else {
                response.setStatus(404);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                log.info("video not found");
            }
            log.info("video");

        } catch (Exception e) {
            log.error("Error handling video", e);
            // response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(500);
        }
    }
}
