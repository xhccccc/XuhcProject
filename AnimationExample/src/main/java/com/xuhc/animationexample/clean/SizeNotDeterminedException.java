package com.xuhc.animationexample.clean;

/**
 * @author ArvinYoung
 * @date 2020/12/9
 * @description 宽高都未指明的异常类
 */
public class SizeNotDeterminedException extends Exception {
    public SizeNotDeterminedException(String message) {
        super(message);
    }
}
