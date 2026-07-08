package com.tankaokesulu.financeapi;

public class Result<T> {

    private String code;    // 状态码，字符串类型
    private String msg;     // 提示信息
    private T data;         // 返回数据，可为 null

    public Result() {}

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 无参成功
    public static <T> Result<T> success() {
        return new Result<>("1", "success", null);
    }

    // 带数据成功
    public static <T> Result<T> success(T data) {
        return new Result<>("1", "success", data);
    }

    // 错误返回
    public static <T> Result<T> error(String msg) {
        return new Result<>("0", msg, null);
    }

    // getter & setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}