package com.vinfol.model.enums;

public enum StatusEnums {

    /**
     * 有效
     */
    VALID("01", "有效"),

    /**
     * 无效
     */
    INVALID("02", "无效"),

    /**
     * 审核中
     */
    VERIFY("03", "审核中"),

    /**
     * 已删除
     */
    DELETE("04", "已删除");


    /**
     * 代码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private StatusEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
