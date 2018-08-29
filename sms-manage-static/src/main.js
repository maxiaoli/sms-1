import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App'
import './plugins/element'

Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
  render: h => h(App),
  store,
  router
}).$mount('#app');
