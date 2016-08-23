package com.endava.wiki.repository;

import com.endava.wiki.models.WikiArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vsima on 8/11/2016.
 */
@Repository
public interface WikiArticleRepository extends CrudRepository <WikiArticleEntity,Long>{
    WikiArticleEntity findByTitle(String title);
}

