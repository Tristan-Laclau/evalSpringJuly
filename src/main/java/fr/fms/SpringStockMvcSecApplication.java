package fr.fms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fr.fms.business.IBusinessImpl;

@SpringBootApplication
public class SpringStockMvcSecApplication implements CommandLineRunner {
	
	@Autowired
	IBusinessImpl businessImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringStockMvcSecApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		businessImpl.generateValues();
	}

}
