package com.yjn.adapter;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exaple.aame.R;
import com.yjn.gouzhao.HuodongInfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Huodongadapter extends BaseAdapter {
	ArrayList<HuodongInfo> list;
	LayoutInflater inflater;
	Executor exe ;
	Cache cache;
	
	public Huodongadapter(Context context) {
		inflater = LayoutInflater.from(context);
		exe =  Executors.newFixedThreadPool(3);
		cache = Cache.getIntents();
	}
	
	public void setList(ArrayList<HuodongInfo> list){
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.huodongitem, null);
			holder = new ViewHolder();
			holder.init(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		HuodongInfo info = list.get(position);
		String status = info.getStatus();
	//	String status2 = status.replace("[\t\r\n]",null);
		holder.status.setText(status);
		if(status.equals("进行中")){
			holder.status.setBackgroundResource(R.drawable.huodonglv);
		}else if(status.equals("已结束")){
			holder.status.setBackgroundResource(R.drawable.huodonghui);
		}
		
		String totoal = info.getTotal_join_user();
		//String totoal2 = totoal.replace("[\t\r\n]",null);
		holder.total_join_user.setText(totoal);
		
		String anme = info.getAname();
		//String anme2 = anme.replace("[\t\r\n]",null);
		holder.anme.setText(anme);
		
		String shortname = info.getShortname();
		//String shortname2 = shortname.replace("[\t\r\n]",null);
		holder.shortname.setText(shortname);
		
		
		final String hotpic = info.getHotpic();
		holder.hotpic.setTag(hotpic);
		holder.hotpic.setImageResource(R.drawable.ic_360);
		Bitmap bit = cache.getBitmap(hotpic);
		if(bit!=null){
			holder.hotpic.setImageBitmap(bit);
		}else{
			new Myimagetask(new ImageCallback() {
				
				@Override
				public void Imageback(Bitmap result) {
					// TODO Auto-generated method stub
					if(holder.hotpic.getTag().toString().equals(hotpic)){
						holder.hotpic.setImageBitmap(result);
					}
					cache.add(hotpic, result);
				}
			}).execute(hotpic);
			
		}

		return convertView;
	}

	class ViewHolder {
		TextView status;
		TextView total_join_user;
		TextView anme;
		TextView shortname;
		ImageView hotpic;

		void init(View v) {
			status = (TextView) v.findViewById(R.id.statustextView6);
			total_join_user = (TextView) v.findViewById(R.id.totaltextView3);
			anme = (TextView) v.findViewById(R.id.anametextView1);
			shortname = (TextView) v.findViewById(R.id.shortnametextView2);
			hotpic = (ImageView) v.findViewById(R.id.hotpicimageView1);
		}
	}

}
