/**
 * 
 */
package com.teste.texoit.restcsv.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.teste.texoit.restcsv.model.Premio;
import com.teste.texoit.restcsv.repository.PremioRepository;

/**
 * @author Bot
 *
 */
@DataJpaTest
public class IntegracaoBanco {

	@Autowired
	private TestEntityManager tEM;
	
	@Autowired 
	private PremioRepository repository;
	
	
	@Test 
	public void ConsultandoVencendoresTest () {
		
		Premio p1 = new Premio();
		p1.setProducers("Allan Carr");
		p1.setStudios("Associated Film Distribution");
		p1.setTitle("Can't Stop the Music");
		p1.setYear(1980);
		p1.setWinner(false);
		tEM.persist(p1);
		
		
		Premio p2 = new Premio();
		p2.setProducers("Steve Shagan");
		p2.setStudios("MGMand United Artists");
		p2.setTitle("The Formula");
		p2.setYear(1980);
		p2.setWinner(false);
		tEM.persist(p1);
	
		
		List<Premio> p = repository.findByWinner();
		
		assertEquals(0, p.size());
	}
}
