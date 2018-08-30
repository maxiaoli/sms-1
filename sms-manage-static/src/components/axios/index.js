import axios from 'axios'
import config from '../../../config/index'

const service = axios.create({
  baseURL: config.api.baseUrl,
  timeout: config.api.timeout,// 请求超时时间
  withCredentials: false,
  headers: {'X-Requested-With': 'XMLHttpRequest'},
  transformRequest: [function (data) { //对请求数据进行处理
    // Do whatever you want to transform the data
    let ret = '';
    for (let key in data) {
      ret += encodeURIComponent(key) + '=' + encodeURIComponent(data[key]) + '&';
    }
    return ret
  }],
  validateStatus: function (status) {
    return status >= 200 && status < 300 || status === 401 || status === 403; // default
  },
});

// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (response.status === 200 || response.status === 304) {
      return res;
    } else if (response.status === 401) {
      // 未认证，或者token过期，登出
      alert('401')
    } else if (response.status === 403) {
      //没有权限
      alert("No Authorization")
    } else {
      return Promise.reject(res);
    }
  },
  error => {
    return Promise.reject(error);
  }
);

export default service