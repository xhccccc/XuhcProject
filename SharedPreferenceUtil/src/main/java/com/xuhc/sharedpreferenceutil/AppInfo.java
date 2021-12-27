package com.xuhc.sharedpreferenceutil;

import android.text.TextUtils;

public class AppInfo {
    private String mPackageName;// 包名
    private String mAppName;// 应用名
//    private Drawable mIcon;// 应用图标
    private String mVersionName;// 版本名称
    private int mVersionCode;// 版本号
    private String[] mPermissions;// 权限名称数组
    private String mLaunchActivityName;// 应用首页Activity
    private int mUid;
    private String mSharedUserId;
    private boolean isSystemApp;// 是否是系统应用
    private String mManageSpaceActivityName;
    private String mApkPath;// 存放APK应用的绝对路径
    private long mCacheSize;// 缓存大小
    private long mDataSize;// 数据大小
    private long mCodeSize;// 应用大小
    private long mAppSize;// 应用总大小
    private String mApkFileSize;// apk文件大小
    private String mApkLastModifyTime;// apk最后修改时间
    private long mAppInstalledTime;
    // 平台中是否已经有应用
    private boolean isInstalled = false;

    // 是否是升级后的系统应用
    private boolean isUpdatedSystemApp;

    public AppInfo() {
    }

    public boolean isUpdatedSystemApp() {
        return isUpdatedSystemApp;
    }

    public void setUpdatedSystemApp(boolean updatedSystemApp) {
        isUpdatedSystemApp = updatedSystemApp;
    }

    public String getSharedUserId() {
        return mSharedUserId;
    }

    public void setSharedUserId(String mSharedUserId) {
        this.mSharedUserId = mSharedUserId;
    }

    public int getUid() {
        return mUid;
    }

    public void setUid(int mUid) {
        this.mUid = mUid;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String mAppName) {
        this.mAppName = mAppName;
    }

//    public Drawable getIcon() {
//        return mIcon;
//    }
//
//    public void setIcon(Drawable mIcon) {
//        this.mIcon = mIcon;
//    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String mVersionName) {
        this.mVersionName = mVersionName;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int mVersionCode) {
        this.mVersionCode = mVersionCode;
    }

    public String[] getPermissions() {
        return mPermissions;
    }

    public void setPermissions(String[] mPermissions) {
        this.mPermissions = mPermissions;
    }

    public String getLaunchActivityName() {
        return mLaunchActivityName;
    }

    public void setLaunchActivityName(String mLaunchActivityName) {
        this.mLaunchActivityName = mLaunchActivityName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setIsSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public String getSpaceManagerActivityName() {
        return mManageSpaceActivityName;
    }

    public void setSpaceManagerActivityName(String mManageSpaceActivityName) {
        this.mManageSpaceActivityName = mManageSpaceActivityName;
    }

    public long getCacheSize() {
        return mCacheSize;
    }

    public void setCacheSize(long mCacheSize) {
        this.mCacheSize = mCacheSize;
    }

    public long getDataSize() {
        return mDataSize;
    }

    public void setDataSize(long mDataSize) {
        this.mDataSize = mDataSize;
    }

    public long getCodeSize() {
        return mCodeSize;
    }

    public void setCodeSize(long mCodeSize) {
        this.mCodeSize = mCodeSize;
    }

    public long getAppSize() {
        return mAppSize;
    }

    public void setAppSize(long mAppSize) {
        this.mAppSize = mAppSize;
    }

    public String getApkPath() {
        return mApkPath;
    }

    public void setApkPath(String mApkPath) {
        this.mApkPath = mApkPath;
    }

    public String getApkFileSize() {
        return mApkFileSize;
    }

    public void setApkFileSize(String mApkFileSize) {
        this.mApkFileSize = mApkFileSize;
    }

    public String getApkLastModifyTime() {
        return mApkLastModifyTime;
    }

    public void setApkLastModifyTime(String mApkLastModifyTime) {
        this.mApkLastModifyTime = mApkLastModifyTime;
    }

    public long getAppInstalledTime() {
        return mAppInstalledTime;
    }

    public void setAppInstalledTime(long appInstalledTime) {
        mAppInstalledTime = appInstalledTime;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[AppName]" + format(mAppName) + ",");
        sb.append("isSystemApp=" + isSystemApp + ",");
        sb.append("isUpdatedSystemApp=" + isUpdatedSystemApp + ",");
        sb.append("packageName=" + format(mPackageName) + ",");
        sb.append("mVersionName=" + format(mVersionName) + ",");
        sb.append("mLaunchActivityName=" + format(mLaunchActivityName) + ",");
        sb.append("mManageSpaceActivityName=" + format(mManageSpaceActivityName) + ",");
        sb.append("mUid=" + mUid + ",");
        sb.append("mSharedUserId=" + mSharedUserId + ",");
        sb.append("mCacheSize=" + mCacheSize + ",");
        sb.append("mDataSize=" + mDataSize + ",");
        sb.append("mCodeSize=" + mCodeSize + ",");

        if (mPermissions != null) {
            // permissions
            sb.append("[permission=");
            for (String permission :
                    mPermissions) {
                sb.append(permission + ",");
            }
            sb.append("]");
        }

        return sb.toString();
    }

    /**
     * 预防空指针
     *
     * @param str
     * @return
     */
    private String format(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppInfo that = (AppInfo) o;
        return mPackageName.equals(that.mPackageName);
    }

    @Override
    public int hashCode() {
        return mPackageName.hashCode();
    }

}
