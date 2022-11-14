package com.crash.br.adapter.broker.in.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public record ClienteRequest(String nome,
                             @JsonProperty("sobre_nome") String sobreNome,
                             Integer idade,
                             @JsonSerialize(using = LocalDateSerializer.class)
                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                             @JsonDeserialize(using = LocalDateDeserializer.class)
                             @JsonProperty("data_de_nascimento") LocalDate dataDeNascimento,
                             String cpf,
                             @JsonProperty("conta_bancaria") ContaBancariaRequest contaBancaria,
                             EnderecoRequest endereco) {

}
