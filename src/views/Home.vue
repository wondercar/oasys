<template>
    <!--主容器-->
    <div class="body_container">
        <div v-if="!pageToSuccess"
             class="pageunLoad">
            <i class="el-icon-loading" />
        </div>
        <el-container class="el-container"
                      v-else>
            <!--左侧菜单-->
            <el-aside :width="isCollapse ? '64px' : '250px'"
                      class="el-aside">
                <el-menu router
                         unique-opened
                         collapse="true"
                         :default-active="activePath"
                         class="el-menu-vertical-demo"
                         text-color="#000000"
                         :active-text-color="theme"
                         :collapse="isCollapse"
                         :default-openeds="openeds"
                         :collapse-transition="false"
                         style="border-right: none">
                    <!--logo-->
                    <div id="title"
                         :style="{color: theme}">
                        <el-link v-if="!isCollapse"
                                 href="/#/home"
                                 :underline="false"
                                 target="_self"
                                 style="color:#409EFF;font-size:18px; font-family: 'Helvetica Neue';font-weight:700;">
                            OA管理系统</el-link>
                        <span v-else><i class="el-icon-loading" /></span>
                    </div>

                    <!--菜单树-->
                    <el-submenu :index="index + ''"
                                v-for="(item,index) in routes"
                                :key="index"
                                v-if="!item.hidden">
                        <template slot="title">
                            <i :class="item.iconCls"
                               style="margin-right: 10px" />
                            <span>{{item.name}}</span>
                        </template>
                        <el-menu-item @click="saveNavState(children.path)"
                                      :index="children.path"
                                      v-for="children in item.children">
                            <i :class="children.iconCls"
                               style="margin-right: 10px" />
                            <span>{{children.name}}</span>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-container class="el-container">
                <!--头部-->
                <el-header class="el_header"
                           height="60px">
                    <!--菜单li-->
                    <ul class="header_left_ul">
                        <li :title="isCollapse ? '展开菜单' : '收起菜单'"
                            @click="changeMenu">
                            <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'" />
                        </li>
                    </ul>
                    <!--右侧状态li-->
                    <ul class="header_right_ul">
                        <!-- <li title="小雨">
                            <i class="el-icon-heavy-rain" />
                        </li> -->
                        <li title="在线聊天"
                            @click="goChat">
                            <i class="el-icon-chat-round" />
                        </li>
                        <li class="throwLiStyle"
                            style="width: 100px">
                            <el-dropdown @command="handleCommand">
                                <div class="el-dropdown-link">
                                    <!--用户头像 src默认未加载图片路径-->
                                    <el-avatar :size="30"
                                               src=""
                                               @error="errorHandler"
                                               class="user_Face">
                                        <img :src="user.userFace">
                                    </el-avatar>
                                    <!--用户名-->
                                    <span>{{user.name}}</span>
                                </div>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="userInfo"
                                                      icon="el-icon-s-custom">个人信息</el-dropdown-item>
                                    <el-dropdown-item command="exit"
                                                      icon="el-icon-switch-button"
                                                      divided>注销登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </li>
                        <!-- <li title="更多">
                            <i class="el-icon-s-operation" />
                        </li> -->
                    </ul>
                </el-header>
                <!-- 导航路径 -->
                <div class="header_crumbs">
                    <el-breadcrumb separator-class="el-icon-arrow-right"
                                   v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <el-breadcrumb separator-class="el-icon-arrow-right"
                                   v-else>
                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <!--中间主体-->
                <el-main class="el-main">
                    <!--路由跳转-->
                    <router-view class="home_router_view" />
                    <div id="fireworks"></div>
                </el-main>
            </el-container>
        </el-container>
    </div>

</template>
<script>

