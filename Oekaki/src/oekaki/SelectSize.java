package oekaki;

import java.awt.Button;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
/**
 * サイズを変更するクラス
 * @author Tomochika Waku
 *
 */
public class SelectSize implements ActionListener,AdjustmentListener{
	DrawWindow dw;//ウィンドウの宣言
	Scrollbar size;//スクロールバーの宣言
	TextField txsize;//テキストフィールドの宣言
	Button b;//ボタンの宣言
	SelectSize(){//コンストラクタ
		size=new Scrollbar(Scrollbar.HORIZONTAL, 1, 10, 1, 50);//スクロールバーを平行、初期値1バーの大きさ10、範囲1~50で初期化
		size.addAdjustmentListener(this);//スクロールバーの監視
		txsize=new TextField("size");//テキストフィールドの初期化
		txsize.addActionListener(this);//テキストフィールドの監視
		b=new Button("OK");b.addActionListener(this);//ボタンの初期化、監視
	}

	void select() {//サイズ選択ウィンドウを表示するメソッド
		dw=new DrawWindow("サイズを指定します",400,300);//ウィンドウの初期化
		dw.makeLayout(2, null);//レイアウトをボーダーレイアウトに指定
		dw.add(size,"North");//sizeを上に
		dw.add(txsize,"Center");//txsizeを真ん中に
		dw.add(b,"South");//bを下に配置
		dw.setVisible(true);//dwを表示
	}
	//スクロールバーの監視
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj=e.getSource();//イベントが起きた場所を入れる
		if(obj==size) {//イベントがsizeで起きたなら
			txsize.setText(String.valueOf(size.getValue()));//txsizeに値を入れる
		}
	}
	//テキストフィールド、ボタンの監視
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj=e.getSource();//イベントが起きた場所を入れる
		if(obj==txsize) {//txsizeでイベントが起きたとき
			try {
				size.setValue(Integer.parseInt(txsize.getText()));//sizeに整数型で入れる
			} catch (Exception e2) {//エラーが起きたとき実行
				// TODO: handle exception
				System.out.println("Error");
			}
		}
		if(obj==b) {//bでイベントが起きたとき
			dw.dispose();//ウィンドウを消す
		}
	}

}
