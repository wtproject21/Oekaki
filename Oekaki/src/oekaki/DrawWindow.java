package oekaki;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
/**
 * ×ボタンで消せるようにしたフレーム
 * @author Tomochika Waku
 *
 */
public class DrawWindow extends Frame{
	Panel pnl;//パネルの宣言
	Button bt1;//ボタンの宣言
	// GridBagLayout マネージャーの生成
    GridBagLayout      gBag = new GridBagLayout();
    // 格納方法を指定するクラスの生成
    GridBagConstraints gCon = new GridBagConstraints();
    TestWindowAdapter testWindowAdapter;//×ボタンの監視を行う
   	DrawWindow(int width,int height){//コンストラクタ
   		super("None");//表示される名前をNoneに
   	    this.setSize(width,height);//サイズを指定されたサイズに
   	    //this.setVisible(true);
   	    testWindowAdapter = new TestWindowAdapter();//クラスの宣言
   	    this.addWindowListener(testWindowAdapter);//×ボタンを監視するように指定
   	    //this.setVisible(true);
   	}
   	DrawWindow(String s,int width,int height){
   		super(s);//指定した名前を設定
   	    this.setSize(width,height);//サイズを指定されたサイズに
   	    //this.setVisible(true);
   	    testWindowAdapter = new TestWindowAdapter();//クラスの宣言
   	    this.addWindowListener(testWindowAdapter);//×ボタンを監視するように指定
   	    //this.setVisible(true);
   	}
   	DrawWindow(int x,int y,int width,int height){
   		super("None");
   	    this.setBounds(x, y, width, height);//サイズを指定されたサイズに,位置を指定した位置に
   	    this.setVisible(true);//ウィンドウの表示
   	    testWindowAdapter = new TestWindowAdapter();//クラスの宣言
   	    this.addWindowListener(testWindowAdapter);//×ボタンを監視するように指定
   	}
   	void master() {
   		testWindowAdapter.b=true;//このウィンドウの×ボタンが押されたらプログラムを終了するように設定
   	}

	/** レイアウトを変更するためのメソッド
	 * <p>
	 * フローレイアウトは左上から右下に詰めて配置する。<br>
	 * n=null:左上から配置<br>
	 * n[0]:方角を指定 0:LEFT 1:CENTER 2:RIGHT 3:Leading 4:Traing<br>
	 * n[1],n[1]:縦、横の個数指定<br>
	 * <br>
	 * ボーダーレイアウトは上中下左右に配置する。<br>
	 * n=null:間隔を詰めて配置<br>
	 * n[0],n[1]:間隔の設定(h,v)<br>
	 * <br>
	 * グリッドレイアウトは格子に分割して左上から順に配置する<br>
	 * n=null:1行につき1つの部品で並べる<br>
	 * n[0],n[1]:縦、横の個数設定<br>
	 * n[2],n[3]:縦、横の間隔設定<br>
	 * <br>
	 * グリッドバッグレイアウトは配置を決めて表示する。<br>
	 * gBagはこのクラスのみなので汎用性はない<br>
	 * 別途gConを使用して配置する必要あり<br>
	 * <br>
	 * カードレイアウトは入れたものをサタックに入れて表示させる。<br>
	 * n=null:一枚だけ表示（一番上だけ見える状態）<br>
	 * n[0],n[1]:縦、横の個数設定
	 * </p>
	 * @param f レイアウトを変更したいFrame
	 * @param mode 0:絶対位置,1:フローレイアウト,2:ボーダーレイアウト,3:グリッドレイアウト,4:グリッドバッグレイアウト,5:カードレイアウト
	 * @param n 引数の数に応じてレイアウトを変更させる。n=nullで要素数0
	 */

