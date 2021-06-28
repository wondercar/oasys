package com.hellowy.oasys.server.vo;

import com.hellowy.oasys.server.pojo.Joblevel;
import lombok.Data;

/**
 * 业务层职称实体类
 *
 * @author wondercar
 * @date 2021/2/5
 */
@Data
public class JoblevelVO extends Joblevel {
    private Integer page;
    private Integer size;
}
