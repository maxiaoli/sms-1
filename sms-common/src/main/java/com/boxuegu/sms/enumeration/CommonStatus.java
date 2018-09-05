package com.boxuegu.sms.enumeration;

/**
 * 通用数据状态枚举
 *
 * @author leonzhangxf 20180905
 */
public enum CommonStatus {

    ENABLE(1), //启用
    DISABLE(0); //禁用

    private int status;

    CommonStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static boolean inStatus(Integer params) {
        if (null == params) return false;

        CommonStatus[] values = CommonStatus.values();
        if (values.length > 0) {
            for (CommonStatus value : values) {
                if (params.equals(value.getStatus()))
                    return true;
            }
        }

        return false;
    }
}
