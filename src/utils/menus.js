import { getRequest } from "./api";
export const initMenu = (router, store) => {
  if (store.state.routes.length > 0) {
    return;
  }

  getRequest('/system/config/menu').then(data => {
    if (data) {
      // 格式化Router
      let fmtRoutes = formatRoutes(data);
      // 添加到路由路径
      router.addRoutes(fmtRoutes);
      // 将数据存入vuex中
      store.commit('initRoutes', fmtRoutes);
      // 加载菜单时连接websocket
      store.dispatch('connect');
    }
  })
}

export const formatRoutes = (routes) => {
  let fmtRoutes = [];
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      iconCls,
      children
    } = router;
    if (children && children instanceof Array) {
      //递归
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      name: name,
      iconCls: iconCls,
      children: children,
      component (resolve) {
        // 左侧菜单遍历方法
        if (component.startsWith('Home')) {
          require(['../views/' + component + '.vue'], resolve);
        } else if (component.startsWith('Emp')) {
          require(['../views/emp/' + component + '.vue'], resolve);
        } else if (component.startsWith('Per')) {
          require(['../views/per/' + component + '.vue'], resolve);
        } else if (component.startsWith('Sal')) {
          require(['../views/sal/' + component + '.vue'], resolve);
        } else if (component.startsWith('Dep')) {
          require(['../views/dep/' + component + '.vue'], resolve);
        } else if (component.startsWith('Pos')) {
          require(['../views/pos/' + component + '.vue'], resolve);
        } else if (component.startsWith('Job')) {
          require(['../views/job/' + component + '.vue'], resolve);
        } else if (component.startsWith('Sys')) {
          require(['../views/sys/' + component + '.vue'], resolve);
        }
      }
    }
    fmtRoutes.push(fmRouter);
  });
  return fmtRoutes;
}
