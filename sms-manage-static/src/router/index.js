import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import InitAuth from '../components/keycloak'

Vue.use(VueRouter);

const load = (component) => {
  return () => import(`@/views/${component}.vue`)
};

//router
const routeMap = [
  {
    path: '',
    name: 'index',
    component: load('layout/index'),
    meta: {requiresAuth: true},
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'index-home',
      component: load('home/index'),
      meta: {requiresAuth: true}
    }]
  },
  {
    path: 'channel',
    name: 'channel',
    component: load('home/index'),
    meta: {requiresAuth: true, sideShow: true, title: '渠道管理', icon: 'ios-switch'},
    children: [
      {
        path: 'config',
        name: 'channel-config',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '渠道账号管理', icon: 'md-battery-charging'}
      },
      {
        path: 'signature',
        name: 'channel-signature',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '渠道签名管理', icon: 'md-clipboard'}
      },
      {
        path: 'template',
        name: 'channel-template',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '渠道模板管理', icon: 'md-pricetags'}
      }
    ]
  },
  {
    path: 'sms',
    name: 'sms',
    component: load('home/index'),
    meta: {requiresAuth: true, sideShow: true, title: '短信服务管理', icon: 'ios-albums'},
    children: [
      {
        path: 'log',
        name: 'sms-log',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '发送记录查询', icon: 'md-book'},
      },
      {
        path: 'client',
        name: 'sms-client',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '接入方管理', icon: 'md-link'},
      },
      {
        path: 'template',
        name: 'sms-template',
        component: load('home/index'),
        meta: {requiresAuth: true, sideShow: true, title: '模板管理', icon: 'ios-copy'},
      }
    ]
  },
  {
    path: '/404',
    name: '404',
    component: load('404'),
    meta: {requiresAuth: false}
  },
  {
    //其他所有未知跳转到首页
    path: '*',
    name: 'others',
    redirect: '/',
    meta: {requiresAuth: true}
  }
];

const router = new VueRouter({
  routes: routeMap
});

//路由拦截，加入鉴权
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    let keycloak = store.getters.keycloak;
    if (keycloak === undefined || !keycloak.authenticated) {
      InitAuth(next);
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router