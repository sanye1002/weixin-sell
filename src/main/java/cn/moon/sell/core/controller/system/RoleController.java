package cn.moon.sell.core.controller.system;

import cn.moon.sell.common.controller.CrudController;
import cn.moon.sell.core.dto.system.role.FindRoleDTO;
import cn.moon.sell.core.dto.system.role.RoleAddDTO;
import cn.moon.sell.core.dto.system.role.RoleUpdateDTO;
import cn.moon.sell.core.entity.system.SysRole;
import cn.moon.sell.core.service.system.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author Licoy
 * @version 2018/4/19/9:41
 */
@RestController
@RequestMapping(value = {"/system/role"})
@Api(tags = {"角色管理"})
public class RoleController implements CrudController<SysRole,RoleAddDTO,RoleUpdateDTO,String,FindRoleDTO,SysRoleService>{

    private final SysRoleService sysRoleService;

    @Autowired
    public RoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    public SysRoleService getService() {
        return sysRoleService;
    }
}
