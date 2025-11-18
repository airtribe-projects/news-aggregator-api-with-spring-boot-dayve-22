package com.dayve22.newsservice.service;

import com.dayve22.newsservice.dto.PreferencesDto;
import com.dayve22.newsservice.model.NewsPreferences;
import com.dayve22.newsservice.model.User;
import com.dayve22.newsservice.repository.NewsPreferencesRepository;
import com.dayve22.newsservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferencesService {

    private final NewsPreferencesRepository preferencesRepository;
    private final UserRepository userRepository;

    /**
     * Helper method to get the currently authenticated user.
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Converts a NewsPreferences entity to its DTO representation.
     */
    private PreferencesDto mapToDto(NewsPreferences preferences) {
        return PreferencesDto.builder()
                .keywords(preferences.getKeywords())
                .sources(preferences.getSources())
                .build();
    }

    @Transactional(readOnly = true)
    public PreferencesDto getPreferences() {
        User user = getCurrentUser();

        // Find preferences by user. We already created an empty one on registration.
        NewsPreferences preferences = preferencesRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Preferences not found for user")); // Should not happen

        return mapToDto(preferences);
    }

    @Transactional
    public PreferencesDto updatePreferences(PreferencesDto preferencesDto) {
        User user = getCurrentUser();

        NewsPreferences preferences = preferencesRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Preferences not found for user"));

        // Update the preferences entity
        preferences.setKeywords(preferencesDto.getKeywords());
        preferences.setSources(preferencesDto.getSources());

        // Save the updated entity
        NewsPreferences savedPreferences = preferencesRepository.save(preferences);

        return mapToDto(savedPreferences);
    }
}
