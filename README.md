
# Sobre o projeto
A inspiração de criar esse repositório com configurações do Kafka, é justamente para quando eu precisar relembrar de algo, eu posso consultar aqui. As informações, código e configurações foi tirada do dia-a-dia no ambiente de trabalho. Aqui não existe certo ou errado, mas sim entender todas as vantagens e desvantagens que o Kafka traz. Vou tentar explicar cada ponto das configurações que fiz aqui. Qualquer critica construtiva e correção é perfeitamente bem-vinda.

## Overview
O kafka foi desenvolvido pelo Linkedin, criado em janeiro de 2011, ele é escrito em Scala e parte da familía de brokers de mensagerias.
Caso esteja se perguntando do [porque utilizar o Kafka](https://blog.kafkabr.com/posts/porque-apache-kafka/) tem esse topico muito legal
## Porque precisamos de mensagerias?
Hoje a maioria do mercado de trabalho foca bastante em [MicroServiço](https://martinfowler.com/articles/microservices.html) porém microserviços por sí só são complexos, e o que os torna complexos são 8 fatores que nós desenvolvedores de sistema distribuidos vivenciamos, que são:

1. Instabilidade da rede
2. Problema de latencia
3. Limite de bandas
4. Segurança de Rede
5. Topologia
6. Densenvolvedores que trabalham em sistema distintos que muda constantemente
7. Custo para transporta dados
8. Heterogenidade da nossa rede: Clientes que podem ser uma maquina, um browser, smartphone, internet das coisas e sistemas embarcados.

Agora imagine em uma startup que existem diversos microserviços se conectando entre si:

![image](https://user-images.githubusercontent.com/54369158/203449742-8214123a-0ffd-41c3-a310-1f2af05f1ce2.png)

Isso com certeza é um caos! Diversas peças fazendo conexões entre si, podendo ocorrer os 8 fatores de sistema distribuido.
Se um microservço A cai, diretamente ele vai afeta o B C D e por ai vai. Imagine o inferno que é isso! Então mensageria veio para mitigar alguns gaps
dessas conexões entre sistemas distribuidos, trazendo:
* Desacoplamento
* Gestão centralizada das mensagens
* Buffering de mensagens

Com um serviço de mensageria nossas peças agora podem centralizar sua conexão, dessa forma:
![image](https://user-images.githubusercontent.com/54369158/203450508-4289c0f8-ec87-4e06-941c-68d25891afc5.png)

Muito mais simples, não? Temos agora os serviços se conectando em uma peça especifica que faz diversas coisas. E como eu coloquei no desenho, pode
ser Kafka, RabbitMQ, ActiveMq, não importa. Claro que se o broker de mensageria cair, as peças podem ser prejudicadas, no entanto temos um único ponto de falha,
oque torna a manutenção muito mais simples e rápida.

### Principais características do Kafka
* Escalabilidade: Partições ajudam na escalabilidade dos produtores e consumidores
* Publish-Subscrive: Consumer Groups
* Ordenação: Para que a odernação ocorra, é necessário definir uma chave para as mensagens
* Alta disponibilidade: Atráves de técnicas de replicação, o Kafka garante alta disponibilidade das mensagens
* Alto Throughput
### Porque o Kafka é considerado tão rápido?
* Acesso sequencial ao disco
* Usa técnica de Zero-copy
* USa OS page cache
* usa técnica de ´batch compression´ para reduzir o tamanho do pacte trafegado

### Semânticas de Produção
 * At least Once: Retenta se não obtiver o ACK do Broker
 * At most once: Nunca retenta
 * Exactly once: Garante sempre 1 mensagem(Idempotence)
### Semânticas de Consumo
* At least Once: Processa mensagens, então faz o commit dos offsets
* At most once: Faz o commit dos offsets, então processa as mensagens
* Exactly once: At lest Once + Idempotência do lado do consumidor



(![image](https://docs.confluent.io/platform/current/_images/schema-registry-and-kafka.png)
