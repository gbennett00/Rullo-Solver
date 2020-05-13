package rullosolver;

public class PuzzleValues{
    public PuzzleValues(){

    }

    public static final int[][] value = {
        {6, 5, 2, 5, 5},
        {9, 5, 1, 8, 5},
        {9, 4, 4, 9, 1}, 
        {9, 4, 8, 8, 2},
        {4, 2, 7, 3, 4}
    };

    public static final int[] columns = {28, 4, 13, 25, 11};
    public static final int[] rows = {18, 17, 17, 11, 18};
    
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

    public void deleteGreaterValues(){
        
        for(int r = 0; r < 5; r++){
            for(int c = 0; c < 5; c++){
                if(value[r][c] > rows[r] || value[r][c] > columns[c]){
                    deactivateNumber(r, c);
                }
            }
        }
    }

    public void confirmGreaterThanTDiff(){
        int currentDiff;
        for(int c = 0; c < 5; c++){
            currentDiff = getTopDifference(c, true); //currentSum(c, true) - columns[c];
            for(int r = 0; r < 5; r++){
                if(value[r][c] > currentDiff || contributor[r][c]){
                    confirmedTrue[r][c] = true;
                }
            }
        }
        for(int r = 0; r < 5; r++){
            currentDiff = getTopDifference(r, false);
            for(int c = 0; c < 5; c++){
                if(value[r][c] > currentDiff){
                    confirmedTrue[r][c] = true;
                }
            }
        }
    }

    /*public void deleteGreaterThanLDiff(){
        int currentDiff;
        for(int c = 0; c < 5; c++){
            currentDiff = columns[c];
        }
    }*/



}