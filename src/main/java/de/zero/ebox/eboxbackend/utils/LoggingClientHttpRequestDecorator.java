package de.zero.ebox.eboxbackend.utils;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


public class LoggingClientHttpRequestDecorator extends ClientHttpRequestDecorator {

	public LoggingClientHttpRequestDecorator(ClientHttpRequest delegate) {
		super(delegate);
	}


	@Override
	public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
		return super.writeAndFlushWith(body);
	}


	@Override
	public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
		// optionally aggregate for full body content
		body = DataBufferUtils.join(body)
				.doOnNext(content -> {
					System.out.println(content.toString(StandardCharsets.UTF_8));
				});
		return super.writeWith(body);
	}
}
