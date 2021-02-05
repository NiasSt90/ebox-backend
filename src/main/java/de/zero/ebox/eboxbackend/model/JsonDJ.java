package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonDJ implements Serializable {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int id;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int nid;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String country;

	@JsonSerialize(using = InstantToEpochSecondsSerializer.class)
	private Instant lastfm_artistinfo_timestamp;

	private JsonLastFmArtistInfo lastfm_artistinfo;

	private Map<Integer, JsonGenre> taxonomy;

	private List<JSonSimilarArtists> similar_artists;

}
