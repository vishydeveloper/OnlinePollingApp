package com.ep.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@ToString
public class UserAddedEvent<T> extends ApplicationEvent {
    private final T data;

    public UserAddedEvent(T data) {
        super(data);
        this.data = data;
    }
}
