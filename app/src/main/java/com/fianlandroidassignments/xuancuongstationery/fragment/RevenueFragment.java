package com.fianlandroidassignments.xuancuongstationery.fragment;

import android.graphics.Color;
import android.icu.number.NumberFormatter;
import android.icu.number.NumberFormatterSettings;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fianlandroidassignments.xuancuongstationery.Common.Common;
import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.RevenueCategoryDTO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.color.MaterialColors;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    BarChart barChart;
    List<String> xValues;
    List<RevenueCategoryDTO> revenueCategoryDTOList;
    TextView totalRevenue;
    DatabaseHelper databaseHelper;
    public RevenueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        databaseHelper = new DatabaseHelper(requireContext());
        barChart = requireActivity().findViewById(R.id.barChart);
        revenueCategoryDTOList = databaseHelper.selectRevenueByCategory();

        totalRevenue = requireActivity().findViewById(R.id.tvTotalRevenueChart);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_revenue, container, false);
        xValues = new ArrayList<>();
        barChart = inflatedView.findViewById(R.id.barChart);
        totalRevenue = inflatedView.findViewById(R.id.tvTotalRevenueChart);


        totalRevenue.setText("Total Revenue: ~" + Common.getCurrencyFormat()
                                            .format(databaseHelper.selectTotalRevenue()) + " VND");
        barChart.getAxisRight().setDrawLabels(false);
        ArrayList<BarEntry> entries = new ArrayList<>();
        databaseHelper = new DatabaseHelper(requireContext());
        revenueCategoryDTOList = databaseHelper.selectRevenueByCategory();
        int i = 0;
        for (RevenueCategoryDTO revenue: revenueCategoryDTOList
             ) {
            xValues.add(revenue.getCategory_name());
            entries.add(new BarEntry(i, revenue.getTotal_revenue()));
            i++;
        }

       /* entries.add(new BarEntry(0, 54f));
        entries.add(new BarEntry(1, 23f));
        entries.add(new BarEntry(2, 12f));
        entries.add(new BarEntry(3, 91f));*/

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(0);
        yAxis.setAxisMaximum(DatabaseHelper.getInstance(requireContext()).selectTotalRevenue());

        yAxis.setAxisLineWidth(2f);
        yAxis.setLabelCount(10);
        yAxis.setAxisLineColor(Color.BLACK);

        BarDataSet dataSet = new BarDataSet(entries, "Category");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);

        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);



        // Inflate the layout for this fragment
        return inflatedView;
    }
}