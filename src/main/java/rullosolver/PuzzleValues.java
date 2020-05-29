package rullosolver;


public class PuzzleValues{
    
    /*public static final int[][] value1 = {
        {7, 1, 9, 4, 1, 8, 7},
        {5, 6, 3, 4, 2, 2, 2},
        {9, 7, 8, 4, 5, 2, 8}, 
        {3, 5, 1, 2, 5, 9, 5},
        {9, 5, 4, 8, 9, 3, 5},
        {5, 1, 1, 9, 7, 4, 2},
        {9, 9, 6, 1, 2, 4, 2}
    };*/
    private static final int[][] value1 = App.value;

    String image = App.readImage("Rullo.jpg");

    protected static final int gridSize = 7;

    private static final int[] columns = {44, 29, 26, 19, 14, 18, 29};
    private static boolean[] columnFinished = new boolean[gridSize];
    private static final int[] rows = {28, 20, 41, 21, 30, 12, 27};
    private static boolean[] rowFinished = new boolean[gridSize];
    protected static boolean puzzleFinished = false;
    protected static boolean changeMade = false;
    protected static boolean systemOut = false;
    
    private static boolean[][] contributor = new boolean[gridSize][gridSize];
    
    protected static void setContributorTrue(){
        for(int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                contributor[x][y] = true;
            }
        }
    }
    
    protected static boolean[][] confirmedTrue = new boolean[gridSize][gridSize];
    
    protected void checkForSolve(){
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

    private int currentSum(int section, boolean column){
        int currentSum = 0;
        if(column){
            for(int i = 0; i < gridSize; i++){
                if(contributor[i][section]){
                    currentSum += value1[i][section];
                }
            }
        }else{
            for(int i = 0; i < gridSize; i ++){
                if(contributor[section][i]){
                    currentSum += value1[section][i];
                }
            }
        }
        return currentSum;
    }

    private int currentConfirmedSum(int section, boolean column){
        int confirmedSum = 0;
        for(int i = 0; i < gridSize; i++){
            if(column){ 
                if(confirmedTrue[i][section]){
                    confirmedSum += value1[i][section];
                }
            }else{
                if(confirmedTrue[section][i]){
                    confirmedSum += value1[section][i];
                }
            }
        }
        return confirmedSum;
        
    }

    private void deactivateNumber(int row, int column, String deactivation){
        contributor[row][column] = false;
        changeMade = true;
        if(systemOut)System.out.println("Deactivated by " + deactivation + " at " + row + "," + column);
        checkSectionSolve(row, column);
    }

    private void confirmNumber(int row, int column, String confirmation){
        confirmedTrue[row][column] = true;
        changeMade = true;
        if(systemOut)System.out.println("confirmed by " + confirmation + " at " + row + "," + column);
        checkSectionSolve(row, column);
        
    }

    private int getTopDifference(int section, boolean column){
        if(column){
            return (currentSum(section, column) - columns[section]);
        }else{
            return (currentSum(section, column) - rows[section]);
        }
    }

    private int getLowerDifference(int section, boolean column){
        if(column){
            return columns[section] - currentConfirmedSum(section, column);
        }else{
            return rows[section] - currentConfirmedSum(section, column);
        }
    }

    private void checkSectionSolve(int r, int c){
        if(!columnFinished[c]){
            if(currentConfirmedSum(c, true) == columns[c]){
                columnFinished[c] = true;
                deleteUnconfirmedValues(c, true);
                if(systemOut)System.out.println("column " + c + " finished");
            }else if(currentSum(c, true) == columns[c]){
                columnFinished[c] = true;
                confirmRemainingValues(c, true);
                if(systemOut)System.out.println("column " + c + " finished");
            }
        } 
        if(!rowFinished[r]){
            if(currentConfirmedSum(r, false) == rows[r] && !rowFinished[r]){
                rowFinished[r] = true;
                deleteUnconfirmedValues(r, false);
                if(systemOut)System.out.println("row " + r + " finished");
            }else if(currentSum(r, false) == rows[r]){
                rowFinished[r] = true;
                confirmRemainingValues(r, false);
                if(systemOut)System.out.println("row " + r + " finished");
            }
        }
    }

    private void deleteUnconfirmedValues(int section, boolean column){
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

    private void confirmRemainingValues(int section, boolean column){
        if(column){
            for(int r = 0; r < gridSize; r++){
                if(!confirmedTrue[r][section] && contributor[r][section]){
                    confirmNumber(r, section, "confirm remainders(column) ");
                }
            }
        }else{
            for(int c = 0; c < gridSize; c++){
                if(!confirmedTrue[section][c] && contributor[section][c]){
                    confirmNumber(section, c, "confirm rmainders(row) ");
                }
            }
        }
    }

    protected void deleteGreaterValues(){
        
        for(int r = 0; r < gridSize; r++){  //cycle through every value
            for(int c = 0; c < gridSize; c++){
                if(value1[r][c] > rows[r] || value1[r][c] > columns[c]){ //if given value is greater than column/ row sum
                    deactivateNumber(r, c, "deleteGreaterValues ");
                }
            }
        }
    }

    protected void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){                      //cycle through every column
            if(!columnFinished[c]){                             //if column isn't finished 
                currentDiff = getTopDifference(c, true);        //set currentDiff as diff b/w current sum and target
                for(int r = 0; r < gridSize; r++){              //cycle through every value by column
                    if(value1[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmNumber(r, c, "confirmGreaterThanTDiff(column) ");
                    }
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getTopDifference(r, false);
            if(!rowFinished[r]){
                for(int c = 0; c < gridSize; c++){
                    if(value1[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                        confirmNumber(r, c, "confirmGreaterThanTDiff(row) ");
                    }
                }
            }
        }
        deleteGTLowerDiff();
        
    }

    private void deleteGTLowerDiff(){
        int currentDiff;
        for(int c = 0; c < gridSize; c++){
            currentDiff = getLowerDifference(c, true);
            for(int r = 0; r < gridSize; r++){
                if(value1[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c, "deleteGTLowerDiff(column) ");
                }
            }
        }
        for(int r = 0; r < gridSize; r++){
            currentDiff = getLowerDifference(r, false);
            for(int c = 0; c < gridSize; c++){
                if(value1[r][c] > currentDiff && !confirmedTrue[r][c] && contributor[r][c]){
                    deactivateNumber(r, c, "deleteGTLowerDiff(row) ");
                }
            }
        }
        valuePlusLowest();
    }

    private void valuePlusLowest(){
        for(int c = 0; c < gridSize; c++){
            if(!columnFinished[c]){
                int topDiff = getTopDifference(c, true);
                int lowerDiff = getLowerDifference(c, true);
                int lowestUnconfirmedValue = 9;
                int secondLowestValue = 9;
                int lowestValueLocation = 0;
                for(int x = 0; x < gridSize; x++){  //cycle to set variables
                    if(contributor[x][c] && !confirmedTrue[x][c] && value1[x][c] < lowestUnconfirmedValue){
                        lowestUnconfirmedValue = value1[x][c];
                        lowestValueLocation = x;
                    }
                }for(int y = 0; y < gridSize; y++){
                    if(contributor[y][c] && !confirmedTrue[y][c] && value1[y][c] < secondLowestValue && value1[y][c] != lowestUnconfirmedValue){
                        secondLowestValue = value1[y][c];
                    }
                }for(int r = 0; r < gridSize; r++){  //cycle to delete impossible values
                    if(contributor[r][c] && !confirmedTrue[r][c]){
                        if(value1[r][c] != topDiff && r != lowestValueLocation && value1[r][c] + lowestUnconfirmedValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(column1) ");
                        }else if(value1[r][c] != topDiff && r == lowestValueLocation && value1[r][c] + secondLowestValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(column2) ");
                        }else if(value1[r][c] != lowerDiff && r != lowestValueLocation && value1[r][c] + lowestUnconfirmedValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(column3) ");
                        }else if(value1[r][c] != lowerDiff && r == lowestValueLocation && value1[r][c] + secondLowestValue > lowerDiff){
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
                    if(contributor[r][x] && !confirmedTrue[r][x] && value1[r][x] < lowestUnconfirmedValue){
                        lowestUnconfirmedValue = value1[r][x];
                        lowestValueLocation = x;
                    }
                }for(int y = 0; y < gridSize; y++){
                    if(contributor[r][y] && !confirmedTrue[r][y] && value1[r][y] < secondLowestValue && value1[r][y] != lowestUnconfirmedValue){
                        secondLowestValue = value1[r][y];
                    }
                }for(int c = 0; c < gridSize; c++){  //cycle to delete impossible values
                    if(contributor[r][c] && !confirmedTrue[r][c]){
                        if(value1[r][c] != topDiff && c != lowestValueLocation && value1[r][c] + lowestUnconfirmedValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(row1) ");
                        }else if(value1[r][c] != topDiff && c == lowestValueLocation && value1[r][c] + secondLowestValue > topDiff){
                            confirmNumber(r, c, "valuePlusLowest(row2) ");
                        }else if(value1[r][c] != lowerDiff && c != lowestValueLocation && value1[r][c] + lowestUnconfirmedValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(row3) ");
                        }else if(value1[r][c] != lowerDiff && c == lowestValueLocation && value1[r][c] + secondLowestValue > lowerDiff){
                            deactivateNumber(r, c, "valuePlusLowest(row4) ");
                        }
                    }
                }
            } 
        }
    }
}