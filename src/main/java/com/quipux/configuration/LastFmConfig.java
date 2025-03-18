package com.quipux.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LastFmConfig {
	@Value("${lastfm.api.key}")
	private String apiKey;

	@Value("${lastfm.api.url}")
	private String apiUrl;

	public String getApiKey() {
		return apiKey;
	}

	public String getApiUrl() {
		return apiUrl + "&api_key=" + apiKey;
	}

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
