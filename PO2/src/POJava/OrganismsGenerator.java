package POJava;

import POJava.Plants.*;
import POJava.Animals.*;

public class OrganismsGenerator {
    public static Organism CreateNewOrganism
            (Organism.OrganismType organismType, World world, Point position) {
        switch (organismType) {
            case GRASS: return new Grass(world, position, world.getRoundNumber());
            case GUARANA: return new Guarana(world, position, world.getRoundNumber());
            case DANDELION: return new Dandelion(world, position, world.getRoundNumber());
            case BELLADONNA: return new Belladonna(world, position, world.getRoundNumber());
            case PINEBORSCHT: return new Pineborscht(world, position, world.getRoundNumber());
            case WOLF: return new  Wolf(world, position, world.getRoundNumber());
            case FOX: return  new Fox(world, position, world.getRoundNumber());
            case SHEEP: return new Sheep(world, position, world.getRoundNumber());
            case TURTLE: return new Turtle(world, position, world.getRoundNumber());
            case ANTYLOPE: return new Antelope(world, position, world.getRoundNumber());
            case HUMAN: return new Human(world, position, world.getRoundNumber());
            default:
                return null;//UNDEFINED TYP
        }
    }
}
