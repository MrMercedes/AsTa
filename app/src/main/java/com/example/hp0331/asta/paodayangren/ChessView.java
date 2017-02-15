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
	private static int  qipansize = 6;//棋盘的大小6*6
	private static int gewidth=200;//每格的宽度
	private static int chesssize =30;//棋子的大小
	protected static int sx=30;// 棋盘定位的左上角X
	protected static int sy=100;// 棋盘定位的左上角Y
	private Pwan bin;//兵或者炮对象
	
	private boolean isFirstChoice=true;
	private boolean isPawn=true;
	private boolean successMove=false;
	public static ChessView c; 
	private int red = Color.RED;
	 Context mycontext;
	private Resources res;
	private boolean isSelected;
	Bitmap[] chessImage;//棋子图片数组
	private boolean trun =true;// 默认用户选择是小兵 大炮先走
	private boolean isComputer=false;
	private static int[][] qipan = new int[qipansize][qipansize];;//[i][j]=1代表坐标上有棋子，0代表没有棋子
	private Paint p = new Paint();//画笔
	private Paint pchoice = new Paint();
	private int myCR=-1;//当前棋子所在的行	
	private int myCC=-1;//所在的列	
	int bx=0;//用于记录选择棋子的x坐标
	int by=0;//
	private int binsum=0;
	private int paosum=0;
	int gamestate = GAMESTATE_READY;//游戏现在的状态,0为没有开始，1正在游戏，2gameover
	static final int GAMESTATE_READY = 0;
	static final int GAMESTATE_RUN = 1;
	static final int GAMESTATE_OVER = 2;
	
	public TextView gametv; //  根据游戏状态设置显示的文字
	
	ArrayList<Pwan> blist = new ArrayList<Pwan>();
	/**
	 * @param context
	 * @param attrs
	 */
	public ChessView(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("super chessview");
		this.setFocusable(true);//获得触摸时焦点
		this.setFocusableInTouchMode(true);
		c=this;
		mycontext=context;
		res= context.getResources();
		chessImage=new Bitmap[2];//棋子图片数组
		chessImage[0]= BitmapFactory.decodeResource(res, R.mipmap.pao);
		chessImage[1]= BitmapFactory.decodeResource(res, R.mipmap.bin);
		
	}
	

	public void init(){
		gamestate=GAMESTATE_RUN;//初始化的时候游戏运行
	}
