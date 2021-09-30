public class LessEqualsOperator extends Operator{


    public LessEqualsOperator()
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
            LessThanOperator lessThanOperator = new LessThanOperator();
            EqualsOperator equalsOperator = new EqualsOperator();
            return lessThanOperator.doOperation(leftValue, rightValue) || equalsOperator.doOperation(leftValue, rightValue);
        }
        return false;
    }
}
