package io.aleosiss.sts.character.marisa.model;

public class ScreenPosition {
	public float x, y;

	private ScreenPosition(float x, float y) {
		this.x = x;
		this.y = y;
	};

	public static ScreenPosition of(float x, float y) {
		return new ScreenPosition(x, y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
