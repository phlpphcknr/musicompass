package com.hackner.musicompass.secret;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DiscogsSecret {

    @Value("${discogs.token}")
    private String discogsToken;

}
