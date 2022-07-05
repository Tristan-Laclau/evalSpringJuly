package fr.fms.business;



import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.fms.dao.BookingRepository;
import fr.fms.dao.CinemaRepository;
import fr.fms.dao.CityRepository;
import fr.fms.dao.CustomerRepository;
import fr.fms.dao.MovieRepository;
import fr.fms.dao.RoleRepository;
import fr.fms.dao.ScreeningRepository;
import fr.fms.dao.UserRepository;
import fr.fms.entities.Booking;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Customer;
import fr.fms.entities.Movie;
import fr.fms.entities.Screening;
import fr.fms.entities.User;

@Service
public class IBusinessImpl implements IBusiness{
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	CinemaRepository cinemaRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ScreeningRepository screeningRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public IBusinessImpl() {
		
	}

	@Override
	@PostConstruct
	public List<Cinema> getAllCinemas() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema createCinema(String name, String address, City city) {
		return cinemaRepository.save(new Cinema(null, name, address, city, null));
	}

	@Override
	@Transactional
	public void deleteCinema(Long id) {
		try {
			cinemaRepository.deleteById(id);
		}
		catch (Exception e) {
			System.out.println("Cannot delete cinema");
		}
	}

	@Override
	public void updateCinema(Long id, String name, String address, City city) {
		cinemaRepository.save(new Cinema(id, name,address,city,null));
		
	}

	@Override
	public Page<Cinema> getCinemasPage(String keyword, Pageable pageable) throws Exception {
		return cinemaRepository.findByNameContains(keyword, pageable);
	}

	@Override
	public Page<Cinema> getCinemasPageByCityId(String kw, Long cityId, Pageable pageable) {
		return cinemaRepository.findByCityIdAndNameContains(kw, cityId, pageable);
	}

	@Override
	@PostConstruct
	public List<City> getAllCities() {
		return cityRepository.findAll();
	}

	@Override
	public City createCity(String name) {
		return cityRepository.save(new City(null, name, null));
		
	}

	@Override
	@Transactional
	public void deleteCity(Long id) {
		try {
			cityRepository.deleteById(id);
		}
		catch (Exception e) {
			System.out.println("Cannot delete city");
		}
	}

	@Override
	public void updateCity(Long id, String name) {
		cityRepository.save(new City(id, name, null));
	}

	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie createMovie(String name, String picture) {
		return null;
		
	}

	@Override
	public void deleteMovie(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMovie(Long id, String name, String picture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Movie> getMoviePage(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Screening> getAllScreenings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Screening createScreening(String day, String startingHour, Movie movie, Cinema cinema) {
		return null;
	}

	@Override
	public void deleteScreening(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScreening(Long id, String day, String startingHour, Movie movie, Cinema cinema) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Screening> getScreeningsPage(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking createBooking(Screening screening, User user, Customer customer) {
		return null;
		
	}

	@Override
	public void deleteBooking(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCustomer(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void generateValues() {
		City bayonneCity = cityRepository.save(new City(null,"Bayonne",null));
		City tarnosCity = cityRepository.save(new City(null,"Tarnos",null));
		
		Cinema cinema1 = cinemaRepository.save(new Cinema(null,"CGR Bayonne","14 Rue de Bayonne",bayonneCity,null));
		Cinema cinema2 = cinemaRepository.save(new Cinema(null,"CGR Tarnos","9 Rue de la tuilerie", tarnosCity, null));
		Cinema cinema3 = cinemaRepository.save(new Cinema(null,"L'Atalante","6 Quai de Lesseps",bayonneCity, null));
		
		Movie movie1 = movieRepository.save(new Movie(null,"Josee, le tigre et les poissons","picture.png",null));
		
		screeningRepository.save(new Screening(null,"06/07","17H00",movie1,cinema1));
		screeningRepository.save(new Screening(null,"07/07","16H00",movie1,cinema2));
		screeningRepository.save(new Screening(null,"08/07","15H00",movie1,cinema3));
		
		
	}

}
