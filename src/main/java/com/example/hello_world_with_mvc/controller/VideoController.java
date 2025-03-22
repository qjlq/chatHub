package com.example.hello_world_with_mvc.controller;

import java.nio.charset.StandardCharsets;
// import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.hello_world_with_mvc.utils.VideoHandler;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoHandler VideoHandler;


    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public void video(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Path path = Paths.get("test.txt");;
            // File file = new File(path);

            if (Files.exists(path)) {
                request.setAttribute(VideoHandler.ATTR_FILE, path);
                VideoHandler.handleRequest(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {

        }
    }
}
