package com.hackner.musicompass.secret;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Data
@Configuration
@Repository
public class DiscogsSecret {

    @Value("@discogs.token@")
    private String discogsToken;

}
