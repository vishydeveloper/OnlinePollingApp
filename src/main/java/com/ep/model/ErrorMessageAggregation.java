package com.ep.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessageAggregation extends BaseEntity {
    private String errorMessage;
    private Integer count;

    public ErrorMessageAggregation(String errorMessage, Integer count) {
        this.errorMessage = errorMessage;
        this.count = count;
    }
}
