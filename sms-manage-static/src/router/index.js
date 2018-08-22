import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

import Main from '../views/Main'


//router
const routeMap = [
  {path: '/main', component: Main}
];

const router = new VueRouter({
  routes: routeMap
});

export default router