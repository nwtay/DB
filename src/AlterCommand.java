public class AlterCommand extends GeneralCommand {

    public AlterCommand()
    {
        this.name = "Alter";
        this.mandatoryKeywords.add("ALTER");
        this.mandatoryKeywords.add("TABLE");
        this.allKeywords.add("TABLE");
        this.allKeywords.add("ALTER");
        this.allKeywords.add("ADD");
        this.allKeywords.add("DROP");
        this.minNumParameters = 5;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        AlterParse alterParse = new AlterParse(userCommand);
        validCommand = alterParse.isAlter(this.currentDB);
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            String tableName = userCommand.get(2);
            DataReader dataReader = new DataReader(currentDB, tableName);
            Table table = dataReader.getTable();
            if(userCommand.get(3).equals("ADD")){
                table.addColumn(userCommand.get(4));
            }
            if(userCommand.get(3).equals("DROP")){
                table.removeColumn(userCommand.get(4));
            }
            String filePath = "Databases/" + this.currentDB.getName() + "/" + table.getName();
            DataWriter dataWriter = new DataWriter();
            dataWriter.writeData(filePath, table);
        }
    }

}
