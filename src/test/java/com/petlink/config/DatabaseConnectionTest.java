package com.petlink.config;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseConnectionTest {
	@Autowired
	private DataSource dataSource;

	@Test
	@DisplayName("데이터 소스가 정상적으로 주입되는지 확인")
	void dataSourceShouldBeConfigured() throws Exception {
		// 데이터 소스가 null이 아닌지 확인
		assertNotNull(dataSource);

		// 데이터 소스로부터 커넥션을 얻을 수 있는지 확인
		try (Connection connection = dataSource.getConnection()) {
			assertNotNull(connection);
		}
	}
}