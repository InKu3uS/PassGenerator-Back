package com.neftali.passgenerator.dto;

import com.neftali.passgenerator.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(source = "site", target = "site")
    CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);

    @Mapping(source = "site", target = "site")
    Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);
}
