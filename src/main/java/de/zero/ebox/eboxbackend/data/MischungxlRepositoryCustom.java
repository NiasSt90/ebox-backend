package de.zero.ebox.eboxbackend.data;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MischungxlRepositoryCustom {

	Mono<JsonSetNode> getMischungxlNode(int nid);
}
