/**
 *@author chenbo
 *2010-12-1
 */
package com.example.hp0331.asta.paodayangren;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp0331.asta.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Administrator
 *
 */
public class ChessView extends View {
	private int sum=18;
	private static int  qipansize = 6;//���̵Ĵ�С6*6
	private static int gewidth=200;//ÿ��Ŀ��
	private static int chesssize =30;//���ӵĴ�С
	protected static int sx=30;// ���̶�λ�����Ͻ�X
	protected static int sy=100;// ���̶�λ�����Ͻ�Y
	private Pwan bin;//�������ڶ���
	
	private boolean isFirstChoice=true;
	private boolean isPawn=true;
	private boolean successMove=false;
	public static ChessView c; 
	private int red = Color.RED;
	 Context mycontext;
	private Resources res;
	private boolean isSelected;
	Bitmap[] chessImage;//����ͼƬ����
	private boolean trun =true;// Ĭ���û�ѡ����С�� ��������
	private boolean isComputer=false;
	private static int[][] qipan = new int[qipansize][qipansize];;//[i][j]=1���������������ӣ�0����û������
	private Paint p = new Paint();//����
	private Paint pchoice = new Paint();
	private int myCR=-1;//��ǰ�������ڵ���	
	private int myCC=-1;//���ڵ���	
	int bx=0;//���ڼ�¼ѡ�����ӵ�x����
	int by=0;//
	private int binsum=0;
	private int paosum=0;
	int gamestate = GAMESTATE_READY;//��Ϸ���ڵ�״̬,0Ϊû�п�ʼ��1������Ϸ��2gameover
	static final int GAMESTATE_READY = 0;
	static final int GAMESTATE_RUN = 1;
	static final int GAMESTATE_OVER = 2;
	
	public TextView gametv; //  ������Ϸ״̬������ʾ������
	
	ArrayList<Pwan> blist = new ArrayList<Pwan>();
	/**
	 * @param context
	 * @param attrs
	 */
	public ChessView(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("super chessview");
		this.setFocusable(true);//��ô���ʱ����
		this.setFocusableInTouchMode(true);
		c=this;
		mycontext=context;
		res= context.getResources();
		chessImage=new Bitmap[2];//����ͼƬ����
		chessImage[0]= BitmapFactory.decodeResource(res, R.mipmap.pao);
		chessImage[1]= BitmapFactory.decodeResource(res, R.mipmap.bin);
		
	}
	

	public void init(){
		gamestate=GAMESTATE_RUN;//��ʼ����ʱ����Ϸ����
	}
public void choiceBin() {
	trun=true;
	gamestate=GAMESTATE_RUN;//��ʼ����ʱ����Ϸ����
}
public void choicePao(){
	trun=false;
	gamestate=GAMESTATE_RUN;//��ʼ����ʱ����Ϸ����
}
	/**
	 * @param context
	 */
	public ChessView(Context context) {
		super(context);
		
	}
	/**
	 * ������
	 */
	protected void onDraw(Canvas canvas) {
		int c = Color.rgb(10,240,240);//���û��ʵ���ɫ
		p.setColor(c);
		p.setStrokeWidth(2);
		p.setStyle(Style.STROKE);

		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				int mLeft = i * gewidth + sx;
				int mTop = j * gewidth + sy;
				int mRright = mLeft + gewidth;
				int mBottom = mTop + gewidth;
				canvas.drawRect(mLeft, mTop, mRright, mBottom, p);

			}
		}
		//�����̵ı߿�
		p.setStrokeWidth(4);
		canvas.drawRect(sx, sy, sx + gewidth*5, sy + gewidth*5, p);
		drawChess(canvas);
}
	/**
	 * ������
	 *@author chenbo
	 *@date 2010-12-2
	 * @param canvas
	 */
	public void drawChess(Canvas canvas){
		//�����ӣ�ͨ��ͼƬ����
		if(myCC!=-1){
			for(int i=0;i<blist.size();i++){
				Pwan b = blist.get(i);
				Bitmap bitmap =b.getBin();
				int binx =b.getBx();
				int biny = b.getBy();
				int mLeft = binx* gewidth + sx-10;
				int mTop = biny * gewidth + sy-10;
				if(b.getBx()==bx&&b.getBy()==by){
					pchoice.setColor(red);
					pchoice.setStrokeWidth(1);
					pchoice.setStyle(Style.STROKE);
					if(bitmap.equals(chessImage[1])){
					canvas.drawBitmap(chessImage[1], mLeft, mTop,null);
					 isPawn=true;
					}else{
						canvas.drawBitmap(chessImage[0], mLeft, mTop,null);
						 isPawn=false;
					}
					canvas.drawCircle(mLeft+10, mTop+10, 15,pchoice);//ѡ�����ӵĻ�Ȧ
					isSelected=true;
				}else{
					if(b.getBin().equals(chessImage[1])){//�Ǳ�����
					canvas.drawBitmap(chessImage[1], mLeft, mTop,null);
					}else{//���ڻ���
						canvas.drawBitmap(chessImage[0], mLeft, mTop,null);
					}
				}
			}
		
		}
		else{//��ʼ������Ϸ����,��һ�ΰ����ӵ�������ӵ�list��
		for(int i=0;i<=5;i++){
			for(int j=0;j<=5;j++){
				int mLeft = i * gewidth + sx-10;
				int mTop = j * gewidth + sy-10;
				if((i==2&&j==0)||(i==3&&j==0)){//������
					paosum++;
					canvas.drawBitmap(chessImage[0], mLeft, mTop, null);
					blist.add(new Pwan(chessImage[0], i, 0,paosum));//��ӵ��б���ʱ������������ֵ
					qipan[i][j]=1;
				}
				if(j==3||j==4||j==5){//��С��
					binsum++;
					canvas.drawBitmap(chessImage[1], mLeft, mTop, null);
					blist.add(new Pwan(chessImage[1], i, j,binsum));
					qipan[i][j]=1;
				}
			}
		}
		}
	}
