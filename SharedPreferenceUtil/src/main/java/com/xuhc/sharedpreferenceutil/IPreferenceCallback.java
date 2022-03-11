package com.xuhc.sharedpreferenceutil;

public interface IPreferenceCallback {

    /**
     *  preference change
     *  @param key 对应key值回调
     */
    default void onPreferenceChange(String key) {

    }

}
