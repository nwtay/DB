public class GreaterThanOperator extends Operator{

    public GreaterThanOperator()
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
            Float f1 = Float.parseFloat(leftValue.getString());
            Float f2 = Float.parseFloat(rightValue.getString());
            return f1.compareTo(f2) > 0;
        }
        return false;
    }
}
