package com.xuhc.animationexample.clean;

import java.util.Random;

/**
 * @author ArvinYoung
 * @date 2020/12/9
 * @description 定义随机分布小圆点
 */
public class SmallCircleDot {
    private float mX; //x坐标
    private float mY; //y坐标
    private float mRadius; //小点半径

    public static int WIDTH = 0; //父组件宽度
    public static int SPEED = 0; //当前速度
    public Random sRandom;
    public static int sMaxDotRadius; //最大半径
    public static int sMinDotRadius; //最小半径

    public SmallCircleDot() {
        setRightPosition();
    }

    public SmallCircleDot(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {

        return mY;
    }

    public void setY(float y) {
        mY = y;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public double getZ() {
        return Math.sqrt(mX * mX + mY * mY);
    }

    public void checkAndChange() {
        if (getZ() + getRadius() < WIDTH / 4) {
            setRightPosition();
        } else {
            adjustPosition();
        }
    }

    public void adjustPosition() {
        float xOffset = (float) (mX * SPEED / getZ());
        mX -= xOffset;
        float yOffset = (float) (mY * SPEED / getZ());
        mY -= yOffset;
    }


    /**
     * TODO 设置小圆点随机角度以及X/Y坐标
     *
     * @return void
     */
    public void setRightPosition() {
        if (sRandom == null) {
            sRandom = new Random();
        }
        mRadius = (int) (sRandom.nextFloat() * (sMaxDotRadius - sMinDotRadius) + sMinDotRadius);
        int angle = sRandom.nextInt(360); //获取角度值
        if (angle < 90) {
            mY = -WIDTH / 2 - mRadius - sRandom.nextInt(50);
            angle = angle - 45;
            mX = (int) (angle < 0 ? (-Math.tan(angle * Math.PI / 360) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else if (angle < 180) {
            mX = WIDTH / 2 + mRadius + sRandom.nextInt(50);
            angle = angle - 135;
            mY = (int) (angle < 0 ? (-Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else if (angle < 270) {
            mY = WIDTH / 2 + mRadius + sRandom.nextInt(50);
            angle = angle - 225;
            mX = (int) (angle < 0 ? (Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (-Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else {
            mX = -WIDTH / 2 - mRadius - sRandom.nextInt(50);
            angle = angle - 315;
            mY = (int) (angle > 0 ? (-Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        }
    }
}
