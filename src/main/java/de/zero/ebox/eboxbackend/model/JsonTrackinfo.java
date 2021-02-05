package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonTrackinfo  {

	private String filename;

	@JsonProperty("downloadfilename")
	private String downloadFilename;

	@JsonProperty("fsize")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer filesize;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer duration;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer frequency;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Short bitrate;

	//joint stereo, ...
	@JsonProperty("mode")
	private String mp3Mode;

	private String artist;

	private String title;

}
