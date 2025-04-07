package com.example.hello_world_with_mvc.service;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.Task.TaskStatus;

public class TaskQueueService {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    private final Object lock = new Object();

    // @Autowired
    // private TaskRepository taskRepository;
    
    @Autowired
    private WebSocketServer socketServer;

    public synchronized Task submitTask(String fileName, String creator, Task.TaskType type) {
        Task task = new Task();
        task.setFileName(fileName);
        task.setCreator(creator);
        task.setType(type);
        task.setStatus(TaskStatus.QUEUED);
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
        task.setStatus(TaskStatus.PROCESSING);
        task.setStartTime(LocalDateTime.now());
        // taskRepository.save(task);

        // 异步处理任务
        CompletableFuture.runAsync(() -> {
            try {
                String result = socketServer.startTask(task.getFileName()).get(); // 调用WebSocketServer的startTask方法，返回Future对象, get()方法  阻塞  等待获取结果
                if (result.equals("success")) {
                    task.setStatus(TaskStatus.COMPLETED);
                }
            } catch (Exception e) {
                task.setStatus(TaskStatus.FAILED);
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
