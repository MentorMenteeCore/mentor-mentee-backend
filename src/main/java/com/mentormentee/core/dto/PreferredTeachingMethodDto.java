package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class PreferredTeachingMethodDto {

    private String preferredTeachingMethod;

    public PreferredTeachingMethodDto() {
    }

    public PreferredTeachingMethodDto(String preferredTeachingMethod) {
        this.preferredTeachingMethod = preferredTeachingMethod;
    }
}
