package com.quipux.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFMResponse {
	private TopTags toptags;

    public TopTags getToptags() {
        return toptags;
    }

    public void setToptags(TopTags toptags) {
        this.toptags = toptags;
    }

}