public void choiceBin() {
	trun=true;
	gamestate=GAMESTATE_RUN;//初始化的时候游戏运行
}
public void choicePao(){
	trun=false;
	gamestate=GAMESTATE_RUN;//初始化的时候游戏运行
}
	/**
	 * @param context
	 */
	public ChessView(Context context) {
		super(context);
		
	}
	/**
	 * 画棋盘
	 */
	protected void onDraw(Canvas canvas) {
		int c = Color.rgb(10,240,240);//设置画笔的颜色
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
		//画棋盘的边框
		p.setStrokeWidth(4);
		canvas.drawRect(sx, sy, sx + gewidth*5, sy + gewidth*5, p);
		drawChess(canvas);
}
	/**
	 * 画棋子
	 *@author chenbo
	 *@date 2010-12-2
	 * @param canvas
	 */
	public void drawChess(Canvas canvas){
		//画棋子，通过图片来画
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
					canvas.drawCircle(mLeft+10, mTop+10, 15,pchoice);//选中棋子的画圈
					isSelected=true;
				}else{
					if(b.getBin().equals(chessImage[1])){//是兵画兵
					canvas.drawBitmap(chessImage[1], mLeft, mTop,null);
					}else{//是炮画炮
						canvas.drawBitmap(chessImage[0], mLeft, mTop,null);
					}
				}
			}
		
		}
		else{//初始化的游戏界面,第一次把棋子的坐标添加到list中
		for(int i=0;i<=5;i++){
			for(int j=0;j<=5;j++){
				int mLeft = i * gewidth + sx-10;
				int mTop = j * gewidth + sy-10;
				if((i==2&&j==0)||(i==3&&j==0)){//画大炮
					paosum++;
					canvas.drawBitmap(chessImage[0], mLeft, mTop, null);
					blist.add(new Pwan(chessImage[0], i, 0,paosum));//添加到列表，此时的坐标是整数值
					qipan[i][j]=1;
				}
				if(j==3||j==4||j==5){//画小兵
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
		if(binx==x && biny==y){//确定选择的对象在图片的显示范围内
			if(trun){
				if(bin.getBin().equals(chessImage[1])){//用户选择是小兵
					invalidate();//重画突出选中的棋子
					isFirstChoice=false;
					return true;
				}else{
					System.out.println("please choice youselfchess bin ok?");
				}
			}
			if(!trun)	{
				if(bin.getBin().equals(chessImage[0])){//用户选择是大炮
					invalidate();//重画突出选中的棋子
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
//游戏开始
private void gamebegin(int x,int y) {
	if(!isComputer){
		if(!isFirstChoice){
		//第二步选择要走的坐标
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
		bx=x;//记住选择的棋子
		by=y;
		myCC=by;
		myCR= bx;
		isFirstChoice=false;//已经选择
		//System.out.println("The 1 step");
		}
}
/**
 * 该电脑走了
 *@author chenbo
 * @throws InterruptedException
 *@date 2010-12-16
 */
public void cumputerMove() {
	if(isComputer){
		successMove=false;
		Random r = new Random();
		Pwan b ;
		if(trun){//此时电脑是大炮
				while(true){
					b = blist.get(r.nextInt(blist.size()));
					int i=r.nextInt(5);
					int j = r.nextInt(5);
					if(b.getBin().equals(chessImage[0])){
						int m = b.getBx();
						int n = b.getBy();
						if(paoRule(m, n, i,j)){//成功走了一步
							isComputer=false;
							break;
						}
					}
			}
		}
		if(!trun){//此时电脑是小兵
			while(true){
				b = blist.get(r.nextInt(blist.size()));
				int i=r.nextInt(5);
				int j = r.nextInt(5);
				if(b.getBin().equals(chessImage[1])){
					int m = b.getBx();
					int n = b.getBy();
					if(binRule(m, n, i,j)){//成功走了一步
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
	//此时想吃掉兵的时候,必须隔格，相邻一个单位上没有棋子
	int up1=n-2;
	int up2=n-1;
	int down1=n+2;
	int down2=n+1;
	int left1=m-2;
	int left2=m-1;
	int right1=m+2;
	int right2=m+1;
	if(up1>=0&&up1<=5&&up2>=0&&up2<=5){
		if ((qipan[m][n-2]==1)&&(qipan[m][n-1]==0)) {//上
		eatPwan(m, n, m, n-2);
		}
	}
	if((qipan[m][n+2]==1)&&(qipan[m][n+1]==0)){
		if ((qipan[m][n+2]==1)&&(qipan[m][n+1]==0)) {//下
			eatPwan(m, n, m, n+2);
		}
	}
		if ((qipan[m-2][n]==1)&&(qipan[m-1][n]==0)) {//左
			eatPwan(m, n, m-2, n);
		}
		if ((qipan[m+2][n]==1)&&(qipan[m+1][n]==0)) {//右
			eatPwan(m, n, m+2, n);
		}
		


}
	/*
	 * 处理触摸屏事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:{//只有按下去才生效
			int x,y;
			float x0=gewidth-(event.getX()-sx)%gewidth;//只有在棋盘范围内的才有效
			float y0 =gewidth-(event.getY()-sy)%gewidth;
			if(x0<gewidth/2){//范围上最接近那个坐标点x
				x= (int) ((event.getX()-sx)/gewidth)+1;
			}else{
				x= (int) ((event.getX()-sx)/gewidth);
			}
			if(y0<gewidth/2){//范围上最接近那个坐标点y
				y= (int) ((event.getY()-sy)/gewidth)+1;
			}else{
				y= (int) ((event.getY()-sy)/gewidth);
			}
			if(gamestate==1){
			gamebegin(x, y);//////////////////////////////////////////////////////////////
			}
			//坐标必须在棋盘上
			/*if ((x >= 0 && x < qipansize - 1)&& (y >= 0 && y < qipansize - 1)) {
			//	System.out.println("move to:("+x+","+y+")");
			}*/
		}
		
		}
		return true;
	}
/**
 * 游戏过程中信息提示
 *@author chenbo
 *@date 2010-12-17
 * @param message
 */
	private void showMessage(String message){
		Toast.makeText(mycontext, message, Toast.LENGTH_SHORT).show();
		
	}
	/**
	 * 显示弹出确认框
	 *@author chenbo
	 *@date 2010-12-21
	 * @param message
	 */
private void showDialog(String message){
	AlertDialog.Builder bl = new AlertDialog.Builder(mycontext);
	bl.setTitle("游戏结束");
	bl.setMessage(message+"取得胜利!");
	bl.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			new PaodDaYangRenActivity();
		}
	});
	bl.show();
}
	/**
	 * 兵的走法，只能向前或者横向移动
	 *@author chenbo
	 *@date 2010-12-2
	 * @param   m棋子当前x坐标
	 * @param n 棋子当前的y坐标
	 * @param  sx选择的x坐标
	 * @param  sy选择的y坐标
	 */
	private boolean binRule(int m,int n,int sx,int sy){
		if(sy<=n||1==1){//只能向前或者横向移动
						if ((sy - n == -1) &&(sx - m == 0)&&(qipan[sx][sy]==0)) {
					return	changeTwoChessNum(m, n, sx, sy, false);
						}
						if ((sy - n == 1) &&(sx - m == 0)&&(qipan[sx][sy]==0)) {//可以向后移动
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
	 * 炮的走法:一次可以向前后左右移动一个单位，隔格的时候可以吃掉小兵
	 *@author chenbo
	 *@date 2010-12-6
	 * @param m棋子当前x坐标
	 * @param n 棋子当前的y坐标
	 * @param sx选择的x坐标
	 * @param sy选择的y坐标
	 */
private boolean paoRule(int m,int n,int sx,int sy){
	
				//此时想吃掉兵的时候,必须隔格，相邻一个单位上没有棋子
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
				//正常的移动一个
				if ((sy - n ==-1)&& (sx - m == 0)&&(qipan[sx][sy]==0)) {//正常步骤走一格
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
	/**改变两个坐标的值，实现棋子move
	 *@author chenbo
	 *@date 2010-12-3
	 * @param m棋子当前x坐标
	 * @param n 棋子当前的y坐标
	 * @param sx选择的x坐标
	 * @param sy选择的y坐标
	 * @param b 如果是兵false，没有吃的权利，如果是炮true，有吃掉的权利
	 */
	protected boolean changeTwoChessNum(int m, int n, int sx, int sy, boolean b) {
		
		for(int i=0;i<blist.size();i++){
			Pwan bin = blist.get(i);
		if(bin.getBx()==m&&bin.getBy()==n){
				bin.setBx(sx);//交换位置
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
			if(bin.getBx()==sx&&bin.getBy()==sy){//被吃到的时候,当然只能吃bin，不能是自己的
					if(bin.getBin().equals(chessImage[1])){
						blist.remove(i);
						qipan[sx][sy]=0;
						sum--;
						showMessage("吃掉一个小兵！");
						
						if(sum==1){
							showDialog("大炮");
						}
					}else{
						//System.out.println("can not eat youself!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						showDialog("不能吃自己");
						break;
					}
				}
			}
		for(int i=0;i<blist.size();i++){
			if(bin.getBx()==m&&bin.getBy()==n&&(qipan[sx][sy]==0)){
				bin.setBx(sx);//交换位置
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
