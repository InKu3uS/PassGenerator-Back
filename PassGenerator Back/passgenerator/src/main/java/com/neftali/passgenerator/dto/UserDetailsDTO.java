package com.neftali.passgenerator.dto;

import com.neftali.passgenerator.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {

    private String uuid;
    private String username;
    private String email;
    private Role role;
    private String createTime;


}
