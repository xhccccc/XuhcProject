package com.xuhc.drawboard.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xuhc.drawboard.R;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.xuhc.drawboard.view.XLBottomView.TAG;

public class XLDrawBoard extends View {

    //背景颜色
    private int bg_color = Color.WHITE;

    //画板的状态
    private int board_state = BOARD_STATE_PEN;
    public static final int BOARD_STATE_PEN = 0;
    public static final int BOARD_STATE_ERASER = 1;

    //线条颜色
    private int lineColor = Color.MAGENTA;

    //线条的粗细
    private int lineWidth = 20;

    //临时的路径
    private Path temp_path;

    //存储绘制的线条
    private List<Graph> graphs;

    //用于反撤销功能而存储的线条
    private List<Graph> remove_graphs;

    float mLastX;
    float mLastY;

    /**
     * 构造方法：Java代码初始化
     * @param context
     */
    public XLDrawBoard(Context context, int bg_color) {
        super(context);

        this.bg_color = bg_color;

        //初始化操作
        init();
    }

    /**
     * 构造方法：Xml代码初始化
     * @param context
     * @param attrs
     */
    public XLDrawBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //解析属性
        if (attrs != null){
            //1.获得所欲属性值的集合
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XLDrawBoard);
            //2.解析属性
            bg_color = typedArray.getColor(R.styleable.XLDrawBoard_bg_color,bg_color);
            //3.释放资源
            typedArray.recycle();
        }

        //初始化操作
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        //初始化集合
        graphs = new ArrayList<>();
        remove_graphs = new ArrayList<>();

        //设置背景颜色
        setBackgroundColor(bg_color);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 触摸事件
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触摸点
        float x = event.getX();
        float y = event.getY();


        //触摸的过程
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //初始化路径
                temp_path = new Path();
                //移动到起点
                mLastX = x;
                mLastY = y;
                temp_path.moveTo(x,y);

                //初始化画笔
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(lineColor);
                paint.setStrokeWidth(lineWidth);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setAntiAlias(true);
                paint.setDither(true);
                paint.setFilterBitmap(true);

                if (board_state == BOARD_STATE_ERASER) {
                    //初始化橡皮檫画笔
                    Paint mEraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mEraserPaint.setStrokeWidth(lineWidth);
                    mEraserPaint.setStyle(Paint.Style.STROKE);
                    mEraserPaint.setStrokeCap(Paint.Cap.ROUND);
                    mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
                    mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mEraserPaint.setAntiAlias(true);
                    mEraserPaint.setDither(true);
                    mEraserPaint.setFilterBitmap(true);
                    //确定Graph
                    Graph graph = new Graph(temp_path, mEraserPaint);
                    graphs.add(graph);
                } else {
                    //确定Graph
                    Graph graph = new Graph(temp_path, paint);
                    graphs.add(graph);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //完善路径
//                temp_path.quadTo(mLastX, mLastY, x, y);
//                mLastX = x;
//                mLastY = y;
                temp_path.lineTo(x,y);

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        //刷新
        invalidate();

        return true;
    }

    /**
     * 重新绘制的过程
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //遍历Graphs数组
        for (Graph graph : graphs) {
            //绘制图形
            canvas.drawPath(graph.path, graph.paint);
        }
    }

    /**
     * 内部类：管理每一个线条的路径以及画笔
     */
    private static class Graph {
        //路径
        Path path;
        //画笔
        Paint paint;

        //构造方法
        Graph(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }

    }

    /**
     * 管理的方法-撤销功能的实现核心
     * @return
     */
    public void removeLast(){
        //防止抛出异常，判断线条数量
        if (graphs.size() > 0){
            //移除最后一个线条
            remove_graphs.add(graphs.get(graphs.size()-1));
            graphs.remove(graphs.size()-1);

            //刷新
            invalidate();
        }
    }

    /**
     * 管理的方法-反撤销功能的实现
     */
    public void resumeLast(){
        //防止抛出异常，判断线条数量
        if (remove_graphs.size() > 0){
            //添加到绘制的线条中
            graphs.add(remove_graphs.get(remove_graphs.size()-1));
            //移除最后一个线条
            remove_graphs.remove(remove_graphs.size()-1);

            //刷新
            invalidate();
            return;
        }

        //小提示
        Toast.makeText(getContext(), "不可还原", Toast.LENGTH_SHORT).show();
    }

    /**
     * 管理的方法-清空功能的实现核心
     * @return
     */
    public void removeAll(){
        //防止抛出异常，判断线条数量
        if (graphs.size() > 0){
            //移除所有的线条
            graphs.clear();
            remove_graphs.clear();

            //刷新
            invalidate();
        }
    }

    /**
     * 管理的方法-保存图片
     * @return
     */
    public Bitmap save(){
        //创建一个bitmap
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //以这个bitmap创建一个画板
        Canvas canvas = new Canvas(bitmap);
        //绘制背景
//        canvas.drawColor(bg_color);
        //绘制线条
        if (graphs != null){
            //遍历Graphs数组
            for (Graph graph : graphs) {
                //绘制图形
                canvas.drawPath(graph.path, graph.paint);
            }
        }

        Bitmap backBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        backBitmap.eraseColor(Color.parseColor("#ffffff"));


        return mergeBitmap(backBitmap,bitmap);
    }

    public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            Log.e(TAG, "backBitmap=" + backBitmap + ";frontBitmap=" + frontBitmap);
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect  = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }


    //setter / getter方法
    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getBoard_state() {
        return board_state;
    }

    public void setBoard_state(int board_state) {
        this.board_state = board_state;

        if (board_state == BOARD_STATE_ERASER) {
//            lineColor = bg_color;

            lineWidth = 50;
        }else {
            lineColor = Color.MAGENTA;

            lineWidth = 20;
        }
    }

}