public class overwriteException extends Exception {
    @Override
    public String getMessage() {
        return ("Map was overwritten, description is ambiguous");
    }
}
