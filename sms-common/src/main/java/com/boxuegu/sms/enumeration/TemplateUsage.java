package com.boxuegu.sms.enumeration;

/**
 * 短信服务模板用途枚举
 *
 * @author leonzhangxf 20180906
 */
public enum TemplateUsage {

    VERIFICATION(0), //验证

    NOTIFICATION(1), //通知

    PROMOTION(2); //推广

    private int code;

    TemplateUsage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TemplateUsage getTemplateUsage(Integer params) {
        if (null == params) return null;

        TemplateUsage[] values = TemplateUsage.values();
        if (values.length > 0) {
            for (TemplateUsage value : values) {
                if (value.getCode() == params) {
                    return value;
                }
            }
        }

        return null;
    }
}
