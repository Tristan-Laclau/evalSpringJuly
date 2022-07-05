package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{

}