private boolean choiceChess(int x,int y){
	for(int i=0;i<blist.size();i++){
		bin= blist.get(i);
		int binx = bin.getBx();
		int biny = bin.getBy();
		if(binx==x && biny==y){//ȷ��ѡ��Ķ�����ͼƬ����ʾ��Χ��
			if(trun){
				if(bin.getBin().equals(chessImage[1])){//�û�ѡ����С��
					invalidate();//�ػ�ͻ��ѡ�е�����
					isFirstChoice=false;
					return true;
				}else{
					System.out.println("please choice youselfchess bin ok?");
				}
			}
			if(!trun)	{
				if(bin.getBin().equals(chessImage[0])){//�û�ѡ���Ǵ���
					invalidate();//�ػ�ͻ��ѡ�е�����
					isFirstChoice=false;
					return true;
					}else{
						System.out.println("please choice youselef chess pao ok?");
					}
			}
		}
		
	}
	return false;
}
//��Ϸ��ʼ
private void gamebegin(int x,int y) {
	if(!isComputer){
		if(!isFirstChoice){
		//�ڶ���ѡ��Ҫ�ߵ�����
			if(isSelected==true&&isPawn==true&&qipan[x][y]==0){
				if(binRule(bx,by,x,y)){
					isComputer=true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cumputerMove();
				}
				System.out.println("The 2 step");
			}
			if(isSelected==true&&isPawn==false){
				if(paoRule(bx,by,x,y)){
					isComputer=true;
				
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cumputerMove();
				}
				System.out.println("The 2 step");

			}
	}
}	
	if(choiceChess(x, y)){
		bx=x;//��סѡ�������
		by=y;
		myCC=by;
		myCR= bx;
		isFirstChoice=false;//�Ѿ�ѡ��
		//System.out.println("The 1 step");
		}
}
/**
 * �õ�������
 *@author chenbo
 * @throws InterruptedException
 *@date 2010-12-16
 */
public void cumputerMove() {
	if(isComputer){
		successMove=false;
		Random r = new Random();
		Pwan b ;
		if(trun){//��ʱ�����Ǵ���
				while(true){
					b = blist.get(r.nextInt(blist.size()));
					int i=r.nextInt(5);
					int j = r.nextInt(5);
					if(b.getBin().equals(chessImage[0])){
						int m = b.getBx();
						int n = b.getBy();
						if(paoRule(m, n, i,j)){//�ɹ�����һ��
							isComputer=false;
							break;
						}
					}
			}
		}
		if(!trun){//��ʱ������С��
			while(true){
				b = blist.get(r.nextInt(blist.size()));
				int i=r.nextInt(5);
				int j = r.nextInt(5);
				if(b.getBin().equals(chessImage[1])){
					int m = b.getBx();
					int n = b.getBy();
					if(binRule(m, n, i,j)){//�ɹ�����һ��
						System.out.println(m+","+n);
						System.out.println(i+","+j);
						isComputer=false;
						break;
					}
				}
		}
		}
	}
}

