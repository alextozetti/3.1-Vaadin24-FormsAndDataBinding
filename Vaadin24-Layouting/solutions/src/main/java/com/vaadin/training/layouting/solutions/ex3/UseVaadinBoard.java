package com.vaadin.training.layouting.solutions.ex3;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.training.layouting.solutions.MainLayout;

@Route(value = UseVaadinBoard.ROUTE, layout = MainLayout.class)
public class UseVaadinBoard extends VerticalLayout {
    public static final String ROUTE = "ex3";
    public static final String TITLE = "Exercise 3";

    public UseVaadinBoard(){
        setSizeFull();
        Board board = new Board();
        board.addRow(getPieChart(), getPieChart());
        board.addRow(getColumnChart());
        add(board);
    }

    private Chart getColumnChart(){
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Monthly Average Rainfall");
        conf.setSubTitle("Source: WorldClimate.com");

        XAxis x = new XAxis();
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Rainfall (mm)");
        conf.addyAxis(y);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(100);
        legend.setY(70);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);

        conf.addSeries(new ListSeries("Tokyo", 49.9, 71.5, 106.4, 129.2, 144.0,
                176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4));
        conf.addSeries(new ListSeries("New York", 83.6, 78.8, 98.5, 93.4,
                106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3));
        conf.addSeries(new ListSeries("London", 48.9, 38.8, 39.3, 41.4, 47.0,
                48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
        conf.addSeries(new ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7, 52.6,
                75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));

        return chart;
    }

    private Chart getPieChart(){
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Browser market shares at a specific website, 2010");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels
                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);

        final DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Firefox", 45.0));
        series.add(new DataSeriesItem("IE", 26.8));
        DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        chrome.setSliced(true);
        chrome.setSelected(true);
        series.add(chrome);
        series.add(new DataSeriesItem("Safari", 8.5));
        series.add(new DataSeriesItem("Opera", 6.2));
        series.add(new DataSeriesItem("Others", 0.7));
        conf.setSeries(series);

        return chart;
    }
}
