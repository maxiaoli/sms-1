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
    path: '/',
    name: 'index',
    component: load('layout/index'),
    meta: {requiresAuth: true, sideShow: true},
    redirect: '/home',
    children: [{
      path: '/home',
      name: 'home',
      component: load('home/index'),
      meta: {requiresAuth: true, sideShow: true}
    }]
  },
  {
    path: '/404',
    name: '404',
    component: load('404'),
    meta: {requiresAuth: false, sideShow: true}
  },
  {
    //其他所有未知跳转到首页
    path: '*',
    redirect: '/',
    meta: {requiresAuth: true, sideShow: true}
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