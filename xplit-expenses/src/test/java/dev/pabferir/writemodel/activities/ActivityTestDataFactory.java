package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activities.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activities.events.ActivityCreatedEvent;
import dev.pabferir.writemodel.activities.events.ParticipantsEnrolledEvent;
import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.Currency;

import java.util.List;
import java.util.function.Supplier;

public final class ActivityTestDataFactory {
    public static final String ACTIVITY_UUID = "3f7a5465-dd3e-4eaf-8b00-9b067f929a92";
    public static final String ACTIVITY_TITLE = "A Testable Activity";
    public static final Currency ACTIVITY_CURRENCY = Currency.EUR;
    public static final String[] ACTIVITY_PARTICIPANTS = new String[]{"Alice", "Bob"};

    public static final Supplier<ActivityId> activityId = () -> new ActivityId(ACTIVITY_UUID);

    public static final class Commands {
        public static final Supplier<CreateActivityCommand> createActivity =
                () -> new CreateActivityCommand(activityId.get(),
                                                ActivityTestDataFactory.ACTIVITY_TITLE,
                                                ActivityTestDataFactory.ACTIVITY_CURRENCY.getAlphaCode());
        public static final Supplier<EnrollParticipantsCommand> enrollParticipants =
                () -> new EnrollParticipantsCommand(activityId.get(),
                                                    List.of(ACTIVITY_PARTICIPANTS));

        private Commands() {
        }
    }

    public static final class Events {
        public static final Supplier<ActivityCreatedEvent> activityCreated =
                () -> new ActivityCreatedEvent(activityId.get(),
                                               ActivityTestDataFactory.ACTIVITY_TITLE,
                                               ActivityTestDataFactory.ACTIVITY_CURRENCY);
        public static final Supplier<ParticipantsEnrolledEvent> participantsEnrolled =
                () -> new ParticipantsEnrolledEvent(List.of(ACTIVITY_PARTICIPANTS));

        private Events() {
        }
    }

    private ActivityTestDataFactory() {
    }
}
