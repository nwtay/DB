import java.util.ArrayList;

public class SplitList {

    final ArrayList<Character> splitList;

    public SplitList()
    {
        splitList = new ArrayList<>();
        splitList.add('>');
        splitList.add('<');
        splitList.add('=');
        splitList.add('!');
        splitList.add('(');
        splitList.add(')');
        splitList.add(',');
        splitList.add(';');
        splitList.add('*');
    }

    public boolean isInSplitList(Character inputChar)
    {
        return splitList.contains(inputChar);
    }

}
