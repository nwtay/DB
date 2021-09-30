public class GreaterEqualsOperator extends Operator{

    public GreaterEqualsOperator()
    {
    }

    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        ValueType leftValueType = leftValue.getValueType();
        // don't need to check v2Type as done in OperatorParse

        if(leftValueType == ValueType.STRINGLITERAL || leftValueType == ValueType.BOOLEAN){
            return false;
        }
        if(leftValueType == ValueType.FLOAT){
            GreaterThanOperator greaterThanOperator = new GreaterThanOperator();
            EqualsOperator equalsOperator = new EqualsOperator();
            return equalsOperator.doOperation(leftValue, rightValue) || greaterThanOperator.doOperation(leftValue, rightValue);

        }
        return false;
    }

}
