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

}
