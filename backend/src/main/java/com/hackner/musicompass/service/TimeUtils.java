package com.hackner.musicompass.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TimeUtils {

    public Instant now(){ return Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }
}