package com.juniorcalado.apicadastrodepessoa.repository;


import com.juniorcalado.apicadastrodepessoa.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    @Query("SELECT p FROM People p")
    List<People> findAllPeople();

    @Query("SELECT COUNT(p) > 0 FROM People p WHERE p.cpf = :cpf")
    boolean hasCpf(@Param("cpf") String cpf);

    @Query("SELECT p FROM People p WHERE " +
            "(:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%'))) AND " +
            "(:cpf IS NULL OR lower(p.cpf) LIKE lower(concat('%', :cpf, '%')))")
    List<People> findAllByParams(
            @Param("name") String name,
            @Param("cpf") String cpf
    );

}
