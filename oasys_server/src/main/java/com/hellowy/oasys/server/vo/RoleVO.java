package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.Role;
import lombok.Data;

/**
 * 业务层角色实体类
 *
 * @author wondercar
 * @date 2021/2/6
 */
@Data
public class RoleVO extends Role {
    private Integer page;
    private Integer size;
}
