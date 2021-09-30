import java.io.File;

public class DropCommand extends GeneralCommand {

    public DropCommand()
    {
        this.name = "Drop";
        this.mandatoryKeywords.add("DROP");
        this.allKeywords.add("DROP");
        this.allKeywords.add("DATABASE");
        this.allKeywords.add("TABLE");
        this.minNumParameters = 3;
    }

    public void checkValidity() throws Exception
    {
        if(!userCommand.get(1).equals("DATABASE")){
            // only check that a DB is selected if we're not executing the drop database command
            checkDBSelected(currentDB);
            checkTableExists(currentDB);
        }
        DropParse dropParse = new DropParse(userCommand);
        validCommand = dropParse.isDrop();
    }

    private void checkTableExists(Database currentDB) throws Exception {

        if (userCommand.get(1).equals("TABLE")) {
            String path = "Databases/" + currentDB.getName() + "/" + userCommand.get(2);
            File file = new File(path);
            if (!file.exists()) {
                throw new Exception("Cannot drop the table " + userCommand.get(2) + " as it doesn't exist");
            }
        }
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            if(userCommand.get(1).equals("DATABASE")){
                deleteDirectory(userCommand.get(2));
            }
            if(userCommand.get(1).equals("TABLE")){
                deleteTable(userCommand.get(2));
            }
        }
    }

    private void deleteDirectory(String dirName) throws Exception
    {
        String path = "Databases/" + dirName;
        File directory = new File(path);

        if(directory.exists()){
            File[] tablesInDirectory = directory.listFiles();
            if(tablesInDirectory != null && tablesInDirectory.length != 0){
                for (File file : tablesInDirectory) {
                    if (!file.delete()) {
                        throw new Exception("Table " + file.getName() + " could not be deleted from directory");
                    }
                }
            }
            if(currentDB.getName().equals(dirName)){
                // if dropping the db we're currently in, change currentDB to null then delete
                this.currentDB = null;
            }
            if(!directory.delete()){
                throw new Exception(dirName + " could not be deleted.");
            }
        }
    }

    private void deleteTable(String tableName) throws Exception
    {
        String path = "Databases/" + currentDB.getName() + "/" + tableName;
        File table = new File(path);
        if (!table.delete()) {
            throw new Exception("Table " + table.getName() + " could not be deleted from directory");
        }
    }
}
