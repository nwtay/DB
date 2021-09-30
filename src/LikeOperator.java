public class LikeOperator extends Operator{

    public LikeOperator()
    {
    }

    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        ValueType leftValueType = leftValue.getValueType();
        // don't need to check v2Type as done in OperatorParse

        if(leftValueType != ValueType.STRINGLITERAL){
            return false;
        }
        String stringToSearch = leftValue.getString();
        int lastIndex = rightValue.getString().length();
        String charSeq = rightValue.getString().substring(1, lastIndex - 1);
        return stringToSearch.contains(charSeq);
    }

}
