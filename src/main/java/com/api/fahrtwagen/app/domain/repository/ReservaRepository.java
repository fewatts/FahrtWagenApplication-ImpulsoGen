package com.api.fahrtwagen.app.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.fahrtwagen.app.domain.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    Page<Reserva> findAll(Pageable paginacao);
}
