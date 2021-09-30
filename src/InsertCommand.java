

import java.util.ArrayList;

public class InsertCommand extends GeneralCommand {

    private InsertParse insertParse;

    public InsertCommand()
    {
        this.name = "Insert";
        this.mandatoryKeywords.add("INTO");
        this.mandatoryKeywords.add("INSERT");       // recently added this, thought it should be here?
        this.mandatoryKeywords.add("VALUES");
        this.mandatoryKeywords.add("(");
        this.mandatoryKeywords.add(")");
        this.allKeywords.add("INSERT");
        this.allKeywords.add("INTO");
        this.allKeywords.add("VALUES");
        this.minNumParameters = 6;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        insertParse = new InsertParse(userCommand);
        validCommand = insertParse.isInsert(this.currentDB);
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            String tableName = userCommand.get(2);
            DataReader dataReader = new DataReader(currentDB, tableName);
            Table table = dataReader.getTable();
            Row rowToInsert = valuesToRow(insertParse.getPlainValueList());
            table.insertRow(rowToInsert);
            String filePath = "Databases/" + this.currentDB.getName() + "/" + tableName;
            DataWriter dataWriter = new DataWriter();
            dataWriter.writeData(filePath, table);
        }
    }

    private Row valuesToRow(ArrayList<String> valueList)
    {
        Row resultRow = new Row();
        for (String stringAtIndex : valueList) {
            Cell cell = new Cell(stringAtIndex);
            resultRow.addCellEnd(cell);
        }
        return resultRow;
    }
}
