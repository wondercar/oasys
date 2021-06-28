//拦截器配置
//请求拦截器
axios.interceptors.request.use(config => {
  //如果存在token，则请求携带这token
  if (window.sessionStorage.getItem('tokenStr')) {
    config.headers['Authorization'] = window.sessionStorage.getItem('tokenStr');
  }
  return config;
}, error => {
  console.log(error);
})

//响应拦截器
import axios from 'axios'
import { Message } from 'element-ui';
import { Notification } from 'element-ui';
import router from '../router'

axios.interceptors.response.use(success => {
  //业务逻辑错误
  if (success.status && success.status == 200) {
    if (success.data.code == 500 || success.data.code == 401 || success.data.code == 403) {
      Notification.error({
        title: success.data.message,
        //message: ''
      })
      //Message.error({message:success.data.message});
      return;
    }
    //操作成功
    if (success.data.message) {
      Notification.success({
        title: success.data.message,
        position: 'bottom-right'
      })
      //Message.success({message:success.data.message});
    }
  }
  return success.data;
}, error => {
  if (error.response.code == 504 || error.response.code == 404) {
    Message.error({ message: '服务器宕机了，( ╯□╰ )' });
  } else if (error.response.code == 403) {
    Message.error({ message: '您的权限不足' });
  } else if (error.response.code == 401) {
    Message.error({ message: '尚未登录，请登录' });
    //跳至登录页面
    router.replace('/');
  } else if (error.response.data.message) {
    Message.error({ message: error.response.data.message })
  } else {
    Message.error({ message: '未知错误' });
  }
  return;
});


//前置路径
let baseURL = '';

//请求json格式的post请求
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${baseURL}${url}`,
    data: params
  })
}

//请求json格式的put请求
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${baseURL}${url}`,
    data: params
  })
}

//请求json格式的get请求
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${baseURL}${url}`,
    data: params
  })
}

//请求json格式的delete请求
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${baseURL}${url}`,
    data: params
  })
}
