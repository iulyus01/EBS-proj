package ebs.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum Operator implements Serializable{

    EQUAL,
    NOT_EQUAL,
    GREATER,
    LOWER,
    GREATER_EQUAL,
    LOWER_EQUAL,
    EQUAL_AVERAGE,
    NOT_EQUAL_AVERAGE,
    GREATER_AVERAGE,
    LOWER_AVERAGE,
    GREATER_EQUAL_AVERAGE,
    LOWER_EQUAL_AVERAGE;

    public boolean isSimple() {

        List<Operator> simpleOperators = Arrays.asList(EQUAL, NOT_EQUAL, GREATER, LOWER, GREATER_EQUAL, LOWER_EQUAL);

        return simpleOperators.stream().filter(simpleOperator -> simpleOperator == this).count() == 1;
    }
}
