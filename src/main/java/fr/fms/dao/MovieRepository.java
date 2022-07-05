package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

}
