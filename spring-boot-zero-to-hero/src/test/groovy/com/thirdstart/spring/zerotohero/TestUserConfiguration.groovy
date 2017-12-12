package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.util.jwt.RuntimeJwtHelper
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.YamlMapFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class TestUserConfiguration {
    @Value("classpath:test-users.yml")
    Resource testUsersYamlResource

    @Bean
    public Map testUsers() {
        // yes, this is factory abuse.
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean(
                resources: testUsersYamlResource
        )
        yamlMapFactoryBean.afterPropertiesSet()

        return yamlMapFactoryBean.getObject()
    }

}



