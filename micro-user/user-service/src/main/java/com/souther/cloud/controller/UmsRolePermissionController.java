package com.souther.cloud.controller;


import com.souther.cloud.service.IUmsRolePermissionService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@RestController
@RequestMapping("/ums-role-permission")
public class UmsRolePermissionController {

  @Resource
  private IUmsRolePermissionService iUmsRolePermissionService;
}
