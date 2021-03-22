package com.hackner.musicompass.secret;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Data
@Configuration
@Repository
@ConfigurationProperties(prefix = "discogs.auth")
public class DiscogsSecret {

    @Value("@discogs.token@")
    private String discogsToken;

}
