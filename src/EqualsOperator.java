public class EqualsOperator extends Operator{

    public EqualsOperator()
    {
    }

    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        ValueType leftValueType = leftValue.getValueType();
        // don't need to check v2Type as done in OperatorParse

        if(leftValueType == ValueType.STRINGLITERAL){
            String s1 = leftValue.getString();
            String s2 = rightValue.getString();
            return s1.equals(s2);
        }

        if(leftValueType == ValueType.BOOLEAN){
            return leftValue.getString().equalsIgnoreCase(rightValue.getString().toLowerCase());
        }

        if(leftValueType == ValueType.FLOAT){
            Float f1 = Float.parseFloat(leftValue.getString());
            Float f2 = Float.parseFloat(rightValue.getString());
            return f1.compareTo(f2) == 0;
        }
        return false;
    }

}
