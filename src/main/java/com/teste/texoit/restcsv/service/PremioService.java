package com.teste.texoit.restcsv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.teste.texoit.restcsv.model.Premio;

import com.teste.texoit.restcsv.repository.PremioRepository;
import com.teste.texoit.restcsv.util.CSVUtil;

@Service
public class PremioService {

	@Autowired
	PremioRepository repository;

	public void save(MultipartFile arquivo) {
		try {
			List<Premio> premios = CSVUtil.csvParaPremios(arquivo.getInputStream());
			repository.saveAll(premios);
		} catch (IOException e) {
			throw new RuntimeException("falha persistir dados csv: " + e.getMessage());
		}
	}

	public List<Premio> getPremioIntervalo() {
		return repository.findByWinner();
	}
	
	// buscar o intervalo de premios dos produtores em minimo e maximo
	// 1º buscar produtores ganhadores
	// 2º quantificar os premios
	// 3º diferenca do tempo YEAR
	
	

}
