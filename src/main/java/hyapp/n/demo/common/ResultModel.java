package hyapp.n.demo.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Wang Zhen
 * @date 2020/7/23 9:49 上午
 */
@Getter
@Setter
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = -7211292962222685696L;

    /**
     * 请求是否成功
     */
    @JSONField(ordinal = 1)
    private boolean success = true;

    /**
     * 返回结果描述
     */
    @JSONField(ordinal = 2)
    private String message;

    /**
     * 返回内容
     */
    @JSONField(ordinal = 3)
    private T result;

    public ResultModel<T> sendFailedMessage(Exception e) {
        this.success = false;
        this.message = e.getMessage();
        return this;
    }

    public ResultModel<T> sendFailedMessage(String msg) {
        this.success = false;
        this.message = msg;
        return this;
    }


    public ResultModel<T> sendSuccessResult(T data) {
        this.success = true;
        this.result = data;
        return this;
    }
}

