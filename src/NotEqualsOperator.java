public class NotEqualsOperator extends Operator{

    public NotEqualsOperator()
    {
    }

    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        EqualsOperator equalsOperator = new EqualsOperator();
        // opposite result to equals operator
        return !equalsOperator.doOperation(leftValue, rightValue);
    }
}
