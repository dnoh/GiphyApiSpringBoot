package com.sofi.giphy.service;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GiphyAccessorTest {
    private static final String JSON_LESS_THAN_FIVE = "{\"data\":[{\"id\":1,\"url\":\"Todo 1\",\"completed\":false}," +
            "{\"id\":2,\"url\":\"Todo 2\",\"completed\":false},{\"id\":3,\"url\":\"Todo 3\",\"completed\":true}]}";
    private static final String JSON_FIVE_ENTRIES = "{\"data\":[{\"id\":1,\"url\":\"Todo 1\",\"completed\":false}," +
            "{\"id\":2,\"url\":\"Todo 2\",\"completed\":false},{\"id\":3,\"url\":\"Todo 3\",\"completed\":true}," +
            "{\"id\":4,\"url\":\"Todo 3\",\"completed\":true},{\"id\":5,\"url\":\"Todo 3\",\"completed\":true}]}";
    private static final String JSON_RESPONSE_FIVE_ENTRIES = "{\"data\":[{\"gif_id\":\"1\",\"url\":\"Todo 1\"}," +
            "{\"gif_id\":\"2\",\"url\":\"Todo 2\"},{\"gif_id\":\"3\",\"url\":\"Todo 3\"}," +
            "{\"gif_id\":\"4\",\"url\":\"Todo 3\"},{\"gif_id\":\"5\",\"url\":\"Todo 3\"}]}";
    private static final String JSON_RESPONSE_LESS_THAN_FIVE = "{\"data\":[]}";

    @InjectMocks
    private GiphyAccessor giphyAccessor = new GiphyAccessor();

    @Mock
    private RestTemplate restTemplate;

    @SneakyThrows
    @Test
    public void testGetGifs() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(JSON_LESS_THAN_FIVE);
        final String response = giphyAccessor.getGifs("test");
        Assert.assertEquals(response, JSON_RESPONSE_LESS_THAN_FIVE);

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(JSON_FIVE_ENTRIES);
        final String response2 = giphyAccessor.getGifs("test");
        Assert.assertEquals(response2, JSON_RESPONSE_FIVE_ENTRIES);
    }
}
