package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.commands.CreateActivityCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityTest {
    private AggregateTestFixture<Activity> activityFixture;

    @BeforeEach
    void setUp() {
        activityFixture = new AggregateTestFixture<>(Activity.class);
    }

    @Test
    void handleCreateActivityCommand_shouldTriggerActivityCreatedEvent_whenCurrencyAlphaCodeIsValid() {
        activityFixture.givenNoPriorActivity()
                       .when(ActivityTestDataFactory.Commands.createActivity.get())
                       .expectSuccessfulHandlerExecution()
                       .expectEvents(ActivityTestDataFactory.Events.activityCreated.get());
    }

    @Test
    void handleCreateActivityCommand_shouldThrowIllegalStateException_whenCurrencyAlphaCodeIsNotValid() {
        activityFixture.givenNoPriorActivity()
                       .when(new CreateActivityCommand(ActivityTestDataFactory.activityId.get(),
                                                       ActivityTestDataFactory.ACTIVITY_TITLE,
                                                       "INVALID CURRENCY"))
                       .expectException(IllegalStateException.class)
                       .expectNoEvents();
    }

    @Test
    void handleEnrollParticipantsCommand_shouldTriggerParticipantsEnrolledEvent() {
        activityFixture.given(ActivityTestDataFactory.Events.activityCreated.get())
                       .when(ActivityTestDataFactory.Commands.enrollParticipants.get())
                       .expectSuccessfulHandlerExecution()
                       .expectEvents(ActivityTestDataFactory.Events.participantsEnrolled.get());
    }
}
