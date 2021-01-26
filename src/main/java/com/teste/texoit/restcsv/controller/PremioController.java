package com.teste.texoit.restcsv.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.teste.texoit.restcsv.message.MensagemResposta;
import com.teste.texoit.restcsv.model.Premio;
import com.teste.texoit.restcsv.service.PremioService;
import com.teste.texoit.restcsv.util.CSVUtil;

@Controller
@RequestMapping("/api/csv")
public class PremioController {

	@Autowired
	PremioService arquivoService;

	@PostMapping("/upload")
	public ResponseEntity<MensagemResposta> uploadFile(@RequestParam("arquivo") MultipartFile arquivo) {

		String mensagem = "";

		if (CSVUtil.FormatatoCSV(arquivo)) {
			try {
				arquivoService.save(arquivo);

				mensagem = "Arquivo enviado com sucesso: " + arquivo.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new MensagemResposta(mensagem));
			} catch (Exception e) {
				mensagem = "Arquivo não pode ser enviado: " + arquivo.getOriginalFilename() + " ! " + e.getMessage();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MensagemResposta(mensagem));
			}
		}

		mensagem = "Inserir um arquivo csv!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemResposta(mensagem));
	}

	@GetMapping("/intervalo")
	public ResponseEntity<String> getIntervalo() {
		try {

			List<Premio> premiosVencedores = arquivoService.getPremioIntervalo();

			JSONObject obJson = null;
			JSONArray arrayJson = new JSONArray();

			Integer intervalo = 0;

			// Filtrar por numero de ocorrencias nomes dos produtores
			for (int a = 0; a < premiosVencedores.size(); a++) {

				Premio p1 = premiosVencedores.get(a);
				for (int b = a + 1; b < premiosVencedores.size(); b++) {

					Premio p2 = premiosVencedores.get(b);
					if (p1.getProducers().equals(p2.getProducers())) {
						// add lista json
						intervalo = p2.getYear() - p1.getYear();
						obJson = new JSONObject();
						obJson.put("producer", p1.getProducers());
						obJson.put("interval", intervalo);
						obJson.put("previousWin", p1.getYear());
						obJson.put("followingWin", p2.getYear());
						arrayJson.put(obJson);

					}

				}
			}

			// Montar Json e Filtrar Min e Max
			JSONObject obJsonMinMax = new JSONObject();
			JSONArray novoarrayJsonMin = new JSONArray();
			JSONArray novoarrayJsonMax = new JSONArray();

			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject rec = arrayJson.getJSONObject(i);
				int intevalo = rec.optInt("interval");
				if (intevalo < 5) {
					obJsonMinMax.put("min", novoarrayJsonMin);
					novoarrayJsonMin.put(arrayJson.getJSONObject(i));

				} else {
					obJsonMinMax.put("max", novoarrayJsonMax);
					novoarrayJsonMax.put(arrayJson.getJSONObject(i));

				}
			}

			if (obJsonMinMax.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(obJsonMinMax.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
