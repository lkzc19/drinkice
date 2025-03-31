package org.example.lamp.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

@Configuration
@EnableConfigurationProperties
public class LdapServerConfiguration {

    @Bean
    public UnboundIdContainer ldapContainer() {
        UnboundIdContainer container = new UnboundIdContainer("dc=example,dc=com", "classpath:static/ldap");
        container.setPort(8389);
        return container;
    }

    @Bean
    public PersonContextMapper personContextMapper() {
        return new PersonContextMapper();
    }
}