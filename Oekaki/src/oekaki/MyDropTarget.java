package oekaki;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/**
 * ファイルドロップを監視するクラス
 * @author Tomochika Waku
 *
 */
public class MyDropTarget extends DropTarget{
	File f=null;//ファイルの宣言
	MyCanvas mc=null;//MyCanvasの宣言
	//ファイルがドロップされたときに発生するイベント
	@Override
	public void drop(DropTargetDropEvent e) {
		System.out.println("ファイルがドラッグされました");//文字列を出力
	      try {
	         Transferable t = e.getTransferable();//変換できる形に変える
	         if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {//読み込めるファイルなら
	            e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//コピーまたは移動したときにファイルを受け付ける
	            @SuppressWarnings("unchecked")
	            java.util.List<File> fileList = (java.util.List<File>)
	                  (t.getTransferData(DataFlavor.javaFileListFlavor));//取得したものをファイルリストに変換
	            setFileNames(fileList);//Filenameをセットする
	            if(mc!=null&&f!=null) {//myCanvasが存在し、ファイルが存在するなら
	            	try {
						BufferedImage img = ImageIO.read(f);//画像を読み込み
						mc.img = mc.createImage(mc.getWidth(), mc.getHeight());//初期化
						mc.gc = mc.img.getGraphics();//初期化
						mc.gc.setColor(new Color(255, 255, 255));//透明色に設定
						mc.gc.fillRect(0, 0, mc.getWidth(), mc.getHeight());//塗りつぶし
						if(mc.d.width>img.getWidth()&&mc.d.height>img.getHeight()) {//縦横が範囲内なら
							mc.gc.drawImage(img,0,0,null);//そのまま描画
						}else {//範囲外なら
							int d=img.getHeight()-mc.d.height;//変化量を取得
							if(d<0) {//高さが足りているなら
								mc.gc.drawImage(img,0,0,null);//そのまま描画
							}else {//足りていないなら
								double f=(double)(mc.d.height)/(double)(img.getHeight());//比率を取得
								mc.gc.drawImage(img,0,0,(int)(img.getWidth()*f),mc.d.height,null);//同じ比率で縮小
							}

						}
						int tmp=mc.mode;//modeの保存
						mc.mode = 0;//modeを0に変更
						mc.repaint();//再描画
						mc.mode=tmp;//保存したモードに戻す
					} catch (Exception e2) {//エラーしたときに発生する
						// TODO: handle exception
						System.out.println("Erorr");
					}
	            }
	            }
	         }
	      catch(Exception ex){ex.printStackTrace(System.err);}//エラーが起きたときに実行する
	}
	public void setFileNames(java.util.List<File> fileList) {//fileを取得する
	      //StringBuilder sb = new StringBuilder();//文字列に変換するクラス
	      if(fileList.size()!=1) {//fileが一つ以外のとき
	    	  System.out.println("Erorr");//エラーを起こす
	    	  return;//中断
	      }
	      for (File file:fileList) {//fileすべてを
	         //sb.append(file.getAbsolutePath());
	         f=file;//ファイルを取得
	      }
	      //textArea.setText(sb.toString());
	}
}
