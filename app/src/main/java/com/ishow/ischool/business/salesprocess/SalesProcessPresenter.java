package com.ishow.ischool.business.salesprocess;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ishow.ischool.R;
import com.ishow.ischool.bean.saleprocess.Marketposition;
import com.ishow.ischool.bean.saleprocess.SaleProcess;
import com.ishow.ischool.bean.saleprocess.Subordinate;
import com.ishow.ischool.common.api.ApiObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqf on 16/8/14.
 */
public class SalesProcessPresenter extends SalesProcessContract.Presenter implements OnChartValueSelectedListener {


    @Override
    public void getSaleProcessData(int campus_id, int position_id, int user_id, int type) {
        mModel.getSaleProcessData(campus_id, position_id, user_id, type).subscribe(new ApiObserver<SaleProcess>() {
            @Override
            public void onSuccess(SaleProcess saleProcess) {
                mView.getListSuccess(saleProcess);
            }

            @Override
            public void onError(String msg) {
                mView.getListFail(msg);
            }

            @Override
            protected boolean isAlive() {
                return mView != null && !mView.isActivityFinished();
            }
        });
    }

    @Override
    public void getOption(String option, int position_id) {
        mModel.getOption(option, position_id).subscribe(new ApiObserver<Marketposition>() {
            @Override
            public void onSuccess(Marketposition marketpositions) {
                mView.getListSuccess(marketpositions);
            }

            @Override
            public void onError(String msg) {
                mView.getListFail(msg);
            }

            @Override
            protected boolean isAlive() {
                return mView != null && !mView.isActivityFinished();
            }
        });
    }

    @Override
    public void getOptionSubordinate(String option, int campus_id, int position_id) {
        mModel.getOptionSubordinate(option,campus_id,position_id).subscribe(new ApiObserver<Subordinate>() {
            @Override
            public void onSuccess(Subordinate subordinate) {
                mView.getListSuccess(subordinate);
            }

            @Override
            public void onError(String msg) {
                mView.getListFail(msg);
            }
            @Override
            protected boolean isAlive() {
                return mView != null && !mView.isActivityFinished();
            }
        });
    }

    @Override
    public void getOptionSubordinateKeyWords(String option, int campus_id, int position_id, String keywords) {
        mModel.getOptionSubordinateKeyWords(option,campus_id,position_id,keywords).subscribe(new ApiObserver<Subordinate>() {
            @Override
            public void onSuccess(Subordinate subordinate) {
                mView.getListSuccess(subordinate);
            }

            @Override
            public void onError(String msg) {
                mView.getListFail(msg);
            }
            @Override
            protected boolean isAlive() {
                return mView != null && !mView.isActivityFinished();
            }
        });
    }

    ArrayList<String> list = null;

    public ArrayList<String> getSpinnerData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add("7天");
            list.add("30天");
            list.add("180天");
            list.add("365天");
        }
        return list;
    }

    /**
     * 1.初始化LineChart
     * 2.添加数据x，y轴数据
     * 3.刷新图表
     */
    public void initChart(Context context, LineChart mChart) {

        mChart.setOnChartValueSelectedListener(this);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);


        // add data
        //setData();

        mChart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.sale_gray_txt_color_));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        // xAxis.setAxisMaxValue(10);
        xAxis.setAxisMinValue(0);
        xAxis.setGranularity(1);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setLabelRotationAngle(145);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(context, R.color.sale_gray_txt_color_));
        // leftAxis.setAxisMaxValue(200f);
        leftAxis.setAxisMinValue(0);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void setData(Context context, LineChart mChart, List<String> full_amount, List<String> apply_number) {

        ArrayList<Entry> point1 = null;
        if (apply_number != null) {
            int aplply_size = apply_number.size();
            point1 = new ArrayList<Entry>();
            for (int i = 0; i < aplply_size; i++) {
                point1.add(new Entry(i, Float.valueOf(apply_number.get(i))));
            }
        }

        ArrayList<Entry> point2 = null;
        if (full_amount != null) {
            int full_size = full_amount.size();
            point2 = new ArrayList<Entry>();
            for (int i = 0; i < full_size; i++) {
                point2.add(new Entry(i, Float.valueOf(full_amount.get(i))));
            }
        }

        LineDataSet set1, set2;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(point1);
            set2.setValues(point2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(point1, context.getString(R.string.apply_count));
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(context, R.color.sale_apply_fill_color_));
            set1.setCircleColor(ContextCompat.getColor(context, R.color.sale_apply_fill_color_));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(255);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighlightEnabled(true);
            set1.setHighLightColor(ContextCompat.getColor(context, R.color.sale_apply_fill_color_));
            set1.setDrawCircleHole(false);
            set1.setDrawValues(set1.isDrawValuesEnabled());

            // create a dataset and give it a type
            set2 = new LineDataSet(point2, context.getString(R.string.full_amount));
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(ContextCompat.getColor(context, R.color.sale_full_fill_color_));
            set2.setCircleColor(ContextCompat.getColor(context, R.color.sale_full_fill_color_));
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(255);
            set2.setFillColor(ContextCompat.getColor(context, R.color.sale_full_fill_color_));
            set2.setDrawCircleHole(false);
            set2.setDrawHighlightIndicators(true);
            set2.setHighLightColor(ContextCompat.getColor(context, R.color.sale_full_fill_color_));
            set2.setDrawValues(set2.isDrawValuesEnabled());
            //set2.setFillFormatter(new MyFillFormatter(900f));
            //set1.removeFirst();
            // set2.removeFirst();
            // set1.removeLast();
            // set2.removeLast();

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            data.setValueTextColor(Color.GRAY);
            data.setValueTextSize(9f);
            // set data
            mChart.setData(data);
            //mChart.setVisibleXRangeMaximum(15);
        }
    }
}
