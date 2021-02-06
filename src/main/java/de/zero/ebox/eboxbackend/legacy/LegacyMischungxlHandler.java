package de.zero.ebox.eboxbackend.legacy;

import de.zero.ebox.eboxbackend.DrupalForwardingRestClient;
import de.zero.ebox.eboxbackend.EBoxService;
import de.zero.ebox.eboxbackend.data.MischungxlRepository;
import de.zero.ebox.eboxbackend.model.JsonSetNode;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;


/**
 * If already implemented, parse parameters from request and handover call to {@link EBoxService}<br/>
 * if not implemented, forward the call to the old drupal backend.
 */
@Service
@AllArgsConstructor
public class LegacyMischungxlHandler {

    private static ParameterizedTypeReference<List<Integer>> typeRefListInteger = new ParameterizedTypeReference<>() {};

    private final EBoxService eBoxService;

    private final DrupalForwardingRestClient drupalForwardingClient;

    private final MischungxlRepository repository;


    /**
     * Retrieves a mischungxl node
     * @param request with node id as path-parameter
     * @return node
     */
    public Mono<ServerResponse> retrieve(ServerRequest request) {
        int nid = Integer.parseInt(request.pathVariable("nid"));
        return ok().body(repository.getMischungxlNode(nid), JsonSetNode.class);

    }

    /**
     * Retrieves a listing of mischungxl nodes
     *
     * @param request with perpage/page and SearchSpec URL-parameters
     * @return array of nodes
     */
    public Mono<ServerResponse> index(ServerRequest request) {
        return drupalForwardingClient.forwardGet(request);
    }


    /**
     * delete a node (Admin-only)
     * @param request request with node id as path-parameter
     * @return {successful:$Boolean, message: $String}
     */
    public Mono<ServerResponse> delete(ServerRequest request) {
        return drupalForwardingClient.forwardDelete(request);
    }


    /**
     * Returns the details of currently logged in user
     * @param request
     * @return
     */
    public Mono<ServerResponse> connect(ServerRequest request) {
        return drupalForwardingClient.forwardPost(request);
    }

    /**
     * Vote a set
     * @param request with parameters id, vote, timestamp
     * @return node
     */
    public Mono<ServerResponse> vote(ServerRequest request) {
        return drupalForwardingClient.forwardPost(request);
    }


    public Mono<ServerResponse> getNode(ServerRequest request) {
        return ok().body(eBoxService.getNode(Integer.parseInt(request.pathVariable("nid"))), JsonSetNode.class);
    }


    public Mono<ServerResponse> forwardGet(ServerRequest request) {
        return drupalForwardingClient.forwardGet(request);
    }
    public Mono<ServerResponse> forwardPost(ServerRequest request) {
        return drupalForwardingClient.forwardPost(request);
    }
}
