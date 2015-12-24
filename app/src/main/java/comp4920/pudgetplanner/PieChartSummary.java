package comp4920.pudgetplanner;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartSummary extends Activity {

    private RelativeLayout mainLayout;
    private PieChart mChart;
//    // we're going to display pie chart for smartphones martket shares
//    private float[] yData = { 5, 10, 15, 30, 20, 10, 10 };
//    private String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung", "Test", "Test2" };
    private float[] yData ;
     private String[] xData;
    int total = 0;

    Spinner spinUser;
    ArrayList<String> array_spinner = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //retrieve Xdata and Ydata
        Bundle b=this.getIntent().getExtras();
        xData = b.getStringArray("xData");
        yData = b.getFloatArray("yData");
        total = getIntent().getIntExtra("total",0);

        setContentView(R.layout.piechart);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        //spinUser = (Spinner) findViewById(R.id.spinner2);//fetch the spinner from layout file

        mChart = new PieChart(this);
        // add pie chart to main layout
        //mainLayout.addView(mChart);
        setContentView(mChart);
        mainLayout.setBackgroundColor(Color.parseColor("#55656C"));


        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(PieChartSummary.this,
                        xData[e.getXIndex()] + " = " + e.getVal()*100 + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        //spinner

//        array_spinner.add("HI");
//        array_spinner.add("HIIII");
//        array_spinner.add("HHJHKJH");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, array_spinner);
//        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiii  " + adapter.getCount());


//        spinUser.setAdapter(adapter);


//        spinUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
    }



    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Total : " + total);
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        //mChart.invalidate();

        mChart.animateXY(1800, 1800); // animate horizontal and vertical 3000 milliseconds
    }

}