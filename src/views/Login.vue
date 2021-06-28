<template>
    <!--登录主体部分-->
    <div class="loginBody"
         v-loading="loading"
         element-loading-text="正在登入..."
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.2)">

        <!--登录表单容器-->
        <el-form ref="loginFormRef"
                 :rules="rules"
                 :model="loginForm"
                 class="loginContainer">

            <!--表单标题-->
            <p class="title"
               :style="{color : themeColor}">OA管理系统</p>

            <!--表单描述-->
            <p class="desc">Everything will gone with the wind.</p>

            <el-form-item prop="username">
                <el-input v-model="loginForm.username"
                          clearable
                          auto-complete="false"
                          placeholder="用户名"
                          prefix-icon="el-icon-user-solid" />
            </el-form-item>

            <el-form-item prop="password">
                <el-input type="password"
                          v-model="loginForm.password"
                          clearable
                          auto-complete="false"
                          placeholder="密码"
                          prefix-icon="el-icon-lock" />
            </el-form-item>

            <el-form-item prop="code">
                <div style="display: flex;justify-content: space-between">
                    <el-input v-model="loginForm.code"
                              auto-complete="false"
                              @keyup.enter.native="submitLogin"
                              placeholder="验证码"
                              style="width: 220px;"
                              prefix-icon="el-icon-picture" />
                    <div class="captchaContent">
                        <div v-if="captchaLoad">
                            <i class="el-icon-loading"
                               style="margin-right: 5px" />loading...
                        </div>
                        <img v-else
                             :src="captchaUrl"
                             @click="getCaptcha"
                             title="点击更换">
                    </div>
                </div>
            </el-form-item>

            <el-form-item>
                <el-checkbox v-model="checked"
                             :style="{color: themeColor}">记住我</el-checkbox>
                <a href="javascript:;"
                   class="forget"
                   :style="{color: themeColor}">忘记密码</a>
            </el-form-item>

            <el-form-item>
                <!--提交按钮-->
                <el-button type="primary"
                           class="submitBtn"
                           @click="submitLogin"
                           :style="{backgroundColor: themeColor}">
                    <span v-if="!loading">登&emsp;入</span>
                    <span v-else><i class="el-icon-loading"
                           style="margin-right: 10px" />登&emsp;入&emsp;中&emsp;...</span>
                </el-button>
            </el-form-item>

            <!--其他登入方式-->
            <el-form-item>
                <ul class="otherLogin">
                    <li title="使用GitHub账号登录"
                        style="background-color: #252A2F"><i class="fa fa-github" /></li>
                    <li title="使用QQ账号登录"
                        style="background-color: #00B0FB"><i class="fa fa-qq" /></li>
                    <li title="使用微信账号登录"
                        style="background-color: #46D800"><i class="fa fa-wechat" /></li>
                </ul>
            </el-form-item>
        </el-form>

        <!--底部内容-->
        <p class="footer_content"
           :style="{color: themeColor}">
            <span>
                Copyright © 2021
            </span>
            <span>
                <i class="fa fa-github" />
                <a href="https://gitee.com/wondercar"
                   target="_blank"
                   :style="{color: themeColor}">GitHub</a>
            </span>
            <span>
                <i class="fa fa-qq" />
                507160360
            </span>
            <span>
                <i class="fa fa-history" />
                version:1.0
            </span>
        </p>
    </div>
</template>

<script>
export default {
    name: "Login",
    data () {
        return {
            //主题颜色
            themeColor: '#409EFF',
            //验证码加载遮罩
            captchaLoad: true,
            //验证码url
            captchaUrl: '',
            //表单登录属性
            loginForm: {
                username: 'wondercar',
                password: '123',
                code: '',
                uuid: ''
            },
            //记住我功能
            checked: false,
            //整个页面加载
            loading: false,
            //非空验证 与prop属性对应
            rules: {
                username: [{ required: true, message: "请输入用户名", trigger: "change" }],
                password: [{ required: true, message: "请输入密码", trigger: "change" }],
                code: [{ required: true, message: "请输入验证码", trigger: "change" }]
            }
        }
    },
    mounted () {
        //加载验证码
        this.getCaptcha();
    },
    methods: {
        //获取验证码
        getCaptcha () {
            this.captchaLoad = true;
            this.getRequest('/captchaNumber?time=' + new Date()).then(resp => {
                if (resp) {
                    // 使用算数验证码
                    this.captchaUrl = resp.captcha;
                    this.uuid = resp.captchaUuid;
                    this.captchaLoad = false;
                    // 使用图形验证码
                    // this.captchaUrl = resp;
                }
            })
        },
        //表单提交
        submitLogin () {
            //与ref属性对应
            this.$refs.loginFormRef.validate((valid) => {
                if (valid) {
                    //加载动画
                    this.loading = true;
                    //封装的post请求
                    this.postRequest('/login', this.loginForm).then(resp => {
                        if (resp) {
                            //存储用户token
                            const tokenStr = resp.obj.tokenHead + resp.obj.token;
                            window.sessionStorage.setItem('tokenStr', tokenStr);
                            //跳转页面
                            //this.$router.replace('/home');
                            let path = this.$route.query.redirect;
                            this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                        } else {
                            //登录失败
                            this.getCaptcha();
                        }
                        this.loading = false;
                    })
                    //表单非空验证不通过
                } else {
                    this.$message({
                        //showClose: true,
                        message: '请输入必填项',
                        type: 'warning',
                    });
                    return false;
                }
            });
        }
    }
}
</script>

<style lang="less" scoped>
/*登录主体部分*/
.loginBody {
    width: 100%;
    height: 100%;
    overflow: hidden;
    background: #f5f5f5 url("../assets/images/background2.svg");
    background-size: cover;
}

/*登录主容器*/
.loginContainer {
    background-clip: padding-box;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 350px;
}

/*登录标题*/
.title {
    margin: 0 auto;
    text-align: center;
    font-size: 35px;
    font-weight: bold;
}

/*描述*/
.desc {
    text-align: center;
    color: grey;
    font-size: 14px;
    line-height: 60px;
}

/*图片验证码区域*/
.captchaContent {
    width: 115px;
    height: 40px;
    cursor: pointer;
    color: #808080;
    text-align: center;
}

.el-form-item__content {
    display: flex;
    align-items: center;
}

/*忘记密码*/
.forget {
    float: right;
    font-size: 14px;
    text-decoration: none;
}

/*登录按钮*/
.submitBtn {
    border: none;
    width: 100%;
    font-size: 15px;
    font-weight: bold;
}

/*其他登录方式*/
.otherLogin {
    /*平均分布*/
    display: flex;
    justify-content: space-between;
    margin: 10px 0 0 0;
    padding: 0;
    list-style: none;
    width: 100%;
}
.otherLogin li {
    line-height: 35px;
    font-size: 20px;
    text-align: center;
    color: #fff;
    width: 35px;
    height: 35px;
    border-radius: 50%;
    cursor: pointer;
}

//脚步内容
.footer_content {
    display: flex;
    justify-content: space-between;
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 14px;
}

/*底部a标签*/
.footer_content a {
    font-size: 14px;
    margin-left: 5px;
    text-decoration: none;
}
.footer_content a:hover {
    text-decoration: underline;
}
.footer_content span {
    margin-right: 20px;
}
</style>
