package rullosolver;

public class PuzzleValues{
    public PuzzleValues(){

    }

    public static final int[][] value = {
        {4, 8, 8, 7, 9, 7},
        {9, 9, 4, 5, 6, 1},
        {6, 6, 7, 1, 9, 8}, 
        {8, 6, 6, 3, 7, 2},
        {6, 2, 7, 6, 5, 5},
        {8, 6, 3, 5, 9, 5}
    };

    public static final int gridSize = 6;

    public static final int[] columns = {21, 29, 28, 23, 27, 20};
    public static boolean[] columnFinished = new boolean[gridSize];
    public static final int[] rows = {19, 29, 21, 29, 25, 25};
    public static boolean[] rowFinished = new boolean[gridSize];
    public static boolean puzzleFinished = false;
    public static boolean changeMade = false;
    protected static boolean systemOut = false;
    
    public static boolean[][] contributor = new boolean[gridSize][gridSize];
    
    public static void setContributorTrue(){
        for(int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                contributor[x][y] = true;
            }
        }
    }
    
    public static boolean[][] confirmedTrue = new boolean[gridSize][gridSize];
    
    public void checkForSolve(){
        for(int x = 0; x < 5; x++){
            checkSectionSolve(x, x);
        }
        for(int x = 0; x < gridSize; x++){
            if(!columnFinished[x] || !rowFinished[x]){
                return;
            }
        }
        puzzleFinished = true;
    }

    public int currentSum(int section, boolean column){
        int currentSum = 0;
        if(column){
            for(int i = 0; i < gridSize; i++){
                if(contributor[i][section]){
                    currentSum += value[i][section];
                }
            }
        }else{
            for(int i = 0; i < gridSize; i ++){
                if(contributor[section][i]){
                    currentSum += value[section][i];
                }
            }
        }
        return currentSum;
    }

    public int currentConfirmedSum(int section, boolean column){
        int confirmedSum = 0;
        for(int i = 0; i < gridSize; i++){
            if(column){ 
                if(confirmedTrue[i][section]){
                    confirmedSum += value[i][section];
                }
            }else{
                if(confirmedTrue[section][i]){
                    confirmedSum += value[section][i];
                }
            }
        }
        return confirmedSum;
        
    }

    public boolean isFinished(int section, boolean column){
        if(column){
            return columns[section] == currentSum(section, column);
        }else{
            return rows[section] == currentSum(section, column);
        }
    }

    public void deactivateNumber(int row, int column){
        contributor[row][column] = false;
    }

    public int getTopDifference(int section, boolean column){
        if(column){
            return (currentSum(section, column) - columns[section]);
        }else{
            return (currentSum(section, column) - rows[section]);
        }
    }

    public int getLowerDifference(int section, boolean column){
        if(column){
            return columns[section] - currentConfirmedSum(section, column);
        }else{
            return rows[section] - currentConfirmedSum(section, column);
        }
    }

    public void checkSectionSolve(int r, int c){
        if(currentConfirmedSum(c, true) == columns[c] && !columnFinished[c]){
            deleteUnconfirmedValues(c, true);
            columnFinished[c] = true;
            if(systemOut)System.out.println("column " + c + " finished");
        }
        if(currentConfirmedSum(r, false) == rows[r] && !rowFinished[r]){
            deleteUnconfirmedValues(r, false);
            rowFinished[r] = true;
            if(systemOut)System.out.println("row " + r + " finished");
        }
    }

    public void deleteUnconfirmedValues(int section, boolean column){
        if(column){
            for(int r = 0; r < gridSize; r++){
                if(!confirmedTrue[r][section] && contributor[r][section]){
                    deactivateNumber(r, section);
                    if(systemOut)System.out.println("deactivated bc solved at " + r + "," + section);
                    changeMade = true;
                }
            }
        }else{
            for(int c = 0; c < gridSize; c++){
                if(!confirmedTrue[section][c] && contributor[section][c]){
                    deactivateNumber(section, c);
                    if(systemOut)System.out.println("deactivated bc solved at " + section + "," + c);
                    changeMade = true;
                }
            }
        }
    }

    public void deleteGreaterValues(){
        
        for(int r = 0; r < gridSize; r++){  //cycle through every value
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > rows[r] || value[r][c] > columns[c]){ //if given value is greater than column/ row sum
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated first at " + r + "," + c);
                }
            }
        }
    }

    public void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){                      //cycle through every column
            if(!columnFinished[c]){                             //if column isn't finished 
                currentDiff = getTopDifference(c, true);        //set currentDiff as diff b/w current sum and target
                for(int r = 0; r < gridSize; r++){              //cycle through every value by column
                    if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmedTrue[r][c] = true;
                        changeMade = true;
                        if(systemOut)System.out.println("confirmed by column at " + r + "," + c);
                        checkSectionSolve(r, c);
                    }
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getTopDifference(r, false);
            if(!rowFinished[r]){
                for(int c = 0; c < gridSize; c++){
                    if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmedTrue[r][c] = true;
                        changeMade = true;
                        if(systemOut)System.out.println("confirmed by row at " + r + "," + c);
                        checkSectionSolve(r, c);
                    }
                }
            }
        }
        deleteGTLowerDiff();
    }

    public void deleteGTLowerDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){
            currentDiff = getLowerDifference(c, true);
            for(int r = 0; r < gridSize; r++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated by column at " + r + "," + c);
                    checkSectionSolve(r, c);
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getLowerDifference(r, false);
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated by row at " + r + "," + c);
                    checkSectionSolve(r, c);
                }
            }
        }
    }
}