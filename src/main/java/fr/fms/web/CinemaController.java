package fr.fms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
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

}
