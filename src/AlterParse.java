import java.io.File;
import java.util.ArrayList;

public class AlterParse {

    final ArrayList<String> userCommand;
    private Database thisDataBase;

    public AlterParse(ArrayList<String> userCommand)
    {
        this.userCommand = userCommand;
    }

    public boolean isAlter(Database inputDataBase) throws Exception
    {
        this.thisDataBase = inputDataBase;
        if(userCommand.get(0).equals("ALTER"))
        {
            checkKeyword(userCommand.get(1));
            checkCommandSize();
            checkTableName(userCommand.get(2));
            checkAlterationType(userCommand.get(3));
            checkAttribute();
            return true;
        }
        return false;
    }

    private void checkCommandSize() throws Exception
    {
        if(userCommand.size() > 5)
        {
            throw new Exception("Excess terms in query. Last term expected is alphanumeric attribute name.");
        }
    }

    private void checkTableName(String input) throws Exception
    {
        if(!thisDataBase.tableNameExists(input)){
            throw new Exception("Table does not exist in current database");
        }
    }

    private void checkKeyword(String input) throws Exception
    {
        if(!input.equals("TABLE")){
            throw new Exception("Expected TABLE");
        }
    }


    private void checkAlterationType(String input) throws Exception
    {
        if(!input.equals("ADD") && !input.equals("DROP")){
            throw new Exception("Invalid Alteration Type: Expected ADD or DROP");
        }
    }

    private void checkAttribute() throws Exception
    {
        String path = "Databases/" + thisDataBase.getName() + "/" + userCommand.get(2);
        File file = new File(path);

        if(!allAlphNumeric(userCommand.get(4))) {
            throw new Exception("Invalid Attribute Name: Contains alphanumeric characters.");
        }

        if(file.exists()){
            DataReader dataReader = new DataReader(thisDataBase, userCommand.get(2));
            Table table = dataReader.getTable();
            if(userCommand.get(3).equals("ADD")){
                checkAdd(table);
            }
            if(userCommand.get(3).equals("DROP")){
                checkDrop(table);
            }
        }
    }

    private void checkAdd(Table table) throws Exception
    {
        if(table.columnExists(userCommand.get(4))){
            throw new Exception("Invalid Attribute Name: Attribute already exists in this table.");
        }
    }

    private void checkDrop(Table table) throws Exception
    {
        if(!table.columnExists(userCommand.get(4))){
            throw new Exception("Invalid Attribute Name: Attribute cannot be dropped as doesn't exist.");
        }
        if(userCommand.get(4).equalsIgnoreCase("id")){
            throw new Exception("id column is fixed, cannot drop.");
        }
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
