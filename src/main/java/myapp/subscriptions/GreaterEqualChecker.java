package myapp.subscriptions;

public class GreaterEqualChecker implements MatchChecker {
    @Override
    public boolean checkOperation(Object pubVal, Object subVal) {
        if(pubVal instanceof Integer) {
            return (Integer) pubVal >= (Integer) subVal;
        }
        return false;
    }
}
