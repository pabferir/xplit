package dev.pabferir.api.activities.controllers;

import dev.pabferir.api.ApiPaths;
import dev.pabferir.api.activities.contracts.CreateActivityRequest;
import dev.pabferir.api.activities.contracts.EnrollParticipantsRequest;
import dev.pabferir.api.activities.contracts.RegisterExpenseRequest;
import dev.pabferir.writemodel.activities.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activities.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activities.commands.RegisterExpenseCommand;
import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(path = ApiPaths.ACTIVITIES)
public class ActivitiesCommandController {
    private final ReactorCommandGateway commandGateway;

    public ActivitiesCommandController(ReactorCommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> createActivity(@RequestBody CreateActivityRequest request) {
        return commandGateway.send(new CreateActivityCommand(
                                     request.title(),
                                     request.currencyAlphaCode()))
                             .map(ResponseEntity::ok);
    }

    @PostMapping(ApiPaths.Activities.ENROLL_PARTICIPANTS)
    public Mono<ResponseEntity<Object>> enrollParticipants(@PathVariable UUID activityId,
                                                           @RequestBody EnrollParticipantsRequest request) {
        return commandGateway.send(new EnrollParticipantsCommand(
                                     new ActivityId(activityId.toString()),
                                     request.participantNames()))
                             .map(ResponseEntity::ok);
    }

    @PostMapping(ApiPaths.Activities.REGISTER_EXPENSE)
    public Mono<ResponseEntity<Object>> registerExpense(@PathVariable UUID activityId,
                                                        @RequestBody RegisterExpenseRequest request) {
        return commandGateway.send(new RegisterExpenseCommand(
                                     new ActivityId(activityId.toString()),
                                     request.title(),
                                     request.amount(),
                                     request.currencyAlphaCode(),
                                     new ParticipantId(request.paidBy().toString()),
                                     request.paidFor()
                                            .stream()
                                            .map(uuid -> new ParticipantId(uuid.toString()))
                                            .toList(),
                                     Instant.now()
                             ))
                             .map(ResponseEntity::ok);
    }
}
