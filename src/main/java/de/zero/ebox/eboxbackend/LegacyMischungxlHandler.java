package de.zero.ebox.eboxbackend;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@AllArgsConstructor
public class LegacyMischungxlHandler {

    private static ParameterizedTypeReference<List<Integer>> typeRefListInteger = new ParameterizedTypeReference<>() {};

    private final EBoxService eBoxService;

    private final DrupalForwardingRestClient drupalForwardingClient;

    public Mono<ServerResponse> index(ServerRequest request) {
        return drupalForwardingClient.forwardGet(request);
    }

    public Mono<ServerResponse> getNodes(ServerRequest request) {
        return drupalForwardingClient.forwardPost(request);
    }

    public Mono<ServerResponse> getNode(ServerRequest request) {
        return ok().body(eBoxService.getNode(Integer.parseInt(request.pathVariable("nid"))), JsonSetNode.class);
    }

}
