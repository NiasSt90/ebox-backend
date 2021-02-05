package de.zero.ebox.eboxbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LegacyMischungxlRouter {

	@Bean
	RouterFunction<ServerResponse> routes(LegacyMischungxlHandler handler) {
		return route()
				.GET("/js-api/mischungxl", handler::index)
				.GET("/js-api/mischungxl/{nid}", handler::getNode)
				.POST("/js-api/mischungxl/getnodes", handler::getNodes)
				.build();
	}

}