private void checkWin(int m,int n){
	//��ʱ��Ե�����ʱ��,�����������һ����λ��û������
	int up1=n-2;
	int up2=n-1;
	int down1=n+2;
	int down2=n+1;
	int left1=m-2;
	int left2=m-1;
	int right1=m+2;
	int right2=m+1;
	if(up1>=0&&up1<=5&&up2>=0&&up2<=5){
		if ((qipan[m][n-2]==1)&&(qipan[m][n-1]==0)) {//��
		eatPwan(m, n, m, n-2);
		}
	}
	if((qipan[m][n+2]==1)&&(qipan[m][n+1]==0)){
		if ((qipan[m][n+2]==1)&&(qipan[m][n+1]==0)) {//��
			eatPwan(m, n, m, n+2);
		}
	}
		if ((qipan[m-2][n]==1)&&(qipan[m-1][n]==0)) {//��
			eatPwan(m, n, m-2, n);
		}
		if ((qipan[m+2][n]==1)&&(qipan[m+1][n]==0)) {//��
			eatPwan(m, n, m+2, n);
		}
		


}
	/*
	 * ���������¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:{//ֻ�а���ȥ����Ч
			int x,y;
			float x0=gewidth-(event.getX()-sx)%gewidth;//ֻ�������̷�Χ�ڵĲ���Ч
			float y0 =gewidth-(event.getY()-sy)%gewidth;
			if(x0<gewidth/2){//��Χ����ӽ��Ǹ������x
				x= (int) ((event.getX()-sx)/gewidth)+1;
			}else{
				x= (int) ((event.getX()-sx)/gewidth);
			}
			if(y0<gewidth/2){//��Χ����ӽ��Ǹ������y
				y= (int) ((event.getY()-sy)/gewidth)+1;
			}else{
				y= (int) ((event.getY()-sy)/gewidth);
			}
			if(gamestate==1){
			gamebegin(x, y);//////////////////////////////////////////////////////////////
			}
			//���������������
			/*if ((x >= 0 && x < qipansize - 1)&& (y >= 0 && y < qipansize - 1)) {
			//	System.out.println("move to:("+x+","+y+")");
			}*/
		}
		
		}
		return true;
	}
/**
 * ��Ϸ��������Ϣ��ʾ
 *@author chenbo
 *@date 2010-12-17
 * @param message
 */
	private void showMessage(String message){
		Toast.makeText(mycontext, message, Toast.LENGTH_SHORT).show();
		
	}
	/**
	 * ��ʾ����ȷ�Ͽ�
	 *@author chenbo
	 *@date 2010-12-21
	 * @param message
	 */
private void showDialog(String message){
	AlertDialog.Builder bl = new AlertDialog.Builder(mycontext);
	bl.setTitle("��Ϸ����");
	bl.setMessage(message+"ȡ��ʤ��!");
	bl.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			new PaodDaYangRenActivity();
		}
	});
	bl.show();
}
	/**
	 * �����߷���ֻ����ǰ���ߺ����ƶ�
	 *@author chenbo
	 *@date 2010-12-2
	 * @param   m���ӵ�ǰx����
	 * @param n ���ӵ�ǰ��y����
	 * @param  sxѡ���x����
	 * @param  syѡ���y����
	 */
	private boolean binRule(int m,int n,int sx,int sy){
		if(sy<=n||1==1){//ֻ����ǰ���ߺ����ƶ�
						if ((sy - n == -1) &&(sx - m == 0)&&(qipan[sx][sy]==0)) {
					return	changeTwoChessNum(m, n, sx, sy, false);
						}
						if ((sy - n == 1) &&(sx - m == 0)&&(qipan[sx][sy]==0)) {//��������ƶ�
							return	changeTwoChessNum(m, n, sx, sy, false);
						}
						if ((sy - n == 0) &&(sx - m == 1)&&(qipan[sx][sy]==0)) {
							return	changeTwoChessNum(m, n, sx, sy, false);
						}
						if ((sy - n == 0)&& (sx - m == -1)&&(qipan[sx][sy]==0)) {
							return	changeTwoChessNum(m, n, sx, sy, false);
						}
				}
		
		return false;
	}
	/**
	 * �ڵ��߷�:һ�ο�����ǰ�������ƶ�һ����λ�������ʱ����ԳԵ�С��
	 *@author chenbo
	 *@date 2010-12-6
	 * @param m���ӵ�ǰx����
	 * @param n ���ӵ�ǰ��y����
	 * @param sxѡ���x����
	 * @param syѡ���y����
	 */
