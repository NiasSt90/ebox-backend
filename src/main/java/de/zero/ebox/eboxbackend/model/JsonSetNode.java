package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"nid", "uid", "userName", "title", "replaceTitle", "status", "created", "modified", "setcreated",
	"lastheared", "duration", "genre", "vote", "bookmarked", "trackinfos", "comments", "artists", "myPlaycount", "allPlaycount", "dj"})
public class JsonSetNode implements Serializable {

	@JsonProperty("nid")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int nid;

	@JsonProperty("uid")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer uid;

	@JsonProperty("name")
	private String userName;

	@JsonProperty("title")
	private String title;

	@JsonProperty("replacetitle")
	private String replaceTitle;

	@JsonProperty("status")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer status;

	@JsonProperty("created")
	@JsonSerialize(using = InstantToEpochSecondsSerializer.class)
	private Instant created;

	@JsonProperty("changed")
	@JsonSerialize(using = InstantToEpochSecondsSerializer.class)
	private Instant modified;

	@JsonProperty("setcreated")
	@JsonSerialize(using = InstantToEpochSecondsSerializer.class)
	private Instant setcreated;

	@JsonProperty("lastheard")
	@JsonSerialize(using = InstantToEpochSecondsSerializer.class)
	private Instant lastheared;

	@JsonProperty("dj")
	private JsonDJ dj;

	@JsonProperty("duration")
	private Integer duration;

	@JsonProperty("taxonomy")
	private Map<Integer, JsonGenre> genre;

	@JsonProperty("votes")
	private JsonVote vote;

	@JsonProperty("bookmarked")
	private Boolean bookmarked;

	@JsonProperty("myplaycount")
	private int myPlaycount;

	@JsonProperty("allplaycount")
	private int allPlaycount;

	@JsonProperty("trackinfo")
	private List<JsonTrackinfo> trackinfos;

	@JsonProperty("comments")
	private List<JsonComment> comments;

	@JsonProperty("artists")
	private List<JsonArtistID> artists;

	//choose Genre Update Mode:
	/*
		0: take FROM dj again
		1: write TO dj
		2: write TO set
	 */
	@JsonProperty("taxonomy_by_dj")
	private Integer taxonomyByDj;

	@JsonProperty("score")
	private Integer score;

	//UNUSED
	private String djset;
}
