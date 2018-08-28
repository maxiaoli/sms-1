import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const load = component => {
  return () => import(`@/views/${component}.vue`)
};

//router
const routeMap = [
  {
    path: '',
    name: 'index',
    component: load('layout/index'),
    redirect: 'dashboard',
    children: [{
      path: '/dashboard',
      name: 'dashboard',
      component: load('dashboard/index')
    }]
  }
];

const router = new VueRouter({
  routes: routeMap
});

export default router