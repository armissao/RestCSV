package com.teste.texoit.restcsv.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.teste.texoit.restcsv.service.PremioService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.bytebuddy.implementation.bytecode.Multiplication;

@WebMvcTest
public class PremioControllerTest {
	
	@MockBean
	private  PremioController premioController;
	
	@MockBean
	private PremioService premioService;
	
	// Setar somente o controler
	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(this.premioController);
	}
	/*
	@Test
	public void deveRetornarArquivoInvalido() {

		when(this.premioService.save(MultipartFile arquivo));
		
		RestAssuredMockMvc.given()
			.accept(ContentType.BINARY)
		.when()
			.post("/api/csv/upload")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
*/
	
}
