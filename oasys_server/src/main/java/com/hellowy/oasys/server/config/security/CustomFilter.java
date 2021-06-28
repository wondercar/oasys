package com.hellowy.oasys.server.config.security;

import com.hellowy.oasys.server.config.constant.SecurityConst;
import com.hellowy.oasys.server.pojo.Menu;
import com.hellowy.oasys.server.pojo.Role;
import com.hellowy.oasys.server.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求的url分析请求所需的角色
 *
 * @author wondercar
 * @date 2021/2/3
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Resource
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 获取所有角色的菜单列表
        List<Menu> menusWithRole = menuService.getMenusWithRole();
        for (Menu menu : menusWithRole) {
            // 判断请求url与菜单角色对应url是否匹配
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                String[] str = menu.getRoles().
                        stream().
                        map(Role::getCode).
                        toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        // 没匹配的url默认登录即可访问(渲染菜单列表)
        return SecurityConfig.createList(SecurityConst.DEFAULT_ROLE);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
