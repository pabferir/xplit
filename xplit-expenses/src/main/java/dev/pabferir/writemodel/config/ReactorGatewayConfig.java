package dev.pabferir.writemodel.config;

import dev.pabferir.writemodel.activities.commands.CreateActivityCommandValidator;
import dev.pabferir.writemodel.activities.commands.EnrollParticipantsCommandValidator;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactorGatewayConfig {

    @Autowired
    public void reactiveCommandGatewayConfiguration(ReactorCommandGateway reactorCommandGateway) {
        reactorCommandGateway.registerDispatchInterceptor(new CreateActivityCommandValidator());
        reactorCommandGateway.registerDispatchInterceptor(new EnrollParticipantsCommandValidator());
    }
}
