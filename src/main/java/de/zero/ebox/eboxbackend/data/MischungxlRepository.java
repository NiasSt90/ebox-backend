package de.zero.ebox.eboxbackend.data;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface MischungxlRepository extends ReactiveCrudRepository<JsonSetNode, Integer>, MischungxlRepositoryCustom {

	@Modifying
	@Query("UPDATE node SET status = :status where nid = :nid")
	Mono<Boolean> updateStatus(int nid, int status);

}
