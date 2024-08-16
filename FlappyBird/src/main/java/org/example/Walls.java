package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Walls {
    int x = 550;
    int y1 = 0;
    int y2;
    JLabel wallRoof;
    JLabel wallFloor;
    Random rand = new Random();
    int height;

    public Walls(){
        height = rand.nextInt(100, 500);
        wallRoof = new JLabel();
        wallRoof.setBounds(x, y1, 25, height);
        wallRoof.setBackground(Color.green);
        wallRoof.setOpaque(true);

        wallFloor = new JLabel();
        y2 = setFloorY();
        wallFloor.setBounds(x, y2, 25,700);
        wallFloor.setBackground(Color.green);
        wallFloor.setOpaque(true);
    }

    public void MoveWall(){
        wallRoof.setLocation(wallRoof.getX()-5, y1);
        wallFloor.setLocation(wallFloor.getX()-5, y2);
    }

    public JLabel getWallRoof() {
        return wallRoof;
    }

    public JLabel getWallFloor() {
        return wallFloor;
    }

    public int setFloorY(){
        return height+230;
    }

    public void resetPos(){
        height = rand.nextInt(100, 500);
        wallRoof.setBounds(x, y1, 25, height);
        y2 = setFloorY();
        wallFloor.setLocation(x,y2);
    }


    public void detectLimits(){
        if(wallRoof.getX()<=-50){
            height = rand.nextInt(100, 500);
            wallRoof.setBounds(x, y1, 25, height);
            y2 = setFloorY();
            wallFloor.setLocation(x,y2);
        }

    }


}
