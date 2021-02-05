package de.zero.ebox.eboxbackend.data;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.time.Instant;
import java.util.function.BiFunction;


public class JsonSetNodeMapper implements BiFunction<Row, RowMetadata, JsonSetNode> {

	@Override
	public JsonSetNode apply(Row row, RowMetadata rowMetadata) {
		return JsonSetNode.builder()
				.nid(row.get("nid", Integer.class))
				.uid(row.get("uid", Integer.class))
				.status(row.get("status", Integer.class))
				.title(row.get("title", String.class))
				.replaceTitle(row.get("replacetitle", String.class))

				.created(ofEpochSecond(row.get("created", Integer.class)))
				.modified(ofEpochSecond(row.get("changed", Integer.class)))
				.setcreated(ofEpochSecond(row.get("setcreated", Integer.class)))

				.build();
	}

	private static Instant ofEpochSecond(Integer seconds) {
		return seconds != null ? Instant.ofEpochSecond(seconds) : null;
	}

}
