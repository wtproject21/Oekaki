package oekaki;

import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
/**
 * 設定を行うクラス
 * @author Tomochika Waku
 *
 */
public class MySetting implements ActionListener,ItemListener{
	DrawWindow dw=null;//ウィンドウの宣言
	Checkbox cb1,cb2,cb3;//チェックボックスの宣言
	TextField tf1,tf2,tf3;//テキストフィールドの宣言
	Brush b=null;//ブラシの宣言
	MySetting(){//コンストラクタ
		cb1=new Checkbox("G-Pen",null,true);//G-penを初期値trueで初期化
		cb2=new Checkbox("Pencil");//初期化
		cb3=new Checkbox("Spray");//初期化
		cb1.addItemListener(this);//監視の設定
		cb2.addItemListener(this);//
		cb3.addItemListener(this);//
		tf1=new TextField();//初期化
		tf2=new TextField();//
		tf3=new TextField();//
		tf1.addActionListener(this);//監視の設定
		tf2.addActionListener(this);//
		tf3.addActionListener(this);//

	}
	void FreeHandSetting() {//FreeHandの設定画面を開く
		if(dw!=null)dw.dispose();//ウィンドウが開いているなら閉じる
		Panel pnl1=new Panel();//パネルの初期化
		Panel pnl2=new Panel();//
		//Panel pnl3=new Panel();//

		pnl1.setLayout(new GridLayout(3,1));//pnl1のレイアウトを3×1に指定
		pnl1.add(cb1);//チェックボックスの追加
		pnl1.add(cb2);//
		pnl1.add(cb3);//

		pnl2.setLayout(new GridLayout(3,2));//pnl2のレイアウトを3×2に指定
		pnl2.add(new Label("Frequency"));//ラベルの追加
		tf1.setText(String.valueOf(b.freq));pnl2.add(tf1);//最初のテキストを指定して追加
		pnl2.add(new Label("粒の半径"));//ラベルの追加
		tf2.setText(String.valueOf(b.particleR));pnl2.add(tf2);//最初のテキストを指定して追加
		pnl2.add(new Label("集積率"));//ラベルの追加
		tf3.setText(String.valueOf(b.centerPar));pnl2.add(tf3);//最初のテキストを指定して追加

		//pnl3.setLayout(new GridLayout(1,3));
		//pnl3.add(cb3);


		dw=new DrawWindow("FreeHandSetting",400,200);//ウィンドウの設定
		dw.setLayout(new GridLayout(1,3));//レイアウトの指定
		dw.add(pnl1);//ウィンドウに要素を追加
		dw.add(pnl2);//ウィンドウに要素を追加
		//dw.add(pnl3);
		dw.setAlwaysOnTop(true);//常に前にあるように指定
		dw.setVisible(true);//ウィンドウの表示

	}
	//テキストフィールドの監視
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj=e.getSource();//イベントが起きたところを入れる
		if(obj==tf1) {//tf1でイベントが起きた時
			try {
				b.freq=Integer.parseInt(tf1.getText());//freqに整数型で入れる
			} catch (NumberFormatException e1) {//エラーが起きたとき実行
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
		if(obj==tf2) {//tf2でイベントが起きたとき
			try {
				b.particleR=Integer.parseInt(tf2.getText());//particleRに整数型で入れる
			} catch (NumberFormatException e1) {//エラーが起きたとき実行
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
		if(obj==tf3) {//tf3でイベントが起きたとき
			try {
				b.centerPar=Double.parseDouble(tf3.getText());//centerParに実数型で入れる
			} catch (NumberFormatException e1) {//エラーが起きたとき実行
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
	}
	//チェックボックスの監視
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj=e.getSource();//イベントが起きた場所をいれる
		if(obj==cb1) {//cb1でイベントが起きたとき
			cb1.setState(true);//cb1をtrueに
			cb2.setState(false);//cb2をfalseに
			cb3.setState(false);//cb3をfalseに
			b.brushnum=1;//brushnumを1に
		}
		if(obj==cb2) {
			cb1.setState(false);//cb1をfalseに
			cb2.setState(true);//cb2をtrueに
			cb3.setState(false);//cb3をfalseに
			b.brushnum=2;//brushnumを2に
		}
		if(obj==cb3) {
			cb1.setState(false);//cb1をfalseに
			cb2.setState(false);//cb2をfalseに
			cb3.setState(true);//cb3をtrueに
			b.brushnum=3;//brushnumを3に
		}
	}

}
