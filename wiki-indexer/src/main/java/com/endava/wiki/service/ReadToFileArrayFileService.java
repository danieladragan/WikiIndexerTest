package com.endava.wiki.service;

import java.io.File;
import java.util.List;
import java.io.IOException;

/**
 * Created by vsima on 8/12/2016.
 */
public interface ReadToFileArrayFileService {


    List<String> readLines(File file);
}