private boolean paoRule(int m,int n,int sx,int sy){
	
				//��ʱ��Ե�����ʱ��,�����������һ����λ��û������
				if ((sy - n ==-2)&& (sx - m == 0)&&(qipan[sx][sy]==1)&&(qipan[sx][sy+1]==0)) {
				
					return eatPwan(m, n, sx, sy);
				}
				if ((sy - n ==2)&& (sx - m == 0)&&(qipan[sx][sy]==1)&&(qipan[sx][sy-1]==0)) {
				
					return eatPwan(m, n, sx, sy);
				}
				if ((sy - n == 0)&&(sx - m == 2)&&(qipan[sx][sy]==1)&&(qipan[sx-1][sy]==0)) {
					
					return eatPwan(m, n, sx, sy);
				}
				if ((sy - n == 0)&&(sx - m == -2)&&(qipan[sx][sy]==1)&&(qipan[sx+1][sy]==0)) {
					
					return 	eatPwan(m, n, sx, sy);
				}
				//�������ƶ�һ��
				if ((sy - n ==-1)&& (sx - m == 0)&&(qipan[sx][sy]==0)) {//����������һ��
					return changeTwoChessNum(m, n, sx, sy, true);
					
				}
				if ((sy - n ==1)&& (sx - m == 0)&&(qipan[sx][sy]==0)) {
					return changeTwoChessNum(m, n, sx, sy, true);
					
				}
				if ((sy - n == 0)&&(sx - m == 1)&&(qipan[sx][sy]==0)) {
					return changeTwoChessNum(m, n, sx, sy, true);
					
				}
				if ((sy - n == 0)&&(sx - m == -1)&&(qipan[sx][sy]==0)) {
					return changeTwoChessNum(m, n, sx, sy, true);
					
				}
		return false;
}
	/**�ı����������ֵ��ʵ������move
	 *@author chenbo
	 *@date 2010-12-3
	 * @param m���ӵ�ǰx����
	 * @param n ���ӵ�ǰ��y����
	 * @param sxѡ���x����
	 * @param syѡ���y����
	 * @param b ����Ǳ�false��û�гԵ�Ȩ�����������true���гԵ���Ȩ��
	 */
	protected boolean changeTwoChessNum(int m, int n, int sx, int sy, boolean b) {
		
		for(int i=0;i<blist.size();i++){
			Pwan bin = blist.get(i);
		if(bin.getBx()==m&&bin.getBy()==n){
				bin.setBx(sx);//����λ��
				bin.setBy(sy);
				qipan[m][n]=0;
				qipan[sx][sy]=1;
				System.out.println("hava exchange 1");
				invalidate();
				isSelected=false;
				isFirstChoice=true;
				successMove=true;
				return true;
			}
	}
		return false;
	}
	protected boolean eatPwan(int m, int n, int sx, int sy){
		for(int i=0;i<blist.size();i++){
			Pwan bin = blist.get(i);
			if(bin.getBx()==sx&&bin.getBy()==sy){//���Ե���ʱ��,��Ȼֻ�ܳ�bin���������Լ���
					if(bin.getBin().equals(chessImage[1])){
						blist.remove(i);
						qipan[sx][sy]=0;
						sum--;
						showMessage("�Ե�һ��С����");
						
						if(sum==1){
							showDialog("����");
						}
					}else{
						//System.out.println("can not eat youself!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						showDialog("���ܳ��Լ�");
						break;
					}
				}
			}
		for(int i=0;i<blist.size();i++){
			if(bin.getBx()==m&&bin.getBy()==n&&(qipan[sx][sy]==0)){
				bin.setBx(sx);//����λ��
				bin.setBy(sy);
				qipan[m][n]=0;
				qipan[sx][sy]=1;
				invalidate();
				System.out.println("hava exchange 2");
				isSelected=false;
				isFirstChoice=true;
				successMove=true;
				return true;
			}
		}
		return false;
	}
}
