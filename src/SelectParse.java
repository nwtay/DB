import java.util.ArrayList;

public class SelectParse {

    final ArrayList<String> userCommand;
    private int currentIndex;
    final Database database;
    private Table table;
    private int fromIndex = 0;
    private WildAttributeList wildAttributeList;
    private ArrayList<String> conditionStatement;
    private Condition condition;

    public SelectParse(Database database, ArrayList<String> userCommand)
    {
        this.database = database;
        this.userCommand = userCommand;
        currentIndex = 0;
    }

    public boolean isSelect() throws Exception
    {
        currentIndex++;
        checkFromClause();
        checkTableName();
        checkWildAttributeList();
        currentIndex = fromIndex + 1;
        if(currentIndex == userCommand.size() - 1){
            return true;
        }
        currentIndex++;
        checkWhere();
        currentIndex++;
        checkCondition();
        return true;
    }

    private void checkCondition() throws Exception
    {
        int lastIndex = userCommand.size();
        ArrayList<String> subString = new ArrayList<>(userCommand.subList(currentIndex, lastIndex));
        condition = new Condition(subString, table);
        if(condition.isCondition()){
            throw new Exception("Invalid condition");
        }
        conditionStatement = subString;
    }

    public ArrayList<String> getConditionStatement()
    {
        return conditionStatement;
    }

    private void checkWhere() throws Exception
    {
        if(!userCommand.get(currentIndex).equals("WHERE")){
            throw new Exception("Expected WHERE before Condition");
        }
    }

    private void checkTableName() throws Exception
    {
        String tableName;
        int tableNameIndex = fromIndex + 1;
        tableName = userCommand.get(tableNameIndex);

        if(!database.tableNameExists(tableName)){
            throw new Exception("Table with this name does not exist");
        }

        DataReader dataReader = new DataReader(database, tableName);
        table = dataReader.getTable();
    }

    private void checkFromClause() throws Exception
    {
        for(int i = currentIndex; i < userCommand.size(); i++){
            if(userCommand.get(i).equals("FROM")){
                fromIndex = i;
            }
        }
        if(fromIndex == 0 )
        {
            throw new Exception("FROM cannot precede name value list");
        }
    }

    private void checkWildAttributeList() throws Exception
    {
        if(currentIndex != 1)
        {
            throw new Exception("Wild Attribute List in wrong place");
        }

        ArrayList<String> wildStringList = new ArrayList<>(userCommand.subList(currentIndex, fromIndex));
        wildAttributeList = new WildAttributeList(wildStringList);
        if(!wildAttributeList.isWildAttributeList(table))
        {
            throw new Exception("Invalid Wild Attribute list");
        }
    }

    public ArrayList<String> getWildAttrList()
    {
        // gets wild attribute list as an array list of strings
        return wildAttributeList.getStringAttributes();
    }

    public Table getResultTable()
    {
        return condition.getSubTable();
    }

}
