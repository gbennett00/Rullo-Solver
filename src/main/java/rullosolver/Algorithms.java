package rullosolver;

import static rullosolver.RulloSolver.systemOut;

/**
 * Contains all logical alrogithms used to solve the Rullo puzzle. 
 * 
 * @author Garrett Bennett
 * @version 1.0.1
 */
public class Algorithms{
    // integer values in each location
    protected static int[][] value; // = ImageReader.getMainValues();
    /*protected static final int[][] value = {
        {7, 2, 4, 1, 1},
        {4, 7, 6, 6, 7},
        {4, 7, 5, 1, 5},
        {3, 4, 5, 6, 2},
        {3, 8, 6, 9, 9}
    };*/

    // eventually will be adjusted by user input
    protected static final int gridSize = 6;

    protected static int[] columns; // = ImageReader.getColumnValues(); //{18, 24, 17, 1, 16}; 
    private static boolean[] columnFinished = new boolean[gridSize];
    protected static int[] rows; // = ImageReader.getRowValues(); //{9, 17, 17, 10, 23};
    private static boolean[] rowFinished = new boolean[gridSize];
    protected static boolean puzzleFinished = false;
    protected static boolean changeMade = false;
    
    protected static boolean[][] contributor = new boolean[gridSize][gridSize];

    protected static boolean[][] confirmedTrue = new boolean[gridSize][gridSize];
    
