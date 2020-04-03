public class Cell {
    
    double west;
    double north;
    double east;
    double south;

    boolean boundary;
    boolean goal;

    int row;
    int col;

    Cell() {
        
        west = 0.0;
        north = 0.0;
        east = 0.0;
        south = 0.0;

        boundary = false;
        goal = false;
    }

}