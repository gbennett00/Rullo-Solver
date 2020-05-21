package rullosolver;

public class PuzzleValues{
    public PuzzleValues(){

    }

    public static final int[][] value = {
        {7, 2, 3, 7, 4, 9},
        {2, 2, 1, 4, 9, 4},
        {1, 5, 9, 5, 1, 8}, 
        {1, 7, 2, 1, 5, 8},
        {7, 5, 5, 6, 3, 9},
        {3, 6, 2, 1, 7, 3}
    };

    public static final int gridSize = 6;

    public static final int[] columns = {17, 14, 6, 23, 26, 20};
    public static boolean[] columnFinished = new boolean[gridSize];
    public static final int[] rows = {23, 18, 19, 9, 27, 10};
    public static boolean[] rowFinished = new boolean[gridSize];
    public static boolean puzzleFinished = false;
    public static boolean changeMade = false;
    private boolean systemOut = false;
    
    public static boolean[][] contributor = {
        {true, true, true, true, true, true},
        {true, true, true, true, true, true},
        {true, true, true, true, true, true},
        {true, true, true, true, true, true},
        {true, true, true, true, true, true},
        {true, true, true, true, true, true}
    };

    public static boolean[][] confirmedTrue = new boolean[gridSize][gridSize];

    public void checkForSolve(){
        for(int x = 0; x < gridSize; x++){
            if(!rowFinished[x] || !columnFinished[x]){
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

    public void deleteUnconfirmedValues(int section, boolean column){
        if(column){
            for(int r = 0; r < gridSize; r++){
                if(!confirmedTrue[r][section]){
                    deactivateNumber(r, section);
                    changeMade = true;
                }
            }
        }else{
            for(int c = 0; c < gridSize; c++){
                if(!confirmedTrue[section][c]){
                    deactivateNumber(section, c);
                    changeMade = true;
                }
            }
        }
    }

    public void deleteGreaterValues(){
        
        for(int r = 0; r < gridSize; r++){
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > rows[r] || value[r][c] > columns[c]){
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated first at " + r + "," + c);
                }
            }
        }
    }

    public void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){
            if(!columnFinished[c]){
                currentDiff = getTopDifference(c, true); //currentSum(c, true) - columns[c];
                for(int r = 0; r < gridSize; r++){
                    if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmedTrue[r][c] = true;
                        changeMade = true;
                        if(systemOut)System.out.println("confirmed by column at " + r + "," + c);
                        if(currentConfirmedSum(c, true) == columns[c]){
                            deleteUnconfirmedValues(c, true);
                            columnFinished[c] = true;
                        }
                    }
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getTopDifference(r, false);
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                    confirmedTrue[r][c] = true;
                    changeMade = true;
                    if(systemOut)System.out.println("confirmed by row at " + r + "," + c);
                    if(currentConfirmedSum(r, false) == rows[r]){
                        deleteUnconfirmedValues(r, false);
                        rowFinished[r] = true;
                    }
                }
            }
        }
    }

    public void deleteGTLowerDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){
            currentDiff = getLowerDifference(c, true);
            for(int r = 0; r < gridSize; r++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c]){
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated by column at " + r + "," + c);
                    if(currentConfirmedSum(c, true) == columns[c]){
                        deleteUnconfirmedValues(c, true);
                        columnFinished[c] = true;
                    }
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getLowerDifference(r, false);
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c]){
                    deactivateNumber(r, c);
                    changeMade = true;
                    if(systemOut)System.out.println("deactivated by row at " + r + "," + c);
                    if(currentConfirmedSum(r, false) == rows[r]){
                        deleteUnconfirmedValues(r, false);
                        rowFinished[r] = true;
                    }
                }
            }
        }
    }
}