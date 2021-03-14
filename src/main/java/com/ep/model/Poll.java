package com.ep.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "polls")
public class Poll extends BaseEntity {
    private String pollingname;
    private String validoptions;
    private String starttime;
    private String endtime;
    private static List options;

    public Poll(String pollingname, String validoptions, String starttime, String endtime){

        this.pollingname = pollingname;
        this.validoptions = validoptions;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public static <R> R convertToList(Poll poll) {
        options = new ArrayList();
        options.add(poll.validoptions);
        return (R) poll;
    }
}
