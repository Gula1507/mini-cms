package de.aygul.minicms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum BlogPostStatus {
    DRAFT, PUBLISHED, ARCHIVED;

    @JsonCreator
    public static BlogPostStatus fromString(@JsonProperty("blogpoststatus") String value) {
        return BlogPostStatus.valueOf(value.toUpperCase());
    }
    }

