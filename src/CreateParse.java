import java.util.ArrayList;

public class CreateParse {

    final ArrayList<String> commandList;
    final int currentIndex;
    final Database currentDB;
    private CreateTableParse createTableParse;

    public CreateParse(ArrayList<String> commandList, Database currentDB)
    {
        this.commandList = commandList;
        currentIndex = 0;
        this.currentDB = currentDB;
    }

    public boolean isCreate() throws Exception
    {
        checkCommand();
        if(commandList.get(currentIndex).equals("CREATE")){
            // split off between table and database
            checkCommand();

            if(commandList.get(currentIndex+1).equals("DATABASE")){
                checkLength();
                CreateDatabaseParse createDB = new CreateDatabaseParse(commandList.get(0), commandList.get(1), commandList.get(2));
                return createDB.isCreateDatabase();
            }

            if(commandList.get(currentIndex+1).equals("TABLE")){
                createTableParse = new CreateTableParse(commandList);
                return createTableParse.isCreateTable(currentDB);
            }

        }
        return false;
    }

    private void checkLength() throws Exception
    {
        if(commandList.size() > 3){
            throw new Exception("Excess terms for create database command");
        }
    }

    public void checkCommand() throws Exception
    {
        if(commandList.get(currentIndex).isEmpty()){
            throw new Exception("Empty string not allowed here");
        }
    }

    public ArrayList<String> getPlainAttributeList()
    {
        ArrayList<String> plainAttributeList;
        plainAttributeList = createTableParse.getPlainAttributeList();
        return plainAttributeList;
    }

}
