package POJava.Plants;

import POJava.Plant;
import POJava.Point;
import POJava.World;

import java.awt.*;
import java.util.Random;

public class Dandelion extends Plant {
    private static final int DANDELION_POWER = 0;
    private static final int DANDELION_INITIATIVE = 0;
    private static final int BREED_CHANCES = 3;

    public Dandelion(World world, Point position, int age){
        super(OrganismType.DANDELION, world, position, age, DANDELION_POWER, DANDELION_INITIATIVE);
        setColor(Color.YELLOW);
    }
   @Override
   public void Action(){
        Random random = new Random();
        for(int i = 0; i < BREED_CHANCES; i++){
            int tmp = random.nextInt(20);
            if(tmp < 1) this.Split();
        }
   }
   @Override
   public String OrganismTypeToString() {return "Dandelion";}
}
