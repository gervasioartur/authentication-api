package domain.entities.errors;

public class EmailInUserError extends Error {
    private static final long serialVersionUID = 1L;
    public EmailInUserError() {
        super("This email is already in use, please user other email or try to login.");
    }
}
