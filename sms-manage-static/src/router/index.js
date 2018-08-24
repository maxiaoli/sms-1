import Vue from 'vue'
import VueRouter from 'vue-router'

import Main from '../views/Main'

Vue.use(VueRouter);

//router
const routeMap = [
  {
    path: '/main',
    name: 'main',
    component: Main
  }
];

const router = new VueRouter({
  routes: routeMap
});

export default router