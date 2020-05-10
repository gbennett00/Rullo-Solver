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

    public static boolean[][] confirmed = new boolean[5][5];

   
    public int columnCurrentSum(int column){
        int currentSum = 0;
        for(int i = 0; i < 5; i++){
            if(contributor[i][column]){
                currentSum += value[i][column];
            }
        }
        return currentSum;
    }

    public int rowCurrentSum(int row){
        int currentSum = 0;
        for(int i = 0; i < 5; i ++){
            if(contributor[row][i]){
                currentSum += value[row][i];
            }
        }
        return currentSum;
    }

    public boolean columnIsFinished(int column){
        return columns[column] == columnCurrentSum(column);
    }

    public boolean rowIsFinished(int row){
        return rows[row] == rowCurrentSum(row);
    }

    public void deactivateNumber(int row, int column){
        contributor[row][column] = false;
        confirmed[row][column] = true;
    }

}