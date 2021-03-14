package com.ep.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultAggregation extends BaseEntity {
    private String pollingname;
    private String useroption;
    private Integer count;

    public ResultAggregation(String pollingname, String useroption, Integer count) {

        this.pollingname = pollingname;
        this.useroption = useroption;
        this.count = count;
    }
}
