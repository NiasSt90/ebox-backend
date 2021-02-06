package de.zero.ebox.eboxbackend.legacy;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;


/**
 * compare new implementation with old (drupal based) one.
 */
@SpringBootTest
@AutoConfigureWebTestClient
class LegacyMischungxlIntegrationTest {

	@Autowired
	private ApplicationContext context;

	@Value("${ebox.legacy_backend.url}")
	private String legacyBackendUrl;

	private WebTestClient webTestClient;

	private WebClient webClient;


	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().build();
		webClient = WebClient.builder().baseUrl(legacyBackendUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name())
				.build();
	}


	@Test
	void index() {
		String uri = "/js-api/mischungxl/173893";
		JsonSetNode oldNode = webClient.get().uri(uri)
				.retrieve()
				.bodyToMono(JsonSetNode.class)
				.block();
		webTestClient.get().uri(uri)
				.exchange()
				.expectBody(JsonSetNode.class)
				.value(Matchers.equalTo(oldNode));
	}
}