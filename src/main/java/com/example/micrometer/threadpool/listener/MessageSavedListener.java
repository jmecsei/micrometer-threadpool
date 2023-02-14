package com.example.micrometer.threadpool.listener;

import com.example.micrometer.threadpool.event.MessageSavedEvent;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MessageSavedListener {

  private final Tracer tracer;

  @Async
  @EventListener
  public void handleEvent(MessageSavedEvent event) {
    log.info("TraceId: {}", tracer.nextSpan().context().traceId());
  }
}