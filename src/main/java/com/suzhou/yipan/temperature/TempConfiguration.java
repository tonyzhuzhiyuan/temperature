package com.suzhou.yipan.temperature;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import reactor.core.publisher.Mono;

@SpringBootConfiguration
public class TempConfiguration {
	/*
	 配置keyresolver
	*/
	@Primary
	@Bean
	public KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}
}
