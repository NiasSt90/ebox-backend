package de.zero.ebox.eboxbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSonSimilarArtists {

	private int nid;

	private float match;
}
