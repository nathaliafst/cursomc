package com.nathaliafst.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="CLIENTE")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message="Preenchimento obrigatorio!")
    private String nome;
    private String cpfCnpj;
    @Email(message="Sintaxe do Email incorreta.")
    private String email;
    @JsonIgnore
    private String senha;

    public Cliente(){}

    public Cliente(Integer id, String nome, String cpfCnpj, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = cpfCnpj;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id) && nome.equals(cliente.nome) && cpfCnpj.equals(cliente.cpfCnpj)
                && email.equals(cliente.email) && senha.equals(cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpfCnpj, email, senha);
    }
}
