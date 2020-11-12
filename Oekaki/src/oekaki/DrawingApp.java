package oekaki;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import javax.imageio.ImageIO;
/**
 * ペイントソフト "Oekaki" メインクラス
 * @author Tomochika Waku
 *
 */
public class DrawingApp implements ActionListener, AdjustmentListener,MouseMotionListener {
	// ■ フィールド変数
	Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11,bt12; // フレームに配置するボタンの宣言
	Label serect;//押されたボタンを表示するラベルの宣言
	Panel pnl; // ボタン配置用パネルの宣言
	Colorchose ch = new Colorchose();//色設定を行うクラスの宣言、初期化
	MyCanvas mc; // 別途作成した MyCanvas クラス型の変数の宣言
	MenuItem menuitem1;//メニューアイテムの宣言
	MenuItem menuitem2;
	MenuItem menuitem3;
	MenuItem menuitem4;
	Label lb;//現在の色を表示するためのラベル
	Color co;//現在の色のColorクラス
	MenuBars mbs;//メニュバーを一括管理するクラスの宣言
	SelectSize ss = new SelectSize();//サイズを選択するクラスの宣言、初期化
	MyDropTarget mdt=new MyDropTarget();//ドラッグアンドドロップ用のクラスの宣言、初期化
	DrawWindow dw;//このウィンドウを表示するためのクラスの宣言
	DrawWindow panels;//ボタンを別ウィンドウに表示するためのクラスの宣言（未実装）
	Button b=new Button();//移動させたボタンのクラス（未実装）
	MySetting ms;//設定用クラスの宣言

	// ■ main メソッド（スタート地点）
	public static void main(String[] args) {
		new DrawingApp();
	}

	// ■ コンストラクタ
	DrawingApp() {

		//super("Drawing Appli");
		dw = new DrawWindow("Oekaki", 1000, 600);//ウィンドウを作成

		ms=new MySetting();//設定用クラスの初期化

		panels=new DrawWindow("",300,300);//別ウィンドウを作成（未実装）
		panels.setLayout(new FlowLayout(1));

		pnl = new Panel(); // Panel のオブジェクト（実体）を作成
		mc = new MyCanvas(this); // mc のオブジェクト（実体）を作成

		mbs = new MenuBars();//メニュバーを一括管理するクラスの宣言
		mbs.setMenu("File", "Edit","Setting");//メニュバーのメニューを設定
		mbs.setItem("File", "New", "Open", "Save");//メニューのアイテムを設定
		mbs.setItem("Edit", "Undo","Redo");//
		mbs.setItem("Window", "FreeHand");//
		mbs.setItem("Setting", "FreeHand");//
		mbs.set();//設定したアイテムを決定する
		menuitem1 = mbs.getItem("File", "New");//メニューアイテムを置き換える
		menuitem2 = mbs.getItem("File", "Open");//
		menuitem3 = mbs.getItem("File", "Save");//
		menuitem4 = mbs.getItem("Edit", "Undo");//
		mbs.addActionListener(this);//mbs内のメニューアイテムすべてをこのActionListnerに追加
		dw.setMenuBar(mbs);//ウィンドウにメニューバーを設定する

		dw.setLayout(new BorderLayout(10, 10)); // レイアウト方法の指定
		dw.add(pnl, BorderLayout.WEST); // 右側に パネルを配置
		dw.add(mc, BorderLayout.CENTER); // 左側に mc （キャンバス）を配置
		// BorerLayout の場合，West と East の幅は
		// 部品の大きさで決まる，Center は West と East の残り幅
		pnl.setLayout(new GridLayout(15, 1)); // ボタンを配置するため，15行１列のグリッドをパネル上にさらに作成
		//ボタン、ラベルを順に追加
		serect = new Label("MODE");//選択したモードを表示するラベルの初期化
		pnl.add(serect);//順に配置
		bt11 = new Button("Eraser");//消しゴムに変更するボタン
		bt11.addActionListener(this);//ActionListenerに追加
		pnl.add(bt11);//順に追加:下記も同様
		bt1 = new Button("Free Hand");//手描き
		bt1.addActionListener(this);
		bt1.addMouseMotionListener(this);
		pnl.add(bt1);
		bt2 = new Button("Line");//線の描画
		bt2.addActionListener(this);
		pnl.add(bt2);
		bt3 = new Button("Rectangle");//四角の描画
		bt3.addActionListener(this);
		pnl.add(bt3);
		bt4 = new Button("Oval");//円の描画
		bt4.addActionListener(this);
		pnl.add(bt4);
		bt5 = new Button("fillRect");//塗りつぶした四角の描画
		bt5.addActionListener(this);
		pnl.add(bt5);
		bt6 = new Button("fillOval");//塗りつぶした円の描画
		bt6.addActionListener(this);
		pnl.add(bt6);
		bt7 = new Button("Delete");//全消し
		bt7.addActionListener(this);
		pnl.add(bt7);
		bt8 = new Button("Save");//イメージの保存
		bt8.addActionListener(this);
		pnl.add(bt8);
		bt9 = new Button("Color");//色の設定ウィンドウを開く
		bt9.addActionListener(this);
		pnl.add(bt9);
		bt10 = new Button("Size");//サイズ変更ウィンドウを開く
		bt10.addActionListener(this);
		pnl.add(bt10);
		bt12=new Button("Fill");//塗りつぶし
		bt12.addActionListener(this);
		pnl.add(bt12);
		lb = new Label();//現在の色のラベルの初期化
		co = new Color(0, 0, 0);//現在の色のColorクラスの初期化
		lb.setBackground(co);//ラベルの背景色を現在の色にする
		pnl.add(lb);//パネルに追加
		mc.lb = lb;//色変更用にmcにラベルを渡す

		mc.ch=ch;//色変更用にmcにchを渡す
		mc.ss=ss;//サイズ変更用にmcにssを渡す
		ch.lb=lb;//色変更用にchにlbを渡す
		mc.setDropTarget(mdt);//ドラッグアンドドロップ用のクラスを設定
		mdt.mc=mc;//キャンバス上で受け取れるようにmdtにmcを渡す
		ms.b=mc.brush;//設定用にmcのbrushをmsに渡す


		dw.master();//dwを消すとプログラムが終了するように変更
		dw.setVisible(true);//dwを表示する
	}

