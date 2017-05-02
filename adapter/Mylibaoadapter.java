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
import com.yjn.gouzhao.Libaoinfo;
import com.yjn.task.Myimagetask;
import com.yjn.task.Myimagetask.ImageCallback;
import com.yjn.util.Cache;

public class Mylibaoadapter extends BaseAdapter {
	ArrayList<Libaoinfo> list;
	LayoutInflater inflater;
	Executor exe;
	Cache cache;
	Context context;
	
	

	public Mylibaoadapter(Context context) {
		inflater = inflater.from(context);
		exe = Executors.newFixedThreadPool(3);
		cache = Cache.getIntents();
		this.context = context;
	
	}

	public void setList(ArrayList<Libaoinfo> list) {
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
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.libaoitem, null);
			holder.init(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Libaoinfo libao = list.get(position);
		String s = libao.getContent();
		String content = s.replaceAll("[\t\r\n]", "");
		holder.content.setText(content);
		
		String name3 = libao.getName();
		holder.name.setText(name3);
		
		String remain3 = libao.getRemain();
		holder.rmain.setText(remain3);

		final String url = libao.getIcon();
		holder.iv.setTag(url);
		holder.iv.setImageResource(R.drawable.ic_360);
		Bitmap b = cache.getBitmap(url);
		if(b!=null){
			holder.iv.setImageBitmap(b);
		}else{

		new Myimagetask(new ImageCallback() {

			@Override
			public void Imageback(Bitmap result) {
				// TODO Auto-generated method stub
				if (holder.iv.getTag().toString().equals(url)) {
					holder.iv.setImageBitmap(result);
				}
				cache.add(url, result);
			}
		}).execute(libao.getIcon());}
		String remain = libao.getRemain();
		if(remain.equals("0")){
			holder.bt.setText("Ã‘∫≈");
			holder.bt.setTextSize(12);
			holder.bt.setBackgroundResource(R.drawable.orgenge);
			holder.bt.setTextColor(context.getResources().getColorStateList(R.drawable.textorgenge));
		}else{
			holder.bt.setText("√‚∑—¡Ï»°");
			holder.bt.setTextSize(12);
			holder.bt.setBackgroundResource(R.drawable.blu);
			holder.bt.setTextColor(context.getResources().getColorStateList(R.drawable.textblu));
		}
		
		
		
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView name;
		TextView rmain;
		TextView content;
		Button bt;

		void init(View v) {
			iv = (ImageView) v.findViewById(R.id.libaoimageView1);
			
			bt = (Button) v.findViewById(R.id.button1);
		
		
			name = (TextView) v.findViewById(R.id.youxiname);
			rmain = (TextView) v.findViewById(R.id.xiaoyourenshu);
			content = (TextView) v.findViewById(R.id.xiaoyouchuping);
		}
	}

		
		
	
}
