package com.yc.mema.view.PopupView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.yc.mema.R;
import com.yc.mema.adapter.SearchSortAdapter;
import com.yc.mema.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/9
 * Time: 17:24
 *  显示综合排序
 */
public class PSortView extends PartShadowPopupView {

    private Context act;

    public PSortView(@NonNull Context context) {
        super(context);
        this.act = context;
    }

    private int[] pos = {1, 4};
    private String[] str = {"综合排序", "好评优先"};

    @Override
    protected int getImplLayoutId() {
        return R.layout.p_sort;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ListView listView = findViewById(R.id.listView);
        List<DataBean> listBean = new ArrayList<>();
        for (int i = 0;i < pos.length;i++){
            DataBean bean = new DataBean();
            bean.setName(str[i]);
            bean.setType(pos[i]);
            listBean.add(bean);
        }
        SearchSortAdapter adapter = new SearchSortAdapter(act, listBean);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            adapter.setmPosition(i);
            adapter.notifyDataSetChanged();
            DataBean bean = listBean.get(i);
            if (listener != null){
                listener.onClick(bean.getName(), bean.getType());
                dismiss();
            }
        });
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onClick(String text, int type);
    }

}