export default {
    name: "Home",
    /*methods:{
      menuClick(index){
        this.$router.push(index);
      }
    }*/
    data () {
        return {
            pageToSuccess: false,
            //默认张开的菜单index
            openeds: [''],
            user: {},
            //登录的用户信息
            //user: JSON.parse(window.sessionStorage.getItem('user')),
            //是否折叠左侧菜单
            isCollapse: false,
            //主题颜色
            theme: '#409EFF',
        };
    },
    //组件创建完成生命周期钩子
    created () {
        this.pageToSuccess = false;
        //加载用户信息
        this.getRequest('/admin/info').then(resp => {
            if (resp) {
                this.user = resp;
                //存入sessionStorage中
                window.sessionStorage.setItem('user', JSON.stringify(resp));
                this.pageToSuccess = true;
            } else {
                this.$message({
                    type: 'error',
                    message: '用户信息加载失败'
                });
            }
        })
    },
    methods: {
        /*点击展开收起左侧菜单*/
        changeMenu () {
            this.isCollapse = !this.isCollapse;
        },
        /*用户信息下拉列表点击*/
        handleCommand (command) {
            switch (command) {
                case 'userInfo':
                    this.$router.push('/userInfo');
                    break;
                case 'setting':
                    break;
                case 'exit':
                    this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        //执行注销登录
                        this.postRequest('logout');
                        //清空sessionStorage中的数据
                        window.sessionStorage.clear();
                        //清空vuex中的菜单信息
                        this.$store.commit('initRoutes', []);
                        //跳转到登录页
                        this.$router.replace('/');
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '操作已取消'
                        });
                    });
                    break;
            }
        },
        // 保存用户点击菜单的状态
        saveNavState (activePath) {
            window.sessionStorage.setItem('activePath', activePath);
        },
        // 用户头像加载失败
        errorHandler () {
            return true
        },
        // 跳转至聊天窗口
        goChat () {
            this.$router.push('/chat');
        }
    },
    computed: {
        //获取菜单数据
        routes () {
            return this.$store.state.routes;
        },
        //激活sessionStorage中保存中的菜单
        activePath () {
            return window.sessionStorage.getItem('activePath');
        },
        getUserInfo () {
            return this.$store.state.currentAdmin;
        }
    }
}
</script>

<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 250px;
}

/*主容器*/
.body_container {
    height: 100%;
    overflow: hidden;
}
.el-container {
    height: 100%;
}

/*页面未加载出来*/
.pageunLoad {
    font-size: 150px;
    display: flex;
    justify-content: center;
    color: grey;
}

.pageunLoad i {
    position: absolute;
    top: 35%;
}

/*侧边栏*/
.el-aside {
    overflow-x: hidden;
    background-color: #ffffff;
    box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2);
}

/*滚动条隐藏*/
::-webkit-scrollbar {
    display: none;
}

/*logo*/
#title {
    border-bottom: 1px solid #101117;
    height: 60px;
    text-align: center;
    font-size: 23px;
    font-weight: bold;
    line-height: 60px;
}

/*头部*/
.el_header {
    display: flex;
    /*align-items: center;*/
    /*去除头部内边距多余的空格*/
    justify-content: space-between;
    border-bottom: 1px #f5f5f5 solid;
}

//头部图标ul li
.el_header ul {
    margin: 0;
    padding: 0;
    list-style: none;
}
.el_header ul li {
    float: left;
    width: 50px;
    text-align: center;
    line-height: 60px;
}

/*鼠标移至头部icon*/
.el_header ul li:hover {
    background-color: #f7f7f7;
}

/*舍弃样式*/
.el_header .throwLiStyle:hover {
    background-color: #fff;
}

/*显示的头像*/
.el-dropdown-link {
    cursor: pointer;
}

/*用户头像*/
.user_Face {
    margin-right: 10px;
    width: 30px;
    height: 32px;
    background-color: #fff;
    vertical-align: middle;
}

/*中间主体*/
.el-main {
    padding: 10px;
}
/*面包屑*/
.header_crumbs {
    //border-bottom: 1px #F5F5F5 dashed;
    padding: 10px 20px 0 20px;
}

.home_router_view {
    height: 100%;
    //background-color: #F5F5F5;
}
#fireworks {
    position: fixed;
    top: 0px;
    z-index: 9999;
}
</style>
