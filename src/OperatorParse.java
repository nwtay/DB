public class OperatorParse {

    final String input;
    private ValueType leftValueType;
    private ValueType rightValueType;
    final Operator operator;

    public OperatorParse(String input)
    {
        OperatorCollection operatorCollection = new OperatorCollection();
        this.input = input;
        operator = operatorCollection.identifyOperator(input);
    }

    public boolean isOperator()
    {
        return this.operator != null;
    }

    private void checkTypes() throws Exception
    {
        if(leftValueType != rightValueType){
            String errMsg = "Couldn't convert " + leftValueType + " to " + rightValueType;
            throw new Exception(errMsg);
        }
    }

    public String getInput()
    {
        return input;
    }

    public boolean doOperation(Value v1, Value v2) throws Exception
    {
        leftValueType = v1.getValueType();
        rightValueType = v2.getValueType();
        checkTypes();
        return operator.doOperation(v1, v2);
    }

}
