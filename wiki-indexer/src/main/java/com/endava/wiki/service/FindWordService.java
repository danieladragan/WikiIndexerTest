package com.endava.wiki.service;

import com.endava.wiki.dto.WordDTO;

/**
 * Created by bsoimu on 8/26/2016.
 */
public interface FindWordService {

    WordDTO findWord(String wordToBeFound, String title);
}
