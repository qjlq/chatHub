package com.example.hello_world_with_mvc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.utils.TokenUtil;
import com.google.gson.JsonObject;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileController {
    
    // private String uploadDir = "/home/yaukalok/myweb/UserVideo/";
    private String uploadDir = "C:\\Users\\yau ka lok\\Desktop";
    @Autowired
    private DatabaseService database;
    private static FileController serverHandler;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;        
        // 初使化时将已静态化的testService实例化
    }  

    @RequestMapping("/valid")
    public String vaild(@RequestParam("token") String token) {
        if (TokenUtil.verify(token) != null){
            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            log.info("upload request frome [cid]: " + cid);
            // return true;
            return "valid";

        }
        else{
            log.info("upload request frome with fail token ");
            // return false;
            return "invalid";

        }
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file,@RequestParam("token") String token) {
    // public ResponseEntity<String> uploadFile(MultipartFile file,@RequestHeader("Authorization") String token) {
        
        log.info("upload request frome [token]: " + token);
        
        if (TokenUtil.verify(token) != null){
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String cid = TokenUtil.verify(token).getClaim("cid").asString();
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = uploadPath.resolve(filename);
                log.info("saving: " + filename + " to " + filePath);

                if (Files.exists(filePath)) {
                    log.info("filePath:" + filePath + "  exit");
                    Set<String> videoList = new HashSet<String>(serverHandler.database.getVideoByCid(cid));
                    if (videoList.contains(filename))
                        return ResponseEntity.status(400).body("文件已存在");
                    else{
                        serverHandler.database.AddVideo(filename, cid);
                        return ResponseEntity.ok("success");
                    }
                    // return ResponseEntity.ok("400");

                }

                // log.info("filePath: " + filePath);
                file.transferTo(filePath);
                // file.transferTo(new File(uploadDir, filename));
                log.info("upload success: " + filePath);
                serverHandler.database.AddVideo(filename, cid);
                return ResponseEntity.ok("success");
            } catch (IOException ex) {
                log.error("上传失败: {}", ex.getMessage(), ex);
                return ResponseEntity.status(500).body("上传失败");
                // return ResponseEntity.ok("500");

            }
        } else{
            log.info("upload request frome with fail token ");
            return ResponseEntity.status(401).body("无效token");
            // return ResponseEntity.ok("401");
        }
    }

    // @GetMapping("/download/{filename:.+}")
    // public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    //     try {
    //         Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
    //         Resource resource = new UrlResource(filePath.toUri());
            
    //         if (resource.exists()) {
    //             return ResponseEntity.ok()
    //                     .header(HttpHeaders.CONTENT_DISPOSITION, 
    //                             "attachment; filename=\"" + resource.getFilename() + "\"")
    //                     .body(resource);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } catch (Exception ex) {
    //         return ResponseEntity.internalServerError().build();
    //     }
    // }

    // @GetMapping("/files")
    // public ResponseEntity<List<String>> listFiles() {
    //     try {
    //         List<String> filenames = Files.list(Paths.get(uploadDir))
    //                 .map(Path::getFileName)
    //                 .map(Path::toString)
    //                 .collect(Collectors.toList());
    //         return ResponseEntity.ok(filenames);
    //     } catch (IOException ex) {
    //         return ResponseEntity.status(500).build();
    //     }
    // }
}
