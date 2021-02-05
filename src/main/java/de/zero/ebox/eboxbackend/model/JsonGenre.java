package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User: Markus Schulz <msc@0zero.de>
 *
 *      "taxonomy": {
 "20": {
 "tid": "20",
 "vid": "2",
 "name": "house",
 "description": "term for a genre of mischungxl (House)",
 "weight": "0"
 },
 "115": {
 "tid": "115",
 "vid": "2",
 "name": "progressive",
 "description": "",
 "weight": "0"
 }
 },

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonGenre implements Serializable {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int tid;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int vid;

	private String name;

	private String description;

	private String weight;

	private String v_weight_unused;
}
