package com.endava.wiki.service.impl;

import com.endava.wiki.dto.WordDTO;
import com.endava.wiki.service.ArticleParserService;
import com.endava.wiki.service.FindWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by bsoimu on 8/26/2016.
 */
@Service
public class FindWordServiceImpl implements FindWordService {

    @Autowired
    ArticleParserService articleParserService;

    public WordDTO findWord(String wordToBeFound, String title) {
        WordDTO wordDTO = new WordDTO();

        wordToBeFound = wordToBeFound.toLowerCase();

        articleParserService.refreshWordMap();

        Map<String, Integer> wordFrequency = articleParserService.countWordsInArticle(title);

        Integer occurences = wordFrequency.get(wordToBeFound);
        if( occurences == null){
            occurences = new Integer(0);
        }

        wordDTO.setWord(wordToBeFound);
        wordDTO.setOccurrences(occurences);


        return wordDTO;
    }
}
