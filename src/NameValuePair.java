public class NameValuePair {

    final String inputString1;
    final String inputString2;
    final String inputString3;

    public NameValuePair(String inputString1, String inputString2, String inputString3)
    {
        this.inputString1 = inputString1;
        this.inputString2 = inputString2;
        this.inputString3 = inputString3;
    }

    public boolean isNameValuePair(Table t) throws Exception
    {
        AttributeName attrName = new AttributeName(inputString1);
        if(attrName.isAttributeName(t)){
            if(inputString2.equals("=")){
                Value v1 = new Value(inputString3);
                return v1.isValue();
            }
        }
        return false;
    }

}
