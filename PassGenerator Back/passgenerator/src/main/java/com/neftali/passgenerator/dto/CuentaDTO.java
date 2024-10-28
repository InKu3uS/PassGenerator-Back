package com.neftali.passgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaDTO {

    private String site;

    private String password;

    private String createTime;

    private String expirationTime;

}