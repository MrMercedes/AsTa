package com.example.hp0331.asta.txtreader;

/**
 * 
 * ��¼��ĵ�ַ������״̬
 * Download by http://javaapk.com
 * @author
 */
public class BookVo {
	private String owen;// �鼮·��
	private int local;// ����״̬

	// ��ַ
	public String getOwen() {
		return owen;
	}

	public void setOwen(String owen) {
		this.owen = owen;
	}

	// ����״̬
	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public BookVo(String owen, int local) {
		super();
		this.owen = owen;
		this.local = local;
	}

}
