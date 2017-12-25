package com.liuyang.android.newquestion.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.liuyang.android.newquestion.Main2;
import com.liuyang.android.newquestion.R;
import com.liuyang.android.newquestion.TestresultAct;
import com.liuyang.android.newquestion.cahe.BitmapCache;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
	// 传递过来的页面view的集合
	private List<View> viewItems;
	// 每个item的页面view
	private View convertView;
	private Main2 mContext = null;
	private List<String> dataItems;
	ViewHolder holder = null;
	int[] mycount = new int[10];

	// 构造方法
	public MyPagerAdapter(Main2 context, List<View> viewItems,
						  List<String> dataItems) {
		this.mContext = context;
		this.viewItems = viewItems;
		this.dataItems = dataItems;
	}

	// 获取总数
	@Override
	public int getCount() {
		return viewItems.size();
	}

	// 官方推荐这么写
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 移除页卡
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewItems.get(position));
	}

	// 这里返回View
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// 初始化viewholder
		holder = new ViewHolder();
		// 取到每一个子项，进行赋值
		convertView = viewItems.get(position);
		holder.rapQuestion = (RadioGroup) convertView
				.findViewById(R.id.rap_question);

		holder.questionContent = (TextView) convertView
				.findViewById(R.id.questioncontent);

		holder.txtTitle = (TextView) convertView
				.findViewById(R.id.txt_title);

		holder.netimg = convertView.findViewById(R.id.netimg);

		//网络下载图片操作
		RequestQueue mQueue = Volley.newRequestQueue(mContext);
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
		holder.netimg.setDefaultImageResId(R.mipmap.ic_launcher);
		holder.netimg.setErrorImageResId(R.mipmap.ic_launcher);
		holder.netimg.setImageUrl("http://39.106.27.181/static/healthimg.png",
				imageLoader);


		Log.i("tag","dataItems:"+dataItems.get(position));
		holder.questionContent.setText(dataItems.get(position));
		holder.txtTitle.setText(position + 1 + "/10");
		// 监听RadioGroup的选中事件
		holder.rapQuestion
			.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (position != viewItems.size() - 1) {
							mContext.setCurrentView(position + 1);
						}// 点击默认跳转到下一页
						switch (checkedId) {
						case R.id.chk_nothave:
							mycount[position] = 10;// 对应的分数值
							Log.i("tag","希望设置mycount【position】= 10，实际上mycount【position】="+mycount[position]);
							break;
						case R.id.chk_sometimes:
							mycount[position] = 5;
							Log.i("tag","希望设置mycount【position】= 5，实际上mycount【position】="+mycount[position]);

							break;
						case R.id.chk_usual:
							mycount[position] = 1;
							Log.i("tag","希望设置mycount【position】= 1，实际上mycount【position】="+mycount[position]);

							break;
						}
                        //最后一页
						if (position == viewItems.size() - 1) {
							/*
							 * 统计分数
							 */

							int num = 0;
							for (int i = 0; i < mycount.length; i++) {
								Log.i("tag","mycount["+i+"]:" + mycount[i]);
								num = num + mycount[i];
							}
							// 跳转到测试结果页面
							Intent myintent = new Intent(mContext,
									TestresultAct.class);
							Log.i("tag","VPAdepter的总分："+num);
							myintent.putExtra("num", String.valueOf(num));
							//0表示健康问卷
                            myintent.putExtra("type",String.valueOf(0));
							mContext.startActivity(myintent);
							mContext.finish();
						}
					}
				});
		container.addView(convertView);// 添加页卡
		return convertView; // 返回的是View对象进行显示
	}

	// ViewHolder控件容器
	class ViewHolder {
		//按钮（没有，有时，经常
		RadioGroup rapQuestion;
		//问题
		TextView questionContent;
		//图片（通过网络下载
        NetworkImageView netimg;
		//页号
		TextView txtTitle;
	}

}
