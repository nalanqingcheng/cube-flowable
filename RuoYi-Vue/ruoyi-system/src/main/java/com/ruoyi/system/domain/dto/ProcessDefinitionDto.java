package com.ruoyi.system.domain.dto;

/**
 * @version v1.0
 * @ProjectName: RuoYi-Vue
 * @ClassName: ProcessDenfinitionDto
 * @Description: 流程定义dto
 * @Author: sunyan
 * @Date: 2022/11/21 14:56
 */
public class ProcessDefinitionDto {

    private String key;
    private String name;
    private String category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
