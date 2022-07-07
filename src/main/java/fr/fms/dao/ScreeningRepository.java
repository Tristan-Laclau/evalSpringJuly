package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{
	Page<Screening> findByCinemaId(Long cinemaId, Pageable pageable);

	List<Screening> findByCinemaId(Long id);

	List<Screening> findByMovieId(Long id);

}
