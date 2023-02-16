package com.liujianan.cube.flowable.common.core.web.domain;

import com.liujianan.cube.flowable.common.core.constant.HttpStatus;
import com.liujianan.cube.flowable.common.utils.StringUtils;

import java.util.HashMap;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowResult
 * @Description: 操作消息提醒
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/15 14:41
 */

public class CubeFlowResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public static final String DATA_TOTAL = "total";

    /**
     * 初始化一个新创建的 CubeFlowResult 对象，使其表示一个空消息。
     */
    public CubeFlowResult()
    {
    }

    /**
     * 初始化一个新创建的 CubeFlowResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public CubeFlowResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 CubeFlowResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public CubeFlowResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public CubeFlowResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CubeFlowResult success()
    {
        return CubeFlowResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static CubeFlowResult success(Object data)
    {
        return CubeFlowResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static CubeFlowResult success(String msg)
    {
        return CubeFlowResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static CubeFlowResult success(String msg, Object data)
    {
        return new CubeFlowResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static CubeFlowResult error()
    {
        return CubeFlowResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CubeFlowResult error(String msg)
    {
        return CubeFlowResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static CubeFlowResult error(String msg, Object data)
    {
        return new CubeFlowResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CubeFlowResult error(int code, String msg)
    {
        return new CubeFlowResult(code, msg, null);
    }

}
