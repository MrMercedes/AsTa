/**
 *@author chenbo
 *2010-12-2
 */
package com.example.hp0331.asta.paodayangren;

import android.graphics.Bitmap;

/**
 * 小兵类
 * @author Administrator
 *
 */
public class Pwan {

	private Bitmap bin;//显示的位图
	private int bx;//兵所在坐标位置x
	private int by;//兵所在坐标位置y
	private int id;//兵的id
	
	/**
	 * 
	 */
	public Pwan() {
		super();
	}
	
	
	/**
	 * @param bin
	 * @param bx
	 * @param by
	 * @param id
	 */
	public Pwan(Bitmap bin, int bx, int by, int id) {
		super();
		this.bin = bin;
		this.bx = bx;
		this.by = by;
		this.id = id;
	}


	/**
	 * @return the bin
	 */
	public Bitmap getBin() {
		return bin;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param bin the bin to set
	 */
	public void setBin(Bitmap bin) {
		this.bin = bin;
	}
	/**
	 * @return the bx
	 */
	public int getBx() {
		return bx;
	}
	/**
	 * @param bx the bx to set
	 */
	public void setBx(int bx) {
		this.bx = bx;
	}
	/**
	 * @return the by
	 */
	public int getBy() {
		return by;
	}
	/**
	 * @param by the by to set
	 */
	public void setBy(int by) {
		this.by = by;
	}
	
	
	
	
	
}
