package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@Data
@TableName("x_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    @TableField(exist = false)
    private List<Integer> menuIdList;
}
