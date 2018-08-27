import axios from 'axios'
import auth from './auth'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8081', // api的base_url
  timeout: 15000,// 请求超时时间
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

// request拦截器
service.interceptors.request.use(config => {
  if (auth.token) {
    config.headers['Authorization'] = 'Bearer ' + auth.token;
  }

  return config
}, error => {
  // Do something with request error
  Promise.reject(error)
});

// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (response.status === 200 || response.status === 304) {
      return res;
    } else if (response.status === 401) {
      // 未认证，或者token过期，登出
      auth.logout();
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