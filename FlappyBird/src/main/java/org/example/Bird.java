package org.example;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;

public class Bird implements Comparable<Bird>{

    RedNeuronal brain = new RedNeuronal();
    JLabel bird;
    int x = 100;
    int y;
    int tempY = 1;
    Image birdImagen;
    ImageIcon birdIcon;
    boolean salta = false;
    boolean cae = true;
    boolean live = true;
    float[] inputs = new float[6];
    int puntuacion = 0;

    public Bird(){
        bird = new JLabel();
        bird.setBounds(x, 400, 55,50 );

        birdIcon = new ImageIcon("FlappyBird.png");
        birdImagen = birdIcon.getImage();
        birdImagen = birdImagen.getScaledInstance(55, 50,  Image.SCALE_SMOOTH);
        birdIcon = new ImageIcon(birdImagen);

        bird.setIcon(birdIcon);
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setSalta(boolean salta) {
        this.salta = salta;
    }

    public void saltarIA(int wallX, int wallFloorY, int wallRoofY){
        inputs[0] = bird.getY(); //Posicion y del pajaro
        inputs[1] = wallX-bird.getX(); //Distancia hacia la siguiente pared
        inputs[2] = wallFloorY-bird.getY(); //Posicion y de la pared menos posicion y del pajaro
        inputs[3] = wallRoofY-bird.getY(); //Posicion y de la pared menos posicion y del pajaro
        inputs[4] = 800-bird.getY(); //Distancia al piso
        inputs[5] = bird.getX()-wallX+50; //Distancia al final de la pared, le sumo el ancho a la pared : 50 width


        brain.isSaltar(inputs);
        salta = true;
        if(brain.outputBias[0]!=0){
            salta = true;
        }
        if(brain.outputBias[1]!=0){
            salta = false;
            cae = true;
        }
    }

    public void setCae(boolean cae) {
        this.cae = cae;
    }

    public JLabel getBird(){
        return bird;
    }

    public void resetLocation(){
        bird.setLocation(100, 400);
        puntuacion = 0;
    }

    public void Fall(){
        if(cae && live){
            bird.setLocation(x, bird.getY()+8);
        }
    }

    public void Saltar(){
        if(salta && live){
           if(tempY!=18){
               cae = false;
               y = (int) ((int) -Math.pow((tempY/3)- 3,2) + 9);
               bird.setLocation(x, bird.getY()-y);
               tempY++;
           }
           else {
               cae = true;
               salta = false;
               tempY=1;
           }
        }
    }


    @Override
    public int compareTo(Bird o) {
        return Integer.compare(this.puntuacion, o.puntuacion);
    }
}
