package oekaki;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * 色を取得するクラス
 * @author kusun
 *
 */
public class PixelColorTest {
    Color pixelcolor(int x,int y,Image image) {//指定された位置のcolorを返すメソッド
    	Color color;//colorの宣言
        try {

            //画像ファイルを読み込む
             BufferedImage img = PixelColorTest.createBufferedImage(image);

            //座標(x,y)の色を取得
            color = new Color(img.getRGB(x, y));

            //取得した色を標準出力
            System.out.println("R:" + color.getRed());
            System.out.println("G:" + color.getGreen());
            System.out.println("B:" + color.getBlue());
            System.out.println("A:" + color.getAlpha());

        } catch (Exception e) {//エラーが起きたとき実行
            e.printStackTrace();
            return null;//nullを返す
        }
        return color;//取得した色を返す
    }
    BufferedImage arufa(Image image) {//受け取ったイメージの#FFFFFFの色を透明化するメソッド
    	BufferedImage img;//bufferedimgeの宣言
        try {

            //画像ファイルを読み込む
             img = PixelColorTest.createBufferedImage(image);

            //color = new Color(img.getRGB(height, weight));
            for(int i=0;i<img.getHeight();i++) {//すべてのピクセルに対して
            	for(int j=0;j<img.getWidth();j++) {
            		Color c=new Color(img.getRGB(j, i));//カラーの取得
            		if (c.getRed() != 255 || c.getGreen() != 255 || c.getBlue() != 255) {//#FFFFFF以外なら
            			c = new Color(c.getRed(), c.getGreen(), c.getBlue(),255);//アルファ値を255にする
            			img.setRGB(j, i, c.getRGB());//色をセットする
            		}
            		else {//#FFFFFFなら
            			img.setRGB(j, i, 0x00000000);//透明にする
            		}
            			//c = new Color(200, 0, 0, 0);
            		//img.setRGB(j, i, c.getRGB());
            	}
            }
        } catch (Exception e) {//エラーが起きたとき実行
            e.printStackTrace();
            return null;//nullを返す
        }
        return img;//作成したイメージを返す
    }
    public static BufferedImage createBufferedImage(Image img) {//imageからbufferedimageに変換するメソッド
		BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);//bufferedimageの初期化

		Graphics g = bimg.getGraphics();//Graphicsの取得
		g.drawImage(img, 0, 0, null);//イメージの描画
		g.dispose();//Graphicsの破棄

		return bimg;//作成したイメージを返す
	}
}