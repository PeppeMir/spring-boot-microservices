package com.example.microservices.galleryservice.controller;

import java.text.MessageFormat;
import java.util.List;

import com.example.microservices.galleryservice.model.Gallery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/galleries")
public class GalleryController {
    private final Logger log = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Environment env;

    @GetMapping("/{id}")
    public Mono<Gallery> get(@PathVariable Integer id) {
        log.info(MessageFormat.format("Gallery Service running at port {0} answering...", env.getProperty("local.server.port")));

		return webClientBuilder.build().get().uri("http://image-service/images")
        				.retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                        .map(images -> {
                            final Gallery gallery = new Gallery();

                            gallery.setId(id);
                            gallery.setImages(images);

                            return gallery;
                        });
    }
}
