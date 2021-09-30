import java.io.*;
import java.util.ArrayList;

public class CreateCommand extends GeneralCommand {

    private CreateParse createParse;
    private ArrayList<String> plainAttributeList;

    public CreateCommand()
    {
        this.name = "Create";
        this.mandatoryKeywords.add("CREATE");
        this.allKeywords.add("CREATE");
        this.allKeywords.add("DATABASE");
        this.allKeywords.add("TABLE");
        this.minNumParameters = 3;
    }

    public void checkValidity() throws Exception
    {
        if(!userCommand.get(1).equals("DATABASE")){
            // only check if current using a DB if creating a table
            checkDBSelected(currentDB);
        }
        createParse = new CreateParse(userCommand, this.currentDB);
        validCommand = createParse.isCreate();
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            if(userCommand.get(1).equals("DATABASE")){
                createDataBase(userCommand.get(2));
            }
            if(userCommand.get(1).equals("TABLE")){
                if(userCommand.size() == 3){
                    createEmptyTable(userCommand.get(2));
                }
                if(userCommand.size() > 3){
                    plainAttributeList = createParse.getPlainAttributeList();
                    createAttributeTable(userCommand.get(2));
                }
            }
        }
    }

    private void createDataBase(String dbName) throws Exception
    {
        String filePath = "Databases/" + dbName;
        File file = new File(filePath);
        if(!file.mkdir()){
            throw new Exception("Failed to create directory " + dbName);
        }
    }

    private void createAttributeTable(String tableName) throws Exception
    {
        ArrayList<Column> columnList = createColumnList();
        Table attributeTable = new Table(tableName, columnList);
        String filePath = "Databases/" + currentDB.getName() + "/" + tableName;
        DataWriter dateWriter = new DataWriter();
        dateWriter.writeData(filePath, attributeTable);
    }

    private ArrayList<Column> createColumnList()
    {
        ArrayList<Column> colList = new ArrayList<>();
        Column idColumn = new Column("id");
        colList.add(idColumn);
        if(userCommand.size() > 3){
            for (String s : plainAttributeList) {
                Column col = new Column(s);
                colList.add(col);
            }
        }
        return colList;
    }

    private void createEmptyTable(String tableName) throws Exception
    {
        ArrayList<Column> columnList = createColumnList();
        Table emptyTable = new Table(tableName, columnList);
        String filePath = "Databases/" + currentDB.getName() + "/" + tableName;
        DataWriter dateWriter = new DataWriter();
        dateWriter.writeData(filePath, emptyTable);
    }
}
