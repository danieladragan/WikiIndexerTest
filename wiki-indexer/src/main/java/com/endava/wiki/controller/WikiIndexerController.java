package com.endava.wiki.controller;

import com.endava.wiki.dto.ArticleDTO;
import com.endava.wiki.service.WordFrequencyService;
import com.endava.wiki.service.impl.ReadToArrayFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * Created by aancuta on 8/10/2016.
 */
@RestController
@RequestMapping("/wiki-indexer")
public class WikiIndexerController {

    private static final int WORDS_TO_SHOW = 10;
    private static String UPLOAD_LOCATION = "../wiki-indexer/src/main/resources/";
    private static String UPLOADED_FILENAME ="titles.txt";

    @Autowired
    WordFrequencyService wordFrequencyService;

    @Autowired
    ReadToArrayFileService readToArrayFileService;

    @RequestMapping(method = RequestMethod.GET)
    public ArticleDTO getWordFrequency(@RequestParam(value = "title") String title) {
        return wordFrequencyService.getWordsByFrequency(title, WORDS_TO_SHOW);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ArticleDTO getWordFrequencyBatch(@RequestParam(value = "file") MultipartFile file) {
        try {
            File localFile =  new File(UPLOAD_LOCATION + UPLOADED_FILENAME);
            FileCopyUtils.copy(file.getBytes(), localFile);
            List<String> titles = readToArrayFileService.readLines(localFile);
            return wordFrequencyService.getWordsByFrequencyInMultipleArticles(titles, WORDS_TO_SHOW);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
