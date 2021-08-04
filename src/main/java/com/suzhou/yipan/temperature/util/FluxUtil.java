package com.suzhou.yipan.temperature.util;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxUtil {
	public static Mono<Void> writeFlux(ServerWebExchange exchange,
			String recMsg, GatewayFilterChain chain) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
		byte[] bytes = recMsg.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Flux.just(buffer)).then(
				chain.filter(exchange));
	}
}
