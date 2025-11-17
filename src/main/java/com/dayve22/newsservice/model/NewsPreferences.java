package com.dayve22.newsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NewsPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Stores a list of simple strings (e.g., "tech", "finance")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "preference_keywords", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    // Stores a list of preferred news sources (e.g., "bbc-news", "cnn")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "preference_sources", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "source")
    private List<String> sources;
}
