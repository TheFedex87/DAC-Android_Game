package it.thefedex87.dac.ui;

public class Box {
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Box(float x, float y, float width, float height) {
		setBox(x, y, width, height);
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float value) {
		x = value;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float value) {
		y -= value;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float value) {
		width = value;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float value) {
		height = value;
	}
	
	public void setBox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
