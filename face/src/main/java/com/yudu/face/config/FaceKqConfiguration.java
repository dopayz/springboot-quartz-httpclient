package com.yudu.face.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FaceKqConfiguration {

    @Value("${facekq.url}")
    public String url;
}
