package com.github.handioq.weatherapp.loader;

/**
 * Load params for json loader
 */
public class JsonLoadParams extends LoadParams {

    private String filename;

    public JsonLoadParams()
    {

    }

    public JsonLoadParams(String filename)
    {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
