package com.ep.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "results")
public class Result extends BaseEntity {
    private String useridentity;
    private String pollingname;
    private String useroption;

    public Result(String useridentity, String pollingname, String useroption) {

        this.useridentity = useridentity;
        this.pollingname = pollingname;
        this.useroption = useroption;
    }
}