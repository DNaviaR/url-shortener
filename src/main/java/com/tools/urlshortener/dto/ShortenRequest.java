package com.tools.urlshortener.dto;

import lombok.Data;

@Data
public class ShortenRequest {
    private String url; // La URL larga que el usuario quiere acortar
}