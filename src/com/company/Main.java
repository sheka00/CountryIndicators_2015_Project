package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String args[]) throws IOException {

        var fillDatabase = false; // false значит база данных заполнена
        try {
            var dbHandler = DbHandler.getInstance();
            var taskHandler = new TaskHandler();
            if(fillDatabase){
                var data = Parser.getCountryIndicatorsFromCSV();
                dbHandler.fillDatabase(data);
            }
            taskHandler.printMostAverageIndicators(dbHandler);
            taskHandler.printHighEconomy(dbHandler);
            taskHandler.createBarChart(dbHandler);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}