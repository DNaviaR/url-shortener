package com.tools.urlshortener.controller; // Asegúrate de que el paquete coincide con tu estructura

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Esto busca el archivo index.html en src/main/resources/static/
        return "index.html";
    }
}