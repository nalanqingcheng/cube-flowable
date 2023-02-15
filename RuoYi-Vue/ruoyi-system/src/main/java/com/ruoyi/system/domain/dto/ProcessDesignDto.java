package com.ruoyi.system.domain.dto;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: ProcessDesignDto
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 张馨月
 * @Date: 2022/11/22 14:01
 */

public class ProcessDesignDto {

    private String key;
    private String name;
    private String formId;
    private String category;
    private String description;
    private Integer pageNum;
    private Integer pageSize;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFormId() {
        return formId;
    }
    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
