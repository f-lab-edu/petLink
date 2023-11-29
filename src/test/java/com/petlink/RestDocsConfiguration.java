package com.petlink;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

@TestConfiguration
public class RestDocsConfiguration {
    @Bean
    public RestDocumentationResultHandler write() {
        return MockMvcRestDocumentationWrapper.document(
                "{class-name}/{method-name}", // 문서의 경로와 파일명을 지정
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), // 문서의 request 부분을 예쁘게 출력
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()) // 문서의 response 부분을 예쁘게 출력
        );
    }
}
