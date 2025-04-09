package com.example.hello_world_with_mvc.service;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.Task.TaskStatus;

@Service
public class TaskQueueService {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    private final Object lock = new Object();

    // @Autowired
    // private TaskRepository taskRepository;
    
    @Autowired
    private WebSocketServer socketServer;

    @Autowired
    private FastApiConnect fastApiConnect;

    public synchronized Task submitTask(String fileName, String creator, Task.TaskType type) {
        Task task = new Task();
        task.setFileName(fileName);
        task.setCreator(creator);
        task.setTaskType(type);
        task.setTaskStatus(TaskStatus.QUEUED);
        task.setCreateTime(LocalDateTime.now());
        
        // task = taskRepository.save(task);
        
        if (isProcessing.compareAndSet(false, true)) {
            startProcessing(task);
        } else {
            taskQueue.add(task);
        }
        return task;
    }

    private void startProcessing(Task task) {
        task.setTaskStatus(TaskStatus.PROCESSING);
        task.setStartTime(LocalDateTime.now());
        // taskRepository.save(task);

        // 异步处理任务
        CompletableFuture.runAsync(() -> {
            try {
                String result = fastApiConnect.startTask(Integer.parseInt(task.getFileName())).get(); // 调用WebSocketServer的startTask方法，返回Future对象, get()方法  阻塞  等待获取结果
                if (result.equals("success")) {
                    task.setTaskStatus(TaskStatus.COMPLETED);
                }
            } catch (Exception e) {
                task.setTaskStatus(TaskStatus.FAILED);
            } finally {
                task.setEndTime(LocalDateTime.now());
                // taskRepository.save(task);
                completeProcessing();
            }
        });
    }

    private void completeProcessing() {
        synchronized (lock) {
            if (!taskQueue.isEmpty()) {
                Task nextTask = taskQueue.poll();
                startProcessing(nextTask);
            } else {
                isProcessing.set(false);
            }
        }
    }
}
