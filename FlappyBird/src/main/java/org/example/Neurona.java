package org.example;

import java.util.Random;

public class Neurona {
    int sourceID;
    int targetID;
    float weight;
    Random rand;
    public boolean hidden;

    public Neurona(){
        rand = new Random();
        if(rand.nextInt(2)>=0.5){
            hidden = true;
            sourceID = rand.nextInt(6);
            targetID = rand.nextInt(7);
        }
        else {
            hidden = false;
            sourceID = rand.nextInt(7);
            targetID = rand.nextInt(2);
        }
        weight = rand.nextFloat(-1,1);
    }

    public int getSourceID() {
        return sourceID;
    }


    public void mutarPeso(){
        weight += rand.nextFloat((float) -0.1, (float) 0.1);
    }


    public int getTargetID() {
        return targetID;
    }

    public float getWeight() {
        return weight;
    }
}
