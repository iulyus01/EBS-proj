package myapp.subscriptions;

public interface MatchChecker {
    boolean checkOperation(Object pubVal, Object subVal);
}
