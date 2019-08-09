package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.view.ViewGroup;

import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yc.mema.R;
import com.yc.mema.adapter.BirthdayRecordsAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FBirthdayRecordsBinding;
import com.yc.mema.impl.BirthdayRecordsContract;
import com.yc.mema.listeners.OnAdapterClickListener;
import com.yc.mema.presenter.BirthdayRecordsPresenter;
import com.yc.mema.utils.DigitalConversionUtils;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zaaach.toprightmenu.TopRightMenuTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 13:26
 *  生日备忘录
 */
public class BirthdayRecordsFrg extends BaseFragment<BirthdayRecordsPresenter, FBirthdayRecordsBinding> implements BirthdayRecordsContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private BirthdayRecordsAdapter adapter;
    private List<MenuItem> menuItems = new ArrayList<>();
    private View topRightFy;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_birthday_records;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.birthday_memorandum), R.mipmap.y21);
        topRightFy = view.findViewById(R.id.top_right_fy);
        view.findViewById(R.id.top_layout).setBackgroundColor(act.getColor(R.color.red_F67690));
        view.findViewById(R.id.title_bar).setBackgroundColor(act.getColor(R.color.red_F67690));
        mB.calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        mB.calendarView.setSelectionType(SelectionType.SINGLE);
        mB.calendarView.setShowDaysOfWeekTitle(false);

//        ViewGroup.LayoutParams params = mB.fyLayout.getLayoutParams();
//        params.height = params.height + 20;
//        mB.fyLayout.setLayoutParams(params);

        Set<Long> days = new TreeSet<>();
//        days.add(calendar.getTimeInMillis());
        days.add(TimeUtils.string2Millis("2019-07-27 00:00:00"));
        days.add(TimeUtils.string2Millis("2019-07-28 00:00:00"));
        days.add(TimeUtils.string2Millis("2019-08-01 00:00:00"));
        //Define colors
        int textColor = Color.parseColor("#ffffff");
        int selectedTextColor = Color.parseColor("#ffffff");
        int disabledTextColor = Color.parseColor("#ffffff");

        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);
        //Connect days to calendar
        mB.calendarView.addConnectedDays(connectedDays);
        mB.calendarView.setConnectedDayIconRes(R.drawable.ic_triangle_white);


        mB.calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(Month month) {
                String[] split = month.getMonthName().split("月");
                String upper = DigitalConversionUtils.numeral(split[0]);
                LogUtils.e(split[1] + "-" + upper);
            }

            @Override
            public void onSelectChanged(Long time) {
                String[] split = TimeUtils.millis2String(time).split(" ");
                LogUtils.e(split[0]);
            }
        });


        if (adapter == null){
            adapter = new BirthdayRecordsAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(pagerNumber = 1);
        adapter.setOnAdapterClickListener(position -> {
            adapter.setPosition(position);
            adapter.notifyDataSetChanged();
        });

        menuItems.add(new MenuItem(getString(R.string.add_records)));
        menuItems.add(new MenuItem(getString(R.string.recordbook)));
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        TopRightMenuTool.TopRightMenu(act, menuItems, topRightFy, 350, -220, position -> {
            switch (position){
                case 0:
                    UIHelper.startAddBirthdayRecordsFrg(BirthdayRecordsFrg.this);
                    break;
                case 1:
                    UIHelper.startMemorandumFrgFrg(BirthdayRecordsFrg.this);
                    break;
            }
        });
    }

    @Override
    public void setData(List<DataBean> list) {
        mB.tvTitle.setText("10月份生日记录");
        listBean.clear();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
