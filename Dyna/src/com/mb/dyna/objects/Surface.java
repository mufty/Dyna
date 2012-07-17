package com.mb.dyna.objects;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Surface extends Texture {
	public static String TYPE_NONE = "none";
	public static String TYPE_SOLID = "solid";
	private String type = TYPE_NONE;

	public Surface(FileHandle file) {
		super(file);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
