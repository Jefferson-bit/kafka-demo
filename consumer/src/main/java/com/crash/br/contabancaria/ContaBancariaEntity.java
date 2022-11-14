package com.crash.br.contabancaria;

import com.crash.br.cliente.ClienteEntity;

import javax.persistence.*;

@Entity
@Table(name = "conta_bancaria")
public class ContaBancariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer numeroDaConta;
    @Column(nullable = false)
    private Integer agencia;

    @OneToOne(mappedBy = "contaBancaria")
    private ClienteEntity cliente;

    @Deprecated
    public ContaBancariaEntity() {
    }

    public ContaBancariaEntity(Integer numeroDaConta, Integer agencia, ClienteEntity cliente) {
        this.numeroDaConta = numeroDaConta;
        this.agencia = agencia;
        this.cliente = cliente;
    }

    public ContaBancariaEntity(Integer numeroDaConta, Integer agencia) {
        this.numeroDaConta = numeroDaConta;
        this.agencia = agencia;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public Integer getNumeroDaConta() {
        return numeroDaConta;
    }

    public Integer getAgencia() {
        return agencia;
    }
}
