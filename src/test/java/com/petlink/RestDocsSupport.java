package com.petlink;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petlink.common.exception.GlobalExceptionHandler;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

	protected MockMvc mockMvc;

	protected ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp(RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
			.apply(documentationConfiguration(provider))
			.setControllerAdvice(GlobalExceptionHandler.class)
			.build();
	}

	protected abstract Object initController();
}
