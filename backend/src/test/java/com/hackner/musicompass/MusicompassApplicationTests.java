package com.hackner.musicompass;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "discogs.token=testtest")
class MusicompassApplicationTests {

    @Test
    void contextLoads() {
    }

}
