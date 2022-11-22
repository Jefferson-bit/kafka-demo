package com.crash.br.endereco;


import com.crash.br.cliente.ClienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String rua;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String estado;
    private Integer numeroDaCasa;
    @Column(nullable = false)
    private Integer telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY)
    private List<ClienteEntity> cliente = new ArrayList<>();

    @Deprecated
    public EnderecoEntity() {
    }

    public EnderecoEntity(String rua, String bairro, String cep, String cidade, String estado, Integer numeroDaCasa, Integer telefone) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.numeroDaCasa = numeroDaCasa;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getNumeroDaCasa() {
        return numeroDaCasa;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public List<ClienteEntity> getCliente() {
        return cliente;
    }

    public static final class EnderecoEntityBuilder {
        private String rua;
        private String bairro;
        private String cep;
        private String cidade;
        private String estado;
        private Integer numeroDaCasa;
        private Integer telefone;
        private List<ClienteEntity> cliente = new ArrayList<>();

        public EnderecoEntityBuilder withRua(String rua) {
            this.rua = rua;
            return this;
        }

        public EnderecoEntityBuilder withBairro(String bairro) {
            this.bairro = bairro;
            return this;
        }

        public EnderecoEntityBuilder withCep(String cep) {
            this.cep = cep;
            return this;
        }

        public EnderecoEntityBuilder withCidade(String cidade) {
            this.cidade = cidade;
            return this;
        }

        public EnderecoEntityBuilder withEstado(String estado) {
            this.estado = estado;
            return this;
        }

        public EnderecoEntityBuilder withNumeroDaCasa(Integer numeroDaCasa) {
            this.numeroDaCasa = numeroDaCasa;
            return this;
        }

        public EnderecoEntityBuilder withTelefone(Integer telefone) {
            this.telefone = telefone;
            return this;
        }

        public EnderecoEntityBuilder withCliente(List<ClienteEntity> cliente) {
            this.cliente = cliente;
            return this;
        }

        public EnderecoEntity build() {
            EnderecoEntity enderecoEntity = new EnderecoEntity(rua, bairro, cep, cidade, estado, numeroDaCasa, telefone);
            enderecoEntity.cliente = this.cliente;
            return enderecoEntity;
        }
    }
}

