package com.thirdstart.spring.zerotohero.config

import com.thirdstart.spring.zerotohero.util.jwt.JwtCreator
import com.thirdstart.spring.zerotohero.util.rsa.RsaKeyParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.YamlMapFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class TestConfiguration {
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

    @Value('${jwt.privateKey}')
    String jwtPrivateKey

    @Bean
    public jwtCreator() {
        return new JwtCreator(key: RsaKeyParser.toPrivateKey(jwtPrivateKey))
    }
}



