package com.endava.wiki.service;

import java.io.InputStream;

/**
 * Created by aancuta on 8/10/2016.
 */
public interface HttpRequestService {
    /**
     * Returns the content of the Wikipedia MediaAPI response for a given title
     * @param query Title of a wikipedia article
     * @return InputStream
     */
    InputStream getContent(String query);
}
