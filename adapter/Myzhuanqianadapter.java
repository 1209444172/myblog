package com.yjn.adapter;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.serializer.IntArraySerializer;
import com.exaple.aame.R;
import com.yjn.gouzhao.ZhuanqianInfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Myzhuanqianadapter extends BaseAdapter {
	ArrayList<ZhuanqianInfo> list ;
	LayoutInflater inflater;
	Executor exe;
	Cache cache;
	public void setList(ArrayList<ZhuanqianInfo> list){
		this.list = list;
	}
	public  Myzhuanqianadapter(Context context){
		inflater = inflater.from(context);
		exe = Executors.newFixedThreadPool(3);
		cache = Cache.getIntents();
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
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
		if(convertView==null){
			convertView = inflater.inflate(R.layout.zhuanitem,null);
			holder = new ViewHolder();
			holder.init(convertView);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
			
		}
		ZhuanqianInfo info = list.get(position);
		
		String name =info.getName();
		holder.name.setText(name);
		
		String count =info.getCount_dl();
		holder.renshu.setText(count);
		
		String jifen =info.getDl_back_jifen();
		holder.ub.setText(jifen);
		
		String liuliang =info.getSize();
		holder.mb.setText(liuliang);
		
		String rating =info.getScore();
		float i = Float.parseFloat(rating);
		float rati = i/2;
		Log.i("======","==rati"+rati);
		
		holder.rab.setRating(rati);
		
		
		
		
		final String pic = info.getIcon();
		holder.icon.setTag(pic);
		holder.icon.setImageResource(R.drawable.ic_360);
		Bitmap bit = cache.getBitmap(pic);
		if(bit!=null){
			holder.icon.setImageBitmap(bit);
		}else{
			new Myimagetask(new ImageCallback() {
				
				@Override
				public void Imageback(Bitmap result) {
					// TODO Auto-generated method stub
					if(holder.icon.getTag().toString().equals(pic)){
						holder.icon.setImageBitmap(result);
					}
					cache.add(pic, result);
				}
			}).execute(pic);
		}
		
		
		
		return convertView;
	}
	class ViewHolder{
		ImageView icon;
		TextView name;
		TextView renshu;
		TextView mb;
		TextView ub;
		RatingBar rab;
		void init(View v){
			icon = (ImageView) v.findViewById(R.id.zhuanimageView1);
			name  = (TextView) v.findViewById(R.id.zhuannametextView1);
			renshu  = (TextView) v.findViewById(R.id.renshutextView2);
			mb  = (TextView) v.findViewById(R.id.daxiaotextView1);
			ub  = (TextView) v.findViewById(R.id.ubitextView5);
			rab = (RatingBar) v.findViewById(R.id.ratingBar1);
		}
		
	}

}
