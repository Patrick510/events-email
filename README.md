# 📧 Microsserviço de Envio de E-mails (`ms-email`)

Este microsserviço é responsável por receber mensagens via RabbitMQ e enviar e-mails automáticos informando sobre a inscrição de participantes em eventos. Ele escuta uma fila específica, processa os dados recebidos e envia um e-mail para o destinatário informado.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Mail
- Spring AMQP (RabbitMQ)
- MySQL
- RabbitMQ (CloudAMQP)
- Lombok

---

## 📦 Funcionalidade Principal

- Escutar mensagens na fila `ms.email.queue` via RabbitMQ.
- Processar dados do evento e do participante.
- Enviar e-mail confirmando a inscrição.
- Persistir os dados do e-mail enviado no banco de dados.

---

## 🧱 Estrutura de Pacotes

```
com.event.email
├── configs           → Configuração do RabbitMQ
├── consumers         → Listener das mensagens da fila
├── controllers       → Endpoints REST para testes
├── dtos              → Objetos de transferência de dados
├── enums             → Enums para status e perfis
├── models            → Entidades JPA
├── repositories      → Interfaces de acesso ao banco
└── services          → Lógica de envio e persistência de e-mails
```

---

## 📡 Configuração do RabbitMQ

A configuração da fila, exchange e binding está em:

```java
// RabbitMQConfig.java
@Bean
public Queue queue() {
    return new Queue(queue, true);
}

@Bean
public TopicExchange topicExchange() {
    return new TopicExchange("email-exchange");
}

@Bean
public Binding binding(Queue queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(queue).to(topicExchange).with("email-routing-key");
}
```

A aplicação escuta a fila `ms.email.queue` e espera mensagens com o seguinte JSON:

```json
{
  "emailTo": "participante@email.com",
  "eventTitle": "Nome do Evento",
  "eventDate": "2025-07-01T18:00:00"
}
```

---

## 📧 Lógica de Envio de E-mail

O consumer `EmailConsumer.java` recebe a mensagem e converte em um `EmailModel`. Em seguida:

- Define remetente, assunto e corpo do e-mail.
- Tenta enviar usando o `JavaMailSender`.
- Registra o envio com status `SENT` ou `ERROR` no banco de dados.

---

## 📄 Endpoints REST para Testes

Você pode testar o envio direto com o Postman usando o endpoint abaixo:

```
POST /send-email
```

**Body (JSON):**

```json
{
  "ownerRef": "Sistema de Eventos",
  "emailFrom": "seu.email@gmail.com",
  "emailTo": "destinatario@email.com",
  "subject": "Inscrição Confirmada",
  "body": "Parabéns, sua inscrição foi realizada com sucesso!"
}
```

---

## 🛠️ Exemplo de `.properties`

```properties
server.port=9090

spring.datasource.url=jdbc:mysql://localhost:3306/estufa_email?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu.email@gmail.com
spring.mail.password=senha_de_aplicativo
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

spring.rabbitmq.addresses=amqps://<usuário>:<senha>@<host>.rmq.cloudamqp.com/<vhost>
spring.rabbitmq.queue=ms.email.queue
```

---

## ✅ Banco de Dados

O banco de dados armazena os e-mails enviados com status e data de envio. Tabela principal:

- `TB_EMAIL`

---

## 🧪 Testando o Funcionamento

1. Inicie o microsserviço `ms-email`.
2. Registre um participante no `ms-events` (POST `/events/{id}/register`).
3. Verifique se o e-mail foi enviado com sucesso (tanto no console quanto na tabela `TB_EMAIL`).

---

## 👨‍💻 Desenvolvedor

Patrick Dias  
Disciplina: Desenvolvimento de Aplicações Distribuídas  
Professor: [Nome do professor se desejar incluir]

---
