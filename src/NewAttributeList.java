import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NewAttributeList {

    final ArrayList<String> stringList;
    final AttributeList attributeList;

    public NewAttributeList(ArrayList<String> stringList)
    {
        this.stringList = stringList;
        attributeList = new AttributeList(stringList);
    }

    public boolean isAttributeList(boolean newFlag) throws Exception
    {
        // similar to attribute list, however since the initial table will have no
        // attributes, need to include additional test of no repetitions in attribute list
        if(attributeList.isAttributeList(null, newFlag)){
            checkAllUnique();
            checkNoId();
            return true;
        }
        return false;
    }

    private void checkAllUnique() throws Exception
    {
        // plain attributes, no commas
        ArrayList<String> plainAttributes = attributeList.getPlainAttributeList();
        // converted to Hash Set, as only stores unique values
        Set<String> attributeSet = new HashSet<>(plainAttributes);
        if(attributeSet.size() < plainAttributes.size()){
            throw new Exception("New attribute list contains non-unique attribute names.");
        }
    }

    private void checkNoId() throws Exception
    {
       // can't add id column as already exists
        if(stringList.contains("id")){
            throw new Exception("Cannot add id column, as this is created automatically and is fixed");
        }
    }


}