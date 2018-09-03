package com.boxuegu.sms.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChannelConfigAPITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveChannelConfig() {
        Map<String, ?> urlVariables = new HashMap<>();

//        this.restTemplate.postForEntity("http://localhost:" + port + "/api/channel/config", String.class, );
    }
}
