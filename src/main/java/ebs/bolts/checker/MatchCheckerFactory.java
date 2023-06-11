package ebs.bolts.checker;

import ebs.common.Operator;

import java.util.HashMap;
import java.util.Map;

public class MatchCheckerFactory {

    private static MatchCheckerFactory factory;
    private static final Map<Operator, MatchChecker> checkersMap = new HashMap<>();

    private MatchCheckerFactory() {

        checkersMap.put(Operator.EQUAL, new EqualChecker());
        checkersMap.put(Operator.NOT_EQUAL, new NotEqualChecker());
        checkersMap.put(Operator.GREATER, new GreaterChecker());
        checkersMap.put(Operator.LOWER, new LowerChecker());
        checkersMap.put(Operator.GREATER_EQUAL, new GreaterEqualChecker());
        checkersMap.put(Operator.LOWER_EQUAL, new LowerEqualChecker());
    }

    public MatchChecker getMatcher(Operator operator) {
        return checkersMap.get(operator);
    }

    public static MatchCheckerFactory getInstance() {

        if(factory == null) {
            factory = new MatchCheckerFactory();
        }

        return factory;
    }
}
