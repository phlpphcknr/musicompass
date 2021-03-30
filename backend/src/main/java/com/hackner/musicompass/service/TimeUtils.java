package com.hackner.musicompass.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeUtils {

    public Instant now(){
        return Instant.now();
    }
}