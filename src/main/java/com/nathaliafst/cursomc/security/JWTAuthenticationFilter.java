package com.nathaliafst.cursomc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathaliafst.cursomc.dto.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //Tenta autenticar
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {//Tentar pegar no objeto de requisicao os dados que vieram na requisicao
            CredenciaisDTO credenciaisDTO = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciaisDTO.getUsername(), credenciaisDTO.getSenha(), new ArrayList<>());
            //metodo que verifica se o usuario e senha sao validos com base no userdetais e userdetailsservice
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Se a autenticacao ocorrer com sucesso fazer o seguinte, gerar um token e acrescentar na resposta da requisicao
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        //getPrincipal retorna o usuario do spring security
        String username = ((UserSpringSecurity) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        //Acrescenta o token no cabecalho da requisicao
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");

    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }


}
