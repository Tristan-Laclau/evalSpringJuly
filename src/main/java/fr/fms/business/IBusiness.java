package fr.fms.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fr.fms.entities.Booking;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Customer;
import fr.fms.entities.Movie;
import fr.fms.entities.Screening;
import fr.fms.entities.User;

public interface IBusiness {
	
	/**
	 * Returns all cinemas in the database
	 * @return every cinema, in List<Cinema>
	 */
	public List<Cinema> getAllCinemas();
	
	/**
	 * Insert a cinema in the database
	 * @param name of the cinema
	 * @param address of the cinema
	 * @param city where the cinma is located
	 * @return the new cinema
	 */
	public Cinema createCinema(String name, String address, City city);
	
	/**
	 * Delete a cinema from the database
	 * @param id of the cinema
	 */
	public void deleteCinema(Long id);
	
	/**
	 * Update a cinema in the database
	 * @param id of the cinema
	 * @param name of the cinema
	 * @param address of the cinema
	 * @param city where the cinema is located
	 */
	public void updateCinema(Long id, String name, String address, City city);
	
	/**
	 * Returns a page of cinemas for easier display
	 * @param pageable
	 * @return Page<Cinema> a page of cinemas
	 * @throws Exception
	 */
	Page<Cinema> getCinemasPage(String keyword, Pageable pageable) throws Exception;
	
	Page<Cinema> getCinemasPageByCityIdAndKeyword(Long cityId,String kw,  Pageable pageable);
	
	/**
	 * Returns all cities in the database
	 * @return every city, in List<City>
	 */
	public List<City> getAllCities();
	
	/**
	 * Insert a city in the database
	 * @param name of the city
	 * @return the new city
	 */
	public City createCity(String name);
	
	/**
	 * Delete a city from the database
	 * @param id of the city
	 */
	public void deleteCity(Long id);
	
	/**
	 * Update a city in the database
	 * @param id of the city
	 * @param name of the city
	 */
	public void updateCity(Long id, String name);
	
	/**
	 * Returns all movies in the database
	 * @return every movie, in List<Movie>
	 */
	public List<Movie> getAllMovies();
	
	/**
	 * Insert a movie in the database
	 * @param name of the movie
	 * @param picture : a string corresponding to the picture's location in the folders
	 * @return the new movie
	 */
	public Movie createMovie(String name, String picture);
	
	/**
	 * Delete a movie from the database
	 * @param id of the movie
	 */
	public void deleteMovie(Long id);
	
	/**
	 * Update a movie in the database
	 * @param id of the movie
	 * @param name of the movie
	 * @param picture of the movie
	 */
	public void updateMovie(Long id, String name, String picture);
	
	/**
	 * Returns a page of movies for easier display
	 * @param pageable
	 * @return Page<Movie> a page of movies
	 * @throws Exception
	 */
	Page<Movie> getMoviePage(Pageable pageable) throws Exception;
	
	/**
	 * Returns all screenings in the database
	 * @return every screening, in List<Screening>
	 */
	public List<Screening> getAllScreenings();
	
	/**
	 * Insert a screening in the database
	 * @param day when the screening takes place
	 * @param startingHour of the screening
	 * @param Movie that will be displayed
	 * @param Cinema where the screening takes place
	 * @return the new screening
	 */
	public Screening createScreening(String day, String startingHour, Movie movie, Cinema cinema);
	
	/**
	 * Delete a screening from the database
	 * @param id of the screening
	 */
	public void deleteScreening(Long id);
	
	/**
	 * Updates a screening in the database
	 * @param id of the screening
	 * @param day when the screening takes place
	 * @param startingHour of the screening
	 * @param Movie that will be displayed
	 * @param Cinema where the screening takes place
	 */
	public void updateScreening(Long id, String day, String startingHour, Movie movie, Cinema cinema);
	
	/**
	 * Returns a page of screenings for a single cinema for easier display
	 * @param pageable
	 * @param cinemaId
	 * @return Page<Screening> a page of screenings
	 * @throws Exception
	 */
	Page<Screening> getScreeningsByCinemaId(long cinemaId, Pageable pageable);
	
	/**
	 * Insert a booking in the database
	 * @param screening
	 * @param user
	 * @param customer
	 * @return the new booking
	 */
	public Booking createBooking(Screening screening, User user, Customer customer);
	
	/**
	 * Delete a booking from the database
	 * @param id of the booking
	 */
	public void deleteBooking(Long id);
	
	/**
	 * Insert a customer in the database
	 * @param name
	 */
	public void createCustomer(String name);
	
	/**
	 * Delete a customer from the database
	 * @param id of the customer
	 */
	public void deleteCustomer(Long id);
	
	/**
	 * Returns all the screenings of a cinema in a List
	 * @param id of the cinema
	 * @return List<Screening>
	 */
	public List<Screening> getAllScreeningsByCinemaId(Long id);

	/**
	 * Returns all the screenings of a movie in a List
	 * @param id of the movie
	 * @return List<Screening>
	 */
	public List<Screening> getAllScreeningsByMovieId(Long id);

	

}
