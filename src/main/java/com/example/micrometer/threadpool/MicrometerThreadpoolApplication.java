package com.example.micrometer.threadpool;

import com.example.micrometer.threadpool.event.MessageSavedEvent;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync
@SpringBootApplication
public class MicrometerThreadpoolApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicrometerThreadpoolApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(Tracer tracer, ApplicationEventPublisher applicationEventPublisher) {
    return (args) -> {
      log.info("TraceId: {}", tracer.nextSpan().context().traceId());
      applicationEventPublisher.publishEvent(new MessageSavedEvent());
    };
  }
}