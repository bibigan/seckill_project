import Vue from 'vue'
import vuetify from './plugins/vuetify'
import './plugins/base'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import request from './assets/js/request.js'
import axios from 'axios'
import Vuex from 'vuex'
Vue.use(Vuex)
// axios.defaults.withCredentials = true
Vue.use(VueResource)
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$H = request
// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      config.headers.token = localStorage.getItem('Authorization')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  })
new Vue({
  vuetify,
  router,
  store,
  render: h => h(App),
}).$mount('#app')
