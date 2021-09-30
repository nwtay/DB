import java.util.ArrayList;

public class WildAttributeList {

    final ArrayList<String> stringList;

    public WildAttributeList(ArrayList<String> stringList)
    {
        this.stringList = stringList;
    }

    public boolean isWildAttributeList(Table table) throws Exception
    {
        if(stringList.size() == 1 && stringList.get(0).equals("*"))
        {
            return true;
        }

        AttributeList attrList = new AttributeList(stringList);
        return attrList.isAttributeList(table, false);
    }

    public ArrayList<String> getStringAttributes()
    {
        return stringList;
    }

}
