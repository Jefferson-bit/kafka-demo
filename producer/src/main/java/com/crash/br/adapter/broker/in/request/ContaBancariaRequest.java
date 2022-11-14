package com.crash.br.adapter.broker.in.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContaBancariaRequest(Integer agencia, @JsonProperty("numero_da_conta") Integer numeroDaConta){}
