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
import android.widget.TextView;

import com.exaple.aame.R;
import com.yjn.gouzhao.YouxiInfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Yuoxiadapter extends BaseAdapter {
	ArrayList<YouxiInfo>  list ;
	
	LayoutInflater inflater;
	Executor exe;
	Cache cache;
	
	
	public Yuoxiadapter(Context context){
		inflater = LayoutInflater.from(context);
		exe = Executors.newFixedThreadPool(3);
		cache = Cache.getIntents();
	}
	public void setList(ArrayList<YouxiInfo>  list){
		this.list = list;
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
			convertView = inflater.inflate(R.layout.youxiitem, null);
			holder = new ViewHolder();
			holder.init(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		YouxiInfo game = list.get(position);
		String name = game.getName();
		String name2 = name.replace("[\t\r\n]", "");
		//Log.i("=======","=======xiaoyouadapter"+name2);
		holder.desc.setText(name2);
		
		String click = game.getClick();
		String  click2= click.replace("[\t\r\n]", "");
		//Log.i("=======","=======xiaoyouadapter"+name2);
		holder.click.setText(click2);
		
		String desc = game.getGame_desc();
		String  desc2= desc.replace("[\t\r\n]", "");
		//Log.i("=======","=======xiaoyouadapter"+name2);
		holder.name.setText(desc2);
		
		
		
		final String icon = game.getIcon();
		holder.iv.setTag(icon);
		holder.iv.setImageResource(R.drawable.ic_360);
		Bitmap bit = cache.getBitmap(icon);
		if(bit!=null){
			holder.iv.setImageBitmap(bit);
		}else{
		new Myimagetask(new ImageCallback() {
			
			@Override
			public void Imageback(Bitmap result) {
				// TODO Auto-generated method stub
				if(holder.iv.getTag().toString().equals(icon)){
					holder.iv.setImageBitmap(result);
				}
				cache.add(icon, result);
			}
		}).execute(icon);
		}
		
		return convertView;
	}
	
	
	
	
	class ViewHolder{
		ImageView iv;
		
		TextView  name;
		TextView  click;
		TextView  desc;
		void init(View v){
			iv = (ImageView) v.findViewById(R.id.xiaoyoui);
			name = (TextView) v.findViewById(R.id.youxiname);
			click = (TextView) v.findViewById(R.id.xiaoyourenshu);
			desc = (TextView) v.findViewById(R.id.xiaoyouchuping);
		}
	}

}
