package graduation.design.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result implements Serializable {

    /**
     * 200正常，非200异常
     */
    private int code;
    private String msg;
    private Object data;

    /**
     * 操作成功结果封装
     */
    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result success(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * 操作失败结果封装
     */
    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
