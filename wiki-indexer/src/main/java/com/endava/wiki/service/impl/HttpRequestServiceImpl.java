package com.endava.wiki.service.impl;

import com.endava.wiki.service.HttpRequestService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created by aancuta on 8/10/2016.
 */
@Service
public class HttpRequestServiceImpl implements HttpRequestService {
    private static final String API_URL = "https://en.wikipedia.org/w/api.php"
                                        + "?format=json&action=query&prop=extracts&explaintext=&titles=%s";
    @Override
    public InputStream getContent(String query) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(String.format(API_URL, URLEncoder.encode(query,"UTF-8")));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return httpResponse.getEntity().getContent();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
