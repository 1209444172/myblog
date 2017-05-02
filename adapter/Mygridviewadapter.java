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
import com.yjn.gouzhao.YouxiInfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Mygridviewadapter extends BaseAdapter{
		ArrayList<YouxiInfo> list ;
		LayoutInflater inflater;
		Executor exe;
		Cache cache;
	public void setList(ArrayList<YouxiInfo> list){
		this.list = list;
	}
	public Mygridviewadapter(Context context){
		inflater = LayoutInflater.from(context);
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
			convertView = inflater.inflate(R.layout.griveiw,null);
			holder = new ViewHolder();
			holder.init(convertView);
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		YouxiInfo info = list.get(position);
		
		String name = info.getName();
		holder.tv.setText(name);
		
		final String url = info.getIcon();
		holder.iv.setTag(url);
		holder.iv.setImageResource(R.drawable.ic_360);
		Bitmap bit = cache.getBitmap(url);
		if(bit!=null){
			holder.iv.setImageBitmap(bit);
		}else{
			new Myimagetask(new ImageCallback() {
				
				@Override
				public void Imageback(Bitmap result) {
					// TODO Auto-generated method stub
					if(holder.iv.getTag().toString().equals(url)){
						holder.iv.setImageBitmap(result);
					}
					cache.add(url, result);
				}
			}).execute(url);
		}
		
		return convertView;
	}	
	class ViewHolder{
		ImageView iv;
		TextView tv;
		void init(View v){
			iv = (ImageView) v.findViewById(R.id.imageView1);
			tv = (TextView) v.findViewById(R.id.gritextView1);
			
		}
	}	
}
