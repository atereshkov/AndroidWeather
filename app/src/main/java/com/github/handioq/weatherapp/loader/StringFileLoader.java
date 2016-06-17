package com.github.handioq.weatherapp.loader;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for load from file data and return it in String format
 */
public class StringFileLoader implements ILoader<String> {

    private String filename;
    private String path;

    public StringFileLoader() {
    }

    public StringFileLoader(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    @Override
    public String load() {
        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath() + path, filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null)
            {
                text.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
