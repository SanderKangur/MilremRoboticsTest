import java.util.*;
import java.io.*;
import java.math.*;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * This code automatically collects game data in an infinite loop.
 * It uses the standard input to place data into the game variables such as x and y.
 * YOU DO NOT NEED TO MODIFY THE INITIALIZATION OF THE GAME VARIABLES.
 **/
class Player {

    int x;
    int y;
    int nextCheckpointX;
    int nextCheckpointY;
    int nextCheckpointDist;
    int nextCheckpointAngle;
    int opponentX;
    int opponentY;

    int prevCpX;
    int prevCpY;
    int nextDestX;
    int nextDestY;

    int thrust;
    boolean boost = true;

    static Logger LOGGER = Logger.getLogger(Player.class.getName());


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        Player p = new Player();

        // game loop
        while (true) {
            p.x = in.nextInt(); // x position of your pod
            p.y = in.nextInt(); // y position of your pod
            p.nextCheckpointX = in.nextInt(); // x position of the next check point
            p.nextCheckpointY = in.nextInt(); // y position of the next check point
            p.nextCheckpointDist = in.nextInt();
            p.nextCheckpointAngle = in.nextInt();
            p.opponentX = in.nextInt();
            p.opponentY = in.nextInt();

            p.calculateNextMove();
            p.calculateNextDest();
            p.move();

        }
    }

    void move(){
        if(boost && nextCheckpointAngle == 0 && nextCheckpointDist > 3000){
            boost = false;
            System.out.println(nextCheckpointX + " " + nextCheckpointY + " BOOST");
        }
        /*if (nextCheckpointDist < 1000 && Math.abs(opponentX - x) < 1200 && Math.abs(opponentY - y) < 1200) {
            System.out.println(nextCheckpointX + " " + nextCheckpointY + " SHIELD");
        }*/
        System.out.println(nextDestX + " " + nextDestY + " " + thrust);
        //System.out.println(nextCheckpointX + " " + nextCheckpointY + " " + thrust);
    }

    void calculateNextMove(){
        double calcAngle = Math.cos(Math.toRadians(nextCheckpointAngle));
        double calcThrust = nextCheckpointDist / (4*600f);

        LOGGER.log(INFO, calcAngle + " " + calcThrust);

        //LOGGER.log(INFO, "Opponent: " + opponentX + " " + opponentY);
        //LOGGER.log(INFO, "Player: " + x + " " + y);

        if(calcAngle < 0.8){
            prevCpX = x;
            prevCpY = y;
            //LOGGER.log(INFO, "Prev: " + prevCpX + " " + prevCpY);
        }

        if(calcAngle > 0) calcAngle = 1;
        else calcAngle = 1 + calcAngle;

        if(calcThrust > 1) calcThrust = 1;
        else if(calcThrust < 0) calcThrust = 0;

        thrust =  (int) (100 * calcAngle * calcThrust);
    }

    void calculateNextDest(){
        if(prevCpX - nextCheckpointX < -300)
            nextDestX = nextCheckpointX-400;
        else if(prevCpX - nextCheckpointX > 300)
            nextDestX = nextCheckpointX+400;
        else
            nextDestX = nextCheckpointX;

        if(prevCpY - nextCheckpointY < -300)
            nextDestY = nextCheckpointY-400;
        else if(prevCpY - nextCheckpointY > 300)
            nextDestY = nextCheckpointY+400;
        else
            nextDestY = nextCheckpointY;

        //LOGGER.log(INFO, "CP: " + nextCheckpointX + " " + nextCheckpointY);
    }
}
