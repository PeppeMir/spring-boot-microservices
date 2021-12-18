package com.example.microservices.imageservice.controller;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import com.example.microservices.imageservice.model.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private Environment env;
    
    @GetMapping
    public Mono<List<Image>> getAll() {
        log.info(MessageFormat.format("Gallery Service running at port {0} answering...", env.getProperty("local.server.port")));

        return Mono.just(Arrays.asList(
			new Image(1, "Treehouse of Horror V", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3842005760"),
			new Image(2, "The Town", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3698134272"),
			new Image(3, "The Last Traction Hero", "https://www.imdb.com/title/tt0096697/mediaviewer/rm1445594112")));
    }
}
