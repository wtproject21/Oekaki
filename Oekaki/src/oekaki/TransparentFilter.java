package oekaki;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

/**
 * イメージの透かしフィルター
 */
class TransparentFilter extends RGBImageFilter {
	/**
	 * コンストラクタ
	 */
	public TransparentFilter() {
		canFilterIndexColorModel = true;
	}

	/**
	 * RGB ピクセルが#FFFFFFであれば透明に変更する。
	 *
	 * @param x 取り出し位置 X。
	 * @param y 取り出し位置 Y。
	 * @param rgb 変更前の RGB ピクセル。
	 * @return 変更後の RGB ピクセル。
	 */
	public int filterRGB(int x, int y, int rgb) {
		Color c = new Color(rgb);//rgbの取得
		if (c.getRed() != 255 || c.getGreen() != 255 || c.getBlue() != 255) {//#FFFFFF以外なら
			c = new Color(c.getRed(), c.getGreen(), c.getBlue(),255);//アルファ値を255に
		}
		else//それ以外なら
			c = new Color(0, 0, 0, 0);//透明色に
		return c.getRGB();//colorのrgbを返す
	}

	public Image changeimg(Image i, Toolkit tk) {//透明化するメソッド
		//imageにフィルタをかける
		Image transImage = tk.createImage(new FilteredImageSource(i.getSource(), new TransparentFilter()));
		return transImage;//作成したイメージを返す
	}
}
