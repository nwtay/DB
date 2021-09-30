abstract class Operator {

    protected String name;

    public Operator()
    {
    }

    // leftValue is the value on the LHS of the operator
    public boolean doOperation(Value leftValue, Value rightValue) throws Exception
    {
        return false;
    }

}
