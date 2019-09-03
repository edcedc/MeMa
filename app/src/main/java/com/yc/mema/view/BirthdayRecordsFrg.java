package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.OrientationHelper;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.yc.mema.CustomDayView;
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
import com.yc.mema.utils.DatePickerUtils;
import com.yc.mema.utils.DigitalConversionUtils;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.utils.TimeUtil;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zaaach.toprightmenu.TopRightMenuTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
public class BirthdayRecordsFrg extends BaseFragment<BirthdayRecordsPresenter, FBirthdayRecordsBinding> implements BirthdayRecordsContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private BirthdayRecordsAdapter adapter;
    private List<MenuItem> menuItems = new ArrayList<>();
    private View topRightFy;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private String nowString;

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
        view.findViewById(R.id.top_layout).setBackgroundColor(act.getResources().getColor(R.color.red_F67690));
        view.findViewById(R.id.title_bar).setBackgroundColor(act.getResources().getColor(R.color.red_F67690));
        mB.tvDay.setOnClickListener(this);
        if (adapter == null){
            adapter = new BirthdayRecordsAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM"));
        mPresenter.onRequest(nowString);
        adapter.setOnAdapterClickListener(new OnAdapterClickListener() {
            @Override
            public void onClick(int position) {
                adapter.setPosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position, String bookId) {
                PopupWindowTool.showDialog(act, PopupWindowTool.clear_br, () -> mPresenter.onDelBr(position, bookId));
            }
        });

        menuItems.add(new MenuItem(getString(R.string.add_records)));
        menuItems.add(new MenuItem(getString(R.string.recordbook)));

        mB.tvDay.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM")));

        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
//                nowString = date.getYear() + "-" + date.getMonth();
                LogUtils.e(nowString);
//                new Handler().postDelayed(() -> mPresenter.onRequest(nowString), 200);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                mB.calendarView.selectOtherMonth(offset);
            }
        };
        initCalendarView();

        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(nowString);
            }
        });
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        CustomDayView customDayView = new CustomDayView(act, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                 act,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        initMonthPager();
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        mB.calendarView.setAdapter(calendarAdapter);
        mB.calendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        mB.calendarView.setPageTransformer(false, (page, position) -> {
            position = (float) Math.sqrt(1 - Math.abs(position));
            page.setAlpha(position);
        });
        mB.calendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    nowString = date.getYear() + "-" + date.getMonth();
                    mB.tvDay.setText(nowString);
                    new Handler().postDelayed(() -> mPresenter.onRequest(nowString), 200);
                    LogUtils.e(nowString);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
        mB.tvTitle.setText(nowString.split("-")[1] +
                "月份生日记录");
        listBean.clear();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        HashMap markData = new HashMap<>();
        for (DataBean bean : list){
            String brithday = bean.getBrithday();
            String month = brithday.split("-")[1];
            if (month.startsWith("0")){
                month = month.replace("0", "");
            }
            String day = brithday.split("-")[2];
            if (day.startsWith("0")){
                day = day.replace("0", "");
            }
            markData.put(brithday.split("-")[0] + "-" + month + "-" + day, "1");
        }
        calendarAdapter.setMarkData(markData);
        calendarAdapter.notifyDataChanged();
    }

    @Override
    public void setDelBr(int position) {
        listBean.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_day:
                DatePickerUtils.onYearMonthPicker(act, "选择时间", (year, month, day) -> {
                    nowString = year + "-" + month;
                    CalendarDate today = new CalendarDate(Integer.valueOf(year), Integer.valueOf(month), 1);
                    calendarAdapter.notifyDataChanged(today);
                    new Handler().postDelayed(() -> mPresenter.onRequest(nowString), 200);
                    mB.tvDay.setText(nowString);
                    LogUtils.e(year + "-" + month);
                });
                break;
        }
    }

}
