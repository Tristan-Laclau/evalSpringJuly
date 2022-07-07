package fr.fms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Movie;
import fr.fms.entities.Screening;

@Controller
public class CinemaController {
	
	@Autowired
	IBusinessImpl businessImpl;
	
	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/cinemas")
	public String cinemas(Model model, @RequestParam(name = "page", defaultValue = "0")int page, @RequestParam(name="keyword", defaultValue = "")String kw,
			@RequestParam(name = "city", defaultValue="-1")long cityId) {
		Page<Cinema> cinemas;
		
		if(cityId < 0) {
			try {
				cinemas = businessImpl.getCinemasPage(kw, PageRequest.of(page, 5));
			} catch (Exception e) {
				return "cinemas";
			}
		} else {
			cinemas = businessImpl.getCinemasPageByCityIdAndKeyword(cityId, kw, PageRequest.of(page, 5));
		}
		
		List<City> cities = businessImpl.getAllCities();
		model.addAttribute("keyword",kw);
		model.addAttribute("listCities",cities);
		model.addAttribute("listCinemas",cinemas.getContent());
		model.addAttribute("pages",new int[cinemas.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "cinemas";
	
	}
	
	@GetMapping("/screenings")
	public String screenings(Model model, @RequestParam(name = "page", defaultValue = "0")int page,
			@RequestParam(name = "cinema", defaultValue="1")long cinemaId) {
		
		Page<Screening> screenings;
		
		screenings = businessImpl.getScreeningsByCinemaId(cinemaId,PageRequest.of(page, 5));
		model.addAttribute("listScreenings",screenings.getContent());
		model.addAttribute("pages",new int[screenings.getTotalPages()]);
		model.addAttribute("currentPage",page);
		return "screenings";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", defaultValue = "false") boolean loginError) {
		return "login";
	}
	
	@GetMapping("/adminPageCinema")
	public String adminPageCinema(Model model, @RequestParam(name = "action", defaultValue = "Create")String action) {
		model.addAttribute("action", action);
		List<Cinema> cinemas = businessImpl.getAllCinemas();
		List<City> cities = businessImpl.getAllCities();
		model.addAttribute("cinemas",cinemas);
		model.addAttribute("cities",cities);
		model.addAttribute("newCinema",new Cinema());
		return "adminPageCinema";
	}
	
	@GetMapping("/adminPageMovie")
	public String adminPageMovie(Model model, @RequestParam(name = "action", defaultValue = "Create")String action) {
		model.addAttribute("action", action);
		List<Movie> movies = businessImpl.getAllMovies();
		model.addAttribute("movies",movies);
		model.addAttribute("newMovie",new Movie());
		return "adminPageMovie";
	}
	
	@GetMapping("/adminPageScreening")
	public String adminPageScreenings(Model model, @RequestParam(name = "action", defaultValue = "Create")String action) {
		model.addAttribute("action" , action);
		List<Movie> movies = businessImpl.getAllMovies();
		List<Cinema> cinemas = businessImpl.getAllCinemas();
		List<Screening> screenings = businessImpl.getAllScreenings();
		model.addAttribute("screenings",screenings);
		model.addAttribute("cinemas",cinemas);
		model.addAttribute("movies", movies);
		model.addAttribute("newScreening", new Screening());
		return "adminPageScreening";
	}
	
	@PostMapping("/save")
	public String save(@Valid Cinema cinema, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
			businessImpl.createCinema(cinema.getName(), cinema.getAddress(), cinema.getCity());
		} catch (Exception e) {
			return "redirect:/adminPageCinema?action='Failure'";
		}
		
		return "redirect:/adminPageCinema?action='Success'";
	}
	
	@PostMapping("/saveMovie")
	public String saveMovie(@Valid Movie movie, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
			businessImpl.createMovie(movie.getName(),"picture.png");
		} catch (Exception e) {
			return "redirect:/adminPageCinema?action='Failure'";
		}
		
		return "redirect:/adminPageCinema?action='Success'";
	}
	
	@PostMapping("/saveScreening")
	public String saveScreening(@Valid Screening screening, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageScreenings?action='Failure'";
		
		try {
			businessImpl.createScreening(screening.getDay(), screening.getStartingHour(), screening.getMovie(), screening.getCinema());
		}catch (Exception e) {
			return "redirect:/adminPageScreenings?action='Failure'";
		}
		return "redirect:/adminPageScreening?action='Success'";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Cinema cinema,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
			businessImpl.updateCinema(cinema.getId(),cinema.getName(), cinema.getAddress(), cinema.getCity());
		} catch (Exception e) {
			return "redirect:/adminPageCinema?action='Failure'";
		}
		
		return "redirect:/admingPageCinema?action='Success'";
	}
	
	@PostMapping("/editMovie")
	public String editMovie(@Valid Movie movie,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
		businessImpl.updateMovie(movie.getId(),movie.getName(),"picture.png");
		}catch (Exception e) {
			return "redirect:/adminPageCinema?action='Failure'";
		}
		return "redirect:/admingPageCinema?action='Success'";
	}
	
	@PostMapping("/editScreening")
	public String editScreening(@Valid Screening screening, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageScreenings?action='Failure'";
		
		try {
			businessImpl.updateScreening(screening.getId(), screening.getDay(), screening.getStartingHour(), screening.getMovie(), screening.getCinema());
		}catch (Exception e) {
			return "redirect:/adminPageScreening?action='Failure'";
		}
		return "redirect:/adminPageScreening?action='Success'";
	}
	
	@PostMapping("/delete")
	public String delete(@Valid Cinema cinema, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
			for (Screening screening : businessImpl.getAllScreeningsByCinemaId(cinema.getId())){
				businessImpl.deleteScreening(screening.getId());
			}
		businessImpl.deleteCinema(cinema.getId());
		}
		catch (Exception e) {
			return "redirect:/adminPageCinema?action='CannotDelete'";
		}
		return "redirect:/adminPageCinema?action='Success'";
	}
	
	@PostMapping("/deleteMovie")
	public String deleteMovie(@Valid Movie movie, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/adminPageCinema?action='Failure'";
		
		try {
			for (Screening screening : businessImpl.getAllScreeningsByMovieId(movie.getId())){
				businessImpl.deleteScreening(screening.getId());
			}
		businessImpl.deleteMovie(movie.getId());
		}
		catch (Exception e) {
			return "redirect:/adminPageCinema?action='CannotDelete'";
		}
		return "redirect:/adminPageCinema?action='Success'";
	}
	
	@PostMapping("/deleteScreening")
	public String deleteScreening(@Valid Screening screening, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "redirect:/admingPageScreening?action=='Failure'";
		
		try {
			businessImpl.deleteScreening(screening.getId());
		} catch (Exception e) {
			return "redirect:/admingPageScreening?action=='Failure'";
		}
		return "redirect:/adminPageScreening?action=='Success'";
	}

}
