package com.devsuperior.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldMessage {
    private String fieldName;
    private String message;
}
