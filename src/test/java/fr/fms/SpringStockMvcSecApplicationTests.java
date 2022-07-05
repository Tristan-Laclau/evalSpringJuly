package fr.fms;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
class SpringStockMvcSecApplicationTests {
	
	@Autowired
	IBusinessImpl businessImpl;

	@Test
	void contextLoads() {
		assertFalse(1==2);
	}
	
	@Test
	void test_add_cinema() {
		City bayonne = businessImpl.createCity("Bayonne");
		businessImpl.createCinema("CGR", "Bayonne", bayonne);
		
		Cinema cinema = businessImpl.getAllCinemas().get(0);
		
		assertEquals("CGR", cinema.getName());
	}

}
