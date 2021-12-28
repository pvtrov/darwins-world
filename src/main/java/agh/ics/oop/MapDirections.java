package agh.ics.oop;

public enum MapDirections {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static MapDirections getRandom(){
        return values()[(int) (Math.random() * values().length)];
    }

    public MapDirections next(){
        return switch (this){
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case WEST -> new Vector2d(-1, 0);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

}
