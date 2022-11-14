package com.crash.br.adapter.broker.in;

import com.crash.br.adapter.broker.in.request.ClienteRequest;
import com.crash.br.kafka.demo.Cliente;
import com.crash.br.kafka.demo.ContaBancaria;
import com.crash.br.kafka.demo.Endereco;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@Service
public class ProducerKafka {

    private final KafkaTemplate<String, Cliente> kafkaTemplate;

    public ProducerKafka(KafkaTemplate<String, Cliente> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ClienteRequest request) throws ExecutionException, InterruptedException {
        var endereco = Endereco.newBuilder()
                .setRua(request.endereco().rua())
                .setBairro(request.endereco().bairro())
                .setCep(request.endereco().cep())
                .setCidade(request.endereco().cidade())
                .setEstado(request.endereco().estado())
                .setNumeroDaCasa(request.endereco().numeroDaCasa())
                .setTelefone(request.endereco().telefone())
                .build();

        var contaBuild = ContaBancaria.newBuilder()
                .setNumeroDaConta(request.contaBancaria().numeroDaConta())
                .setAgencia(request.contaBancaria().agencia())
                .build();

        var clientBuild = Cliente.newBuilder()
                .setNome(request.cpf())
                .setSobreNome(request.sobreNome())
                .setIdade(request.idade())
                .setCpf(request.cpf())
                .setDataDeNascimento(request.dataDeNascimento().format(DateTimeFormatter.ISO_DATE))
                .setContaBancaria(contaBuild)
                .setEndereco(endereco)
                .build();

        kafkaTemplate.send("kafka-demo", request.cpf(), clientBuild).get();
    }
}