	void makeLayout(Frame f,int mode,int... n) {
		LayoutManager lm=null;//レイアウトの初期化
		if(n==null)n=new int[0];//nがnullなら要素数0の整数型の配列に
		switch(mode) {//モードが
		case 0://0のとき
			lm=null;//lmをnullに
			break;
		case 1://1のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new FlowLayout();//左上から配置
				break;
			case 1://1のとき
				lm=new FlowLayout(n[0]);//指定された方角へ配置
				break;
			case 3://3のとき
				lm=new FlowLayout(n[0],n[1],n[2]);//さらに縦横の個数指定
				break;
			}
			break;
		case 2://2のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new BorderLayout();//上下中央左右に配置できる
				break;
			case 2://2のとき
				lm=new BorderLayout(n[0],n[1]);//間隔を指定
				break;
			}
			break;
		case 3://3のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new GridLayout();//1行につき1部品で配置
				break;
			case 2:
				lm=new GridLayout(n[0],n[1]);//縦横の個数指定
				break;
			case 4:
				lm=new GridLayout(n[0],n[1],n[2],n[3]);//さらに縦横の間隔指定
				break;
			}
			break;
		case 4://4のとき
			lm=gBag;//グリッドバッグレイアウトに指定
			break;
		case 5://5のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new CardLayout();//上に1枚だけ表示
				break;
			case 2://2のとき
				lm=new CardLayout(n[0],n[1]);//縦横の個数設定
				break;
			}
			break;
		}
		f.setLayout(lm);//レイアウトを指定
	}
	/** レイアウトを変更するためのメソッド
	 * <p>
	 * フローレイアウトは左上から右下に詰めて配置する。<br>
	 * n=null:左上から配置<br>
	 * n[0]:方角を指定 0:LEFT 1:CENTER 2:RIGHT 3:Leading 4:Traing<br>
	 * n[1],n[1]:縦、横の個数指定<br>
	 * <br>
	 * ボーダーレイアウトは上中下左右に配置する。<br>
	 * n=null:間隔を詰めて配置<br>
	 * n[0],n[1]:間隔の設定(h,v)<br>
	 * <br>
	 * グリッドレイアウトは格子に分割して左上から順に配置する<br>
	 * n=null:1行につき1つの部品で並べる<br>
	 * n[0],n[1]:縦、横の個数設定<br>
	 * n[2],n[3]:縦、横の間隔設定<br>
	 * <br>
	 * グリッドバッグレイアウトは配置を決めて表示する。<br>
	 * gBagはこのクラスのみなので汎用性はない<br>
	 * 別途gConを使用して配置する必要あり<br>
	 * <br>
	 * カードレイアウトは入れたものをサタックに入れて表示させる。<br>
	 * n=null:一枚だけ表示（一番上だけ見える状態）<br>
	 * n[0],n[1]:縦、横の個数設定
	 * </p>
	 * @param mode 0:絶対位置,1:フローレイアウト,2:ボーダーレイアウト,3:グリッドレイアウト,4:グリッドバッグレイアウト,5:カードレイアウト
	 * @param n 引数の数に応じてレイアウトを変更させる。n=nullで要素数0
	 */
	void makeLayout(int mode,int... n) {
		LayoutManager lm=null;//レイアウトの初期化
		if(n==null)n=new int[0];//nがnullなら要素数0の整数型の配列に
		switch(mode) {//モードが
		case 0://0のとき
			lm=null;//lmをnullに
			break;
		case 1://1のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new FlowLayout();//左上から配置
				break;
			case 1://1のとき
				lm=new FlowLayout(n[0]);//指定された方角へ配置
				break;
			case 3://3のとき
				lm=new FlowLayout(n[0],n[1],n[2]);//さらに縦横の個数指定
				break;
			}
			break;
		case 2://2のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new BorderLayout();//上下中央左右に配置できる
				break;
			case 2://2のとき
				lm=new BorderLayout(n[0],n[1]);//間隔を指定
				break;
			}
			break;
		case 3://3のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new GridLayout();//1行につき1部品で配置
				break;
			case 2:
				lm=new GridLayout(n[0],n[1]);//縦横の個数指定
				break;
			case 4:
				lm=new GridLayout(n[0],n[1],n[2],n[3]);//さらに縦横の間隔指定
				break;
			}
			break;
		case 4://4のとき
			lm=gBag;//グリッドバッグレイアウトに指定
			break;
		case 5://5のとき
			switch(n.length) {//nの大きさが
			case 0://0のとき
				lm=new CardLayout();//上に1枚だけ表示
				break;
			case 2://2のとき
				lm=new CardLayout(n[0],n[1]);//縦横の個数設定
				break;
			}
			break;
		}
		this.setLayout(lm);//自身のレイアウトを変更
	}
}
//ウィンドウを監視するクラス
class TestWindowAdapter extends java.awt.event.WindowAdapter{
	Boolean b=false;//マスターかどうか
	public void windowClosing(java.awt.event.WindowEvent event){//×ボタンが押されたとき
	event.getWindow().setVisible(false);//イベントのあったフレーム閉じる
	event.getWindow().dispose();//イベントのあったフレーム破棄
	if(b)System.exit(0);//マスターならプログラムを終了する
	}
}