	// ■ メソッド
	// ActionListener を実装しているため、例え内容が空でも必ず記述しなければならない
	public void actionPerformed(ActionEvent e) { // フレーム上で生じたイベントを e で取得
		if (e.getSource() == bt1) { // もしイベントが bt1 で生じたなら
			mc.mode = 9; // モードを１に
			serect.setText(bt1.getLabel());//ラベルを変更
		} else if (e.getSource() == bt2) { // もしイベントが bt2 で生じたなら
			mc.mode = 2; // モードを２に
			serect.setText(bt2.getLabel());//ラベルを変更
		} else if (e.getSource() == bt3) { // もしイベントが bt3 で生じたなら
			mc.mode = 3; // モードを３に
			serect.setText(bt3.getLabel());//ラベルを変更
		} else if (e.getSource() == bt4) { // もしイベントが bt4 で生じたなら
			mc.mode = 4; // モードを４に
			serect.setText(bt4.getLabel());//ラベルを変更
		} else if (e.getSource() == bt5) { // もしイベントが bt5 で生じたなら
			mc.mode = 5; // モードを5に
			serect.setText(bt5.getLabel());//ラベルを変更
		} else if (e.getSource() == bt6) { // もしイベントが bt6 で生じたなら
			mc.mode = 6; // モードを6に
			serect.setText(bt6.getLabel());//ラベルを変更
		} else if (e.getSource() == bt7) {//もしイベントがbt7で生じたなら
			mc.mode = 0;//モードを0に
			mc.img = null;//イメージの消去
			mc.gc = null;//Graphicsの消去
			mc.repaint();//再描画
			serect.setText(bt7.getLabel());//ラベルを変更
		} else if (e.getSource() == bt8 || e.getSource() == menuitem3) {//もしイベントがbt8かmenuitem3で生じたなら
			try {
				FileDialog fd = new FileDialog(dw, "ファイルを保存します", FileDialog.SAVE);//ファイル保存ダイアログを開く
				fd.setFile("test");//初期値にtestを設定
				fd.setVisible(true);//fdを表示
				String fileAdd = null;//拡張子用の文字列
				if (fd.getFile() == null)//何も入力がなければ
					return;//中断する
				for (int i = fd.getFile().length() - 1; i >= 0; i--) {//文字列の後ろから最初まで
					if (fd.getFile().charAt(i) == '.') {//もし"."があれば
						fileAdd = fd.getFile().substring(i + 1);//そこからの拡張子を取得
					}
				}
				if (fileAdd == null) {//もし拡張子がないなら
					fileAdd = "png";//拡張子をpngに設定
					fd.setFile(fd.getFile() + ".png");//fdのファイル名に".png"を追加
				}
				ImageIO.write(mc.cleared(), fileAdd, new File(fd.getDirectory() + fd.getFile()));//ファイルとして保存
				System.out.println("出力されました");//システム確認用
			} catch (IOException e1) {//エラーが起きた場合実行される
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		} else if (e.getSource() == bt9) {//もしイベントがbt9で起きた場合
			ch.choice();//色選択ウィンドウを表示
		} else if (e.getSource() == bt10) {//もしイベントがbt10で起きた場合
			ss.select();//サイズ選択ウィンドウを表示
		} else if (e.getSource() == bt11) {//もしイベントがbt11で起きた場合
			mc.mode = 7;//モードを7に変更
			serect.setText(bt11.getLabel());//ラベルを変更
		}else if(e.getSource()==bt12) {//もしイベントがbt12で起きた場合
			mc.mode=8;//モードを8に変更
			serect.setText(bt12.getLabel());//ラベルを変更
		}else if (e.getSource() == menuitem1) {//もしイベントがmenuitem1で起きた場合
			new DrawingApp();//新しくウィンドウを開く
		} else if (e.getSource() == menuitem2) {//もしイベントがmenuitem2で起きた場合
			try {
				FileDialog fd = new FileDialog(dw, "ファイルを開きます", FileDialog.LOAD);//ファイル選択ウィンドウを開く
				fd.setFile("test");//初期値にtestを設定
				fd.setVisible(true);//fdを表示
				if (fd.getFile() == null)//もし入力がなければ
					return;//中断する
				BufferedImage img = ImageIO.read(new File(fd.getDirectory() + fd.getFile()));//ファイルからイメージを開く
				mc.img = mc.createImage(mc.getWidth(), mc.getHeight());//イメージを初期化する
				mc.gc = mc.img.getGraphics();//Graphicsを初期化する
				mc.gc.setColor(new Color(255,255,255));//背景色を設定
				mc.gc.fillRect(0, 0, mc.getWidth(), mc.getHeight());//画面全体を塗りつぶし
				mc.gc.drawImage(img, 0, 0, null);//読み込んだイメージを描画
				mc.mode = 0;//modeを0に
				mc.repaint();//再描画
				for(int i=0;i<mc.redo.length;i++) {//redoすべてを
					mc.redo[i]=null;//初期化
				}
				for(int i=0;i<mc.undo.length;i++) {//undoすべてを
					mc.undo[i]=null;//初期化
				}
			} catch (IOException e1) {//エラーが発生したときに実行される
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		} else if (e.getSource() == menuitem4) {//もしイベントがmenuitem4で起きた場合
			if (mc.undo[0] != null) {//もしundoがあるならば
				int tmp = mc.mode;//モードを保存
				Graphics ub;//仮のGraphicsの宣言
				for (int i = mc.redo.length - 1; i >= 1; i--) {//最後から最初の一つあとまで
					if (mc.redo[i - 1] != null) {//もし一つ前が存在するなら
						mc.redo[i] = mc.createImage(mc.d.width, mc.d.height);//初期化
						ub = mc.redo[i].getGraphics();//Graphicsの取得
						ub.drawImage(mc.redo[i - 1], 0, 0, null);//ひとつ前の画像を描画
					}
				}
				mc.redo[0] = mc.createImage(mc.d.width, mc.d.height);//最初の画像を初期化
				ub = mc.redo[0].getGraphics();//Graphicsを取得
				ub.setColor(new Color(255,255,255));//背景色を設定
				ub.fillRect(0, 0, mc.getWidth(), mc.getHeight());//画面全体を塗りつぶし
				ub.drawImage(mc.img, 0, 0, null);//現在の画像で描画

				Graphics gc = mc.img.getGraphics();//Graphicsを取得
				gc.drawImage(mc.undo[0], 0, 0, null);//undoの最初を描画
				mc.mode = 0;//モードを0に
				mc.repaint();//再描画
				Graphics u;//仮のGraphicsの宣言
				for (int i = 0; i < mc.undo.length - 2; i++) {//最初から最後の一つ前まで
					if (mc.undo[i + 1] != null) {//もし一つ後が存在するなら
						mc.undo[i] = mc.createImage(mc.getWidth(), mc.getHeight());//初期化
						u = mc.undo[i].getGraphics();//Graphicsの取得
						u.drawImage(mc.undo[i + 1], 0, 0, null);//一つ後の画像を描画
					}
				}
				mc.undo[mc.undo.length - 1] = null;//一番最後を消去
				mc.mode = tmp;//保存したモードに戻す
			}
		}else if(e.getSource()==mbs.getItem("Edit", "Redo")) {//もしメニューのRedoが押された時
			if (mc.redo[0] != null) {//もしredoが存在するなら
				int tmp=mc.mode;//モードの保存
				Graphics ub;//仮のGraphicsの宣言
				for (int i = mc.undo.length - 1; i >= 1; i--) {//最後から最初の一つ後まで
					if (mc.undo[i - 1] != null) {//一つ前が存在するなら
						mc.undo[i] = mc.createImage(mc.d.width, mc.d.height);//初期化
						ub = mc.undo[i].getGraphics();//Graphicsの取得
						ub.drawImage(mc.undo[i - 1], 0, 0, null);//一つ前の画像を描画
					}
				}
				mc.undo[0] = mc.createImage(mc.d.width, mc.d.height);//最初の画像を初期化
				ub = mc.undo[0].getGraphics();//Graphicsの取得
				ub.setColor(new Color(255,255,255));//背景色を設定
				ub.fillRect(0, 0, mc.getWidth(), mc.getHeight());//画面全体を塗りつぶし
				ub.drawImage(mc.img, 0, 0, null);//現在の画像で描画

				Graphics gc = mc.img.getGraphics();//Graphicsの取得
				gc.drawImage(mc.redo[0], 0, 0, null);//redoの最初を描画
				mc.mode = 0;//モードを0に
				mc.repaint();//再描画
				Graphics u;//仮のGraphicsの宣言
				for (int i = 0; i < mc.redo.length - 2; i++) {//最初から最後の一つ前まで
					if (mc.redo[i + 1] != null) {//もし一つ後が存在するなら
						mc.redo[i] = mc.createImage(mc.getWidth(), mc.getHeight());//初期化
						u = mc.redo[i].getGraphics();//Graphicsの取得
						u.drawImage(mc.redo[i + 1], 0, 0, null);//一つ後の画像を描画
					}
				}
				mc.redo[mc.redo.length - 1] = null;//一番最後を消去
				mc.mode = tmp;//保存したモードに戻す
			}
		}else if(e.getSource()==mbs.getItem("Setting", "FreeHand")){//もしメニューのFreeHandが押されたとき
			ms.FreeHandSetting();//手描き設定画面を表示
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {//未実装
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void mouseDragged(MouseEvent e) {//未実装
		if(e.getComponent()==bt1) {//もしイベントがbt1で発生したなら
			Boolean bool=false;//boolを初期化
			for(int i=0;i<panels.getComponentCount();i++) {//panels内のすべてのコンポーネントで
				if(panels.getComponent(i)==bt1) {//bt1が存在するなら
					bool=true;//true
				}
			}
			if((e.getX()<bt1.getX()||e.getX()>bt1.getWidth())&&!bool) {//範囲外にドラッグされ、かつpanel内に存在しないなら

				b.setLabel(bt1.getLabel());//bt1と同じラベルを設定
				b.addActionListener(bt1.getActionListeners()[0]);//bt1と同じアクションリスナーに追加
				panels.add(b);//panelsにbを追加
				panels.setVisible(true);//panelsを表示
			}
		}

		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseMoved(MouseEvent e) {//未実装
		// TODO 自動生成されたメソッド・スタブ

	}
}

/**
 * Extended Canvas class for DrawingApli
 * [各モードにおける処理内容]
 * 1: free hand
 *      pressed -> set x, y,  dragged  -> drawline & call repaint()
 * 2: draw line
 *      pressed -> set x, y,  released -> drawline & call repaint()
 * 3: rect
 *      pressed -> set x, y,  released -> calc w, h & call repaint()
 * 4: circle
 *      pressed -> set x, y,  released -> calc w, h & call repaint()
 *
 * @author fukai
 */
class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
	// ■ フィールド変数
	int x, y; // mouse pointer position
	int px, py; // preliminary position
	int sx, sy;//初期値を一時保管するための変数
	int ow, oh; // width and height of the object
	int mode; // drawing mode associated as below
	int size = 2;//初期サイズの設定
	Image img = null; // 仮の画用紙
	Image saveimg = null;//SaveするためのImage
	Image background = null;//背景用のイメージ
	Graphics gc = null; // 仮の画用紙用のペン
	Graphics savegc = null; // 仮の画用紙用のペン
	Dimension d; // キャンバスの大きさ取得用
	Colorchose ch = null;//chの宣言
	SelectSize ss = null;//ssの宣言
	int red = 0;//redの宣言
	int blue = 0;//blueの宣言
	int green = 0;//greenの宣言
	Color color = new Color(red, green, blue);//colorの初期化
	boolean mouseb = false;//マウスが押されているか
	boolean mousemv = false;//マウスが押されていないか
	boolean tes = false;//テスト用
	TransparentFilter tf = new TransparentFilter();//透明化するためのフィルタ
	PixelColorTest pct = new PixelColorTest();//スポイト用のクラスの初期化
	Label lb = null;//現在の色を表示するラベル
	Image[] undo = new Image[10];//undoの初期化
	Image[] redo=new Image[10];//redoの初期化
	CanvasListener cl=new CanvasListener();//筆圧取得のクラスの宣言
	ArrayList<Integer> pointx=new ArrayList<Integer>();//塗りつぶし用のArrayList
	ArrayList<Integer> pointy=new ArrayList<Integer>();//
	Brush brush=new Brush();//ブラシ用クラスの宣言

	// ■ コンストラクタ
	MyCanvas(DrawingApp obj) {
		mode = 0; // initial value
		this.setSize(200, 100); // キャンバスのサイズを指定
		addMouseListener(this); // マウスのボタンクリックなどを監視するよう指定
		addMouseMotionListener(this); // マウスの動きを監視するよう指定
		for (int i = 0; i < undo.length; i++) {
			undo[i] = null;//undoすべての初期化
		}
		for (int i = 0; i < redo.length; i++) {
			redo[i] = null;//redoすべての初期化
		}
		cl.add(this);//このキャンバス上で筆圧取得できるように指定
		cl.can=this;//clに自身を渡す
	}

	// ■ メソッド（オーバーライド）
	// フレームに何らかの更新が行われた時の処理
	public void update(Graphics g) {
		if(!tes) {//tesがfalseなら
			paint(g);
		}
	}

	// ■ メソッド（オーバーライド）
	public void paint(Graphics g) {
		d = getSize(); // キャンバスのサイズを取得

		if (img == null) {
			setBack(new Color(200, 200, 200));//backgroundの作成
			img = createImage(d.width, d.height); // 作成// もし仮の画用紙の実体がまだ存在しなければ
			gc = img.getGraphics(); // Graphicsの作成
			gc.setColor(new Color(255, 255, 255,255));//背景色で
			gc.fillRect(0, 0, d.width, d.height);//塗りつぶし
			saveimg = createImage(d.width, d.height); // 作成// もし仮の画用紙の実体がまだ存在しなければ
			savegc = saveimg.getGraphics(); // 作成
		}
		Graphics2D g2 = (Graphics2D) gc;//Graphics2Dにキャスト
		if (ss == null || ss.size.getValue() == 1) {//ssがないかサイズが一なら
			g2.setStroke(new BasicStroke(1));//太さを1に
		} else {//それ以外なら
			g2.setStroke(new BasicStroke(ss.size.getValue()));//太さを設定したサイズに
		}

		//図形や線のアンチエイリアシングの有効化
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (ch == null) {//chが存在しない場合
			color = new Color(red, green, blue);//colorは持っているRGB値になる
		} else {//それ以外なら
			color = ch.color;//chのColorを取得
		}
		gc.setColor(color);//色をcolorに指定
		savegc.setColor(color);//
		if (mousemv) {//動かしているとき//カーソル処理
			Image im = this.createImage(d.width, d.height);//ローカルなイメージを作成
			Graphics gcc = im.getGraphics();//Graphicsを取得
			Graphics2D gg2 = (Graphics2D) gcc;//Graphics2Dにキャスト
			//図形や線のアンチエイリアシングの有効化
			gg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (ss == null || ss.size.getValue() == 1) {//ssがないかサイズが一なら
				gg2.setStroke(new BasicStroke(1));//太さを1に
			} else {//それ以外なら
				gg2.setStroke(new BasicStroke(ss.size.getValue()*cl.value));//太さを設定したサイズに
			}
			if (ch == null) {//chが存在しない場合
				color = new Color(red, green, blue);//colorは持っているRGB値になる
			} else {//それ以外なら
				color = ch.color;//chのColorを取得
			}
			gcc.setColor(color);//colorに指定
			gcc.drawImage(background, 0, 0, null); // 背景を MyCanvas に描画
			gcc.drawImage(tf.changeimg(img, getToolkit()), 0, 0, null); // Mycanvasの内容を 仮の画用紙 に描画
			switch (mode) {//モードが
			case 1://1または
			case 9://9のとき
				if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255)//もし透明色なら
					gg2.setColor(new Color(254, 254, 254));//白に変化させる
				gg2.drawLine(px, py, px, py);//点の表示
				break;
			case 7:
				gg2.setColor(new Color(254, 254, 254));//白に設定
				gg2.drawLine(px, py, px, py);//点の表示
				break;
			}
			g.drawImage(tf.changeimg(im, getToolkit()), 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画
		}
		if (mouseb) {//もしマウスが押されているとき
			draw(g);//drawの内容を実行//逐次処理
		} else if (!mousemv) {//もしマウスが離されたなら//最終処理
			/*int px,py,ow,oh,x,y;
			px=this.px;py=this.py;x=this.px;y=this.y;
			if(x>px) {
				ow=x-px;
			}else {
				int tmp=x;
				x=px;px=tmp;
				ow=x-px;
			}
			if(y>py) {
				oh=y-py;
			}else {
				int tmp=y;
				y=py;py=tmp;
				oh=y-py;
			}*/
			if(mode==9||mode==7) {//モードが9または7ならば
				return;//中断
			}
			Graphics u;//仮のGraphicsの宣言
			for (int i = undo.length - 1; i >= 1; i--) {//undoの最後から最初の一つ後まで
				if (undo[i - 1] != null) {//一つ前が存在するなら
					undo[i] = createImage(d.width, d.height);//初期化
					u = undo[i].getGraphics();//Graphicsの取得
					u.drawImage(undo[i - 1], 0, 0, null);//一つ前の内容を描画
				}
			}
			undo[0] = createImage(d.width, d.height);//初期化
			u = undo[0].getGraphics();//Graphicsの取得
			u.drawImage(img, 0, 0, null);//現在の内容を描画
			Image im=createImage(d.width,d.height);//仮の画用紙を作成
			Graphics gg=im.getGraphics();//imのGraphicsの取得
			switch (mode) {//モードが
			case 1: // モードが１の場合
				/*if (ss == null) {
					gc.drawLine(px, py, x, y); // 仮の画用紙に描画
					savegc.drawLine(px, py, x, y); // 仮の画用紙に描画
				} else {
					for (int i = px; i < x; i++) {
						for (int j = py; j < y; j++) {
							gc.fillOval(i - ss.size.getValue(), j - ss.size.getValue(), ss.size.getValue() * 2,
									ss.size.getValue() * 2);
							savegc.fillOval(i - ss.size.getValue(), j - ss.size.getValue(), ss.size.getValue() * 2,
									ss.size.getValue() * 2);
						}
					}
				}*/
				break;
			case 2: // モードが２の場合
				g2.drawLine(this.px, this.py, this.x, this.y); // 仮の画用紙に線を描画
				savegc.drawLine(px, py, x, y); // 仮の画用紙に描画
				break;
			case 3: // モードが３の場合
				g2.drawRect(px, py, ow, oh); // 仮の画用紙に四角を描画
				savegc.drawRect(px, py, ow, oh); // 仮の画用紙に描画
				break;
			case 4: // モードが４の場合
				g2.drawOval(px, py, ow, oh); // 仮の画用紙に円を描画
				savegc.drawOval(px, py, ow, oh); // 仮の画用紙に描画
				break;
			case 5:
				g2.fillRect(px, py, ow, oh);//塗りつぶした四角の描画
				savegc.fillRect(px, py, ow, oh);//塗りつぶした四角の描画
				break;
			case 6:
				g2.fillOval(px, py, ow, oh);//塗りつぶした円の描画
				savegc.fillOval(px, py, ow, oh);//塗りつぶした円の描画
				break;
			}
			gg.drawImage(background, 0, 0, null);
			gg.drawImage(cleared(), 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画
			g.drawImage(im, 0, 0, null);

		}

	}

	public void draw(Graphics g) {//逐次描画用の処理
		/*int px,py,ow,oh,x,y;
		px=this.px;py=this.py;x=this.px;y=this.y;
		if(x>px) {
			ow=x-px;
		}else {
			int tmp=x;
			x=px;px=tmp;
			ow=x-px;
		}
		if(y>py) {
			oh=y-py;
		}else {
			int tmp=y;
			y=py;py=tmp;
			oh=y-py;
		}*/

		Image im = null;//仮の画用紙
		Graphics gcc = null;//Graphicsの宣言
		d = getSize(); // キャンバスのサイズを取得
		if (im == null) // もし仮の画用紙の実体がまだ存在しなければ
			im = createImage(d.width, d.height);// 作成
		if (gcc == null) // もし仮の画用紙用のペン (GC) がまだ存在しなければ
			gcc = im.getGraphics(); // 作成
		Graphics2D g2 = (Graphics2D) gcc;//gccをGraphics2Dにキャスト
		//図形や線のアンチエイリアシングの有効化
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (ss == null || ss.size.getValue() == 1) {//ssが存在しなければ
			g2.setStroke(new BasicStroke(1));//太さを１に
		} else {//それ以外なら
			g2.setStroke(new BasicStroke(ss.size.getValue()));//ssのsizeを取得して設定
		}

		if(mode==8) {//もしモードが8ならば
			this.fill(img, x, y);//塗りつぶしを実行
		}

		gcc.drawImage(background, 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画
		//gcc2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4F));
		gcc.drawImage(tf.changeimg(img, getToolkit()), 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画

		Image imm = this.createImage(d.width, d.height);//仮の画用紙の作成
		Graphics gccc = imm.getGraphics();//Graphicsの取得
		Graphics2D gg2 = (Graphics2D) gccc;//Graphics2Dにキャスト
		//図形や線のアンチエイリアシングの有効化
		gg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (ss == null || ss.size.getValue() == 1) {//ssが存在しなければ
			gg2.setStroke(new BasicStroke(1));//太さを１に
		} else {//それ以外なら
			gg2.setStroke(new BasicStroke(ss.size.getValue()*cl.value));//ssのsizeを取得して設定
		}
		if (ch == null) {//chが存在しないなら
			color = new Color(red, green, blue);//colorを持っているRGBに設定
		} else {//それ以外なら
			color = ch.color;//chのカラーを取得
		}
		gg2.setColor(new Color(255,255,255,255));//透明色で
		gg2.fillRect(0, 0, d.width, d.height);//全体を塗りつぶし

		gg2.setColor(color);//colorをセット
		switch (mode) {//モードが
		case 1://1または
		case 9://9のとき
			if (color.getRed() != 255 && color.getGreen() != 255 && color.getBlue() != 255) {//もし透明色以外なら
				gg2.drawLine(px, py, px, py);//点の描画
			} else {//それ以外なら
				gg2.setColor(new Color(254, 254, 254));//白をセットして
				gg2.drawLine(px, py, px, py);//点の描画
			}
			break;
		case 7://7ならば
			gg2.setColor(new Color(254, 254, 254));//白をセットして
			gg2.drawLine(px, py, px, py);//点の描画
			break;
		}
		gcc.drawImage(tf.changeimg(imm, getToolkit()), 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画

		if (ch == null) {//chが存在しないとき
			color = new Color(red, green, blue);//colorに持っているRGBを設定
		} else {//それ以外なら
			color = ch.color;//colorにchのcolorを入れる
		}
		gcc.setColor(color);//colorをセット
		Graphics2D gm=(Graphics2D) gc;//Graphics2Dにキャスト
		switch (mode) {
		case 1: // モードが１の場合
			gm.setStroke(new BasicStroke(ss.size.getValue()*cl.value));//太さを筆圧によって変化させる
			//gc.drawLine(px, py, x, y); // 仮の画用紙に描画
			//savegc.drawLine(px, py, x, y); // 仮の画用紙に描画
			break;
		case 2: // モードが２の場合
			g2.drawLine(this.px, this.py, this.x, this.y); // 仮の画用紙に線を描画
			break;
		case 3: // モードが３の場合
			g2.drawRect(px, py, ow, oh); // 仮の画用紙に四角を描画
			break;
		case 4: // モードが４の場合
			g2.drawOval(px, py, ow, oh); // 仮の画用紙に円を描画
			break;
		case 5://モードが5の場合
			g2.fillRect(px, py, ow, oh);//仮の画用紙に塗りつぶした四角を描画
			break;
		case 6://モードが6の場合
			g2.fillOval(px, py, ow, oh);//仮の画用紙に塗りつぶした円を描画
			break;
		case 7://モードが7の場合
			gm.setStroke(new BasicStroke(ss.size.getValue()*cl.value));//太さを筆圧によって変化させる
			gc.setColor(new Color(255, 255, 255,255));//透明色に設定
			gc.drawLine(px, py, x, y);//線の描画
			break;
		case 9://モードが9の場合
			//x=this.x;y=this.py;px=this.px;py=this.py;
			//ブレゼンハムのアルゴリズム
			int dx=Math.abs(x-px);//x軸の距離を取得
			int dy=Math.abs(y-py);//y軸の距離を取得
			int x0=px,y0=py;//一時的にpx,pyを置く
			int sx,sy;//sx,syの宣言
			if(px<x) {//もしpxがxより小さい（左にある）なら
				sx=1;//sxを1に（右）
			}else {//それ以外なら
				sx=-1;//sxを-1に（左）
			}
			if(py<y) {//もしpyがyより小さい（上にある）なら
				sy=1;//syを1に（下）
			}else {//それ以外なら
				sy=-1;//syを-1に
			}
			int err=dx-dy;//x,y軸の距離の差を取得
			int dsize=(int)(ss.size.getValue()*cl.value)-brush.size;//sizeの変化の差を取得
			int ssize;//ssizeの宣言
			if(dsize<0)ssize=-1;else ssize=1;//dsizeがマイナスならssize=-1,プラスならssize=1
			while(true) {//永遠ループ
				if(brush.size!=(int)(ss.size.getValue()*cl.value))//目標の変化した値ではないなら
				brush.size+=ssize;//ssizeをbrushのsizeに足す

				//brush.brushnum=3;
				brush.paint(gc, x0, y0);//現在の点を基準としてブラシを描画
				if(x0==x&&y0==y)break;//x0がxかつy0がyになればループ終了
				int e2=2*err;//差を二倍する
				if(e2>-dy) {//二倍した値が-dyよりも大きければ
					err=err-dy;//差からdyを引く
					x0=x0+sx;//x0にsxを足す
				}
				if(e2<dx) {//二倍した値がdxより小さければ
					err=err+dx;//差にdxを足す
					y0=y0+sy;//y0にsyを足す
				}
			}
			brush.size=(int)(ss.size.getValue()*cl.value);//サイズを確定させる
			break;
		}

		g.drawImage(im, 0, 0, null); // 仮の画用紙の内容を MyCanvas に描画

	}

	BufferedImage cleared() {//透明化する関数
		BufferedImage im = createBufferedImage(img);//imageをbuffredImageに
		for (int i = 0; i < im.getWidth(); i++) {//すべてのピクセルに対して
			for (int j = 0; j < im.getHeight(); j++) {
				Color c = new Color(im.getRGB(i, j));//色の取得
				if (c.getRed() == 255 && c.getGreen() == 255 && c.getBlue() == 255) {//もし透明色なら
					im.setRGB(i, j, 0);//そのピクセルを透明に
				}
			}
		}
		return im;//作成したイメージを返す
	}
	BufferedImage cleared(Image img) {//透明化する関数
		BufferedImage im = createBufferedImage(img);//imageをbuffedImageに
		for (int i = 0; i < im.getWidth(); i++) {//すべてのピクセルに対して
			for (int j = 0; j < im.getHeight(); j++) {//
				Color c = new Color(im.getRGB(i, j));//色の取得
				if (c.getRed() == 255 && c.getGreen() == 255 && c.getBlue() == 255) {//もし透明色なら
					im.setRGB(i, j, 0);//そのピクセルを透明に
				}
			}
		}
		return im;//作成したイメージを返す
	}

	// ■ メソッド
	// 下記のマウス関連のメソッドは，MouseListener をインターフェースとして実装しているため
	// 例え使わなくても必ず実装しなければならない
	public void mouseClicked(MouseEvent e) {
	}// 今回は使わないが、無いとコンパイルエラー

	public void mouseEntered(MouseEvent e) {
	}// 今回は使わないが、無いとコンパイルエラー

	public void mouseExited(MouseEvent e) {
	} // 今回は使わないが、無いとコンパイルエラー

	public void mousePressed(MouseEvent e) { // マウスボタンが押された時の処理
		mouseb = true;//mousebをtrueに
		mousemv = false;//mousemvをtrueに
		if (e.getButton() == MouseEvent.BUTTON3) {//右クリックされたなら（スポイト）
			if (ch != null) {//chが存在するなら
				ch.color = pct.pixelcolor(e.getX(), e.getY(), img);//その位置の色を取得
				System.out.println(ch.color.getRGB());//RGBを出力
				if (ch.color.getGreen() != 255 && ch.color.getRed() != 255 && ch.color.getBlue() != 255) {//もし透明色以外なら
					ch.lb.setBackground(ch.color);//lbの背景色を取得した色に
					ch.red.setValue(ch.color.getRed());//chのスクロールバーを変更
					ch.green.setValue(ch.color.getGreen());//
					ch.blue.setValue(ch.color.getBlue());//
					ch.txred.setText(String.valueOf(ch.color.getRed()));//chのテキストフィールドの設定
					ch.txgreen.setText(String.valueOf(ch.color.getGreen()));//
					ch.txblue.setText(String.valueOf(ch.color.getBlue()));//
				} else {//透明色なら
					ch.lb.setBackground(new Color(0, 0, 0, 0));//背景色を黒に
					ch.red.setValue(0);//chのスクロールバー
					ch.green.setValue(0);//
					ch.blue.setValue(0);//
					ch.txred.setText(String.valueOf(0));//chのテキストフィールドの設定
					ch.txgreen.setText(String.valueOf(0));//
					ch.txblue.setText(String.valueOf(0));//
				}
			} else {//chが存在しないなら
				Color c = pct.pixelcolor(e.getX(), e.getY(), img);//その位置の色を取得
				red = c.getRed();//持っているRGBに取得したRGBを入れる
				green = c.getGreen();//
				blue = c.getBlue();//
				System.out.println(c.getRGB());//取得した色を出力
				if (lb != null) {	//lbが存在するなら
					if (c.getGreen() != 255 && c.getRed() != 255 && c.getBlue() != 255 && ch != null) {//もし透明色以外なら
						lb.setBackground(new Color(ch.color.getRGB()));//lbの背景色を取得した色に
					} else {//透明色ならば
						lb.setBackground(new Color(0, 0, 0, 255));//lbの背景色を黒色に
					}
				}
			}
			tes=true;//tesをtrueに
			return;//処理を中断
		}
		Graphics u;//仮のグラフィクスの宣言
		switch (mode) {
		case 1: // mode が１の場合，次の内容を実行する
		case 7://7なら
		case 9://9なら
			for (int i = undo.length - 1; i >= 1; i--) {//undoの後ろから最初の一つ後まで
				if (undo[i - 1] != null) {//一つ前が存在するなら
					undo[i] = createImage(d.width, d.height);//初期化
					u = undo[i].getGraphics();//Graphicsの取得
					u.drawImage(undo[i - 1], 0, 0, null);//一つ前の内容を描画
				}
			}
			undo[0] = createImage(d.width, d.height);//初期化
			u = undo[0].getGraphics();//Graphicsの取得
			u.drawImage(img, 0, 0, null);//現在の内容を最初に書き込む
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			px = x;//pxにも代入
			py = y;//
			repaint();//再描画
			break;
		case 2: // mode が２もしくは
		case 3: // ３もしくは
		case 4: // ４の場合，次の内容を実行する
		case 5://５なら
		case 6://６なら
			px = e.getX();//マウスの座標を取得
			py = e.getY();//マウスの座標を取得
			sx = px;//押された場所を保存
			sy = py;//押された場所を保存
			break;
		case 8://modeが８なら
			for (int i = undo.length - 1; i >= 1; i--) {//undoの後ろから最初の一つ後まで
				if (undo[i - 1] != null) {//一つ前が存在するなら
					undo[i] = createImage(d.width, d.height);//初期化
					u = undo[i].getGraphics();//Graphicsの取得
					u.drawImage(undo[i - 1], 0, 0, null);//一つ前の内容を描画
				}
			}
			undo[0] = createImage(d.width, d.height);//初期化
			u = undo[0].getGraphics();//Graphicsの取得
			u.drawImage(img, 0, 0, null);//現在の内容を最初に書き込む
			x=e.getX();//マウスの座標を取得
			y=e.getY();//
			repaint();//再描画
		}
	}

	public void mouseReleased(MouseEvent e) { // マウスボタンが離された時の処理
		//System.out.println("a");
		mouseb = false;//mousebをfalseに
		mousemv = false;//mosuemvをfalseに
		if (e.getButton() == MouseEvent.BUTTON3||tes) {//右クリックなら
			mouseb=false;//mousebをfalseに
			mousemv=true;//mousemvをtrueに
			tes=false;//tesをfalseに
			return;//処理を中断
		}
		switch (mode) {//モードが
		case 2: // mode が２のとき
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			ow = x - sx;//横の長さを計算
			oh = y - sy;//縦の長さを計算
			repaint(); // 再描画
			break;
		case 3: // ３もしくは
		case 4: // ４の場合，次の内容を実行する
		case 5://５のとき
		case 6://６のとき
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			ow = x - sx;//横の長さを計算
			oh = y - sy;//縦の長さを計算
			if (ow < 0) {//もし横の長さが0より小さいなら
				ow = Math.abs(ow);//絶対値に変更
				px = sx - ow;//pxを横の長さ分ずらす
			}
			if (oh < 0) {//もし縦の長さが0より小さいなら
				oh = Math.abs(oh);//絶対値に変更
				py = sy - oh;//pyを縦の長さ縦の長さ分ずらす
			}
			repaint(); // 再描画
		}

	}

	// ■ メソッド
	// 下記のマウス関連のメソッドは，MouseMotionListener をインターフェースとして実装しているため
	// 例え使わなくても必ず実装しなければならない
	public void mouseDragged(MouseEvent e) { // マウスがドラッグされた時の処理
		//if (e.getButton() == MouseEvent.BUTTON3) {return;}
		mouseb = true;//mousebをtrueに
		if (e.getButton() == MouseEvent.BUTTON3||tes) {//右クリックまたはtesがtureのとき
			mouseb=false;//mousebをfalseに
			mousemv=true;//mousemvをtrueに
			return;//処理を中断
		}
		switch (mode) {//モードが
		case 1: // mode が１の場合，次の内容を実行する
		case 7://７のとき
		case 9://９のとき
			px = x;//前の座標をpx,pyに代入
			py = y;//
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			repaint(); // 再描画
			break;
		case 2:
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			ow = x - sx;//横の長さの計算
			oh = y - sy;//縦の長さの計算
			repaint(); // 再描画
			break;
		case 3: // ３もしくは
		case 4: // ４の場合，次の内容を実行する
		case 5://５のとき
		case 6://６のとき
			x = e.getX();//マウスの座標を取得
			y = e.getY();//
			ow = x - sx;//横の長さの計算
			oh = y - sy;//縦の長さの計算
			if (ow < 0) {//横の長さが0未満の場合
				ow = Math.abs(ow);//絶対値に変更
				px = sx - ow;//横の長さ分pxをずらす
			}
			if (oh < 0) {//縦の長さが0未満の場合
				oh = Math.abs(oh);//絶対値に変更
				py = sy - oh;//縦の長さ分pyをずらす
			}
			repaint(); // 再描画
			break;
		}
	}

	public void mouseMoved(MouseEvent e) {
		mousemv = true;//mousemvをtrueに
		if (e.getButton() == MouseEvent.BUTTON3||tes) {//もし右クリックまたはtesがtrueのとき
			mouseb=false;//mousebをfalseに
			mousemv=true;//mousemvをtrueに
			return;//処理を中断
		}
		switch (mode) {//モードが
		case 1://１のとき
		case 7://７のとき
		case 9://９のとき
			px = e.getX();//マウスの座標を取得
			py = e.getY();//マウスの座標を取得
			repaint();//再描画
			break;
		}
	} // 今回は使わないが、無いとコンパイルエラー

	void rep() {
		repaint();//再描画
	}

	void setBack(Color c) {//背景画像を作成
		d = getSize();//サイズの取得
		if (background == null) {//backgroundが存在しないなら
			background = createImage(d.width, d.height);//初期化
		}
		Graphics2D g = (Graphics2D) background.getGraphics();//Graphics2Dの取得
		g.setColor(c);//指定した色で
		g.fillRect(0, 0, d.width, d.height);//全体を塗りつぶし
	}

	public static BufferedImage createBufferedImage(Image img) {//imageをbuffredimageに変更する
		BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);//bufferdimageの初期化

		Graphics g = bimg.getGraphics();//Graphicsの取得
		g.drawImage(img, 0, 0, null);//imageの内容ををbufferdimageに描画
		g.dispose();//graphicsを放棄

		return bimg;//作成したbufferedimageを返す
	}
/**
 * 塗りつぶしの関数
 * @param img
 * 塗りつぶしたい画像
 * @param pointx
 * 塗りつぶし開始のx座標
 * @param pointy
 * 塗りつぶし開始のy座標
 */
	public void fill(Image img,int pointx,int pointy) {
		BufferedImage bimg=createBufferedImage(img);//bufferedimageに変更
		int c=bimg.getRGB(pointx, pointy);//開始位置の色を取得
		Deque<Integer> stack = new ArrayDeque<Integer>();//塗りつぶし用のスタック
		stack.push(pointy);//スタックにいれる
		stack.push(pointx);//スタックにいれる
		while(stack.size()!=0) {//スタックが空になるまで
			int X=stack.remove();//スタックから取り出し
			int Y=stack.remove();//
			bimg.setRGB(X, Y, ch.color.getRGB());//取り出した座標を指定した色で塗りつぶし
			if (X < bimg.getWidth() - 1)//もしXが右端ではなければ
				if (bimg.getRGB(X + 1, Y) == c && bimg.getRGB(X + 1, Y) != ch.color.getRGB()) {//右の色が取得した色と等しければ
					stack.push(Y);//スタックに入れる
					stack.push(X + 1);//スタックに入れる
				}
			if (X > 0)//もしXが左端でなければ
				if (bimg.getRGB(X - 1, Y) == c && bimg.getRGB(X - 1, Y) != ch.color.getRGB()) {//左の色が取得した色と等しければ
					stack.push(Y);//スタックに入れる
					stack.push(X - 1);//スタックに入れる
				}
			if (Y < bimg.getHeight() - 1)//もしYが下端でなければ
				if (bimg.getRGB(X, Y + 1) == c && bimg.getRGB(X, Y + 1) != ch.color.getRGB()) {//下の色が取得した色と等しければ
					stack.push(Y + 1);//スタックに入れる
					stack.push(X);//スタックに入れる
				}
			if (Y > 0)//もしYが上端でなければ
				if (bimg.getRGB(X, Y - 1) == c && bimg.getRGB(X, Y - 1) != ch.color.getRGB()) {//上の色が取得した色と等しければ
					stack.push(Y - 1);//スタックに入れる
					stack.push(X);//スタックに入れる
				}
		}
		Graphics g = img.getGraphics();//Grphicsの取得
		g.drawImage(bimg, 0, 0, null);//作成したイメージを描画
		//g.drawImage(tf.changeimg(bimg2, getToolkit()), 0, 0, null);
		g.dispose();//gを放棄
	}
}