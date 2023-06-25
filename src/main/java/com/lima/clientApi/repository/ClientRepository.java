package com.lima.clientApi.repository;

import com.lima.clientApi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {



    @Query(value = "SELECT * FROM client c",
            nativeQuery = true)
    List<Client> listAll(Pageable page);

    @Query(value = "SELECT c.* FROM client c " +
            "JOIN phone p ON c.id = p.client_id " +
            "WHERE p.ddd = :areaCode ", nativeQuery = true)
    List<Client> listByAreaCode(Pageable page, String areaCode);
}
