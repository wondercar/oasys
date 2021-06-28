package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.Position;
import lombok.Data;

/**
 * 业务层职位实体类
 *
 * @author wondercar
 * @date 2021/2/5
 */
@Data
public class PositionVO extends Position {
    private Integer page;
    private Integer size;
}
