package com.yc.mema.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.LogUtils;
import com.yc.mema.R;
import com.yc.mema.adapter.SortAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSortListBinding;
import com.yc.mema.impl.MemorandumContract;
import com.yc.mema.presenter.MemorandumPresenter;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.weight.sort.CharacterParser;
import com.yc.mema.weight.sort.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 18:08
 *  备忘录
 */
public class MemorandumFrg extends BaseFragment<MemorandumPresenter, FSortListBinding> implements MemorandumContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private SortAdapter adapter;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_sort_list;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.recordbook), R.mipmap.tj01);
        mB.sidrbar.setTextView(mB.dialog);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
        Collections.sort(listBean, pinyinComparator);

        if (adapter == null){
            adapter = new SortAdapter(act, listBean, SortAdapter.recordbook);
        }
        mB.listView.setAdapter(adapter);
        mB.listView.setOnItemLongClickListener((adapterView, view12, i, l) -> {
//            PopupWindowTool.showDialog(act, PopupWindowTool.clear_br, () -> mPresenter.onDelBr(i, listBean.get(i).getBookId()));
            PopupWindowTool.showDialog(act)
                    .asConfirm("确定删除吗？", "",
                            "取消", "确定",
                            () -> {
                                mPresenter.onDelBr(i, listBean.get(i).getBookId());
                            }, null, false)
                    .bindLayout(R.layout.p_dialog) //绑定已有布局
                    .show();;
            return false;
        });

        //设置右侧触摸监听
        mB.sidrbar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = adapter.getPositionForSection(s.charAt(0));
            if(position != -1){
                mB.listView.setSelection(position);
            }
        });

        mB.listView.setOnItemClickListener((parent, view1, position, id) -> {
            //这里要利用adapter.getItem(position)来获取当前position所对应的对象
            LogUtils.e(listBean.get(position).getName());
        });

        //根据输入框输入值的改变来过滤搜索
        mB.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mPresenter.onRequest(null);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        UIHelper.startAddBirthdayRecordsFrg(this);
    }

    @Override
    public void setData(List<DataBean> list) {
        for (DataBean bean : list){
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(bean.getNickName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                bean.setSortLetters(sortString.toUpperCase());
            }else{
                bean.setSortLetters("#");
            }
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDelBr(int position) {
        listBean.remove(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<DataBean> filterDateList = new ArrayList<DataBean>();
        if(TextUtils.isEmpty(filterStr)){
            filterDateList = listBean;
        }else{
            filterDateList.clear();
            for(DataBean sortModel : listBean){
                String name = sortModel.getNickName();
                if(name.indexOf(filterStr) != -1 || characterParser.getSelling(name).startsWith(filterStr)){
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.notifyDataSetChanged();
    }

}
