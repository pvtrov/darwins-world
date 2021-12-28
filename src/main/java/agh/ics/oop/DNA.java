package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class DNA {
    public int[] genotype;

    // constructors
    public DNA(Animal firstParent, Animal secondParent) {
        this.genotype = makingNewBabyGenes(firstParent, secondParent);
    }

    public DNA() {
        this.genotype = makingFirstGenes();
    }


    // getters
    public int[] getIntGenotype(){
        return this.genotype;
    }


    // methods for new genotypes
    public int[] makingFirstGenes(){
        Random random = new Random();
        int[] tempGenotype = new int[32];
        for (int i = 0; i < 32; i++){
            int x = random.nextInt(8);
            tempGenotype[i] = x;
        }
        Arrays.sort(tempGenotype);
        return tempGenotype;
    }

    public int[] makingNewBabyGenes(Animal firstParent, Animal secondParent){
        Random random = new Random();
        int[] tempGenotype = new int[32];
        float all;
        all = firstParent.getEnergy() + secondParent.getEnergy();
        float firstParentShares = firstParent.getEnergy()/all;
        float firstParentRange = 32 * firstParentShares;
        int side = random.nextInt(2); // 0 -> lewa, 1 -> prawa

        if (firstParent.getEnergy() > firstParent.getEnergy() && side == 0){
            for (int i = 0; i < firstParentRange; i++){
                DNA firstParentGenotype = firstParent.getGenotype();
                int[] firstParentIntGenotype = firstParentGenotype.getIntGenotype();
                tempGenotype[i] = firstParentIntGenotype[i];
            }
            for (int i = (int) firstParentRange; i < 32; i++){
                DNA secondParentGenotype = secondParent.getGenotype();
                int[] secondParentIntGenotype = secondParentGenotype.getIntGenotype();
                tempGenotype[i] = secondParentIntGenotype[i];
            }
        }else if (firstParent.getEnergy() == secondParent.getEnergy()) {
            for (int i = 0; i < 16; i++) {
                DNA firstParentGenotype = firstParent.getGenotype();
                int[] firstParentIntGenotype = firstParentGenotype.getIntGenotype();
                tempGenotype[i] = firstParentIntGenotype[i];
            }
            for (int i = 16; i < 32; i++) {
                DNA secondParentGenotype = secondParent.getGenotype();
                int[] secondParentIntGenotype = secondParentGenotype.getIntGenotype();
                tempGenotype[i] = secondParentIntGenotype[i];
            }
        }else{
            for (int i = 0; i < 32 - firstParentRange; i++){
                DNA secondParentGenotype = secondParent.getGenotype();
                int[] secondParentIntGenotype = secondParentGenotype.getIntGenotype();
                tempGenotype[i] = secondParentIntGenotype[i];
            }
            for (int i = 32-(int) firstParentRange; i < 32; i++){
                DNA firstParentGenotype = firstParent.getGenotype();
                int[] firstParentIntGenotype = firstParentGenotype.getIntGenotype();
                tempGenotype[i] = firstParentIntGenotype[i];
            }
        }
        Arrays.sort(tempGenotype);
        return tempGenotype;
    }

}
