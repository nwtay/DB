import java.util.ArrayList;

public class DeleteParse {

    final ArrayList<String> userCommand;
    private int currentIndex;
    final Database database;
    private ArrayList<String> conditionStatement;

    public DeleteParse(Database database, ArrayList<String> userCommand)
    {
        this.database = database;
        this.userCommand = userCommand;
        currentIndex = 0;
    }

    public boolean isDelete() throws Exception
    {
        currentIndex++;
        checkCommand();
        currentIndex++;
        checkTableName();
        currentIndex++;
        checkCommand();
        currentIndex++;
        checkCondition();
        return true;
    }

    private void checkCommand() throws Exception
    {
        if(currentIndex == 1){
            if(!userCommand.get(currentIndex).equals("FROM")){
                throw new Exception("Expected FROM");
            }
        }
        if(currentIndex == 3){
            if(!userCommand.get(currentIndex).equals("WHERE")){
                throw new Exception("Expected WHERE");
            }
        }
    }

    private void checkTableName() throws Exception
    {
        if(currentIndex == 2){
            if(!database.tableNameExists(userCommand.get(currentIndex))){
                throw new Exception("Table name does not exist in this database");
            }
        }
    }

    private void checkCondition() throws Exception
    {
        Table table;
        int lastIndex = userCommand.size();
        ArrayList<String> subString = new ArrayList<>(userCommand.subList(currentIndex, lastIndex));
        String tableName = userCommand.get(2);
        DataReader dataReader = new DataReader(database, tableName);
        table = dataReader.getTable();
        Condition conditionParse = new Condition(subString, table);
        if(conditionParse.isCondition()){
            throw new Exception("Invalid condition");
        }
        conditionStatement = subString;
    }

    public ArrayList<String> getConditionStatement()
    {
        return conditionStatement;
    }

}
