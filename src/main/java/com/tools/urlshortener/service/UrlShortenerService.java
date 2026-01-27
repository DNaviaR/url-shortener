package com.tools.urlshortener.service;

import com.tools.urlshortener.model.UrlMapping;
import com.tools.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final String CARACTERES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LONGITUD_CODIGO = 6; // Longitud del código corto

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlMapping shortenUrl(String longUrl) {
        // 1. Generar un código único
        String shortCode = generateRandomCode();

        // 2. Guardar en base de datos
        // (Nota: En un sistema real, comprobaríamos si el código ya existe para evitar duplicados)
        UrlMapping mapping = new UrlMapping(shortCode, longUrl);
        return urlRepository.save(mapping);
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            int index = random.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }
        return sb.toString();
    }
    
    // Método para buscar la URL larga E INCREMENTAR VISITAS
    public String getOriginalUrl(String shortCode) {
        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL no encontrada"));

        // Sumamos 1 visita
        mapping.setClicks(mapping.getClicks() + 1);
        urlRepository.save(mapping); // Guardamos el contador actualizado

        return mapping.getLongUrl();
    }
}