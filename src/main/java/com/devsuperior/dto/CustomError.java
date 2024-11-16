package com.devsuperior.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter

@RequiredArgsConstructor
public class CustomError {

    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String path;

}
