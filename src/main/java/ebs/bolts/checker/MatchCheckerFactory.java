package ebs.bolts.checker;

import ebs.common.Operator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MatchCheckerFactory implements Serializable {

    private static MatchCheckerFactory factory;
    private static final Map<Operator, MatchChecker> checkersMap = new HashMap<>();

    private MatchCheckerFactory() {

        checkersMap.put(Operator.EQUAL, new EqualChecker());
        checkersMap.put(Operator.EQUAL_AVERAGE, new EqualChecker());
        checkersMap.put(Operator.NOT_EQUAL, new NotEqualChecker());
        checkersMap.put(Operator.NOT_EQUAL_AVERAGE, new NotEqualChecker());
        checkersMap.put(Operator.GREATER, new GreaterChecker());
        checkersMap.put(Operator.GREATER_EQUAL_AVERAGE, new GreaterChecker());
        checkersMap.put(Operator.LOWER, new LowerChecker());
        checkersMap.put(Operator.LOWER_AVERAGE, new LowerChecker());
        checkersMap.put(Operator.GREATER_EQUAL, new GreaterEqualChecker());
        checkersMap.put(Operator.GREATER_AVERAGE, new GreaterEqualChecker());
        checkersMap.put(Operator.LOWER_EQUAL, new LowerEqualChecker());
        checkersMap.put(Operator.LOWER_EQUAL_AVERAGE, new LowerEqualChecker());
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
