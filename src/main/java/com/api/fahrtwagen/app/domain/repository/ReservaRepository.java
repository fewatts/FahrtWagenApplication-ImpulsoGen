package com.api.fahrtwagen.app.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.fahrtwagen.app.domain.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    Page<Reserva> findAll(Pageable paginacao);

    @Query(value = """
            SELECT 
                * 
            FROM 
                reservas 
            WHERE 
                carro_id = :carroId
            ORDER BY 
                data_inicio
            """, nativeQuery = true)
    List<Reserva> findByCarroIdOrderByDataInicio(@Param("carroId") Long carroId);

    @Query(value = """
            SELECT 
                * 
            FROM 
                reservas 
            WHERE 
                carro_id = :carroId
            AND
                id_reserva != :reservaId
            ORDER BY 
                data_inicio
            """, nativeQuery = true)
    List<Reserva> findByCarroIdOrderByDataInicioIgnoreReservaId(@Param("carroId") Long carroId, @Param("reservaId") Long reservaId);


}
