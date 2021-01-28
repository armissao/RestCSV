/**
 * 
 */
package com.teste.texoit.restcsv.integracao;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.teste.texoit.restcsv.controller.PremioController;
import com.teste.texoit.restcsv.service.PremioService;

/**
 * @author Bot
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PremioController.class)
public class IntegracaoUpload {

	@Autowired
    MockMvc mockMvc;

    @MockBean
    PremioService premioService;
    
    
    @Test
    public void validarPayLoadTest() throws Exception {
        
    	Path path = Paths.get("src/main/resources/movielist.csv");
        byte[] bytes = Files.readAllBytes(path);
        
             MockMultipartFile multipartFile =
                        new MockMultipartFile("arquivo", "movielist.csv", "text/csv", bytes);
             mockMvc.perform(multipart("/api/csv/upload").file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());
   
             verify(premioService).save(multipartFile);
    }

    @Test
    public void uploadMultipartTiposArquivosTest() throws Exception {
    	
         MockMultipartFile correctFile =
                new MockMultipartFile("arquivo", "filename.csv", "text/csv", "csv".getBytes());

        MockMultipartFile fileWithWrongExtension =
                new MockMultipartFile("arquivo", "filename.txt", "text/plan" , "arquivo errado".getBytes());
    	
       mockMvc.perform(MockMvcRequestBuilders.multipart("/api/csv/upload")
                .file(correctFile)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
       
       mockMvc.perform(MockMvcRequestBuilders.multipart("/api/csv/upload")
               .file(fileWithWrongExtension)
               .characterEncoding("UTF-8"))
               .andExpect(status().is4xxClientError());
    }
   
          
}

