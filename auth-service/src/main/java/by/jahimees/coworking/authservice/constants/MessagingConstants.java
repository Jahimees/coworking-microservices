package by.jahimees.coworking.authservice.constants;

public final class MessagingConstants {

    private MessagingConstants() {}

    public static final String AUTH_QUEUE = "auth-queue";
    public static final String USER_QUEUE = "user-queue";


    public static final String CREATE_ACTION = "create";
    public static final String ROLLBACK_ACTION = "rollback";
    public static final String NOTHING_ACTION = "nothing";
    public static final String COMMIT_ACTION = "commit";

    public static final String AUTH_SERVICE = "auth-service";
    public static final String USER_SERVICE = "user-service";

    public static final String ERROR_STATUS = "error";
    public static final String DONE_STATUS = "done";
    public static final String CREATED_STATUS = "created";

    public static final String COMMITED_DB_STATUS = "COMMITED";
}
