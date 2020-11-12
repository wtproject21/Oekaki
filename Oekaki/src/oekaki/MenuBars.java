package oekaki;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * メニューを一括管理するクラス
 * @author Tomochika Waku
 *
 */
class MenuBars extends MenuBar{
	ArrayList<Menus> mes=new ArrayList<Menus>();//Menusの宣言
	void set() {//最終設定するメソッド、実行必須
		for(Menus me:mes) {//すべてのメニューに対して
			me.set();//メニューの項目をセット
			this.add(me);//メニューをセット
		}
	}
	void setMenu(String... str) {//メニューを入れるメソッド
		for(int i=0;i<str.length;i++) {//入力されたものを
			mes.add(new Menus(str[i]));//メニューとして追加
		}
	}
	void setItem(String name,String... str) {//メニューアイテムを入れるメソッド
		for(int i=0;i<mes.size();i++) {//すべてのメニューから
			if(mes.get(i).getLabel()==name) {//入力されたものと一致するメニューに
				mes.get(i).setItem(str);//メニューアイテムを追加
			}
		}
	}
	void setItem(int index,String... str) {//メニューアイテムを入れるメソッド
		if(index<mes.size()&&index>=0) {//存在する位置なら
			mes.get(index).setItem(str);//指定した位置にメニューアイテムを入れる
		}
	}
	Menus[] getMenus() {//Menusを取得するメソッド
		Menus[] mes=new Menus[this.mes.size()];//Menusの配列を作成
		for(int i=0;i<mes.length;i++) {//mesすべてを
			mes[i]=this.mes.get(i);//Menusの配列にいれる
		}
		return mes;//配列を返す
	}
	MenuItem getItem(String menuname,String itemname) {//項目を取得するメソッド
		for(int i=0;i<mes.size();i++) {//すべてのメニューに対して
			if(mes.get(i).getLabel()==menuname) {//選択したメニューならば
				for(int j=0;j<mes.get(i).mis.size();j++) {//そのメニューの内容すべてに対して
					if(mes.get(i).mis.get(j).getLabel()==itemname) {//選択したメニューの項目ならば
						return mes.get(i).mis.get(j);//そのメニューの項目を返す
					}
				}
			}
		}
		return null;//ないならnullを返す
	}
	void addActionListener(ActionListener ac) {//項目の監視を一斉に設定するメソッド
		for(Menus me:mes) {//すべてのメニューの
			for(MenuItem mi:me.mis) {//すべての項目に対して
				mi.addActionListener(ac);//監視を設定
			}
		}
	}
}

class Menus extends Menu{//メニューの項目を管理するクラス
	ArrayList<MenuItem> mis=new ArrayList<MenuItem>();//項目を宣言
	Menus(String str){//コンストラクタ
		super(str);//親のコンストラクタを実行
	}
	Menus(){//コンストラクタ
		super();//親のコンストラクタを実行
	}
	void set() {//最終設定するメソッド
		for(MenuItem mi:mis) {//すべてのmenuitemに対して
			this.add(mi);//メニューに追加
		}
	}
	void setItem(String... str) {//項目を設定するメソッド
		for(int i=0;i<str.length;i++) {//入力された名前すべてを
			mis.add(new MenuItem(str[i]));//メニューアイテムとして追加
		}
	}
	MenuItem[] getMenuItems() {//menuitemの配列を取得
		MenuItem[] mis=new MenuItem[this.mis.size()];//配列の宣言
		for(int i=0;i<mis.length;i++) {//すべてのmenuitemい対して
			mis[i]=this.mis.get(i);//配列に入れる
		}
		return mis;//配列を返す
	}
}