package com.ep.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@ToString
public class PollAddedEvent<T> extends ApplicationEvent {
    private final T data;

    public PollAddedEvent(T data) {
        super(data);
        this.data = data;
    }
}
