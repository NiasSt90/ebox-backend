package de.zero.ebox.eboxbackend.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;


public class LoggingClientHttpConnectorWrapper implements ClientHttpConnector {


	private final ClientHttpConnector delegate;

	public LoggingClientHttpConnectorWrapper(ClientHttpConnector delegate) {
		this.delegate = delegate;
	}

	@Override
	public Mono<ClientHttpResponse> connect(HttpMethod method, URI uri,
			Function<? super ClientHttpRequest, Mono<Void>> requestCallback) {
		return this.delegate.connect(method, uri,
				request -> requestCallback.apply(new LoggingClientHttpRequestDecorator(request)));
	}

}
