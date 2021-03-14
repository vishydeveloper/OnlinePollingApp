package com.ep.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    private String pollingname;
    private String useridentity;
    private String eventtime;
    private String useroption;
}
