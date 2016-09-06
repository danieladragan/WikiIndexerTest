package com.endava.wiki.service.impl;

import com.endava.wiki.dto.ArticleDTO;
import com.endava.wiki.dto.WordDTO;
import com.endava.wiki.models.WikiArticleEntity;
import com.endava.wiki.models.WordFrequencyEntity;
import com.endava.wiki.repository.WikiArticleRepository;
import com.endava.wiki.service.WikiArticleService;
import com.endava.wiki.service.WordFrequencyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsima on 8/11/2016.
 */
@Service
public class WikiServiceArticleImpl implements WikiArticleService {

    @Autowired
    private WikiArticleRepository wikiArticleRepository;

    @Autowired
    private WordFrequencyEntityService wordFrequencyEntityService;

    @Override
    @Transactional(readOnly = true)
    public WikiArticleEntity findByTitle(String title) {
        return wikiArticleRepository.findByTitle(title);
    }

    @Override
    public void saveArticle(ArticleDTO articleDTO) {
        WikiArticleEntity wikiArticleEntity = new WikiArticleEntity();

        wikiArticleEntity.setTitle(articleDTO.getTitles().get(0));
        List<WordDTO> wordsList = articleDTO.getWordsList();

        List<WordFrequencyEntity> wordFrequencyEntities = new ArrayList<>();
        wikiArticleEntity.setDate(new Timestamp(System.currentTimeMillis()));


        wikiArticleRepository.save(wikiArticleEntity);

        for(WordDTO entity: wordsList) {
            WordFrequencyEntity wordFrequencyEntity = new WordFrequencyEntity();

            wordFrequencyEntity.setWord(entity.getWord());
            wordFrequencyEntity.setWordCount(entity.getOccurrences());
            wordFrequencyEntity.setArticle(wikiArticleEntity);

            wordFrequencyEntityService.saveWordFrequency(wordFrequencyEntity);

            wordFrequencyEntities.add(wordFrequencyEntity);

        }
        wikiArticleEntity.setWords(wordFrequencyEntities);

        wikiArticleRepository.save(wikiArticleEntity);
    }

    @Override
    public void deleteArticle(WikiArticleEntity wikiArticleEntity){
        for (WordFrequencyEntity word : wikiArticleEntity.getWords()) {
            wordFrequencyEntityService.deteleWordFrequency(word);
        }
        wikiArticleRepository.delete(wikiArticleEntity);
    }
}
