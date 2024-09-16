package com.neftali.passgenerator.repository;
import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    List<Cuenta> findByUser(User user) throws CuentaNotFoundException;

    List<Cuenta> findByExpirationTimeBeforeAndNotifiedForExpirationFalse(String localDateTime);

    Optional<Cuenta> findBySite(String site) throws CuentaNotFoundException;

    Optional<Cuenta> findByUserUuidAndSite(String userUuid, String site);

    void deleteBySite(String site) throws CuentaNotFoundException;

    void deleteByUserUuid(String userUuid);

    long countByUser(User user);
}
