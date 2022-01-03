package com.example.microservices.gatewayservice.filter;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.microservices.gatewayservice.service.jwt.ClaimsData;
import com.example.microservices.gatewayservice.service.jwt.JWTUtilsService;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationGatewayFilterFactory
		extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final List<String> PUBLIC_ENDPOINTS = List.of("/auth/login");

	@Autowired
	private JWTUtilsService jwtUtilsService;

	public AuthenticationGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(final Config config) {
		return ((exchange, chain) -> {
			final ServerHttpRequest request = exchange.getRequest();

			// In case the route is a public one, we just skip the token check
			if (!isRouteSecured(request.getURI())) {
				return chain.filter(exchange);
			}

			if (!request.getHeaders()
						.containsKey(AUTHORIZATION_HEADER)) {
				return buildUnhautorizedResponse(exchange);
			}

			final String token = request.getHeaders()
										.getOrEmpty(AUTHORIZATION_HEADER)
										.get(0)
										.substring(TOKEN_PREFIX.length());

			if (!jwtUtilsService.isTokenValid(token)) {
				return buildUnhautorizedResponse(exchange);
			}

			final ClaimsData claims = jwtUtilsService.getClaims(token);
			exchange.getRequest()
					.mutate()
					.header("userId", String.valueOf(claims.getUserId()))
					.header("userName", String.valueOf(claims.getUserName()))
					.build();

			return chain.filter(exchange);
		});
	}

	private Mono<Void> buildUnhautorizedResponse(final ServerWebExchange exchange) {
		final ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);

		return response.setComplete();
	}

	private boolean isRouteSecured(final URI uri) {
		return PUBLIC_ENDPOINTS	.stream()
								.noneMatch(publicUri -> uri	.getPath()
															.contains(publicUri));
	}

	public static class Config {

	}
}
