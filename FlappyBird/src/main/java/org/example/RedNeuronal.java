package org.example;

import java.util.ArrayList;
import java.util.Random;

public class RedNeuronal {
    Float[][] MatrixWeight = new Float[6][7];
    Float[][] ArrayWeight = new Float[7][2];
    Float[] HiddenBias = new Float[7];
    Float[] outputBias = new Float[2];
    ArrayList<Neurona> neuronas = new ArrayList<>();
    Random rand = new Random();
    int cantGenes = 20;

    public RedNeuronal(){
        for (int j = 0; j<2 ;j++) {
            outputBias[j] = rand.nextFloat(-1, 1);
        }
        for (int i = 0; i<7 ;i++) {
            HiddenBias[i] = rand.nextFloat(-1,1);
            for (int j = 0; j<2 ;j++) {
                ArrayWeight[i][j] = (float) 0;
            }
        }
        for(int i = 0; i<6 ;i++){
            for(int j = 0; j<7 ;j++){
                MatrixWeight[i][j] = (float) 0;
            }
        }

        for(int i = 0; i<cantGenes;i++){
            neuronas.add(new Neurona());
            if(neuronas.get(i).hidden){
                int src = neuronas.get(i).sourceID;
                int trg = neuronas.get(i).sourceID;
                float weight = neuronas.get(i).weight;
                MatrixWeight[src][trg] = weight;
            }
            else {
                int src = neuronas.get(i).sourceID;
                int trg = neuronas.get(i).targetID;
                float weight = neuronas.get(i).weight;
                ArrayWeight[src][trg] = weight;
            }
        }
    }


    public void isSaltar(float[] inputs){
        float total = 0;
        for (int  j=0 ; j<7 ; j++) {
            for (int i = 0; i < 6; i++) {
                total += MatrixWeight[i][j] * inputs[i];
            }
            total+=HiddenBias[j];
            HiddenBias[j] = ReLu(total);
            total = 0;
        }
        total = 0;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 7; j++) {
                total += HiddenBias[j] * ArrayWeight[j][i];
            }
            total+=outputBias[i];
            outputBias[i] = sigmoid(total);
            total = 0;
        }
    }

    public float ReLu(float num){
        return Math.max(0,num);
    }

    private float sigmoid(float x) {
        return 1 / (1 + (float)Math.exp(-x));
    }

    public void mutar() {
        for (Neurona neurona : neuronas) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                neurona.mutarPeso();
            }
        }
        for (int i = 0; i < HiddenBias.length; i++) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                HiddenBias[i] += rand.nextFloat(-0.1f, 0.1f);
            }
        }
        for (int i = 0; i < outputBias.length; i++) {
            if (rand.nextFloat(1) <= 0.1) { // 10% de probabilidad de mutar
                outputBias[i] += rand.nextFloat(-0.1f, 0.1f);
            }
        }
    }

    public RedNeuronal copia(){
        return this;
    }

    public RedNeuronal crossover(RedNeuronal anotherGenome) {
        // Start with a copy of the genome
        RedNeuronal crossed_red = copia();
        // But change some of the genes for some of the other genome's genes
        int amount_of_crossovers = rand.nextInt(1, 5);
        for (int i = 0; i < amount_of_crossovers; i++){
            int index = rand.nextInt(0, cantGenes);
            crossed_red.neuronas.set(index, anotherGenome.neuronas.get(index));
        }
        return crossed_red;
    }


}
