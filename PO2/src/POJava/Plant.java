package POJava;

import java.util.Random;

public abstract class Plant extends Organism{
    protected Plant(Organism.OrganismType organismType, World world, Point position, int age, int power, int initiative){
        super(organismType, world, position, age, power, initiative);
    }

    @Override
    public void Action(){
        Random random = new Random();
        int limit = 100;
        int chance = random.nextInt(limit);
        if (chance < 10)
        {
            Split();
        }
    }

    protected void Split(){
        Point temp = this.ChooseFreeSpot(getPosition());
        if(temp.equals(getPosition())) return;
        else {
            Organism tempOrganism = OrganismsGenerator.CreateNewOrganism(getOrganismType(), this.getWorld(), temp);
            getWorld().AddOrganism(tempOrganism);
        }
    }

    @Override
    public boolean IsAnimal(){return false;}

    @Override
    public void Collision(Organism other){
        this.getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
        this.setAlive(false);
        getWorld().DeleteOrganism(this);
        Log.AddComment(other.OrganismTypeToString() + " eats " + this.OrganismTypeToString());
    }
}
