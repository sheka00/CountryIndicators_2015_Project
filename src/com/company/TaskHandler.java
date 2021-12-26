package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TaskHandler {
    public void printHighEconomy(DbHandler dbHandler) {
        String sql = "SELECT countryName as 'Страна с максимальным показателем экономики'\n" +
                "FROM CountryIndicators\n" +
                "WHERE economy =(SELECT MAX(economy) FROM CountryIndicators WHERE region = 'Latin America and Caribbean' OR region = 'Eastern Asia')";

        try (Statement stmt = dbHandler.getConnection().createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            var res = resultSet.getString("Страна с максимальным показателем экономики");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createViewsForTask(DbHandler dbHandler) throws SQLException {
        String sql = "CREATE VIEW IF NOT EXISTS res\n" +
                "AS  \n" +
                "SELECT countryName, abs((SELECT AVG(happinessScore) FROM CountryIndicators)-happinessScore)/(SELECT AVG(happinessScore) FROM CountryIndicators) as '1',\n" +
                "abs((SELECT AVG(standardError) FROM CountryIndicators)-standardError)/(SELECT AVG(standardError) FROM CountryIndicators) as '2',\n" +
                "abs((SELECT AVG(economy) FROM CountryIndicators)-economy)/(SELECT AVG(economy) FROM CountryIndicators) as '3',\n" +
                "abs((SELECT AVG(family) FROM CountryIndicators)-family)/(SELECT AVG(family) FROM CountryIndicators) as '4',\n" +
                "abs((SELECT AVG(health) FROM CountryIndicators)-health)/(SELECT AVG(health) FROM CountryIndicators) as '5',\n" +
                "abs((SELECT AVG(freedom) FROM CountryIndicators)-freedom)/(SELECT AVG(freedom) FROM CountryIndicators) as '6',\n" +
                "abs((SELECT AVG(trust) FROM CountryIndicators)-trust)/(SELECT AVG(trust) FROM CountryIndicators) as '7',\n" +
                "abs((SELECT AVG(generosity) FROM CountryIndicators)-generosity)/(SELECT AVG(generosity) FROM CountryIndicators) as '8',\n" +
                "abs((SELECT AVG(dystopiaResidual) FROM CountryIndicators)-dystopiaResidual)/(SELECT AVG(dystopiaResidual) FROM CountryIndicators) as '9'\n" +
                "FROM CountryIndicators\n" +
                "WHERE countryName IN (SELECT countryName FROM CountryIndicators \n" +
                "WHERE region = 'Western Europe' OR region = 'North America')\n";

        String sql1 = "CREATE VIEW IF NOT EXISTS sum_res\n" +
                "AS  \n" +
                "select countryName,res.[1]+res.[2]+res.[3]+res.[4]+res.[5]+res.[6]+res.[7]+res.[8]+res.[9] as 'суммы отклонений для страны' from res\n";

        try (Statement stmt = dbHandler.getConnection().createStatement()) {
            stmt.execute(sql);
            stmt.execute(sql1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createBarChart(DbHandler dbHandler) {
        var dataset = new DefaultCategoryDataset();
        var set = dbHandler.getAllCountryIndicators().stream()
                .sorted(Comparator.comparing(CountryIndicators::getEconomy))
                .collect(Collectors.toCollection(ArrayList::new));

        set.forEach(country -> dataset.addValue(country.economy, country.countryName, country.countryName));

        var chart = ChartFactory.createBarChart(
                "Экономические показатели стран",
                "Страна",
                "Показатель",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false);

        var plot = chart.getCategoryPlot();
        var br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0);

        var frame = new JFrame("Экономические показатели стран");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var cp = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(900, 900);
            }
        };

        frame.add(cp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void printMostAverageIndicators(DbHandler dbHandler) throws SQLException {
        createViewsForTask(dbHandler);
        String sql = "select countryName\n" +
                "from sum_res\n" +
                "where sum_res.[суммы отклонений для страны] = (select min(sum_res.[суммы отклонений для страны]) from sum_res)";

        try (Statement stmt = dbHandler.getConnection().createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            var res = resultSet.getString("countryName");
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


