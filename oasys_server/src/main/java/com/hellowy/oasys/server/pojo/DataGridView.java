package com.hellowy.oasys.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据表格实体类
 *
 * @author wondercar
 * @date 2021/2/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView<T> {
    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;


    public DataGridView(Long count, List<T> data) {
        this.count = count;
        this.data = data;
    }
}
