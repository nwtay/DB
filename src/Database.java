import java.io.File;
import java.util.ArrayList;

public class Database {

    final ArrayList<Table> tableList;
    final String databaseName;

    public Database(String databaseName) throws Exception
    {
        this.databaseName = databaseName;
        checkName(databaseName);
        // how will we check database name is unique
        tableList = new ArrayList<>();
    }

    public String getName()
    {
        return databaseName;
    }

    public boolean tableNameExists(String tableName)
    {
        String path = "Databases/" + databaseName + "/" + tableName;
        File file = new File(path);
        return file.exists();
    }

    private void checkName(String name) throws Exception
    {
        if(name.isEmpty())
        {
            throw new Exception("No database existing with empty name.");
        }
    }

}
