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
@Table(name = "errormessages")
public class ErrorMessage extends BaseEntity {
    private String useridentity;
    private String pollingname;
    private String useroption;
    private String errormessage;

    public ErrorMessage(String useridentity, String pollingname, String useroption, String errormessage) {

        this.useridentity = useridentity;
        this.pollingname = pollingname;
        this.useroption = useroption;
        this.errormessage = errormessage;
    }
}