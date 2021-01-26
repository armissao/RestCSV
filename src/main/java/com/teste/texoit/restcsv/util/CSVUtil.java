/**
 * 
 */
package com.teste.texoit.restcsv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.teste.texoit.restcsv.model.Premio;

/**
 * @author ARM
 * 
 *         Classe Responsavel pela conversão do CSV
 *
 */
public class CSVUtil {

	public static String TIPO = "text/csv";

	public static boolean FormatatoCSV(MultipartFile arquivo) {

		if (!TIPO.equals(arquivo.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Premio> csvParaPremios(InputStream is) {

		try (BufferedReader Arquivo = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				CSVParser csvConversor = new CSVParser(Arquivo, CSVFormat.EXCEL.withFirstRecordAsHeader()
						.withIgnoreHeaderCase().withTrim().withDelimiter(';'));) {

			List<Premio> premios = new ArrayList<Premio>();
			Iterable<CSVRecord> csvRegistros = csvConversor.getRecords();
			Premio premio = null;

			for (CSVRecord csvRegistro : csvRegistros) {

				// varificar campo com , e string and inserir em registro individuais

				if (csvRegistro.get("producers").contains(",") || csvRegistro.get("producers").contains("and")) {

					// String regex para producers
					String[] nomes = csvRegistro.get("producers").split("\sand\s|[,]");

					for (int i = 0; i < nomes.length; i++) {
						String nome = nomes[i];

						if (!nome.isBlank() && !nome.equals(null)) {
							premio = new Premio(Integer.parseInt(csvRegistro.get("year")), 
									            csvRegistro.get("title"),
									            csvRegistro.get("studios"), nome,
									            // Validar Campo winner quando yes = true
									            csvRegistro.get("winner").equals("yes") ? true : false);
							                    premios.add(premio);
						}

					}

				} else {
					// Sem Multivalor no campo Producers
					premio = new Premio(Integer.parseInt(csvRegistro.get("year")), csvRegistro.get("title"),
							csvRegistro.get("studios"), csvRegistro.get("producers"),
							// Validar Campo winner quando yes = true
							csvRegistro.get("winner").equals("yes") ? true : false);
					premios.add(premio);
				}

			}

			return premios;
		} catch (IOException e) {
			throw new RuntimeException("falha ao converter arquivo CSV: " + " " + e.getMessage());
		}
	}
}
