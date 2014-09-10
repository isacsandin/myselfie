package com.myselfie.myselfie.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myselfie.myselfie.R;
import com.myselfie.myselfie.model.VideoItem;
import com.myselfie.myselfie.utils.Utils;

@SuppressLint("InflateParams")
public class MasterDetailAdapter extends ArrayAdapter<VideoItem> {

	private List<VideoItem> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;

	public MasterDetailAdapter(Activity context, List<VideoItem> lst) {
		super(context, R.layout.row_masterdetail, lst);
		this._context = context;
		this._list = lst;

		_inflater = this._context.getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_masterdetail, null);
		}

		Utils.setFontAllView(parent);

		VideoItem vidItem = _list.get(position);
		Integer id = vidItem.get_id();

		view.setId(id);
		TextView tvDesc = (TextView) view.findViewById(R.id.text_desc);
		ImageView iv = (ImageView) view.findViewById(R.id.image);

		view.setId(id);
		tvDesc.setText(vidItem.get_desc());

		Bitmap bmp = Utils.GetImageFromAssets(this._context, "images/"
				+ vidItem.get_image());
		iv.setImageBitmap(bmp);

		return view;
	}
}
