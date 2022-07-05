package fr.fms.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.City;

public interface CityRepository extends JpaRepository<City, Long>{

}
