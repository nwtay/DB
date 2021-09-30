import java.util.ArrayList;

public class UpdateParse {

    final ArrayList<String> userCommand;
    private int currentIndex;
    final Database database;
    private Table table;
    private ArrayList<String> conditionStatement;
    private ArrayList<String> nameValueString;

    public UpdateParse(Database database, ArrayList<String> userCommand)
    {
        this.database = database;
        this.userCommand = userCommand;
        currentIndex = 0;
    }

    public boolean isUpdate() throws Exception
    {
        currentIndex++;
        checkTableName();
        currentIndex++;
        checkCommand();
        currentIndex++;
        checkNameValueList();
        currentIndex++;
        checkCondition();
        return true;
    }

    public ArrayList<String> getConditionStatement()
    {
        return conditionStatement;
    }

    private void checkCondition() throws Exception
    {
        int lastIndex = userCommand.size();
        ArrayList<String> subString = new ArrayList<>(userCommand.subList(currentIndex, lastIndex));
        Condition condition = new Condition(subString, table);
        if(condition.isCondition()){
            throw new Exception("Invalid condition");
        }
        conditionStatement = subString;
    }

    private void checkNameValueList() throws Exception
    {
        int whereIndex = 0;
        for(int i = currentIndex; i < userCommand.size(); i++){
            if(userCommand.get(i).equals("WHERE")){
                whereIndex = i;
            }
        }

        checkWhereKeyword(whereIndex);
        nameValueString = new ArrayList<>(userCommand.subList(currentIndex, whereIndex));

        NameValueList nameValueList = new NameValueList(nameValueString);
        if(!nameValueList.isNameValueList(table)){
            throw new Exception("Invalid name value list");
        }
        currentIndex = whereIndex;
    }

    private void checkWhereKeyword(int whereIndex) throws Exception
    {
        if(whereIndex == 0 )
        {
            throw new Exception("WHERE cannot precede name value list");
        }
        if(whereIndex == userCommand.size() - 1)
        {
            throw new Exception("WHERE at end of command not allowed");
        }
    }

    public ArrayList<String> getNameValueList()
    {
        return nameValueString;
    }

    private void checkTableName() throws Exception
    {
        if(currentIndex == 1){
            if(!database.tableNameExists(userCommand.get(currentIndex))){
                throw new Exception("Table name does not exist in this database");
            }
            DataReader dataReader = new DataReader(database, userCommand.get(currentIndex));
            table = dataReader.getTable();
        }
    }

    private void checkCommand() throws Exception
    {
        if(currentIndex == 2){
            if(!userCommand.get(2).equals("SET")){
                throw new Exception("Expected SET");
            }
        }
    }

}
