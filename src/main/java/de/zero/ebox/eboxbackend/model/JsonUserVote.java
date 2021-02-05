package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonUserVote implements Serializable {

    @JsonProperty("uid")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int uid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("vote")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int vote;

    @JsonProperty("timestamp")
    @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
    private Instant date;
}
