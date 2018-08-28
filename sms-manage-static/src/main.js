import Vue from 'vue'
import router from './router'
import App from './App'
import store from './store'
import './plugins/element'

Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
  render: h => h(App),
  store,
  router
}).$mount('#app');