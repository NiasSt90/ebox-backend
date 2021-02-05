package de.zero.ebox.eboxbackend;

import de.zero.ebox.eboxbackend.utils.LoggingClientHttpConnectorWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Flow;


@Service
public class DrupalForwardingRestClient {

	@Value("${ebox.legacy_backend.url}")
	private String legacyBackendUrl;

	private WebClient client;


	@PostConstruct
	private void postConstruct() {
		client = WebClient.builder()
				.baseUrl(legacyBackendUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.clientConnector(new LoggingClientHttpConnectorWrapper(new ReactorClientHttpConnector(HttpClient.create())))
				.build();
	}


	public Mono<ServerResponse> forwardGet(ServerRequest request) {
		return client.get()
				.uri(request.uri().toString())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(StandardCharsets.UTF_8)
				.exchange()
				.flatMap(clientResponse ->
						ServerResponse.status(clientResponse.statusCode())
								.headers(httpHeaders -> httpHeaders.addAll(clientResponse.headers().asHttpHeaders()))
								.body(BodyInserters.fromDataBuffers(clientResponse.body(BodyExtractors.toDataBuffers()))));
	}


	public Mono<ServerResponse> forwardPost(ServerRequest request) {
		return client.post()
				.uri(request.uri().toString())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(StandardCharsets.UTF_8)
				.body(BodyInserters.fromDataBuffers(request.body(BodyExtractors.toDataBuffers())))
				.exchange()
				.flatMap(clientResponse ->
						ServerResponse.status(clientResponse.statusCode())
								.headers(httpHeaders -> httpHeaders.addAll(clientResponse.headers().asHttpHeaders()))
								.body(BodyInserters.fromDataBuffers(clientResponse.body(BodyExtractors.toDataBuffers()))));
	}

}
