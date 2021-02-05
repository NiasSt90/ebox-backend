package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"myVote", "average", "count", "firstGoodVote", "votesList", "voteSum"})
public class JsonVote implements Serializable {

	@JsonProperty("my")
	private Integer myVote;

	@JsonProperty("avg")
	private Double average;

	@JsonProperty("count")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int count;

	@JsonProperty("firstgoodvote")
	private JsonUserVote firstGoodVote;

	@JsonProperty("all")
	private List<JsonUserVote> votesList;

	@JsonProperty("sum")
	private int voteSum;
}
