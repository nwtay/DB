import java.util.ArrayList;

public class Condition {

    final ArrayList<String> stringList;
    final Table thisTable;
    private String mainConjunction;
    private int indexMainConjunction = 0;

    private Table subTable;
    private Table leftSubTable;
    private Table rightSubTable;

    public Condition(ArrayList<String> stringList, Table thisTable)
    {
        this.stringList = stringList;
        this.thisTable = thisTable;
    }

    public boolean isCondition() throws Exception
    {
        checkBalancedBrackets();
        if(isPlainCondition()){
            checkComparison(stringList);
            checkLength();
        }
        else{
            // if not plain condition, its one with brackets
            // find main conjunction: ie find the AND/OR which is between the two bracketed conditions
            findMainConjunction();
            extraBracketsCheck();
            // checks the bracketed condition (recursively) to the left and right of main conjunction
            checkWingConditions();
            // get the table from the left wing condition, and the table from the right wing condition
            // and join them based on the main conjunction: AND/OR
            makeCompoundTable();
        }
        return false;
    }

    private void checkLength() throws Exception
    {
        if(stringList.size() > 3){
            throw new Exception("Excess terms: No more than 3 terms for plain condition");
        }
    }

    private void makeCompoundTable()
    {
        if(mainConjunction.equals("AND"))
        {
            // the left table is the one we join the right table on to
            leftSubTable.andResultTable(rightSubTable);
        }
        if(mainConjunction.equals("OR"))
        {
            leftSubTable.orResultTable(rightSubTable);
        }

        // now they have been joined, store in sub table so can be used
        subTable = leftSubTable;
    }

    private void checkWingConditions() throws Exception
    {
        checkLeft();
        checkRight();
    }

    private void checkLeft() throws Exception
    {
        // make sub string. result of this is the condition inside brackets on the left, ie <Condition> in ( <Condition> )
        ArrayList<String> subStringLeft = new ArrayList<>(stringList.subList(1, indexMainConjunction - 1));
        Condition leftCondition = new Condition(subStringLeft, thisTable);
        if(leftCondition.isCondition()){
            throw new Exception("Subcondition on the left of main conjunction is invalid");
        }
        leftSubTable = leftCondition.getSubTable();
    }

    private void checkRight() throws Exception
    {
        // make sub string. result of this is the condition inside brackets on the right, ie <Condition> in ( <Condition> )
        ArrayList<String> subStringRight = new ArrayList<>(stringList.subList(indexMainConjunction+2, stringList.size()-1));
        Condition rightCondition = new Condition(subStringRight, thisTable);
        if(rightCondition.isCondition()){
            throw new Exception("Sub-condition on the right of main conjunction is invalid");
        }
        rightSubTable = rightCondition.getSubTable();
    }

    private void findMainConjunction() throws Exception
    {
        if(!stringList.get(0).equals("(")){
            throw new Exception("Multiple condition statement needs to start with (");
        }
        int index = 0;
        int bracketBalance = 0;
        while(index <= stringList.size()-1 && indexMainConjunction == 0)
        {
            if(stringList.get(index).equals("AND") || stringList.get(index).equals("OR")){
                if(bracketBalance == 0){
                    indexMainConjunction = index;
                    mainConjunction = stringList.get(index);
                }
            }

            if(stringList.get(index).equals("(")){
                bracketBalance++;
            }

            if(stringList.get(index).equals(")")){
                bracketBalance--;
            }
            index++;
        }

        if(indexMainConjunction == 0)
        {
            throw new Exception("No main conjunction found. Make sure AND / OR is separate from brackets");
        }
    }

    private void extraBracketsCheck() throws Exception
    {
        if(!stringList.get(indexMainConjunction - 1).equals(")")){
            throw new Exception("Main conjunction needs to be preceded by closing bracket");
        }

        if(!stringList.get(indexMainConjunction + 1).equals("(")){
            throw new Exception("Opening bracket must follow main conjunction");
        }

        if(!stringList.get(stringList.size()-1).equals(")")){
            throw new Exception("Conjunctive condition must end with closing bracket");
        }
    }

    private void checkComparison(ArrayList<String> subStringList) throws Exception
    {
        if(!thisTable.columnExists(subStringList.get(0))){
            throw new Exception(": Table " + thisTable.getName() + " doesn't contain column " + subStringList.get(0));
        }
        OperatorParse operatorParse = new OperatorParse(subStringList.get(1));
        if(!operatorParse.isOperator()){
            throw new Exception(": Please enter valid operator");
        }
        Value value = new Value(subStringList.get(2));

        if(!value.isValue()){
            throw new Exception(": Please enter valid value");
        }

        // if interpreting, make a sub table based on this plain condition
        subTable = thisTable.getSubTable(subStringList.get(1), subStringList.get(0), subStringList.get(2));
    }

    // used for interpretation
    public Table getSubTable()
    {
        return subTable;
    }

    private boolean isPlainCondition()
    {
        ArrayList<String> brackets = new ArrayList<>();
        brackets.add("(");
        brackets.add(")");
        for(String bracket : brackets){
            if(stringList.contains(bracket)){
                return false;
            }
        }
        return true;
    }


    private void checkBalancedBrackets() throws Exception
    {
        int bracketBalance = 0;
        for (String string : stringList) {
            if (string.equals("(")) {
                bracketBalance++;
            }
            if (string.equals(")")) {
                bracketBalance--;
            }
        }
        if(bracketBalance != 0){
            throw new Exception("Invalid bracket balancing");
        }
    }


}
