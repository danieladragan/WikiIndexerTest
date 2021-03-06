CREATE SCHEMA IF NOT EXISTS `wiki`
  DEFAULT CHARACTER SET utf8;
USE `wiki`;

CREATE TABLE `wiki_article` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);


CREATE TABLE `word_frequency` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `wiki_article_id` bigint(40) NOT NULL,
  `word` varchar(100) DEFAULT NULL,
  `word_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `wiki_f_idx` (`wiki_article_id`),
  CONSTRAINT `wiki_f` FOREIGN KEY (`wiki_article_id`) REFERENCES `wiki_article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);