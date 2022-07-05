package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Booking;

public interface BookingRepository  extends JpaRepository<Booking, Long>{

}
