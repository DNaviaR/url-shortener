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
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        // Devolvemos un 302 (Found) y en la cabecera "Location" ponemos la URL destino.
        // El navegador leerá esto y cambiará de página automáticamente.
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    // 3. Ver estadísticas (Cuántos clicks lleva)
    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<UrlMapping> getStats(@PathVariable String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}