package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonLastFmImage implements Serializable {

	@JsonProperty("#text")
	private String url;

	private String size;
}
