package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonComment implements Serializable {

	private Long cid;

	private int uid;

	private int nid;

	@JsonProperty("name")
	private String username;

	@JsonProperty("timestamp")
	private Date date;

	private String subject;

	private String comment;
}
