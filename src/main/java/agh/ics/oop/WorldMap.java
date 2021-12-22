package agh.ics.oop;

public class WorldMap extends InputParameters{
    int width;
    int height;
    int jungleWidth;
    int jungleHeight;




    // counting where jungle should be
    private Vector2d jungleCountingLowerLeft(){
        int middleXmap = width / 2;
        int middleYmap = height / 2;
        int halfOfJungleX = jungleWidth / 2;
        int halfOfJungleY = jungleHeight / 2;

        return new Vector2d(middleXmap-halfOfJungleX, middleYmap - halfOfJungleY);
    }

    private Vector2d jungleCountingUpperRight(){
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
