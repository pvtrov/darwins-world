package agh.ics.oop.gui;

import agh.ics.oop.World;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineCharts {
    private LineChart chart;
    private XYChart.Series series;

    // constructor
    public LineCharts(String string){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        this.chart = new LineChart(xAxis, yAxis);

        series = new XYChart.Series();

        chart.setTitle(string);
        chart.setCreateSymbols(true);
        chart.getData().add(series);
    }

    // getter
    public LineChart makeMeChart(){
        return chart;
    }


    // update
    public void updateAnimals(World world){
        series.getData().add(new XYChart.Data(world.day, world.getNumberOfLivingAnimals()));
    }

    public void updatePlants(World world){
        series.getData().add(new XYChart.Data(world.day, world.getNumberOfPlants()));
    }

    public void updateEnergy(World world){
        series.getData().add(new XYChart.Data(world.day, world.getAverageOfEnergy()));
    }

    public void updateKids(World world){
        series.getData().add(new XYChart.Data(world.day, world.getAverageOfNumberOfKids()));
    }

}
