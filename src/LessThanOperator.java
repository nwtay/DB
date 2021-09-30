public class LessThanOperator extends Operator{

    public LessThanOperator()
    {
    }

    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        ValueType leftValueType = leftValue.getValueType();
        // don't need to check v2Type as done in OperatorParse

        if (leftValueType == ValueType.STRINGLITERAL || leftValueType == ValueType.BOOLEAN) {
            return false;
        }

        if (leftValueType == ValueType.FLOAT) {
            Float float1 = Float.parseFloat(leftValue.getString());
            Float float2 = Float.parseFloat(rightValue.getString());
            return float1.compareTo(float2) < 0;
        }

        return false;
    }
}
