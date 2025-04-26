package com.example.hello_world_with_mvc.service;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.controller.TaskController;
import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.Task.TaskStatus;
import com.example.hello_world_with_mvc.entity.VideoState;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class TaskQueueService {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    private final Object lock = new Object();
    public static Task currentTask;
    @Autowired
    private DatabaseService database;
    private static TaskQueueService serverHandler;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;        
        // 初使化时将已静态化的testService实例化
    }
    // @Autowired
    // private TaskRepository taskRepository;
    
    @Autowired
    private WebSocketServer socketServer;

    @Autowired
    private FastApiConnect fastApiConnect;

    public synchronized Task submitTask(String fileName, String creator, Task.TaskType type) {
        Task task = new Task();
        task.setFileName(fileName);
        task.setTaskIdentifier(fileName+ '_' + type.toString());
        task.setCreator(creator);
        task.setTaskType(type);
        task.setCreateTime(LocalDateTime.now());
        
        // task = taskRepository.save(task);
        
        if (isProcessing.compareAndSet(false, true)) {
            log.info("start processing a Task");
            task.setTaskStatus(TaskStatus.PROCESSING);
            task.setStartTime(LocalDateTime.now());
            startProcessing(task);
        } else {
            log.info("add a Task in queue");
            task.setTaskStatus(TaskStatus.QUEUED);

            taskQueue.add(task);
        }
        return task; //返回给taskcontroller 后写入数据库
    }

    private void startProcessing(Task task) {
        currentTask = task;
        // task.setTaskStatus(TaskStatus.PROCESSING);
        // task.setTaskStatus(TaskStatus.PROCESSING);
        // task.setStartTime(LocalDateTime.now());
        // taskRepository.save(task);

        // 异步处理任务
        // CompletableFuture.runAsync(() -> {
        //     try {
        //         // String result = fastApiConnect.startTask(Integer.parseInt(task.getFileName())).get(); // 调用WebSocketServer的startTask方法，返回Future对象, get()方法  阻塞  等待获取结果
        //         CompletableFuture result = fastApiConnect.startTask(task).get(); // 调用WebSocketServer的startTask方法，返回Future对象, get()方法  阻塞  等待获取结果
        //         if (result.equals("success")) {
        //             task.setTaskStatus(TaskStatus.COMPLETED);
        //         }
        //     } catch (Exception e) {
        //         task.setTaskStatus(TaskStatus.FAILED);
        //     } finally {
        //         task.setEndTime(LocalDateTime.now());
        //         // taskRepository.save(task);
        //         completeProcessing();
        //     }
        // });
        CompletableFuture<String> future = fastApiConnect.startTask(task);
        // future.whenComplete((result, ex) -> {
        //     if (ex == null && "success".equals(result)) {
        //         task.setTaskStatus(TaskStatus.COMPLETED);
        //     } else {
        //         task.setTaskStatus(TaskStatus.FAILED);
        //     }
        //     task.setEndTime(LocalDateTime.now());
        //     completeProcessing();
        // });
        future.thenAccept(result -> {
            // if ("success".equals(result)) {
            //     completeProcessing();
            //     log.info("Task completed: " + task.getId());
            //     task.setTaskStatus(TaskStatus.COMPLETED);

            // }

        }).exceptionally(e -> {
            completeProcessing(null,task);
            return null;
        });
    }

    public void completeProcessing(String tid, Task task) {
        log.info("a Task processing completed");
        synchronized (lock) {
            VideoState videoState = serverHandler.database.getVideoStateByFileName(task.getFileName());
            // task.setTaskIdentifier(task.getFileName()+ '_' + task.getTaskType().toString());
            if (tid != null){  //completeProcessing 从fastapi 返回调用的有tid
                task.setTaskStatus(TaskStatus.COMPLETED);
                videoState.setState(task.getTaskType().toString(), "COMPLETED");
                task.setEndTime(LocalDateTime.now());
            }
            else{
                videoState.setState(task.getTaskType().toString(), "FAILED");
                task.setTaskStatus(TaskStatus.FAILED);
            }
            serverHandler.database.updateTaskStateByIdentifier(task);
            serverHandler.database.updateVideoState(videoState); 
            if (!taskQueue.isEmpty()) {
                fastApiConnect.completeTask(tid);

                Task nextTask = taskQueue.poll();
                nextTask.setTaskStatus(TaskStatus.PROCESSING);
                nextTask.setStartTime(LocalDateTime.now());
                serverHandler.database.updateTaskStateByIdentifier(nextTask);

                startProcessing(nextTask);
            } else {
                isProcessing.set(false);
            }

        }
    }
}
