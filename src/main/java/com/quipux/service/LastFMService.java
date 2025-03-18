package com.quipux.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.quipux.model.LastFMResponse;
import com.quipux.model.Tag;

@Service
public class LastFMService {

    private final WebClient webClient;

    @Value("${lastfm.api.url}")
    private String apiUrl;

    public LastFMService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> getGenres() {
    	LastFMResponse response = webClient.get()
    		    .uri(apiUrl)
    		    .retrieve()
    		    .bodyToMono(LastFMResponse.class)
    		    .block();
    	return response.getToptags().getTag().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
