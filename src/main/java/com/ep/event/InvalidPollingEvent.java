package com.ep.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@ToString
public class InvalidPollingEvent<T, E> extends ApplicationEvent {
    private final T data;
    private final E error;

    public InvalidPollingEvent(T data, E error) {
        super(data);
        this.data = data;
        this.error = error;
    }
}
