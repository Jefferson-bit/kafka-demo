package com.crash.br.service;

import com.crash.br.cliente.ClienteEntity;
import com.crash.br.cliente.ClienteRepository;
import com.crash.br.contabancaria.ContaBancariaEntity;
import com.crash.br.endereco.EnderecoEntity;
import com.crash.br.kafka.demo.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
class CadastraClienteNaBase implements BuscaClienteNoKafka {

    private final ClienteRepository repository;

    public CadastraClienteNaBase(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void buscaCliente(Cliente payload) {

        var entity = new ClienteEntity.ClienteEntityBuilder()
                .withNome(payload.getNome())
                .withSobreNome(payload.getSobreNome())
                .withCpf(payload.getCpf())
                .withIdade(payload.getIdade())
                .withDataDeNascimento(LocalDate.parse(payload.getDataDeNascimento(), DateTimeFormatter.ISO_LOCAL_DATE))
                .withContaBancaria(new ContaBancariaEntity(payload.getContaBancaria().getNumeroDaConta(), payload.getContaBancaria().getAgencia()))
                .withEndereco(new EnderecoEntity.EnderecoEntityBuilder()
                        .withRua(payload.getEndereco().getRua())
                        .withBairro(payload.getEndereco().getBairro())
                        .withCidade(payload.getEndereco().getCidade())
                        .withEstado(payload.getEndereco().getEstado())
                        .withTelefone(payload.getEndereco().getTelefone())
                        .withNumeroDaCasa(payload.getEndereco().getNumeroDaCasa())
                        .withCep(payload.getEndereco().getCep())
                        .build())
                .build();

        repository.save(entity);
    }
}
