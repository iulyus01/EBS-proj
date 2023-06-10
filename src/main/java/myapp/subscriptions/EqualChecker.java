package myapp.subscriptions;

public class EqualChecker implements MatchChecker {
    @Override
    public boolean checkOperation(Object pubVal, Object subVal) {
        if(pubVal instanceof String) {
            return ((String) pubVal).compareTo((String) subVal) == 0;
        }
        if(pubVal instanceof Integer) {
            return pubVal == subVal;
        }
        return false;
    }
}
