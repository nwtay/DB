import java.util.HashMap;

public class OperatorCollection {

    final HashMap<String, Operator> operatorLog;

    public OperatorCollection()
    {
        operatorLog = new HashMap<>();
        operatorLog.put("==", new EqualsOperator());
        operatorLog.put("!=", new NotEqualsOperator());
        operatorLog.put(">", new GreaterThanOperator());
        operatorLog.put("<", new LessThanOperator());
        operatorLog.put(">=", new GreaterEqualsOperator());
        operatorLog.put("<=", new LessEqualsOperator());
        operatorLog.put("LIKE", new LikeOperator());
    }

    public Operator identifyOperator(String input)
    {
        return operatorLog.get(input);
    }

}
