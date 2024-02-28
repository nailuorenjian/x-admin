package com.lantu.sys.controller;

import com.lantu.sys.entity.Menu;
import com.lantu.sys.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import com.lantu.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("select all menu data")
    @GetMapping
    public Result<List<Menu>> getAllMenu(){
    List<Menu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }

}
