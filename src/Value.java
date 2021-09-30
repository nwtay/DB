public class Value {

    final String input;
    private ValueType valueType;

    public Value(String input)
    {
        this.input = input;
    }

    public boolean isValue() throws Exception
    {
        return input != null && validType(input);
    }

    private boolean validType(String input) throws Exception
    {
        if(isStringLiteral(input)){
            return true;
        }
        if(isBool(input)){
            return true;
        }
        return isFloat(input);
    }

    private boolean isStringLiteral(String input)
    {
        if(input.charAt(0) == '\'' && input.charAt(input.length()-1) == '\''){
            if(validLiteral()){
                valueType = ValueType.STRINGLITERAL;
                return true;
            }
        }
        return false;
    }

    private boolean validLiteral()
    {
        String subString = input.substring(1, input.length()-1);
        return !subString.contains("\t") && !subString.contains("'");
    }

    // since its the last check in isValue, will throw the exception
    private boolean isFloat(String input) throws Exception
    {
        try{
            Float.parseFloat(input);
            valueType = ValueType.FLOAT;
            return true;
        }
        catch (Exception E)
        {
            throw new Exception(input + " is not a valid value type.");
        }
    }

    private boolean isBool(String input)
    {
        if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")){
            valueType = ValueType.BOOLEAN;
            return true;
        }
        return false;
    }

    public ValueType getValueType() throws Exception
    {
        if(isValue()){
            return valueType;
        }
        return null;
    }

    public String getString()
    {
        return input;
    }

}
