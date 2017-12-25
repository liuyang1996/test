package com.liuyang.android.newquestion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyang.android.newquestion.thread.MyThread;
import com.liuyang.android.newquestion.view.PointerProgressBar;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;


public class TestresultAct extends AppCompatActivity {

	private PointerProgressBar pointerProgressBar;
	ImageView img;
	TextView tv;
	TextView rate;

	public static final String APP_ID = "wx8019aea4dcfd04ac";
	//微信所有的api都是由这个类调用的
	private IWXAPI api;
	LinearLayout wx_friends;
	LinearLayout wx_moments;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
//			result = {"type":1}
			Toast.makeText(TestresultAct.this,"后台设置成功："+result,Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_testresult);
		//初始化IWXAPI
		api = WXAPIFactory.createWXAPI(this,APP_ID);
		//将app_id注册到微信
		api.registerApp(APP_ID);
		initView();
		initData();
	}

	private void initData() {
		Intent it = getIntent();
		String num = it.getStringExtra("num");
		String type = it.getStringExtra("type");
		SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
		if(type.equals("0")){
			tv.setText("健康状况");
			String url = "http://39.106.27.181/three/sethealthscore/?name="+preferences.getString("username","")+"&healthscore="+num;
			new MyThread(handler,url).start();
		}
		else{
			tv.setText("学习状况");
			String url = "http://39.106.27.181/three/setstudyscore/?name="+preferences.getString("username","")+"&studyscore="+num;
			new MyThread(handler,url).start();
		}
		Log.i("tag","ResultAct得到的总分："+num);
		int _num = Integer.parseInt(num);
		if(_num == 100){
			pointerProgressBar.setProgress(_num);
		}
		pointerProgressBar.setProgress(Integer.parseInt(num));
		setRateTv(_num);

	}
	private void setRateTv(int score){
		if(score>=90){
			rate.setText("相当不错，你的表现可喜可贺");
		}else if(score>75){
			rate.setText("表现一般，希望你再接再厉");
		}else{
			rate.setText("你最近怎么了，加油啊少年");
		}
	}
	private void initView() {
		//TextView tvNum = (TextView) findViewById(R.id.tv_warn);
		tv = findViewById(R.id.tv_warn);
		pointerProgressBar=(PointerProgressBar) findViewById(R.id.roundProgressBar);
		img = findViewById(R.id.result_main_btn_back);
		wx_friends = findViewById(R.id.wx_friends);
		wx_moments = findViewById(R.id.wx_moments);
		rate = findViewById(R.id.rate);

//		wx_friends.setOnClickListener(new MyWXOnClickListener(SendMessageToWX.Req.WXSceneSession));
//		wx_moments.setOnClickListener(new MyWXOnClickListener(SendMessageToWX.Req.WXSceneTimeline));

	}
//	class MyWXOnClickListener implements View.OnClickListener{
//		int type;
//		MyWXOnClickListener(int type){
//			this.type = type;
//		}
//		@Override
//		public void onClick(View v) {
//			Toast.makeText(TestresultAct.this,"点击",Toast.LENGTH_SHORT).show();
//
//
//			WXWebpageObject webpage = new WXWebpageObject();
//			webpage.webpageUrl = "http://www.imooc.com/sourse/list";
//
//			WXMediaMessage msg = new WXMediaMessage(webpage);
//			msg.title = "sdnu问卷";
//			msg.description = "一个基于android的问卷调查App";
//
//			Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);
//			msg.thumbData = bmpToByteArray(thumb);
//
//			SendMessageToWX.Req req = new SendMessageToWX.Req();
//			req.scene = type;
//			req.transaction = buildTransaction();
//			req.message = msg;
//			api.sendReq(req);
//		}
//	}
	public void OnClick_Image_fd(View view) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				//第一步：创建WXImageObject对象，并设置url
				String url = "http://39.106.27.181/static/healthimg.png";
				WXImageObject imgObject = new WXImageObject();
				//设置图像对象的url
				imgObject.imagePath = url;


				//第三步：创建WXMediaMessage对象，并包装WXImageObject
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObject;
				// 第四步：下载并压缩图像
				Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap,120,150,true);
				//第五步：释放bitmap所占用的内存资源
				bitmap.recycle();
				//第六步：设置压缩图像
				msg.thumbData = bmpToByteArray(thumbBitmap);
				//第七步：发送图像
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("UrlImage");//设置请求唯一标识
				//是否发送给朋友圈
				//req.scene = share_CheckBox.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : ;SendMessageToWX.Req.WXSceneSession
				req.scene = SendMessageToWX.Req.WXSceneSession;
				req.message = msg;
				//发送请求
				api.sendReq(req);


			}
		}).start();
	}

	public void OnClick_Image_mt(View view) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				//第一步：创建WXImageObject对象，并设置url
				String url = "http://39.106.27.181/static/healthimg.png";
				WXImageObject imgObject = new WXImageObject();
				//设置图像对象的url
				imgObject.imagePath = url;


				//第三步：创建WXMediaMessage对象，并包装WXImageObject
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObject;
				// 第四步：下载并压缩图像
				Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap,120,150,true);
				//第五步：释放bitmap所占用的内存资源
				bitmap.recycle();
				//第六步：设置压缩图像
				msg.thumbData = bmpToByteArray(thumbBitmap);
				//第七步：发送图像
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("UrlImage1");//设置请求唯一标识
				//是否发送给朋友圈
				//req.scene = share_CheckBox.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : ;SendMessageToWX.Req.WXSceneSession
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				req.message = msg;
				//发送请求
				api.sendReq(req);
				//Toast.makeText(MainActivity.this, String.valueOf(api.sendReq(req)), Toast.LENGTH_SHORT).show();

			}
		}).start();
	}




	//为请求生成唯一的标示
	private String buildTransaction(final String type){
		return (type == null)?String.valueOf(System.currentTimeMillis()):type + String.valueOf(System.currentTimeMillis());
	}

	//btp转换成字节数组
	private byte[] bmpToByteArray(Bitmap bitmap){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
		if(true){
			bitmap.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public void onClick_back(View view){
		finish();
	}
}
