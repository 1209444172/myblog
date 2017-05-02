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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exaple.aame.R;
import com.yjn.gouzhao.MyInfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Myadapter extends BaseAdapter {
	ArrayList<MyInfo> list;
	LayoutInflater inflater;
	Executor exe;
	Cache cache;
	private Context context;
	public Myadapter(Context context){
		inflater = LayoutInflater.from(context);
		exe = Executors.newFixedThreadPool(3);
		cache = Cache.getIntents();
		this.context = context;
	}
	public void setList(ArrayList<MyInfo> list){
		this.list=list;
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
			convertView = inflater.inflate(R.layout.myitem,null);
			holder = new ViewHolder();
			holder.init(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		 MyInfo info = list.get(position);
		String Myname = info.getBname();
		holder.name.setText(Myname);
		
		final String icon = info.getBimg();
		holder.iv.setTag(icon);
		holder.iv.setImageResource(R.drawable.ic_360);
		Bitmap bit = cache.getBitmap(icon);
		if(bit!=null){
			holder.iv.setImageBitmap(bit);
		}else {
			new Myimagetask(new ImageCallback() {
				
				@Override
				public void Imageback(Bitmap result) {
					// TODO Auto-generated method stub
					if(holder.iv.getTag().toString().equals(icon)){
						holder.iv.setImageBitmap(result);
					}
					cache.getBitmap(icon);
				}
			}).execute(icon);
		}
		
		
		return convertView;
	}
	class ViewHolder{
		ImageView iv;
		TextView name;
		Button bt;
		Void init(View v){
			iv = (ImageView) v.findViewById(R.id.MyimageView1);
			name = (TextView) v.findViewById(R.id.MytextView1);
			bt = (Button) v.findViewById(R.id.button1);
			return null;
		}
		
		
	} 
}
