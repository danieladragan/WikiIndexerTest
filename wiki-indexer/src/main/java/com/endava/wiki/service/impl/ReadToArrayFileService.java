package com.endava.wiki.service.impl;

/**
 * Created by vsima on 8/12/2016.
 */
import com.endava.wiki.service.ReadToFileArrayFileService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service

public class ReadToArrayFileService implements ReadToFileArrayFileService {


        public  List<String> readLines(File file)  {
            if (!file.exists()) {
                return new ArrayList<String>();
            }
            List<String> results = new ArrayList<String>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))){

                String line = reader.readLine();
                while (line != null) {
                    results.add(line);
                    line = reader.readLine();
                }

            }
            catch (IOException e){

                e.printStackTrace();
            }
            return results;
        }
    }

