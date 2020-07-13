package org.javaboy.vhr.mapper;

import java.util.List;

/**
 * @Author lixiang
 * @Date 2020/7/12 16:48
 * @Version 1.0
 */
public class RespbeanPage {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
