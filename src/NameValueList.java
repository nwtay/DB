import java.util.ArrayList;

public class NameValueList {

    final ArrayList<String> stringList;
    private int currentIndex;

    public NameValueList(ArrayList<String> stringList)
    {
        this.stringList = stringList;
        currentIndex = 0;
    }

    public NameValueList(ArrayList<String> stringList, int currentIndex)
    {
        this.stringList = stringList;
        this.currentIndex = currentIndex;
    }

    public boolean isNameValueList(Table table) throws Exception
    {
        if(currentIndex < stringList.size() - 2){
            NameValuePair nameValuePair = new NameValuePair(stringList.get(currentIndex), stringList.get(currentIndex+1),
                    stringList.get(currentIndex+2));

            if(nameValuePair.isNameValuePair(table)) {
                currentIndex += 2;
                if (currentIndex == stringList.size() - 1) {
                    return true;
                }
                currentIndex++;
                if(stringList.get(currentIndex).equals(",")){
                    currentIndex++;
                    NameValueList nameValueList = new NameValueList(stringList, currentIndex);
                    return nameValueList.isNameValueList(table);
                }
            }
        }
        return false;
    }

}