    /** Sets all contributors to true. */
    protected static void setContributorTrue(){
        for(int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                contributor[x][y] = true;
            }
        }
    }
    
    /** Sets all puzzle values to values read in from ImageReader. */
	public static void setPuzzleValues() {
        value = ImageReader.getMainValues();
        columns = ImageReader.getColumnValues();
        rows = ImageReader.getRowValues();
    }
    
    /** Sets puzzleFinished to true only if all rows/columns are finished. */
    protected void checkForSolve(){
        for(int x = 0; x < gridSize; x++){
            checkSectionSolve(x, x);
        }
        for(int x = 0; x < gridSize; x++){
            if(!columnFinished[x] || !rowFinished[x]){
                return;
            }
        }
        puzzleFinished = true;
    }

    /** Returns the current sum for a specific row or column. */
    protected int currentSum(int section, boolean column){
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

    /** Returns the sum of confirmed values in a row or column. */
    protected int currentConfirmedSum(int section, boolean column){
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

    /** Deactivites a number then checks if that change completed a row or column. */
    protected void deactivateNumber(int row, int column, String deactivation){
        contributor[row][column] = false;
        changeMade = true;
        if(systemOut)System.out.println("Deactivated by " + deactivation + " at " + row + "," + column);
        checkSectionSolve(row, column);
    }

    /** Confirms that a grid value must be activated to solve the puzzle. */
    protected void confirmNumber(int row, int column, String confirmation){
        confirmedTrue[row][column] = true;
        changeMade = true;
        if(systemOut)System.out.println("confirmed by " + confirmation + " at " + row + "," + column);
        checkSectionSolve(row, column);
        
    }

    /** Returns the difference between the current sum and target value. */
    protected int getTopDifference(int section, boolean column){
        if(column){
            return (currentSum(section, column) - columns[section]);
        }else{
            return (currentSum(section, column) - rows[section]);
        }
    }

    /** Returns the difference between the current confirmed sum and target value. */
    protected int getLowerDifference(int section, boolean column){
        if(column){
            return columns[section] - currentConfirmedSum(section, column);
        }else{
            return rows[section] - currentConfirmedSum(section, column);
        }
    }

    /** Checks if a row and column are solved.  */
    protected void checkSectionSolve(int r, int c){
        if(!columnFinished[c]){
            if(currentConfirmedSum(c, true) == columns[c]){
                columnFinished[c] = true;
                if(systemOut)System.out.println("column " + c + " finished");
                deleteUnconfirmedValues(c, true);
            }else if(currentSum(c, true) == columns[c]){
                columnFinished[c] = true;
                if(systemOut)System.out.println("column " + c + " finished");
                confirmRemainingValues(c, true);
            }
        } 
        if(!rowFinished[r]){
            if(currentConfirmedSum(r, false) == rows[r] && !rowFinished[r]){
                rowFinished[r] = true;
                if(systemOut)System.out.println("row " + r + " finished");
                deleteUnconfirmedValues(r, false);
                
            }else if(currentSum(r, false) == rows[r]){
                rowFinished[r] = true;
                if(systemOut)System.out.println("row " + r + " finished");
                confirmRemainingValues(r, false);
            }
        }
    }

    /** Deactivates all unconfirmed values in a row or column. */
    protected void deleteUnconfirmedValues(int section, boolean column){
        if(column){
            for(int r = 0; r < gridSize; r++){
                if(!confirmedTrue[r][section] && contributor[r][section]){
                    deactivateNumber(r, section, "deleteUnconfirmedValues(column) ");
                }
            }
        }else{
            for(int c = 0; c < gridSize; c++){
                if(!confirmedTrue[section][c] && contributor[section][c]){
                    deactivateNumber(section, c, "deleteUnconfirmedValues(row) ");
                }
            }
        }
    }

    /** Confirms all remaining values. */
    protected void confirmRemainingValues(int section, boolean column){
        if(column){
            for(int r = 0; r < gridSize; r++){
                if(!confirmedTrue[r][section] && contributor[r][section]){
                    confirmNumber(r, section, "confirm remainders(column) ");
                }
            }
        }else{
            for(int c = 0; c < gridSize; c++){
                if(!confirmedTrue[section][c] && contributor[section][c]){
                    confirmNumber(section, c, "confirm remainders(row) ");
                }
            }
        }
    }

    /** Deletes all values greater than the target sum. */
    protected void deleteGreaterValues(){
        
        for(int r = 0; r < gridSize; r++){  //cycle through every value
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > rows[r] || value[r][c] > columns[c]){ //if given value is greater than column/ row sum
                    deactivateNumber(r, c, "deleteGreaterValues ");
                }
            }
        }
    }

    /** Confirms values greater than the 'top' difference (see getTopDifference()) */
    protected void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){                      
            if(!columnFinished[c]){                             
                currentDiff = getTopDifference(c, true);       
                for(int r = 0; r < gridSize; r++){              
                    if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmNumber(r, c, "confirmGreaterThanTDiff(column) ");
                    }
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getTopDifference(r, false);
            if(!rowFinished[r]){
                for(int c = 0; c < gridSize; c++){
                    if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmNumber(r, c, "confirmGreaterThanTDiff(row) ");
                    }
                }
            }
        }
        deleteGTLowerDiff();
        
    }

    /** Deactivates all values greater than the lower difference (see getLowerDifference()) */
    protected void deleteGTLowerDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){
            currentDiff = getLowerDifference(c, true);
            for(int r = 0; r < gridSize; r++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c, "deleteGTLowerDiff(column) ");
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getLowerDifference(r, false);
            for(int c = 0; c < gridSize; c++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c, "deleteGTLowerDiff(row) ");
                }
            }
        }
        valuePlusLowest();
    }

    /** 
     * Deactivates value if value does not equal difference between low sum and target 
     * AND the value plus the lowest value in section is greater than low sum minus target. 
     */
    protected void valuePlusLowest(){
        for(int c = 0; c < gridSize; c++){
            if(!columnFinished[c]){
                int topDiff = getTopDifference(c, true);
                int lowerDiff = getLowerDifference(c, true);
                int lowestUnconfirmedValue = 9;
                int secondLowestValue = 9;
                int lowestValueLocation = 0;
                for(int x = 0; x < gridSize; x++){  //cycle to set variables
                    if(contributor[x][c] && !confirmedTrue[x][c] && value[x][c] < lowestUnconfirmedValue){
                        lowestUnconfirmedValue = value[x][c];
                        lowestValueLocation = x;
                    }
                }for(int y = 0; y < gridSize; y++){
                    if(contributor[y][c] && !confirmedTrue[y][c] && value[y][c] < secondLowestValue && y != lowestValueLocation){
                        secondLowestValue = value[y][c];
                    }
                }for(int r = 0; r < gridSize; r++){  //cycle to delete impossible values
                    if(contributor[r][c] && !confirmedTrue[r][c]){
                        if(value[r][c] != topDiff && r != lowestValueLocation && value[r][c] + lowestUnconfirmedValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(column1) ");
                        }else if(value[r][c] != topDiff && r == lowestValueLocation && value[r][c] + secondLowestValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(column2) ");
                        }else if(value[r][c] != lowerDiff && r != lowestValueLocation && value[r][c] + lowestUnconfirmedValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(column3) ");
                        }else if(value[r][c] != lowerDiff && r == lowestValueLocation && value[r][c] + secondLowestValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(column4) ");
                        }
                    }
                }
            } 
        }
        for(int r = 0; r < gridSize; r++){
            if(!rowFinished[r]){
                int topDiff = getTopDifference(r, false);
                int lowerDiff = getLowerDifference(r, false);
                int lowestUnconfirmedValue = 10;
                int secondLowestValue = 10;
                int lowestValueLocation = 0;
                for(int x = 0; x < gridSize; x++){  //cycle to set variables
                    if(contributor[r][x] && !confirmedTrue[r][x] && value[r][x] < lowestUnconfirmedValue){
                        lowestUnconfirmedValue = value[r][x];
                        lowestValueLocation = x;
                    }
                }for(int y = 0; y < gridSize; y++){
                    if(contributor[r][y] && !confirmedTrue[r][y] && value[r][y] < secondLowestValue && y != lowestValueLocation){
                        secondLowestValue = value[r][y];
                    }
                }for(int c = 0; c < gridSize; c++){  //cycle to delete impossible values
                    if(contributor[r][c] && !confirmedTrue[r][c]){
                        if(value[r][c] != topDiff && c != lowestValueLocation && value[r][c] + lowestUnconfirmedValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(row1) ");
                        }else if(value[r][c] != topDiff && c == lowestValueLocation && value[r][c] + secondLowestValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(row2) ");
                        }else if(value[r][c] != lowerDiff && c != lowestValueLocation && value[r][c] + lowestUnconfirmedValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(row3) ");
                        }else if(value[r][c] != lowerDiff && c == lowestValueLocation && value[r][c] + secondLowestValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(row4) ");
                        }
                    }
                }
            } 
        }
    }

}