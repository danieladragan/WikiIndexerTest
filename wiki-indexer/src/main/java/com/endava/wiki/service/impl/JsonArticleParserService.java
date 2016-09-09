package com.endava.wiki.service.impl;

import com.endava.wiki.service.ArticleParserService;
import com.endava.wiki.service.HttpRequestService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;
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

    private static final List<String> ignoreAll = Arrays.asList("the", "of", "a", "and", "in", "to", "are",
            "as", "that", "is", "with", "for", "or", "they", "be", "on", "their", "have", "other", "used",
            "from", "can", "also", "such", "were", "an", "by", "which", "in", "this", "often", "even", "had",
            "has", "not", "been", "some", "it", "n", "many", "its", "s", "000", "nthe", "use", "frp", "but",
            "ten", "half", "de", "at", "was", "most", "1", "u2014", "u", "san", "i", "ii", "k", "first",
            "c", "st","his", "her","he","she", "0", "2", "3", "4", "5", "6","7","8","9",
            "ten", "half", "de", "at", "was", "most", "1","u","san", "who", "0");

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
                                    if (!word.equals("") && !ignoreAll.contains(word)) {
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
