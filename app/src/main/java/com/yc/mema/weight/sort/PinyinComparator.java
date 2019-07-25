package com.yc.mema.weight.sort;

import com.yc.mema.bean.DataBean;

import java.util.Comparator;

/**
 * 
 * @author 
 *
 */
public class PinyinComparator implements Comparator<DataBean> {

	public int compare(DataBean o1, DataBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
