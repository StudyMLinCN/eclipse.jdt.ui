package p;

public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public int area() {
		int width= getWidth();
		int height= getHeight();
		return width*height;
	}
}
