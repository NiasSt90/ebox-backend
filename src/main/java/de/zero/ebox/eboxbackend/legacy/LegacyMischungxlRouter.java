package de.zero.ebox.eboxbackend.legacy;

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
				.GET("/js-api/mischungxl", handler::index)//search/list nodes
				.GET("/js-api/mischungxl/{nid}", handler::retrieve)//retrieve single node
				.DELETE("/js-api/mischungxl/{nid}", handler::delete)//delete single node @ADMIN ONLY
				.POST("/js-api/mischungxl/connect", handler::forwardPost)
				.POST("/js-api/mischungxl/vote", handler::forwardPost)
				.POST("/js-api/mischungxl/playinform", handler::forwardPost)
				.POST("/js-api/mischungxl/djs", handler::forwardPost)
				.POST("/js-api/mischungxl/modifiedsets", handler::forwardPost)
				.POST("/js-api/mischungxl/history", handler::forwardPost)
				.POST("/js-api/mischungxl/modifiedscores", handler::forwardPost)
				.POST("/js-api/mischungxl/modifiedartists", handler::forwardPost)
				.POST("/js-api/mischungxl/disableset", handler::forwardPost)
				.POST("/js-api/mischungxl/forcemp3info", handler::forwardPost)
				.POST("/js-api/mischungxl/addbookmark", handler::forwardPost)
				.POST("/js-api/mischungxl/delbookmark", handler::forwardPost)
				.POST("/js-api/mischungxl/filtercheck", handler::forwardPost)
				.POST("/js-api/mischungxl/editfilter", handler::forwardPost)
				.POST("/js-api/mischungxl/deletefilter", handler::forwardPost)
				.POST("/js-api/mischungxl/getfilters", handler::forwardPost)
				.POST("/js-api/mischungxl/editnode", handler::forwardPost)
				.POST("/js-api/mischungxl/getnodes", handler::forwardPost)
				.POST("/js-api/mischungxl/artistinfo", handler::forwardPost)

				.POST("/js-api/comment", handler::forwardPost)//create comment
				.POST("/js-api/user/login", handler::forwardPost)//login (@FormUrlEncoded)

				.build();
	}

}
