package rullosolver;

public class PuzzleValues{
    public PuzzleValues(){

    }

    public static final int[][] value = {
        {4, 3, 5, 9, 7},
        {5, 1, 9, 4, 2},
        {3, 8, 7, 5, 4}, 
        {6, 2, 3, 5, 4},
        {4, 4, 4, 2, 5}
    };

    public static final int[] columns = {13, 17, 12, 20, 6};
    public static boolean[] columnFinished = new boolean[5];
    public static final int[] rows = {21, 11, 20, 6, 10};
    public static boolean[] rowFinished = new boolean[5];
    private boolean systemOut = false;
    
    public static boolean[][] contributor = {
        {true, true, true, true, true},
        {true, true, true, true, true},
        {true, true, true, true, true},
        {true, true, true, true, true},
        {true, true, true, true, true},
    };

    public static boolean[][] confirmedTrue = new boolean[5][5];

    public int currentSum(int section, boolean column){
        int currentSum = 0;
        if(column){
            for(int i = 0; i < 5; i++){
                if(contributor[i][section]){
                    currentSum += value[i][section];
                }
            }
        }else{
            for(int i = 0; i < 5; i ++){
                if(contributor[section][i]){
                    currentSum += value[section][i];
                }
            }
        }
        return currentSum;
    }

    public int currentConfirmedSum(int section, boolean column){
        int confirmedSum = 0;
        for(int i = 0; i < 5; i++){
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

    public void confirmNumber(int row, int column){
        confirmedTrue[row][column] = true;
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
            for(int r = 0; r < 5; r++){
                if(!confirmedTrue[r][section]){
                    deactivateNumber(r, section);
                }
            }
        }else{
            for(int c = 0; c < 5; c++){
                if(!confirmedTrue[section][c]){
                    deactivateNumber(section, c);
                }
            }
        }
    }

    public void deleteGreaterValues(){
        
        for(int r = 0; r < 5; r++){
            for(int c = 0; c < 5; c++){
                if(value[r][c] > rows[r] || value[r][c] > columns[c]){
                    deactivateNumber(r, c);
                    if(systemOut)System.out.println("deactivated first at " + r + "," + c);
                }
            }
        }
    }

    public void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < 5; c++){
            currentDiff = getTopDifference(c, true); //currentSum(c, true) - columns[c];
            for(int r = 0; r < 5; r++){
                if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                    confirmedTrue[r][c] = true;
                    if(systemOut)System.out.println("confirmed by column at " + r + "," + c);
                    if(currentConfirmedSum(c, true) == columns[c]){
                        deleteUnconfirmedValues(c, true);
                        columnFinished[c] = true;
                    }
                }
            }
        }
        for(int r = 0; r < 5; r++){
            currentDiff = getTopDifference(r, false);
            for(int c = 0; c < 5; c++){
                if(value[r][c] > currentDiff && contributor[r][c] && !confirmedTrue[r][c]){
                    confirmedTrue[r][c] = true;
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
        for(int c = 0; c < 5; c++){
            currentDiff = getLowerDifference(c, true);
            for(int r = 0; r < 5; r++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c]){
                    deactivateNumber(r, c);
                    if(systemOut)System.out.println("deactivated by column at " + r + "," + c);
                    if(currentConfirmedSum(c, true) == columns[c]){
                        deleteUnconfirmedValues(c, true);
                        columnFinished[c] = true;
                    }
                }
            }
        }
        for(int r = 0; r < 5; r++){
            currentDiff = getLowerDifference(r, false);
            for(int c = 0; c < 5; c++){
                if(value[r][c] > currentDiff && !confirmedTrue[r][c]){
                    deactivateNumber(r, c);
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