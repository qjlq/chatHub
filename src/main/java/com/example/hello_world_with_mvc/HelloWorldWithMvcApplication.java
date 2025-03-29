package com.example.hello_world_with_mvc;

import com.example.hello_world_with_mvc.service.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;



@SpringBootApplication
public class HelloWorldWithMvcApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HelloWorldWithMvcApplication.class, args);
        
	    //    ServerSocket ss = new ServerSocket(2236);
        // while(true){
        //     Socket sock=ss.accept();
        //     System.out.println("connected from "+sock.getRemoteSocketAddress());
        //     Thread t =  new SocketServer(sock);
        //     t.start();
		// }
	}
}
