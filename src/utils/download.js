import axios from "axios";

const service = axios.create({
  responseType: 'arraybuffer'
})

//请求拦截器
service.interceptors.request.use(config => {
  config.headers['authorization'] = window.sessionStorage.getItem('tokenStr');
  return config;
},error => {
  alert(error);
})

//响应拦截器
service.interceptors.response.use(resp => {
  const headers = resp.headers;
  let reg = RegExp(/application\/json/);
  if(headers['content-type'].match(reg)){
    resp.data = unitToString(resp.data);
  } else{
    let fileDownLoad = require('js-file-download');
    let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1];
    let contentType = headers['content-type'];
    fileName = decodeURIComponent(fileName);
    fileDownLoad(resp.data,fileName,contentType);
  }

},error => {
  alert(error);
})

function unitToString(unitArr) {
  let encodeStr = String.fromCharCode.apply(null,new Uint8Array(unitArr));
  let decodeStr = decodeURIComponent(escape(encodeStr));
  return JSON.parse(decodeStr);
}

let base = '';
export const downloadRequest = (url,params) => {
  return service({
    method: 'get',
    url: `${base}${url}`,
    data: params
  });
}

export default service;
