package com.nathaliafst.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired //Instanciação do ambiente
    private Environment env;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/categorias/**"
    };


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("teste")) {
            httpSecurity.headers().frameOptions().disable();
        } //pegando os profiles ativos e vendo se tem test

        httpSecurity.cors().and().csrf().disable(); //Se tiver um metodo Cors COnfiguration definido então aplica as configurações.
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //Só vou permitir o metodo get nos endpoint na lista
                .antMatchers(PUBLIC_MATCHERS).permitAll() //tudo que vier deste vetor vou permitir
                .anyRequest().authenticated(); //para to-do resto exigir autenticacao
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Assegur que o back end nao vai segura sessao de usuario
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return urlBasedCorsConfigurationSource;
        //definindo um bean de CorsConfigurationSource, dando acesso basico de multiplas fontes para todos os endpoints.
    }

}
