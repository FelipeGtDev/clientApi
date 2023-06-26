package com.lima.clientApi.repository;

import com.lima.clientApi.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    @Query(value = "SELECT * FROM client c", nativeQuery = true)
    Page<Client> listAll(Pageable page);

    @Query(value = "SELECT c.* FROM client c " +
            "JOIN phone p ON c.id = p.client_id " +
            "WHERE p.ddd = :areaCode ", nativeQuery = true)
    Page<Client> listByAreaCode(Pageable page, String areaCode);

    @Query(value = "SELECT c.* FROM client c WHERE lower(c.name) LIKE lower(concat('%', :name,'%')) ", nativeQuery = true)
    Page<Client> listByName(Pageable page, String name);

    @Query(value = "SELECT * FROM client c WHERE c.id = :id", nativeQuery = true)
    Client findById(long id);

    @Query(value = "DELETE FROM client c WHERE c.id = :id", nativeQuery = true)
    void deleteById(long id);
}
