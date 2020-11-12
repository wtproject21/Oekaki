package oekaki;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 *ブラシを表現するクラス
 * @author Tomochika Waku
 *
 */
public class Brush {
	int size;//サイズの宣言
	int freq=10;//頻度の設定
	int particleR=1;//粒の半径の設定
	double centerPar=0.5;//集積率の設定
	int brushnum=1;//brush番号の設定
	boolean mouse=false;//操作デバイスがマウスであるかどうか
	void paint(Graphics g,int x,int y) {//指定された位置にブラシの描画
		switch(brushnum) {//brushnumが
		case 1://1のとき
			if(size==0&&mouse) {//サイズが0でマウスならば
				Graphics2D g2=(Graphics2D)g;//Grphics2Dの取得
				g2.setStroke(new BasicStroke(1));//太さを1に設定
				g.drawLine(x, y, x, y);//点の描画
			}
			else {//それ以外なら
				g.fillOval(x-size/2, y-size/2, size, size);//丸を描画
			}
			break;
		case 2:
			randomPoint(g,x,y);//点をランダムに描画
			break;
		case 3:
			spray(g,x,y);//スプレーのように描画
			break;
		}
	}
	void randomPoint(Graphics g,int x,int y) {//点をランダムに描画
		for(int i=0;i<freq;i++) {//freqの数値の分
			double theta=Math.random()*Math.PI*2;//角度の指定
			double randx=Math.random();//横の長さの指定
			double randy=Math.random();//縦の長さの指定
			int dx=(int)(Math.cos(theta)*size/2*randx);//dxをsizeの円周内のランダムな位置に指定
			int dy=(int)(Math.sin(theta)*size/2*randy);//dyをsizeの円周内のランダムな位置に指定
			g.fillOval(x+dx-particleR, y+dy-particleR, particleR*2, particleR*2);//粒の描画
		}
	}
	void spray(Graphics g,int x,int y) {
		for(int i=0;i<freq;i++) {//freqの数値の分
			double theta=Math.random()*Math.PI*2;//角度の指定
			double randx=0,randy=0;//randx,randyの初期化
			double j=-2;//jの初期化
			while(true) {//永遠ループ
				if(Math.random()<gause(j*centerPar)) {//ガウス関数の確率で位置を決定
					randx=j;//randxをjに指定
					j=-2;//jの初期化
					break;
				}
				if(j>=2) {//もしｊが2以上になったとき
					randx=0;//randxを0に
					j=-2;//jの初期化
					break;
				}
				j+=0.125;//jをインクリメント
			}
			while(true) {//永遠ループ
				if(Math.random()<gause(j*centerPar)) {//ガウス関数の確率で位置を決定
					randy=j;//randyをjに指定
					j=-2;//jの初期化
					break;
				}
				if(j>=2) {//もしjが2以上になったとき
					randy=0;//randyを0に
					j=-2;//jの初期化
					break;
				}
				j+=0.125;//jをインクリメント
			}
			int dx=(int)(Math.cos(theta)*size/2.0*randx);//dxをガウス関数を使った位置に決定
			int dy=(int)(Math.sin(theta)*size/2.0*randy);//dxをガウス関数を使った位置に決定
			g.fillOval(x+dx-particleR, y+dy-particleR, particleR*2, particleR*2);//粒の描画
		}
	}

	double gause(double x) {//ガウス関数を返す
		return (1.0/Math.sqrt(2.0*Math.PI))*Math.pow(Math.E, x*x/(-2.0));
		//return Math.pow(1.0/Math.sqrt(2.0*Math.PI), x*x/(-2.0));
	}
}
