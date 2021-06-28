import Vue from 'vue'
import Vuex from 'vuex'
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getRequest } from "../utils/api";
import { Notification } from 'element-ui';

Vue.use(Vuex);
const now = new Date();

const store = new Vuex.Store({
  state: {
    websocketPath: "/ws/chat",
    routes: [],
    // 聊天好友列表
    admins: [],
    // 会话消息
    sessions: {},
    // 当前用户
    currentAdmin: JSON.parse(window.sessionStorage.getItem('user')),
    // 当前会话对象
    currentSession: null,
    filterKey: '',
    stomp: null,
    isDot: {}
  },
  mutations: {
    // 日期格式化方法
    dateFormat (fmt, date) {
      let ret;
      const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      };
      for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
          fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
      };
      return fmt;
    },
    init_currentAdmin (state, admin) {
      state.currentAdmin = admin;
    },
    // 初始化路由路径
    initRoutes (state, data) {
      state.routes = data;
    },
    changeCurrentSession (state, currentSession) {
      state.currentSession = currentSession;
      Vue.set(state.isDot, state.currentAdmin.username + '#' + state.currentSession.username, false);
    },
    // 添加消息队列方法
    addMessage (state, msg) {
      // 消息队列key组成:当前用户 + # + 消息接收用户
      let mss = state.sessions[state.currentAdmin.username + '#' + msg.to];
      // 消息对象非空判断
      if (!mss) {
        mss = [];
        // 让vue监听state.sessions，实现消息的及时显示
        Vue.set(state.sessions, state.currentAdmin.username + '#' + msg.to, [])
      }
      // 构造消息实体
      mss.push({
        // 内容
        content: msg.content,
        // 消息时间
        date: new Date(),
        // 发送时notSelf未定义 默认为false 取反则为true 代表是自己发送的 对应聊天列表里显示自己头像发出的信息
        // 每一次信息接收时 对该变量取反 代表别人给你发送的消息 对应聊天列表显示别人头像发出的信息
        notSelf: !msg.notSelf
      })
    },
    // 获取localStorage会话信息
    INIT_DATA (state) {
      let data = localStorage.getItem('vue-chat-session');
      if (data) {
        state.sessions = JSON.parse(data);
      }
    },
    // 获取用户信息方法
    INIT_ADMINS (state, data) {
      state.admins = data;
    }
  },
  actions: {
    // 连接websocket(进行消息接收)
    connect (context) {
      if (typeof (WebSocket) === "undefined") {
        alert("您的浏览器不支持socket")
      } else {
        // 实例化WebSocket(EndPoint：/ws/ep)
        context.state.stomp = Stomp.over(new SockJS('/ws/ep'));
        // 获取本地存储token
        let token = window.sessionStorage.getItem('tokenStr');
        // 通过Token连接订阅地址
        context.state.stomp.connect({ 'Auth-Token': token }, success => {
          // 订阅消息队列
          context.state.stomp.subscribe('/user/queue/chat', msg => {
            // json数据解析
            let receiveMsg = JSON.parse(msg.body);
            // console.log(receiveMsg);
            // 当前未有聊天会话 且消息发送方不是当前会话用户才进行消息提醒
            if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.username) {
              Notification.info({
                title: '【' + receiveMsg.fromNickName + '】发来一条消息',
                // 消息长度大于10，进行消息截取
                message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
                position: 'bottom-right',
                showClose: false,
                duration: 2000
              });
              // 通知vue对红点数据进行实时更新
              Vue.set(context.state.isDot, context.state.currentAdmin.username + '#' + receiveMsg.from, true);
            }
            // 接收的消息肯定不是自己的
            receiveMsg.notSelf = true;
            // 由于是我接收的消息 所以要对发收件人进行位置更换
            receiveMsg.to = receiveMsg.from;
            console.log(receiveMsg);
            context.commit('addMessage', receiveMsg);
          })
        }, error => {
          console.log(error);
        })
      }
    },
    initData (context) {
      // 请求后端加载好友列表数据
      getRequest('/chat/admin').then(resp => {
        if (resp) {
          // console.log(resp);
          // 获取用户信息
          context.commit('INIT_ADMINS', resp)
        }
      })
      // 获取聊天信息
      context.commit('INIT_DATA')
    }
  }
})
// 动态监控session变化并存储在localStorage
store.watch(function (state) {
  return state.sessions
}, function (val) {
  // 只要session发生变化 及时进行localStorage存储
  localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
  // 开启watch监测
  deep: true
})

export default store;
