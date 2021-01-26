package com.teste.texoit.restcsv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teste.texoit.restcsv.model.Premio;

public interface PremioRepository extends JpaRepository<Premio, Long> {

	@Query("select p from Premio p where p.winner = true")
	List<Premio> findByWinner();
}
