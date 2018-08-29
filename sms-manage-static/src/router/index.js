import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import { getKeycloak } from '../store/modules/auth'
import InitAuth from '../plugins/keycloak'

Vue.use(VueRouter);

const load = component => {
  return () => import(`@/views/${component}.vue`)
};

//router
const routeMap = [
  {
    path: '/',
    name: 'index',
    component: load('layout/index'),
    meta: {requiresAuth: true},
    redirect: '/dashboard',
    children: [{
      path: '/dashboard',
      name: 'dashboard',
      component: load('dashboard/index'),
      meta: {requiresAuth: true}
    }]
  },
  {
    path: '/404',
    name: '404',
    component: load('404'),
    meta: {requiresAuth: false}
  },
  {
    path: '*',
    redirect: '/',
    meta: {requiresAuth: true}
  }
];

const router = new VueRouter({
  routes: routeMap
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    let keycloak = store.state.keycloak;
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