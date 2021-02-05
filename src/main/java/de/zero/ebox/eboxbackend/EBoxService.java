package de.zero.ebox.eboxbackend;

import de.zero.ebox.eboxbackend.data.MischungxlRepository;
import de.zero.ebox.eboxbackend.model.JsonSetNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class EBoxService {

    private final MischungxlRepository repository;

    public Mono<JsonSetNode> getNode(int nid) {
        return repository.getMischungxlNode(nid);
    }

    public Flux<JsonSetNode> getNodes() {
        //return Flux.just(SetNodeFactory.createNode173893(), SetNodeFactory.createNode178903());
        return Flux.empty();
    }


    public Flux<JsonSetNode> getNodes(Mono<List<Integer>> nodes) {
        //return nodes.flatMapMany(n -> Flux.just(SetNodeFactory.createNode173893(), SetNodeFactory.createNode178903()));
        return Flux.empty();
    }




}
