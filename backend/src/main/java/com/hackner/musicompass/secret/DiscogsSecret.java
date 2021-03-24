package com.hackner.musicompass.secret;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="discogs")
public class DiscogsSecret {

    private String token;

}
