package com.sofi.giphy.service;

import com.google.gson.Gson;
import com.sofi.giphy.model.GifDataList;
import com.sofi.giphy.util.JsonUtil;
import com.sofi.giphy.exception.FailedApiCallException;
import com.sofi.giphy.model.GifData;
import org.json.JSONArray;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Component("giphyAccessor")
public class GiphyAccessor {

    private static final String API_KEY = System.getenv("GIPHY_API_KEY");
    private static final String SEARCH_URI = "https://api.giphy.com/v1/gifs/search";
    private static final String RESPONSE_KEY = "data";
    private static final String SEARCH_RESULTS_LIMIT = "5";
    private static final String URI_ERROR_FORMAT = "Error when creating the URI with these parameters {}, {}, {}";

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Creates a proper URI to hit the Giphy endpoint and converts it to a Json string to represent the response.
     * @param searchTerm Keyword used to search through Giphy API
     * @return String representing JSON objects retrieved from Giphy
     * @throws FailedApiCallException
     */
    public String getGifs(final String searchTerm) throws FailedApiCallException {
        final List<GifData> responseDataList = new ArrayList<>();
        final JsonUtil jsonUtil = new JsonUtil();

        try {
            final String uri =  new URIBuilder(SEARCH_URI)
                    .addParameter("api_key", API_KEY)
                    .addParameter("q", searchTerm)
                    .addParameter("limit", SEARCH_RESULTS_LIMIT)
                    .build()
                    .toString();

            final String responseJsonString = restTemplate.getForObject(uri, String.class);
            final JSONArray jsonArray = jsonUtil.getJsonArrayByKey(responseJsonString, RESPONSE_KEY);

            for (int index = 0; index < jsonArray.length(); index++) {
                final String id = jsonArray.getJSONObject(index).getString("id");
                final String url = jsonArray.getJSONObject(index).getString("url");
                responseDataList.add(new GifData(id,url));
            }

            if (jsonArray.length() < 5) {
                return new Gson().toJson(new GifDataList(new ArrayList<>()));
            }
            return new Gson().toJson(new GifDataList(responseDataList));
        } catch (URISyntaxException uriSyntaxException) {
            final String uriErrorString = String.format(URI_ERROR_FORMAT, API_KEY, searchTerm, SEARCH_RESULTS_LIMIT);
            LOGGER.log(Level.WARNING, uriErrorString);
            throw new FailedApiCallException(uriErrorString);
        } catch (Exception exception) {
            final String generalErrorString = "Error when calling Giphy's Search API";
            LOGGER.log(Level.WARNING, exception.toString());
            throw new FailedApiCallException(generalErrorString);
        }
    }
}
