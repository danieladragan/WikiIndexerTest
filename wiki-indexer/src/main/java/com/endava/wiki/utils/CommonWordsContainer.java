package com.endava.wiki.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aancuta on 8/10/2016.
 */
public final class CommonWordsContainer {
    private static final String COMMON_WORDS_FILENAME = "commonWords.txt";
    /**
     * Holds the common words read from the commonWords text file
     */
    private static Set<String> commonWords;

    static {
        commonWords = new HashSet<>();
        ClassLoader classLoader = CommonWordsContainer.class.getClassLoader();
        File file = new File(classLoader.getResource(COMMON_WORDS_FILENAME).getFile());
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                commonWords.add(word);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if a given word is in the common words set
     * @param word Word to check
     * @return boolean
     */
    public static boolean isCommonWord(String word) {
        return commonWords.contains(word.toLowerCase());
    }

}
