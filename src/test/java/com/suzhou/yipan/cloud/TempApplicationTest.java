package com.suzhou.yipan.cloud;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempApplicationTest {
	
/*	@Autowired
	private TestRestTemplate rest;
	*/
	protected WebTestClient client;

	protected String baseUri;

	@Test
	public void validLoacationTest() {
		baseUri = "http://localhost:9090/" +"temp/getTemperature/101190401.do";
		this.client = WebTestClient.bindToServer().responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
		client.post().uri("/post").bodyValue("101190401").exchange().expectStatus()
		.isOk().expectHeader().valueEquals("X-TestHeader", "read_body_pred").expectBody();
	}

	@Test
	public void inValidLocationTest(){
		baseUri = "http://localhost:9090/" +"temp/getTemperature/1011904yy.do";
		this.client = WebTestClient.bindToServer().responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
		client.post().uri("/post").bodyValue("1011904qq").exchange().expectStatus()
		.isOk().expectHeader().valueEquals("X-TestHeader", "read_body_pred").expectBody();
	}

}
