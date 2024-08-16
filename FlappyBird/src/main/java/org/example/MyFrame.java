package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyFrame extends JFrame implements KeyListener {
    ArrayList<Bird> birds = new ArrayList<Bird>();
    Timer MainTimer;
    Walls wall;
    int cantBirds = 1000;
    int alive = cantBirds;
    Random rand = new Random();

    public MyFrame(){
        for (int i=0; i<cantBirds;i++){
            birds.add(new Bird());
        }
        wall = new Walls();
        this.setSize(500,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setVisible(true);
        this.setBackground(Color.white);


        for(Bird bird : birds){
            this.add(bird.getBird());
        }
        this.add(wall.getWallRoof());
        this.add(wall.getWallFloor());


        MainTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitWall();
                wall.MoveWall();
                for(Bird bird : birds) {
                    bird.Fall();
                    lifeChecker();
                    bird.saltarIA(wall.getWallFloor().getX(), wall.getWallFloor().getY(), wall.getWallRoof().getY()+wall.getWallRoof().getHeight());
                    bird.Saltar();
                    if(bird.live){
                        bird.puntuacion++;
                    }
                }

                contGeneration();
                wall.detectLimits();
            }
        });

        MainTimer.start();

    }

    public void lifeChecker() {
        for (Bird bird : birds) {
            if ((bird.getBird().getY() > 850 || bird.getBird().getY() < -50 ) && bird.live) {
                bird.setLive(false);
                alive--;
                System.out.println(alive);
                this.remove(bird.getBird());
            }
        }
    }

    public void hitWall(){
        for(Bird bird : birds) {
            if (bird.live) {
                Rectangle birdBounds = bird.getBird().getBounds();
                Rectangle wallsRBounds = wall.getWallRoof().getBounds();
                Rectangle wallsFBounds = wall.getWallFloor().getBounds();

                if (birdBounds.intersects(wallsRBounds) || birdBounds.intersects(wallsFBounds)) {
                    alive--;
                    System.out.println(alive);
                    bird.setLive(false);
                    this.remove(bird.getBird());
                }
            }
        }
    }

    public void contGeneration(){
        if(alive <= 0){
            ArrayList<Bird> new_birds = new ArrayList<Bird>();
            Collections.sort(birds);
            Collections.reverse(birds);

            //El 5% queda igual
            for(int i=0 ; i<cantBirds*0.05; i++){
                new_birds.add(birds.get(i));
            }
            //Agrego un 5% con nuevo brain
            for(int i=0 ; i<cantBirds*0.05; i++){
                new_birds.add(new Bird());
            }
            //El 30% muta del mejor
            for(int i=0 ; i<cantBirds*0.3; i++){
                Bird padre = birds.get(0);
                Bird hijo = new Bird();
                hijo.brain = padre.brain;
                hijo.brain.mutar();
                new_birds.add(hijo);

            }

            //El 40% muta del 5% mejor
            for(int i=0 ; i<cantBirds*0.4; i++){
                Bird padre = birds.get(rand.nextInt(0, (int) (cantBirds*0.05)));
                Bird hijo = new Bird();
                hijo.brain = padre.brain;
                hijo.brain.mutar();
                new_birds.add(hijo);
            }

            for (int i = 0; i < cantBirds * 0.2; i++) {
                Bird father = birds.get((int)rand.nextInt((int) (cantBirds * 0.05)));
                Bird mother = birds.get((int)rand.nextInt((int) (cantBirds * 0.05)));
                Bird son = new Bird();
                son.brain = father.brain.crossover(mother.brain);
                new_birds.add(son);
            }


            birds = new_birds;
            spawnBirds();
            alive = cantBirds;
            wall.resetPos();
        }
    }

    public void spawnBirds(){
        for(Bird bird : birds){
            bird.live = true;
            bird.resetLocation();
            this.add(bird.getBird());
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
