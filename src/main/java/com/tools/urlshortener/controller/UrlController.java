package com.tools.urlshortener.controller;

import com.tools.urlshortener.dto.ShortenRequest;
import com.tools.urlshortener.model.UrlMapping;
import com.tools.urlshortener.repository.UrlRepository;
import com.tools.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlShortenerService urlService;

    private final UrlRepository urlRepository;

    public UrlController(UrlShortenerService urlService, UrlRepository urlRepository) {
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    // 1. Crear URL corta
    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody ShortenRequest request) {
        UrlMapping mapping = urlService.shortenUrl(request.getUrl());
        // Devolvemos la URL completa para probar (ej: http://localhost:8080/abc12)
        String fullUrl = "http://localhost:8080/" + mapping.getShortCode();
        return ResponseEntity.ok(fullUrl);
    }

    // 2. Redireccionar (La magia)
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        try {
            // Intentamos buscar la URL
            String originalUrl = urlService.getOriginalUrl(code);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();
        } catch (RuntimeException e) {
            // Si no existe (ej: es favicon.ico o index.html), devolvemos 404 Not Found
            // en lugar de explotar con un 500.
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Ver estadísticas (Cuántos clicks lleva)
    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<UrlMapping> getStats(@PathVariable String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}