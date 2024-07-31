package com.api.fahrtwagen.app.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fahrtwagen.app.domain.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

    Page<Carro> findAll(Pageable paginacao);

    Boolean existsByPlacaIgnoreCase(String placa);
}
