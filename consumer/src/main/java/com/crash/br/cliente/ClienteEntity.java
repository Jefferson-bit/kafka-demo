package com.crash.br.cliente;


import com.crash.br.contabancaria.ContaBancariaEntity;
import com.crash.br.endereco.EnderecoEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String sobreNome;
    @Column(nullable = false)
    private Integer idade;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dataDeNascimento;

    @JoinColumn(name = "conta_bacaria_id")
    @OneToOne(cascade = CascadeType.PERSIST)
    private ContaBancariaEntity contaBancaria;
    @JoinColumn(name = "endereco_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private EnderecoEntity endereco;

    @Deprecated
    public ClienteEntity() {
    }

    public ClienteEntity(String nome, String sobreNome, Integer idade, String cpf, LocalDate dataDeNascimento, ContaBancariaEntity contaBancaria, EnderecoEntity endereco) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.idade = idade;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.contaBancaria = contaBancaria;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public ContaBancariaEntity getContaBancaria() {
        return contaBancaria;
    }

    public EnderecoEntity getEndereco() {
        return endereco;
    }

    public static final class ClienteEntityBuilder {
        private String nome;
        private String sobreNome;
        private Integer idade;
        private String cpf;
        private LocalDate dataDeNascimento;
        private ContaBancariaEntity contaBancaria;
        private EnderecoEntity endereco;

        public ClienteEntityBuilder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public ClienteEntityBuilder withSobreNome(String sobreNome) {
            this.sobreNome = sobreNome;
            return this;
        }

        public ClienteEntityBuilder withIdade(Integer idade) {
            this.idade = idade;
            return this;
        }

        public ClienteEntityBuilder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public ClienteEntityBuilder withDataDeNascimento(LocalDate dataDeNascimento) {
            this.dataDeNascimento = dataDeNascimento;
            return this;
        }

        public ClienteEntityBuilder withContaBancaria(ContaBancariaEntity contaBancaria) {
            this.contaBancaria = contaBancaria;
            return this;
        }

        public ClienteEntityBuilder withEndereco(EnderecoEntity endereco) {
            this.endereco = endereco;
            return this;
        }

        public ClienteEntity build() {
            return new ClienteEntity(nome, sobreNome, idade, cpf, dataDeNascimento, contaBancaria, endereco);
        }
    }
}
