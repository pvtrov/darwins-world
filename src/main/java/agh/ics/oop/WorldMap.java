package agh.ics.oop;

public class WorldMap extends InputParameters{
    int width;
    int height;
    int jungleWidth;
    int jungleHeight;

    public WorldMap(int width, int height, int jungleHeight, int jungleWidth){
        this.width = width;
        this.height = height;
        this.jungleHeight = jungleHeight;
        this.jungleWidth = jungleWidth;
    }

    public Vector2d getMapUpperRight(){
        return new Vector2d(width-1, height-1);
    }

    public int getJungleFirstX(){
        Vector2d lowerLeft = jungleCountingLowerLeft();
        return lowerLeft.x;
    }

    public int getJungleFirstY(){
        Vector2d lowerLeft = jungleCountingLowerLeft();
        return lowerLeft.y;
    }

    public int getJungleSecondX(){
        Vector2d upperRight = jungleCountingUpperRight();
        return upperRight.x;
    }

    public int getJungleSecondY() {
        Vector2d upperRight = jungleCountingUpperRight();
        return upperRight.y;
    }

    // counting where jungle should be
    public Vector2d jungleCountingLowerLeft(){
        int middleXmap = width / 2;
        int middleYmap = height / 2;
        int halfOfJungleX = jungleWidth / 2;
        int halfOfJungleY = jungleHeight / 2;

        return new Vector2d(middleXmap-halfOfJungleX, middleYmap - halfOfJungleY);
    }

    public Vector2d jungleCountingUpperRight(){
        int middleXmap = width / 2;
        int middleYmap = height / 2;
        int halfOfJungleX = jungleWidth / 2;
        int halfOfJungleY = jungleHeight / 2;

        return new Vector2d(middleXmap+halfOfJungleX, middleYmap+halfOfJungleY);
    }

    // counting which field is which
    public int getFieldAddress(Vector2d position){
        int a = position.x; int b = position.y;
        return (height-(b+1))*width + (a+1);
    }
}
