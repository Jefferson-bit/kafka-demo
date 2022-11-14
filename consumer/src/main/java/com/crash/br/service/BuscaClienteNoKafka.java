package com.crash.br.service;

import com.crash.br.kafka.demo.Cliente;

public interface BuscaClienteNoKafka {

    void buscaCliente(Cliente cliente);
}
