package de.zero.ebox.eboxbackend;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.io.File;

import static org.mockito.Mockito.when;


@WebFluxTest
@ContextConfiguration(classes = {LegacyMischungxlRouter.class, LegacyMischungxlHandler.class,
											DrupalForwardingRestClient.class})
class LegacyMischungxlHandlerTest {

	@Autowired
	private ApplicationContext context;

	@MockBean
	private EBoxService eBoxService;

	private WebTestClient webTestClient;

	@Value("classpath:/json/node_173893.json")
	File node173893;


	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().filter(logResponse()).build();
	}


	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse ->
				clientResponse
						.bodyToMono(String.class)
						//.doOnNext(System.out::println)
						.map(str -> ClientResponse.from(clientResponse).body(str).build())
		);
	}


	@Test
	public void testIndex() {
		webTestClient.get()
				.uri("/js-api/mischungxl?perpage=10")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(JsonSetNode.class)
				.hasSize(10);
	}

	@Test
	public void testGetNodes() {
		webTestClient.post()
				.uri("/js-api/mischungxl/getnodes")
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("[0]"))
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(JsonSetNode.class)
				.hasSize(0);

		webTestClient.post()
				.uri("/js-api/mischungxl/getnodes")
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("[173893]"))
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(JsonSetNode.class)
				.hasSize(1);
	}


	@Test
	public void testGetNodeById() {
		JsonSetNode node = JsonSetNode.builder().nid(1).title("ABC").build();
		when(eBoxService.getNode(1)).thenReturn(Mono.just(node));
		webTestClient.get()
				.uri("/js-api/mischungxl/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(JsonSetNode.class)
				.value(nodeResponse -> {
							Assertions.assertThat(nodeResponse.getNid()).isEqualTo(1);
							Assertions.assertThat(nodeResponse.getTitle()).isEqualTo("ABC");
						}
				);
	}

}