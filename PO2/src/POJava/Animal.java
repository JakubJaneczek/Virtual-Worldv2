package POJava;

public abstract class Animal extends Organism {

    public Animal(OrganismType organismType, World world, Point position, int age, int power, int initiative) {
        super(organismType, world, position, age, power, initiative);
    }

    @Override
    public void Action(){
        Point position = ChooseAnySpot(this.getPosition());
        if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
        else{
            getWorld().getBoard()[position.getY()][position.getX()].Collision(this);
            if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
        }
    }
    public void Move(Point nextPosition){
        getWorld().getBoard()[nextPosition.getY()][nextPosition.getX()] = this;
        getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
        this.getPosition().setX(nextPosition.getX());
        this.getPosition().setY(nextPosition.getY());
    }

    @Override
    public void Collision(Organism other){
        if(this.getOrganismType() == other.getOrganismType()) Breed();
        else if(this.getPower() > other.getPower())
        {
            getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
            other.setAlive(false);
            getWorld().DeleteOrganism(other);
            Log.AddComment(other.OrganismTypeToString() + " attacks stronger " + this.OrganismTypeToString() + " and dies");
        }
        else
        {
            getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
            this.setAlive(false);
            getWorld().DeleteOrganism(this);
            Log.AddComment(other.OrganismTypeToString() + " attacks weaker " + getOrganismType() + " and kills his  opponent");
        }
    }

    public void Breed(){
        Point temp = ChooseFreeSpot(this.getPosition());
        if(temp.equals(getPosition())) return;
        else {
            Organism tempOrganism = OrganismsGenerator.CreateNewOrganism(getOrganismType(), this.getWorld(), temp);
            getWorld().AddOrganism(tempOrganism);
        }
        Log.AddComment(OrganismTypeToString() + " breeds");
    }

    @Override
    public boolean IsAnimal(){return true;}
}
