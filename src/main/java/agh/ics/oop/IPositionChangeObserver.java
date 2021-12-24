package agh.ics.oop;

public interface IPositionChangeObserver {

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
