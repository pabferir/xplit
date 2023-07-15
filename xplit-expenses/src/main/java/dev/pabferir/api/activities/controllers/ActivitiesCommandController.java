package dev.pabferir.api.activities.controllers;

import dev.pabferir.api.ApiPaths;
import dev.pabferir.api.activities.contracts.CreateActivityRequest;
import dev.pabferir.api.activities.contracts.EnrollParticipantsRequest;
import dev.pabferir.writemodel.activity.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activity.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activity.valueobjects.ActivityId;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}
