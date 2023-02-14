package com.example.micrometer.threadpool.configuration;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextSnapshot;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration(proxyBeanMethods = false)
public class AsyncConfiguration {

  @Bean
  public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
      @NonNull
      @Override
      protected ExecutorService initializeExecutor(@NonNull ThreadFactory threadFactory, @NonNull RejectedExecutionHandler rejectedExecutionHandler) {
        ExecutorService executorService = super.initializeExecutor(threadFactory, rejectedExecutionHandler);
        return ContextExecutorService.wrap(executorService, ContextSnapshot::captureAll);
      }
    };
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(100);
    executor.setQueueCapacity(50);
    executor.setThreadNamePrefix("async-");

    return executor;
  }
}