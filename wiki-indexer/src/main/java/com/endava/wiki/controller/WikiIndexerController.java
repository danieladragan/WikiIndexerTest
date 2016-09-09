package com.endava.wiki.controller;

import com.endava.wiki.dto.ArticleDTO;
import com.endava.wiki.dto.MetadataDTO;
import com.endava.wiki.dto.WordDTO;
import com.endava.wiki.service.WordFrequencyService;
import com.endava.wiki.service.impl.FindWordServiceImpl;
import com.endava.wiki.service.impl.ReadToArrayFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by aancuta on 8/10/2016.
 */
@RestController
@RequestMapping("/wiki-indexer")
public class WikiIndexerController {

    private static final int WORDS_TO_SHOW = 10;
    private static String UPLOAD_LOCATION = "../wiki-indexer/src/main/resources/";
    private static String UPLOADED_FILENAME = "titles.txt";

    @Value("${ip}")
    private String IP;

    @Autowired
    WordFrequencyService wordFrequencyService;

    @Autowired
    ReadToArrayFileService readToArrayFileService;

    @Autowired
    FindWordServiceImpl findWordService;

    @RequestMapping(value = "/indexate", method = RequestMethod.GET)
    public ArticleDTO getWordFrequency(@RequestParam(value = "title") String title) {
        ArticleDTO ret = wordFrequencyService.getWordsByFrequency(title);
        return ret;
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ArticleDTO getWordFrequencyBatch(@RequestParam(value = "file") MultipartFile file) {
        try {
            File localFile = new File(UPLOAD_LOCATION + UPLOADED_FILENAME);
            FileCopyUtils.copy(file.getBytes(), localFile);
            List<String> titles = readToArrayFileService.readLines(localFile);
            return wordFrequencyService.getWordsByFrequencyInMultipleArticles(titles);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ArticleDTO getSearchedWord(@RequestParam(value = "word") String wordToBeSearched, @RequestParam(value = "title") String title) {
        WordDTO wordDTO = findWordService.findWord(wordToBeSearched, title);
        ArticleDTO articleDTO = wordFrequencyService.getWordsByFrequency(title);
        articleDTO.setWordDTO(wordDTO);
        return articleDTO;
    }

    @RequestMapping(value = "/init")
    public MetadataDTO getInitData() {

        MetadataDTO metadataDTO = new MetadataDTO();

        try {
            if (InetAddress.getLocalHost().getHostAddress().equals(IP)) {
                System.out.println("equals!");
                metadataDTO.setEnvironment(1);
            } else {
                System.out.println("not equals!");
                metadataDTO.setEnvironment(0); //release
            }
//            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return metadataDTO;
    }
}
