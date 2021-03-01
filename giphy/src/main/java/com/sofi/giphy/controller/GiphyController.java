package com.sofi.giphy.controller;

import com.sofi.giphy.exception.FailedApiCallException;
import com.sofi.giphy.service.GiphyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiphyController {

    @Autowired
    private GiphyAccessor giphyAccessor;

    @GetMapping("/search/{search term}")
    public @ResponseBody String getGiphyData(@PathVariable(value = "search term") String searchTerm)
            throws FailedApiCallException {
        final String gifData = giphyAccessor.getGifs(searchTerm);
        return gifData;
    }

}
