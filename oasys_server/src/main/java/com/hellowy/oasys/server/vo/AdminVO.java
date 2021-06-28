package com.hellowy.oasys.server.vo;

import lombok.Data;

/**
 * 业务层操作员实体类
 *
 * @author wondercar
 * @date 2021/2/9
 */
@Data
public class AdminVO {
    private Integer id;
    private String name;
    private String username;
    private Integer page;
    private Integer size;
}
