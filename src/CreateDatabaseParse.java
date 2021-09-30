import java.io.File;

public class CreateDatabaseParse {

    final String input1;
    final String input2;
    final String input3;

    public CreateDatabaseParse(String input1, String input2, String input3)
    {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
    }

    public boolean isCreateDatabase() throws Exception
    {
        if(input1.equals("CREATE")){
            if(input2.equals("DATABASE")){
                checkDatabase();
                return true;
            }
        }
        return false;
    }

    private void checkDatabase() throws Exception
    {
        if(input3.isEmpty()){
            throw new Exception("Empty string not allowed here");
        }
        if(!allAlphNumeric(input3))
        {
            throw new Exception("Database name contains non alphanumeric characters");
        }
        String dataBaseName = "Databases/" + input3;
        File file = new File(dataBaseName);
        if(file.exists()){
            throw new Exception("Database with the name: " + input3 + " already exists. Please choose another.");
        }
    }

    private boolean allAlphNumeric(String testString)
    {
        String matcher = "([\\W])";
        for(int i = 0; i < testString.length(); i++)
        {
            String string = testString.substring(i,i+1);
            if(string.matches(matcher)){
                return false;
            }
        }
        return true;
    }

}
