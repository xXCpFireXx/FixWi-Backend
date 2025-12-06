package com.fixwi.fixwi_backend.infrastructure.config;

import com.fixwi.fixwi_backend.application.usecase.ticket.CreateTicketUseCase;
import com.fixwi.fixwi_backend.application.usecase.ticket.FindTicketUseCase;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.CreateTicketPort;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.FindTicketPort;
import com.fixwi.fixwi_backend.domain.ports.out.CategoryPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.UserPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

// Clase de configuración que gestiona los Use Cases (Clases de Dominio/Aplicación)
// inyectando las dependencias de Infraestructura
@Configuration
public class UseCaseConfig {

    @Bean
    @Transactional
    public CreateTicketPort createTicketPort(
            TicketPersistencePort ticketPersistencePort,
            UserPersistencePort userPersistencePort,
            CategoryPersistencePort categoryPersistencePort) {

        // Se instancia la clase de lógica de negocio y paso los puertos inyectados
        return new CreateTicketUseCase(
                ticketPersistencePort,
                userPersistencePort,
                categoryPersistencePort
        );
    }
    @Bean
    @Transactional
    public FindTicketPort findTicketPort(TicketPersistencePort ticketPersistencePort){
        return new FindTicketUseCase(ticketPersistencePort);
    }

}