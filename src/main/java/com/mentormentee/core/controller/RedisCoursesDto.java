package com.mentormentee.core.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RedisCoursesDto {
    private List<String> course = new ArrayList<>();
}
