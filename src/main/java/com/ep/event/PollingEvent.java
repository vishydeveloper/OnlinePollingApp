package com.ep.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@ToString
public class PollingEvent<T> extends ApplicationEvent {
    private final T data;

    public PollingEvent(T data) {
        super(data);
        this.data = data;
    }
}
