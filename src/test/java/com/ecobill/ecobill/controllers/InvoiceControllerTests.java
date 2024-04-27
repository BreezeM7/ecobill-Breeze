package com.ecobill.ecobill.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InvoiceControllerTests {

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        @Autowired
        public InvoiceControllerTests(MockMvc mockMvc, ObjectMapper objectMapper) {

                this.mockMvc = mockMvc;
                this.objectMapper = objectMapper;

        }

        @Test
        public void testThatCreateInvoiceSuccessfullyReturnsHttp201Created() throws Exception {

                Resource resource = new ClassPathResource("test_cases_json.json");
                String jsonContent = new String(Files.readAllBytes(Paths.get(resource.getURI())));

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/invoices")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(jsonContent))
                                .andExpect(
                                                MockMvcResultMatchers.status().isCreated());

        }
}
