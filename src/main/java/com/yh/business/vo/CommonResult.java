package com.yh.business.vo;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/14
 */
public class CommonResult {

    private String resultCode;

    private String desc;

    private Object data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "resultCode='" + resultCode + '\'' +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
