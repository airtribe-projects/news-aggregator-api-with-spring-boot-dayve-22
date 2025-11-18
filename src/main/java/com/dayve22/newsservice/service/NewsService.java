package com.dayve22.newsservice.service;

import com.dayve22.newsservice.dto.PreferencesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final WebClient webClient;
    private final PreferencesService preferencesService; // Re-use our existing service

    @Value("${newsapi.key}")
    private String apiKey;

    // Base URL for the NewsAPI "everything" endpoint
    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything";

    public Object getNews() {
        // 1. Get the user's preferences
        PreferencesDto preferences = preferencesService.getPreferences();
        List<String> keywords = preferences.getKeywords();
        List<String> sources = preferences.getSources();

        // 2. Build the query string
        // Example: q=java OR spring boot
        String query = keywords.isEmpty() ? "latest" : String.join(" OR ", keywords);

        // Example: sources=bbc-news,techcrunch
        String sourcesQuery = String.join(",", sources);

        // 3. Build the URI with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(NEWS_API_URL)
                .queryParam("apiKey", apiKey)
                .queryParam("q", query);

        if (!sourcesQuery.isEmpty()) {
            uriBuilder.queryParam("sources", sourcesQuery);
        } else {
            // If no sources are specified, NewsAPI requires 'q' or 'domains'
            // 'q' is already added.
        }

        URI uri = uriBuilder.build().toUri();
        System.out.println("Fetching news from URI: " + uri);

        // 4. Make the asynchronous call using WebClient
        // .block() makes this call synchronous for simplicity.
        // In a fully reactive app, you would return Mono<Object>
        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Object.class) // Deserialize to Object for simplicity
                    .block(); // Block to wait for the response
        } catch (Exception e) {
            // In a real app, handle WebClientResponseException
            throw new RuntimeException("Error fetching news from external API: " + e.getMessage(), e);
        }
    }
}
