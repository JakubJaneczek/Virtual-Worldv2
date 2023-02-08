package POJava.Animals;

import POJava.Animal;
import POJava.Point;
import POJava.World;

import java.awt.*;

public class Fox extends Animal {
    private static final int FOX_POWER = 2;
    private static final int FOX_INITIATIVE = 1;

    public Fox(World world, Point position, int age) {
        super(OrganismType.FOX, world, position, age, FOX_POWER, FOX_INITIATIVE);
        setColor(new Color(255, 128, 0));
    }

    @Override
    public void Action(){
        Point position = ChooseAnySpot(this.getPosition());
        if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
        else{
            if(getWorld().getBoard()[position.getY()][position.getX()].getPower() <= this.getPower()){
                getWorld().getBoard()[position.getY()][position.getX()].Collision(this);
                if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
            }
        }
    }

    @Override
    public String OrganismTypeToString() {
        return "Fox";
    }
}
