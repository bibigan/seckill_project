import Vue from 'vue'
import vuetify from './plugins/vuetify'
import './plugins/base'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import request from './assets/js/request.js'
import axios from 'axios'
Vue.prototype.$axios = axios
Vue.use(VueResource)
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$H = request
new Vue({
  vuetify,
  router,
  store,
  render: h => h(App),
}).$mount('#app')
