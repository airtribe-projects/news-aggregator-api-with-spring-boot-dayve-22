package com.dayve22.newsservice.repository;

import com.dayve22.newsservice.model.NewsPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsPreferencesRepository extends JpaRepository<NewsPreferences, Long> {

    // We'll use this to find preferences by user ID
    Optional<NewsPreferences> findByUserId(Long userId);
}