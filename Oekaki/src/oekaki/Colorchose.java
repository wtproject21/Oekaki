package oekaki;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
/**
 * 	色選択ウィンドウを表示するクラス
 * @author Tomochika Waku
 *
 */
public class Colorchose implements ActionListener,AdjustmentListener {
	Color color;//選択された色
	Panel pnl;//パネルの宣言
	Panel pnl2;//
	DrawWindow dw=null;//ウィンドウのクラスの宣言
	Scrollbar red;//スクロールバーの宣言
	Scrollbar green;//
	Scrollbar blue;//
	TextField txred;//テキストフィールドの宣言
	TextField txgreen;//
	TextField txblue;//
	Button b;//ボタンの宣言
	Label lb=null;//色を表示するラベル
	can ca;//色を表示するキャンバス
	Colorchose(){//コンストラクタ
		//スクロールバーの宣言とスクロールバーの監視を設定
		red=new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 255);red.addAdjustmentListener(this);
		green=new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 255);green.addAdjustmentListener(this);
		blue=new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 255);blue.addAdjustmentListener(this);
		//pnlに要素を追加
		pnl=new Panel();
		pnl.setLayout(new GridLayout(8,1));//pnlのレイアウトを8×1に設定
		pnl.add(new Label("red"));
		pnl.add(red);
		pnl.add(new Label("green"));
		pnl.add(green);
		pnl.add(new Label("blue"));
		pnl.add(blue);
		//テキストフィールドの宣言と監視の宣言
		txred=new TextField("red");txred.addActionListener(this);txred.setBackground(new Color(255,200,200));
		txgreen=new TextField("green");txgreen.addActionListener(this);txgreen.setBackground(new Color(200,255,200));
		txblue=new TextField("blue");txblue.addActionListener(this);txblue.setBackground(new Color(200,200,255));
		//pnl2に要素を追加
		pnl2=new Panel();
		pnl2.setLayout(new GridLayout(1,3));//pnl2のレイアウトを1×3に指定
		pnl2.add(txred);
		pnl2.add(txgreen);
		pnl2.add(txblue);
		//pnlにpnl2を追加
		pnl.add(pnl2);

		color=new Color(red.getValue(),green.getValue(),blue.getValue());//colorの初期化
		ca=new can(color);//色を表示するクラスの宣言
		pnl.add(ca);//pnlにcaを追加
		//決定ボタンの追加
		b=new Button("OK");b.addActionListener(this);
	}


	void choice() {//色選択ウィンドウの表示
		if(dw!=null)dw.dispose();//今ウィンドウが開いているなら閉じる
		dw=new DrawWindow("色を選択してください",500,300);//ウィンドウの作成
		dw.makeLayout(2, null);//dwのレイアウトをボーダーレイアウトに指定
		dw.add(pnl,"Center");//パネルを上に
		dw.add(b,"South");//ボタンを下に
		dw.setVisible(true);//ウィンドウの表示
	}


	//テキストフィールド、ボタンの監視
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();//イベントが起きた場所をobjに代入
		if(obj==txred) {//txredでイベントが起きたなら
			try {
				red.setValue(Integer.parseInt(txred.getText()));//redを入力された値にする
			} catch (Exception e2) {//上の処理でエラーが起きたなら
				System.out.println("erorr");//errorを表示
			}
		}
		if(obj==txgreen) {//txgreenでイベントが起きたなら
			try {
				green.setValue(Integer.parseInt(txgreen.getText()));//greenを入力された値にする
			} catch (Exception e2) {//上の処理でエラーが起きたなら
				System.out.println("erorr");//errorを表示
			}
		}
		if(obj==txblue) {//txblueでイベントが起きたなら
			try {
				blue.setValue(Integer.parseInt(txblue.getText()));//blueを入力された値にする
			} catch (Exception e2) {//上の処理でエラーが起きたなら
				System.out.println("erorr");//errorを表示
			}
		}
		color=new Color(red.getValue(),green.getValue(),blue.getValue());//colorを入力された値に変更
		ca.c=color;//表示する色の変更
		ca.repaint();//再描画
		if(lb!=null)lb.setBackground(color);//lbが渡されていたならその背景を選択した色に変更
		if(obj==b) {//決定が押されたなら
			dw.dispose();//ウィンドウを閉じる
		}
	}
	//スクロールバーの監視
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		Object obj=e.getSource();//イベントが起きた場所をobjに代入
		if(obj==red) {//redでイベントが起きたなら
			txred.setText(String.valueOf(red.getValue()));//txredを入力された値にする
		}
		if(obj==green) {//greenでイベントが起きたなら
			txgreen.setText(String.valueOf(green.getValue()));//txgreenを入力された値にする
		}
		if(obj==blue) {//blueでイベントが起きたなら
			txblue.setText(String.valueOf(blue.getValue()));//txblueを入力された値にする
		}
		color=new Color(red.getValue(),green.getValue(),blue.getValue());//colorを入力された値に変更
		ca.c=color;//表示する色の変更
		ca.repaint();//再描画
		if(lb!=null)lb.setBackground(color);//lbが渡されていたならその背景を選択した色に変更
	}
}
//色を表示するキャンバス
class can extends Canvas{
	Color c;//表示する色
	can(Color c){
		this.setSize(20, 20);//サイズを20×20に変更
		this.c=c;//渡されたcolorでcを初期化
	}
	//再描画で呼び出されるメソッド
	public void update(Graphics g) {
		paint(g);
	}
	//描画するメソッド
	public void paint(Graphics g) {
		Dimension d=getSize();//サイズの取得
		g.setColor(c);//色をcに変更
		g.fillRect(0, 0, d.width, d.height);//全体を塗りつぶし
	}

}