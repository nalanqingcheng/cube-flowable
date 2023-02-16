package com.liujianan.cube.flowable.common.core.exception;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubeFlowableBaseException
 * @Description: 基础异常
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/15 14:54
 */

public class CubeFlowableBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public CubeFlowableBaseException(String module, String code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public CubeFlowableBaseException(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public CubeFlowableBaseException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public CubeFlowableBaseException(String code, Object[] args)
    {
        this(null, code, args, null);
    }

    public CubeFlowableBaseException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    public String getModule()
    {
        return module;
    }

    public String getCode()
    {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }

}
