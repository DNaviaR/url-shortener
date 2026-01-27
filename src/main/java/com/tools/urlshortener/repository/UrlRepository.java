package com.tools.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tools.urlshortener.model.UrlMapping;

public interface UrlRepository extends JpaRepository<UrlMapping, Long>{
    public Optional<UrlMapping> findByShortCode(String shortCode);
}
