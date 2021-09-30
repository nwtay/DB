public class AttributeName {

    final String attributeName;

    public AttributeName(String attrName)
    {
        this.attributeName = attrName;
    }

    public boolean isAttributeName(Table inputTable)
    {
        // assuming table exists?
        if(inputTable != null && allAlphNumeric(attributeName)){
            return inputTable.columnExists(attributeName);
        }
        // if attribute name is not a column name in this table, its not an attribute name
        return false;
    }

    private boolean allAlphNumeric(String testString)
    {
        String matcher = "([\\W])";
        for(int i = 0; i < testString.length(); i++)
        {
            String s = testString.substring(i,i+1);
            if(s.matches(matcher)){
                return false;
            }
        }
        return true;
    }

}
