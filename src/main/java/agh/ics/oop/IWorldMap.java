package agh.ics.oop;

public interface IWorldMap {
    /**
     * Indicate if I can place Plant there or not
     *
     * @param position
     *          Checking if position is occupied by anything
     * @return True if I can place a Plant there
     */
    boolean canPlacePlant(Vector2d position);

    /**
     * Indicate if I can place Animal there or not
     *
     * @param position
     *          Checking if position is occupied and by what
     * @return True if position is not occupied or on position is only Plant
     */
    boolean canPlaceAnimal(Vector2d position);

    /**
     * Indicate if anything is on this position
     *
     * @param position
     *
     * @return True if nothing is on this position
     */
}
