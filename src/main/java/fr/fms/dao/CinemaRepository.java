package fr.fms.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long>{

	Page<Cinema> findByNameContains(String keyword, Pageable pageable);

	Page<Cinema> findByCityIdAndNameContains(String kw, Long cityId, Pageable pageable);

}
