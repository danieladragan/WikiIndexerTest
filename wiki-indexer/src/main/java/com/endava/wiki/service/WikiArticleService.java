package com.endava.wiki.service;

import com.endava.wiki.dto.ArticleDTO;
import com.endava.wiki.models.WikiArticleEntity;

/**
 * Created by vsima on 8/11/2016.
 */
public interface WikiArticleService {
    WikiArticleEntity findByTitle(String title);
    void saveArticle(ArticleDTO articleDTO);
}
