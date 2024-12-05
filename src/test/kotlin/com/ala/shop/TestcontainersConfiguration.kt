package com.ala.shop

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:17.2"))
            .apply {
                withDatabaseName("testdb")
                withUsername("testuser")
                withPassword("testpassword")
                start()
            }
    }

    @DynamicPropertySource
    fun registerDynamicProperties(registry: DynamicPropertyRegistry,
                                  postgresContainer: PostgreSQLContainer<*>) {
        registry.add("spring.datasource.url") { postgresContainer.jdbcUrl }
        registry.add("spring.datasource.username") { postgresContainer.username }
        registry.add("spring.datasource.password") { postgresContainer.password }
    }
}
