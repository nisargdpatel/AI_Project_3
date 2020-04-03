import java.text.DecimalFormat;
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

    static Cell currCell = new Cell();

    public static void main(String[] args) {

        int trials = 20000;
        Cell[][] Q = new Cell[6][7];
        Cell[][] N = new Cell[6][7];
        loadMap(Q, "Q");
        loadMap(N, "N");
        String direction;

        for (int i = 0; i < trials; i++) {
            // finding a random cell every trial
            currCell = findRandomCell(Q);
            while(!currCell.goal)
            {
                direction = generateDirection(Q);
                
                updateN(N, direction);

                updateQ(Q, N, direction);

                move(Q, direction);
            }

        }

        System.out.println("********************PRINTING Q:*******************");
        printFinalMap(Q);
        System.out.println("********************PRINTING N:*******************");
        printFinalMap(N);
        System.out.println("********************PRINTING OPTIMAL MAP:*******************");
        printOptimalAction(Q);

    }

    // Done
    public static Cell findRandomCell(Cell[][] Q) {
        int randomRow;
        int randomCol;
        Random random = new Random();
        do {
            randomRow = random.nextInt(6);
            randomCol = random.nextInt(7);

        } while (Q[randomRow][randomCol].boundary);

        return Q[randomRow][randomCol];
    }

    public static String generateDirection(Cell[][] Q) {
        // Decide on a direction using Q-map
        // Optimal direction is random # is 95%, else random direction
        Random random = new Random();
        int probability = random.nextInt(100);

        // If you get anything above 5% (or 95%), you choose the optimal direction of
        // current cell, else it's 5% or less so choose random direction
        if (probability > 5) {
            // If west is greatest
            if ((currCell.west > currCell.north) && (currCell.west > currCell.east)
                    && (currCell.west > currCell.south)) {
                return "W";
            }
            // else if north is greatest
            else if ((currCell.north > currCell.west) && (currCell.north > currCell.east)
                    && (currCell.north > currCell.south)) {
                return "N";
            }
            // else if east is greatest
            else if ((currCell.east > currCell.west) && (currCell.east > currCell.south)
                    && (currCell.east > currCell.north)) {
                return "E";
            }
            // else if south is greatest
            else if ((currCell.south > currCell.west) && (currCell.south > currCell.north)
                    && (currCell.south > currCell.east)) {
                return "S";
            }
            // else all values are equal, or 0, thus you choose random direction
            else {
                int randNum = random.nextInt(4);
                switch (randNum) {
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
        } else {
            int randNum = random.nextInt(4);
            switch (randNum) {
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

    public static void loadMap(Cell[][] grid, String given) {
        if (given.equals("Q")) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
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

        } else if (given.equals("N")) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
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
        } else {
            System.out.println("You Goofed! Error in loadMap");
        }
    }

    public static void printFinalMap(Cell[][] grid) {
        DecimalFormat df = new DecimalFormat("####0.0");
        for (int row = 0; row <= 5; row++) // row
        {

            // top
            for (int col = 0; col <= 6; col++) {
                if (grid[row][col].boundary) {
                    System.out.print("\t\t\t");
                } else if (grid[row][col].goal) {
                    System.out.print("\t\t\t");
                } else {
                    System.out.print("\t" + df.format(grid[row][col].north) + "\t\t");
                }
            }
            System.out.println();

            // middle
            for (int col = 0; col <= 6; col++) {
                if (grid[row][col].boundary) {
                    System.out.print("\t####\t\t");
                } else if (grid[row][col].west == 100) {
                    System.out.print("\t+100\t\t");
                } else if (grid[row][col].west == -100) {
                    System.out.print("\t-100\t\t");
                } else {
                    System.out.print(df.format(grid[row][col].west) + "\t\t" + df.format(grid[row][col].east) + "\t");
                }
            }
            System.out.println();

            // bottom
            for (int col = 0; col <= 6; col++) {
                if (grid[row][col].boundary) {
                    System.out.print("\t\t\t");
                } else if (grid[row][col].goal) {
                    System.out.print("\t\t\t");
                } else {
                    System.out.print("\t" + df.format(grid[row][col].south) + "\t\t");
                }
            }
            System.out.println();
            System.out.println();

        }
        System.out.println("=================================================================================================================");
    }
    
    public static void printOptimalAction(Cell[][] Q)
    {
        Double west, north, east, south;
        for (int row = 0; row <= 5; row++)
        {
            for (int col = 0; col <= 6; col++)
            {
                west = Q[row][col].west;
                north = Q[row][col].north;
                east = Q[row][col].east;
                south = Q[row][col].south;
                
                if (west > north && west > east && west > south)
                {
                    System.out.print("<<<<\t");
                }
                else if (north > west && north > east && north > south)
                {
                    System.out.print("^^^^\t");
                }
                else if (east > west && east > north && east > south)
                {
                    System.out.print(">>>>\t");
                }
                else if (south > west && south > north && south > east)
                {
                    System.out.print("VVVV\t");
                }
                else if (Q[row][col].boundary)
                {
                    System.out.print("####\t");
                }
                else if (Q[row][col].goal)
                {
                    System.out.print("GGGG\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void updateN(Cell[][] N, String direction)
    {
        int row, col;
        row = currCell.row;
        col = currCell.col;
        if(direction.equals("W"))
        {
            N[row][col].west += 1.0;
        }
        else if (direction.equals("N"))
        {
            N[row][col].north += 1.0;
        }
        else if (direction.equals("E"))
        {
            N[row][col].east += 1.0;
        }
        else if (direction.equals("S"))
        {
            N[row][col].south += 1.0;
        }
        else {
            System.out.println("You Goofed, you may have passed invalid direction, N will not be updated!");
        }
    }

    public static void updateQ(Cell[][] Q, Cell[][] N, String direction)
    {
        // Q(s,a) ← Q(s,a) + (1/N(s,a))[R(s,a) + γ*max(Q(s',a'))−Q(s,a)]
        int row, col;
        row = currCell.row;
        col = currCell.col;

        double gamma = 0.9;

        if(direction.equals("W"))
        {
            Q[row][col].west = Q[row][col].west + (1/N[row][col].west)*(-2 + (gamma*maxOfNextCell(Q, direction)) - Q[row][col].west);
        }
        else if (direction.equals("N"))
        {
            Q[row][col].north = Q[row][col].north + (1/N[row][col].north)*(-1 + (gamma*maxOfNextCell(Q, direction)) - Q[row][col].north);
        }
        else if (direction.equals("E"))
        {
            Q[row][col].east = Q[row][col].east + (1/N[row][col].east)*(-2 + (gamma*maxOfNextCell(Q, direction)) - Q[row][col].east);
        }
        else if (direction.equals("S"))
        {
            Q[row][col].south = Q[row][col].south + (1/N[row][col].south)*(-3 + (gamma*maxOfNextCell(Q, direction)) - Q[row][col].south);
        }
        else
        {
            System.out.print("You Goofed, you may have passed invalid direction, Q will not be updated!");
        }
    }

    // Returns the max value of Q'
    public static double maxOfNextCell(Cell[][] Q, String direction) {
        int row = currCell.row;
        int col = currCell.col;
        if (direction.equals("W") && (currCell.col - 1) >= 0 && !Q[row][col - 1].boundary) {
            // if the west cell is open, find the optimal value from that cell
            return findMax(Q[row][col - 1]);
        } else if (direction.equals("N") && (currCell.row - 1) >= 0 && !Q[row - 1][col].boundary) {
            return findMax(Q[row - 1][col]);
        } else if (direction.equals("E") && (currCell.col + 1) <= 6 && !Q[row][col + 1].boundary) {
            return findMax(Q[row][col + 1]);
        } else if (direction.equals("S") && (currCell.row + 1) <= 5 && !Q[row + 1][col].boundary) {
            return findMax(Q[row + 1][col]);
        } else {
            //Bounce back if the optimal direction cell is not open, so find max in current cell..
            return findMax(currCell);
        }
    }

    // find maximum inside this cell which was passed
    public static double findMax(Cell passedCell) {
        if ((passedCell.west > passedCell.north) && (passedCell.west > passedCell.east)
                && (passedCell.west > passedCell.south)) {
            return passedCell.west;
        }
        // else if north is greatest
        else if ((passedCell.north > passedCell.west) && (passedCell.north > passedCell.east)
                && (passedCell.north > passedCell.south)) {
            return passedCell.north;
        }
        // else if east is greatest
        else if ((passedCell.east > passedCell.west) && (passedCell.east > passedCell.south)
                && (passedCell.east > passedCell.north)) {
            return passedCell.east;
        }
        // else if south is greatest
        else if ((passedCell.south > passedCell.west) && (passedCell.south > passedCell.north)
                && (passedCell.south > passedCell.east)) {
            return passedCell.south;
        } else {
            return passedCell.west;
        }
    }

    public static void move(Cell[][] grid, String direction)
    {
        //10% drift left of that direction
        //80% go forward in that direction
        //10% drift right of that direction
        int row = currCell.row;
        int col = currCell.col;
        
        Random random = new Random();
        int probability = random.nextInt(100) + 1;

        if(probability <= 10)
        {
            //drift left
                //but if left is a boundary or blocade, BOUNCE BACK to same cell
            if(direction.equals("W") && row+1 <= 5 && !grid[row+1][col].boundary)
            {
                currCell = grid[row+1][col];
            }
            else if (direction.equals("N") && col-1 >= 0 && !grid[row][col-1].boundary)
            {
                currCell = grid[row][col-1];
            }
            else if(direction.equals("E") && row-1 >= 0 && !grid[row-1][col].boundary)
            {
                currCell = grid[row-1][col];
            }
            else if(direction.equals("S") && col+1 <= 6 && !grid[row][col+1].boundary)
            {
                currCell = grid[row][col+1];
            }
            else
            {
                //Bounce back, no movement, STAY QUARANTINED (Daddy won't let you out)
            }
        }
        else if(probability <= 90)
        {
            //go in that direction
                //but if that direction is a boundary or blocade, BOUNCE BACK to same cell
            if(direction.equals("W") && col-1 >= 0 && !grid[row][col-1].boundary)
            {
                currCell = grid[row][col-1];
            }
            else if (direction.equals("N") && col-1 >= 0 && !grid[row][col-1].boundary)
            {
                currCell = grid[row][col-1];
            }
            else if(direction.equals("E") && col+1 <= 6 && !grid[row][col+1].boundary)
            {
                currCell = grid[row][col+1];
            }
            else if(direction.equals("S") && row+1 <= 5 && !grid[row+1][col].boundary)
            {
                currCell = grid[row+1][col];
            }
            else
            {
                //Bounce back, no movement, STAY QUARANTINED (Daddy won't let you out)
            }
        }
        else
        {
            //drift right
                //but if right is a boundary or blocade, BOUNCE BACK to same cell
            if(direction.equals("W") && row-1 >= 0 && !grid[row-1][col].boundary)
            {
                currCell = grid[row-1][col];
            }
            else if (direction.equals("N") && col+1 <= 6 && !grid[row][col+1].boundary)
            {
                currCell = grid[row][col+1];
            }
            else if (direction.equals("E") && row+1 <= 5 && !grid[row+1][col].boundary)
            {
                currCell = grid[row+1][col];
            }
            else if (direction.equals("S") && col-1 >= 0 && !grid[row][col-1].boundary)
            {
                currCell = grid[row][col-1];
            }
            else {
                //Bounce back to the same cell or don't reference to the new cell, STAY QUARANTINEED (Daddy won't let you out)
            }
        }
    }

}