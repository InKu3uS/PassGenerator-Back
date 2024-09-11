package com.neftali.passgenerator.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuentas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private User user;

    @Column(unique = true, nullable = false)
    private String site;

    @Column(nullable = false)
    private String password;

    private String createTime;

    private String expirationTime;
}
