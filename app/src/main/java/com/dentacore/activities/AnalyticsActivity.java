package com.dentacore.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dentacore.R;
import com.dentacore.database.AppDatabase;
import com.dentacore.database.ProcedureCount;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.*;

public class AnalyticsActivity extends AppCompatActivity {
    private AppDatabase db;
    private BarChart barChart;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analytics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        barChart = findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);

        loadStats();
    }

    private void loadStats() {
        new Thread(() -> {
            int totalPatients = db.patientDao().count();
            double totalRev = db.patientDao().totalRevenue();
            double totalCollected = db.patientDao().totalCollected();
            List<ProcedureCount> procedures = db.prescriptionDao().procedureCounts();

            // Monthly revenue (last 6 months)
            Calendar cal = Calendar.getInstance();
            List<BarEntry> barEntries = new ArrayList<>();
            List<String> monthLabels = new ArrayList<>();
            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            for (int i = 5; i >= 0; i--) {
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.add(Calendar.MONTH, -i);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0);
                long from = cal.getTimeInMillis();
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59); cal.set(Calendar.SECOND, 59);
                long to = cal.getTimeInMillis();
                double rev = db.prescriptionDao().revenueInRange(from, to);
                barEntries.add(new BarEntry(5 - i, (float) rev));
                monthLabels.add(months[cal.get(Calendar.MONTH)]);
            }

            runOnUiThread(() -> {
                // Summary cards
                ((TextView) findViewById(R.id.tvTotalPatients)).setText(String.valueOf(totalPatients));
                ((TextView) findViewById(R.id.tvTotalRevenue)).setText("₹" + String.format("%.0f", totalCollected));
                ((TextView) findViewById(R.id.tvTotalBilled)).setText("₹" + String.format("%.0f", totalRev));
                double outstanding = totalRev - totalCollected;
                ((TextView) findViewById(R.id.tvOutstanding)).setText("₹" + String.format("%.0f", Math.max(0, outstanding)));

                // Bar Chart
                BarDataSet barSet = new BarDataSet(barEntries, "Revenue (₹)");
                barSet.setColor(Color.parseColor("#00d4ff"));
                barSet.setValueTextColor(Color.WHITE);
                barSet.setValueTextSize(10f);
                BarData barData = new BarData(barSet);
                barData.setBarWidth(0.6f);
                barChart.setData(barData);
                barChart.setBackgroundColor(Color.parseColor("#111827"));
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(monthLabels));
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getXAxis().setTextColor(Color.WHITE);
                barChart.getAxisLeft().setTextColor(Color.WHITE);
                barChart.getAxisRight().setEnabled(false);
                barChart.getLegend().setTextColor(Color.WHITE);
                barChart.getDescription().setEnabled(false);
                barChart.animateY(1000);
                barChart.invalidate();

                // Pie Chart for procedures
                List<PieEntry> pieEntries = new ArrayList<>();
                int[] colors = {
                    Color.parseColor("#00d4ff"), Color.parseColor("#7c3aed"),
                    Color.parseColor("#10b981"), Color.parseColor("#f59e0b"),
                    Color.parseColor("#ef4444"), Color.parseColor("#ec4899")
                };
                if (procedures.isEmpty()) {
                    pieEntries.add(new PieEntry(1f, "No Data"));
                } else {
                    for (ProcedureCount pc : procedures) {
                        if (pc.procedureType != null && !pc.procedureType.isEmpty())
                            pieEntries.add(new PieEntry(pc.cnt, pc.procedureType));
                    }
                }
                PieDataSet pieSet = new PieDataSet(pieEntries, "Procedures");
                pieSet.setColors(colors);
                pieSet.setValueTextColor(Color.WHITE);
                pieSet.setValueTextSize(11f);
                PieData pieData = new PieData(pieSet);
                pieChart.setData(pieData);
                pieChart.setBackgroundColor(Color.parseColor("#111827"));
                pieChart.setHoleColor(Color.parseColor("#1a2236"));
                pieChart.setHoleRadius(40f);
                pieChart.setTransparentCircleRadius(45f);
                pieChart.getLegend().setTextColor(Color.WHITE);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Procedures");
                pieChart.setCenterTextColor(Color.WHITE);
                pieChart.setCenterTextSize(14f);
                pieChart.animateY(1000);
                pieChart.invalidate();
            });
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
