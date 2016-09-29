package com.ishow.ischool.widget.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ishow.ischool.R;
import com.ishow.ischool.bean.campusperformance.MonthTableBodyItem;
import com.ishow.ischool.bean.campusperformance.MonthTableBodyRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mini on 16/9/28.
 */

public class MonthPerformanceTableRowAdapter extends BaseAdapter {

    private Context context;
    private List<MonthTableBodyRow> datas;

    public MonthPerformanceTableRowAdapter(Context context, List<MonthTableBodyRow> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_month_performance_table_row, null);
        MyLinearLayout4ListView lv = (MyLinearLayout4ListView) convertView.findViewById(R.id.row_lv);

        MonthTableBodyRow rowData = datas.get(position);
        ArrayList<MonthTableBodyItem> temp = new ArrayList<>();
        temp.addAll(rowData.subList(1, rowData.size()));        // 去掉第一个"校区"(已固定存在于右边)
        MonthPerformanceTableBodyAdapter adapter = new MonthPerformanceTableBodyAdapter(context, temp);
        lv.setAdapter(adapter);

        return convertView;
    }

}