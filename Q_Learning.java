import java.util.Random;

public class Q_Learning {


    //Algorithm
    //20,000 trials
    //Randomize initial currentCell
    //Decide on a direction using Q-map
        //Optimal direction is random # is 95%, else random direction
    //Update N-map using calculated direction
        // N(s,a) ← N(s,a) + 1
    //Update Q-map using calculated direction
        // Q(s,a) ← Q(s,a) + (1/N(s,a))[R(s,a) + γ*max(Q(s',a'))−Q(s,a)]
    //Calculate optimal direction using Q-map
        //Drift left or right with 10%


    public static void main(String[] args) {
        
        


    }

    
    //Done
    public static Cell findRandomCell(Cell[][] Q)
    {
        int randomRow;
        int randomCol; 
        Random random = new Random();
        do{
            randomRow = random.nextInt(6);
            randomCol = random.nextInt(7);
            
            
        } while (Q[randomRow][randomCol].boundary); 

        return Q[randomRow][randomCol];
    }
    
    
    public static String generateDirection(Cell[][] Q, Cell currentCell)
    {
        //Decide on a direction using Q-map
        //Optimal direction is random # is 95%, else random direction
        Random random = new Random();
        int probability = random.nextInt(100);
        
        //If you get anything above 5% (or 95%), you choose the optimal direction of current cell, else it's 5% or less so choose random direction
        if(probability > 5)
        {
            //If west is greatest
            if((currentCell.west > currentCell.north) && (currentCell.west > currentCell.east) && (currentCell.west > currentCell.south))
            {
                return "W";
            }
            //else if north is greatest
            else if ((currentCell.north > currentCell.west) && (currentCell.north > currentCell.east) && (currentCell.north > currentCell.south))
            {
                return "N";
            }
            //else if east is greatest
            else if ((currentCell.east > currentCell.west) && (currentCell.east > currentCell.south) && (currentCell.east > currentCell.north))
            {
                return "E";
            }
            //else if south is greatest
            else if ((currentCell.south > currentCell.west) && (currentCell.south > currentCell.north) && (currentCell.south > currentCell.east)){
                return "S";
            }
            //else all values are equal, or 0, thus you choose random direction
            else{
                int randNum = random.nextInt(4);
                switch(randNum)
                {
                    case 0:
                        return "W";
                    case 1:
                        return "N";
                    case 2:
                        return "E";
                    case 3:
                        return "S";
                    default:
                        System.out.println("You Goofed! Error inside generated direction");
                        return "Error";
                }
            }
        }
        else
        {
            int randNum = random.nextInt(4);
            switch(randNum)
            {
                case 0:
                    return "W";
                case 1:
                    return "N";
                case 2:
                    return "E";
                case 3:
                    return "S";
                default:
                    System.out.println("You Goofed! Error inside generated direction");
                    return "Error";
            }
        }
    }

    public static void loadMap(Cell[][] grid, String given)
    {
        if (given.equals("Q"))
        {
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 7; col++)
                {
                    grid[row][col] = new Cell();
                    grid[row][col].row = row;
                    grid[row][col].col = col;
                }
            }
            grid[3][1].boundary = true;
            grid[3][2].boundary = true;
            grid[2][2].boundary = true;
            grid[1][2].boundary = true;
            grid[1][3].boundary = true;
            grid[1][4].boundary = true;
            grid[1][5].boundary = true;
            grid[2][5].boundary = true;
            grid[3][5].boundary = true;
            grid[4][5].boundary = true;
            grid[4][4].boundary = true;

            grid[0][6].goal = true;
            grid[0][6].west = -100.0; 
            grid[0][6].north = -100.0; 
            grid[0][6].east = -100.0; 
            grid[0][6].south = -100.0; 

            grid[5][0].goal = true;
            grid[5][0].west = 100.0; 
            grid[5][0].north = 100.0; 
            grid[5][0].east = 100.0; 
            grid[5][0].south = 100.0; 
            
        }
        else if (given.equals("N"))
        {
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 7; col++)
                {
                    grid[row][col] = new Cell();
                    grid[row][col].row = row;
                    grid[row][col].col = col;
                }
            }
            grid[3][1].boundary = true;
            grid[3][2].boundary = true;
            grid[2][2].boundary = true;
            grid[1][2].boundary = true;
            grid[1][3].boundary = true;
            grid[1][4].boundary = true;
            grid[1][5].boundary = true;
            grid[2][5].boundary = true;
            grid[3][5].boundary = true;
            grid[4][5].boundary = true;
            grid[4][4].boundary = true;

            grid[0][6].goal = true;
            grid[0][6].west = -100.0; 
            grid[0][6].north = -100.0; 
            grid[0][6].east = -100.0; 
            grid[0][6].south = -100.0; 

            grid[5][0].goal = true;
            grid[5][0].west = 100.0; 
            grid[5][0].north = 100.0; 
            grid[5][0].east = 100.0; 
            grid[5][0].south = 100.0; 
        }
        else {
            System.out.println("You Goofed! Error in loadMap");
        }
    }

    public static void printMap(Cell grid)
    {
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                
            }
        }
    }


}