package com.boxuegu.sms.enumeration;

/**
 * 短信服务模板用途枚举
 *
 * @author leonzhangxf 20180906
 */
public enum TemplateUsage {

    VERIFICATION("VERIFICATION", 0, "验证"), //验证

    NOTIFICATION("NOTIFICATION", 1, "通知"), //通知

    PROMOTION("PROMOTION", 2, "推广"); //推广

    private String name;

    private int code;

    private String desc;

    TemplateUsage(String name, int code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
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

    public static TemplateUsage getTemplateUsage(String params) {
        if (null == params) return null;

        TemplateUsage[] values = TemplateUsage.values();
        if (values.length > 0) {
            for (TemplateUsage value : values) {
                if (value.getName().equals(params)) {
                    return value;
                }
            }
        }
        return null;
    }
}
