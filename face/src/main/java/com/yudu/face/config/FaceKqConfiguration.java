package com.yudu.face.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FaceKqConfiguration {

    @Value("${faceKq.url1}")
    public String faceKqUrl1;
    @Value("${faceKq.url2}")
    public String faceKqUrl2;
    @Value("${faceKq.url3}")
    public String faceKqUrl3;
    @Value("${faceKq.url4}")
    public String faceKqUrl4;
    @Value("${faceKq.url5}")
    public String faceKqUrl5;
}
