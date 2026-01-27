package com.tools.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortCode; // Ej: "8jK9L"

    @Column(nullable = false)
    private String longUrl;   // Ej: "https://google.com..."

    private LocalDateTime createdDate = LocalDateTime.now();

    private int clicks = 0;

    public UrlMapping(String shortCode, String longUrl) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
    }
}