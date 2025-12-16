package com.fixwi.fixwi_backend.infrastructure.config;

import com.fixwi.fixwi_backend.application.usecase.ai.FetchAISuggestionUseCase;
import com.fixwi.fixwi_backend.application.usecase.auth.LoginUseCase;
import com.fixwi.fixwi_backend.application.usecase.auth.SignupUseCase;
import com.fixwi.fixwi_backend.application.usecase.ticket.CreateTicketUseCase;
import com.fixwi.fixwi_backend.application.usecase.ticket.FindTicketUseCase;
import com.fixwi.fixwi_backend.application.usecase.ticket.UpdateTicketStatusUseCase;
import com.fixwi.fixwi_backend.application.usecase.ticket.TicketMetricsUseCase;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.GetMetricsTicketUseCase;
import com.fixwi.fixwi_backend.domain.ports.in.ai.FetchAISuggestionPort;
import com.fixwi.fixwi_backend.domain.ports.in.auth.LoginPort;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.UpdateTicketStatusPort;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.CreateTicketPort;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.FindTicketPort;
import com.fixwi.fixwi_backend.domain.ports.out.AISuggestionPort;
import com.fixwi.fixwi_backend.domain.ports.out.CategoryPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.UserPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.*;
import com.fixwi.fixwi_backend.domain.ports.out.security.PasswordEncoderPort;
import com.fixwi.fixwi_backend.domain.ports.out.security.TokenProviderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

// Configuration class that manages Use Cases (Domain/Application Classes)
// injecting the Infrastructure dependencies
@Configuration
public class UseCaseConfig {

    @Bean
    @Transactional
    public CreateTicketPort createTicketPort(
            TicketPersistencePort ticketPersistencePort,
            UserPersistencePort userPersistencePort,
            CategoryPersistencePort categoryPersistencePort) {

        // The business logic class is instantiated, and I pass the injected ports.
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

    @Bean
    public FetchAISuggestionPort fetchAISuggestionPort(AISuggestionPort aiSuggestionPort) {
        // This use case does not require @Transactional since it is read-only/calls an external API.
        return new FetchAISuggestionUseCase(aiSuggestionPort);
    }

    @Bean
    @Transactional
    public LoginUseCase loginUseCase(PasswordEncoderPort passwordEncoder, TokenProviderPort tokenProvider, LoadUserPort loadUserPort) {
        return new LoginUseCase(passwordEncoder, tokenProvider, loadUserPort);
    }

    @Bean
    @Transactional
    public SignupUseCase signupUseCase(SaveUserPort saveUserPort, LoadUserPort loadUserPort, PasswordEncoderPort passwordEncoder){
        return new SignupUseCase(saveUserPort, loadUserPort, passwordEncoder);
    }

    @Bean
    @Transactional
    public GetMetricsTicketUseCase ticketMetrictsPort(TicketMetricsPort ticketMetricsPort){
        return new TicketMetricsUseCase(ticketMetricsPort);
    }
    @Bean
    public UpdateTicketStatusPort updateTicketStatusPort(TicketPersistencePort ticketPersistencePort) {
        return new UpdateTicketStatusUseCase(ticketPersistencePort);
    }
}