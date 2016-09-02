package com.endava.wiki.service.impl;

import com.endava.wiki.service.ArticleParserService;
import com.endava.wiki.service.HttpRequestService;
import com.endava.wiki.utils.CommonWordsContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aancuta on 8/10/2016.
 */
@Service
public class JsonArticleParserService implements ArticleParserService {

    private static final String WORD_SPLIT_REGEX = "[^\\p{L}\\p{N}]";

    private Map<String, Integer> workFrequency;

    public Map<String, Map<String, Integer>> getArticlesWordFrequency() {
        return articlesWordFrequency;
    }

    private Map<String, Map<String, Integer>> articlesWordFrequency;

    @Autowired
    private HttpRequestService httpRequestService;

    public void refreshWordMap() {
        workFrequency = new ConcurrentHashMap<>();
        articlesWordFrequency = new HashMap<>();
    }

    @Override
    public Map<String, Integer> getWordFrequency() {
        return workFrequency;
    }

    @Override
    public Map<String, Integer> countWordsInArticle(String articleName, boolean countCommonWords) {
        Map<String, Integer> articleWordFrequency = new HashMap<>();

        try {
            JsonElement jsonElement = new JsonParser()
                    .parse(new InputStreamReader(httpRequestService.getContent(articleName)));
            JsonElement pages = jsonElement.getAsJsonObject()
                    .get("query").getAsJsonObject().get("pages");

            Set<Map.Entry<String, JsonElement>> entrySet = pages.getAsJsonObject().entrySet();

            JsonElement contentHolder = null;
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                contentHolder = entry.getValue();
            }

            if (contentHolder != null) {
                JsonElement extract = contentHolder.getAsJsonObject().get("extract");
                if (extract != null) {
                    String articleContent = extract.getAsString();
                    try (BufferedReader bufferedReader = new BufferedReader(new StringReader(articleContent))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] tokens = line.split(WORD_SPLIT_REGEX);
                            for (String word : tokens) {
                                word = word.toLowerCase();
                                // Add words to the map
                                if (countCommonWords == true) {
                                    if (!word.equals("")) {
                                        workFrequency.put(word, workFrequency.getOrDefault(word, 0) + 1);
                                    }
                                } else {
                                    if (!word.equals("") && !CommonWordsContainer.isCommonWord(word)) {
                                        workFrequency.put(word, workFrequency.getOrDefault(word, 0) + 1);
                                        articleWordFrequency.put(word, articleWordFrequency.getOrDefault(word, 0) + 1);
                                    }
                                }
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        articlesWordFrequency.put(articleName, articleWordFrequency);
        return workFrequency;
    }
}
