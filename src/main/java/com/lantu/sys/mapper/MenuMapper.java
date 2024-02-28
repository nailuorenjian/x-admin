package com.lantu.sys.mapper;

import com.lantu.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

    // pid = parent ID
    public List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);

}
