package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonArtistID {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int djid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int artistnid;
}
