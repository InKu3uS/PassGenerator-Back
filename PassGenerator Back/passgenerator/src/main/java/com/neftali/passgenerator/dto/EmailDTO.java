package com.neftali.passgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {
    private String recipent;
    private String subject;
    private String message;
}
