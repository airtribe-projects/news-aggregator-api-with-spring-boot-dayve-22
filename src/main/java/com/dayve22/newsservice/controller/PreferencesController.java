package com.dayve22.newsservice.controller;

import com.dayve22.newsservice.dto.PreferencesDto;
import com.dayve22.newsservice.service.PreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferencesController {

    private final PreferencesService preferencesService;

    @GetMapping
    public ResponseEntity<PreferencesDto> getPreferences() {
        // The service will automatically get preferences for the logged-in user
        return ResponseEntity.ok(preferencesService.getPreferences());
    }

    @PutMapping
    public ResponseEntity<PreferencesDto> updatePreferences(
            @RequestBody PreferencesDto preferencesDto
    ) {
        // The service will update preferences for the logged-in user
        return ResponseEntity.ok(preferencesService.updatePreferences(preferencesDto));
    }
}
