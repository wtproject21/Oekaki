package oekaki;



import java.awt.Canvas;
import java.awt.Component;

import jpen.PButtonEvent;
import jpen.PKindEvent;
import jpen.PLevel.Type;
import jpen.PLevelEvent;
import jpen.PScrollEvent;
import jpen.event.PenListener;
import jpen.owner.multiAwt.AwtPenToolkit;
/**
 * 筆圧取得用のクラス
 * @author Tomochika Waku
 *
 */
public class CanvasListener implements PenListener{
	float value=0;//筆圧
	int mode=1;//モード
	float a=10;//シグモイド関数の定数
	Canvas can=null;//canvas
	boolean mouse=false;//マウスであるかどうか
	void add(Component c) {
		AwtPenToolkit.addPenListener(c, this);//筆圧を取得するコンポーネントの追加
	}
	void remove(Component c) {
		AwtPenToolkit.removePenListener(c, this);//コンポーネントの削除
	}

	//ボタンを押されたときのイベント
	//今回は使用しない
	@Override
	public void penButtonEvent(PButtonEvent ev) {

	}
	//デバイスが変更されたときのイベント
	//今回は使用しない
	@Override
	public void penKindEvent(PKindEvent ev) {

	}
	//シグモイド関数を返す
	float sigmoid(float a,float x) {
		return (float)(1+Math.tanh(a*x/2))/2;
	}
	//状態が変化したときのイベント
	//筆圧が変更されたとき呼び出される
	@Override
	public void penLevelEvent(PLevelEvent ev) {
		if(ev.getDevice().getName()=="Mouse") {//操作しているデバイスがマウスの時
			value=0.5f;//valueを0.5fに
			mouse=true;//mouseをtrueに
		}else {//それ以外なら
			mouse=false;//mouseをfalseに
			if(mode==0) {//modeが0ならば
				value=0.5f;//valueをtrueに
			}else if(mode==1) {//modeが1ならば
				value=ev.pen.getLevelValue(Type.PRESSURE);//筆圧の取得
			}else if(mode==2) {//modeが2ならば
				value=ev.pen.getLevelValue(Type.PRESSURE);//筆圧の取得
				value*=value;//二次関数
			}else if(mode==3) {//modeが3ならば
				value=sigmoid(a,ev.pen.getLevelValue(Type.PRESSURE)-0.5f);//シグモイド関数上で指定
			}
			//if(can!=null) {
				//can.repaint();
			//}
		}
	}
	//移動したときに発生するイベント
	//今回は利用しない
	@Override
	public void penScrollEvent(PScrollEvent ev) {
		// TODO 自動生成されたメソッド・スタブ

	}
	//ラグを表示するイベント
	//今回は利用しない
	@Override
	public void penTock(long ev) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
