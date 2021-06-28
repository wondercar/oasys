import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/Login.vue";
import Home from "../views/Home";
import Chat from "../views/Chat";
import UserInfo from "../views/UserInfo";


Vue.use(VueRouter);

const routes = [
  {
    //登录页面
    path: "/",
    name: "Login",
    component: Login,
    hidden: true
  },
  {
    //home主页面
    path: "/home",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/chat",
        name: "在线聊天",
        component: Chat
      },
      {
        path: "/userInfo",
        name: "个人中心",
        component: UserInfo
      }
    ]
  }
];



// 解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
//push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
//replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}
const router = new VueRouter({
  routes,
  //mode: 'history'
});

export default router;
