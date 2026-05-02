package com.learngraphql.springgraphql.employeeproject.service;

import com.learngraphql.springgraphql.employeeproject.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class EmployeeEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EmployeeEventPublisher.class);

    private final Sinks.Many<Employee> createdSink =
            Sinks.many().multicast().onBackpressureBuffer();

    private final Sinks.Many<Employee> updatedSink =
            Sinks.many().multicast().onBackpressureBuffer();

    private final Sinks.Many<Employee> deletedSink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publishCreated(Employee employee) {
        Sinks.EmitResult result = createdSink.tryEmitNext(employee);
        log.info("Created event emitted. employeeId={}, result={}", employee.getId(), result);
    }

    public void publishUpdated(Employee employee) {
        Sinks.EmitResult result = updatedSink.tryEmitNext(employee);
        log.info("Updated event emitted. employeeId={}, result={}", employee.getId(), result);
    }

    public void publishDeleted(Employee employee) {
        Sinks.EmitResult result = deletedSink.tryEmitNext(employee);
        log.info("Deleted event emitted. employeeId={}, result={}", employee.getId(), result);
    }

    public Flux<Employee> employeeCreatedStream() {
        return createdSink.asFlux()
                .doOnSubscribe(s -> log.info("employeeCreated subscription started"))
                .doOnNext(e -> log.info("Sending created event: {}", e.getName()))
                .doOnCancel(() -> log.info("employeeCreated subscription cancelled"));
    }

    public Flux<Employee> employeeUpdatedStream() {
        return updatedSink.asFlux()
                .doOnSubscribe(s -> log.info("employeeUpdated subscription started"))
                .doOnNext(e -> log.info("Sending updated event: {}", e.getName()))
                .doOnCancel(() -> log.info("employeeUpdated subscription cancelled"));
    }

    public Flux<Employee> employeeDeletedStream() {
        return deletedSink.asFlux()
                .doOnSubscribe(s -> log.info("employeeDeleted subscription started"))
                .doOnNext(e -> log.info("Sending deleted event: {}", e.getName()))
                .doOnCancel(() -> log.info("employeeDeleted subscription cancelled"));
    }
}