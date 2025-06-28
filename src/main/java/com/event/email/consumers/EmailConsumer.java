package com.event.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.event.email.dtos.EventEmailDto;
import com.event.email.models.EmailModel;
import com.event.email.services.EmailService;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EventEmailDto emailDto) {
        EmailModel emailModel = new EmailModel();

        emailModel.setOwnerRef("Sistema de Eventos");
        emailModel.setEmailFrom("patrick.dias@estudante.ifms.edu.br");
        emailModel.setEmailTo(emailDto.emailTo());
        emailModel.setSubject("Inscrição Confirmada: " + emailDto.eventTitle());

        String corpo = String.format(
                "Olá!\nVocê está inscrito no evento: %s\nData: %s\nEsperamos por você!",
                emailDto.eventTitle(),
                emailDto.eventDate());

        emailModel.setBody(corpo);

        emailService.sendEmail(emailModel);
        System.out.println("Email status: " + emailModel.getStatusEmail().toString());
    }
}
