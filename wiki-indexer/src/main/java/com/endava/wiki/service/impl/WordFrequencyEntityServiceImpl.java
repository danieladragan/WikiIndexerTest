package com.endava.wiki.service.impl;

import com.endava.wiki.models.WordFrequencyEntity;
import com.endava.wiki.repository.WordFrequencyRepository;
import com.endava.wiki.service.WordFrequencyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vsima on 8/11/2016.
 */
@Service
public class WordFrequencyEntityServiceImpl implements WordFrequencyEntityService{
    @Autowired
    private WordFrequencyRepository wordFrequencyRepository;

    @Override
    public void saveWordFrequency(WordFrequencyEntity wordFrequencyEntity) {
        wordFrequencyRepository.save(wordFrequencyEntity);
    }
}
