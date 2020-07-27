package com.example.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @Program: learn
 * @description: 学习Future
 * @author: WangKai
 * @create: 2020/07/18 14:30
 **/
@Slf4j
@RestController
@RequestMapping("/Future")
public class FutureController {

    @RequestMapping("/1")
    public String learn1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        log.info("启动了");
        Callable<String> callable=()->{
            log.info("子线程启动");
            Thread.sleep(1000);
            return "Hello from Callable";
        };
        log.info("线程提交到线程池");
        Future<String> future = executorService.submit(callable);
        log.info("主线程继续执行");
        log.info("主线程等待获取Future结果");
        //get造成了堵塞
        String result = future.get();
        log.info("主线程获取的结果:"+result);
        executorService.shutdown();
        return result;
    }

    @RequestMapping("/2")
    public String learn2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        log.info("启动了");
        Callable<String> callable = () ->{
            log.info("子线程启动");
            Thread.sleep(5000);
            return "Hello from Callable";
        };
        log.info("线程提交到线程池");
        Future<String> future = executorService.submit(callable);
        log.info("主线程继续执行");
        log.info("主线程等待Future结果");
        while (!future.isDone()) {
            log.info("子线程未结束");
            Thread.sleep(1000);
        }
        //get造成了堵塞
        String result = future.get();
        log.info("主线程获取的结果:"+future.get());
        executorService.shutdown();
        return result;
    }
}
