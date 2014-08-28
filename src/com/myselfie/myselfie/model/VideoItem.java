package com.myselfie.myselfie.model;

public class VideoItem {
	private int _id;
	private String _image;
	private String _desc;
	private Boolean _selected;

	public VideoItem() {
	};

	public VideoItem(int id,String image, String desc,Boolean selected) {
		this._id = id;
		this._image = image;
		this._desc = desc;
		this._selected = selected;
	};

	public int get_id() {
		return _id;
	}

	public String get_image() {
		return _image;
	}

	public String get_desc() {
		return _desc;
	}
	
	public Boolean get_selected() {
		return _selected;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void set_image(String _image) {
		this._image = _image;
	}

	public void set_desc(String _desc) {
		this._desc = _desc;
	}
	
	public void set_selected(Boolean _selected) {
		this._selected = _selected;
	}

}
