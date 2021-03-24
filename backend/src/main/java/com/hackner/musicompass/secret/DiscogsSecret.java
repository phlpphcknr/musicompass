package com.hackner.musicompass.secret;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="discogs")
public class DiscogsSecret {

    //@Value("${discogs.token}")
    private String token;

}
