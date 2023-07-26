package dev.pabferir.api;

public final class ApiPaths {
    static final String API_V1 = "/api/v1";
    public static final String ACTIVITIES = API_V1 + "/activities";

    private ApiPaths() {
    }

    public static final class Activities {
        public static final String ENROLL_PARTICIPANTS = "/{activityId}/participants";
        public static final String REGISTER_EXPENSE = "/{activityId}/expenses";

        private Activities() {
        }
    }

}
