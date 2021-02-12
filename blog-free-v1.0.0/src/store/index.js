import Vue from 'vue'
import Vuex from 'vuex'
// mutations
import axios from 'axios'
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
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    userName: '',
    articles: require('@/data/articles.json'),
    order: require('@/data/order.json'),
    drawer: false,
    items: [
      {
        text: '首页',
        href: 'home',
      },
      {
        // 跳转到首页的正在秒杀栏
        text: '正在秒杀',
        href: '#about',
      },
      {
        // 跳转到首页的即将秒杀栏
        text: '即将秒杀',
        href: '#soon',
      },
      {
        // 跳转到我的订单页
        text: '我的订单',
        href: 'order',
      },
      {
        text: '当前用户：',
        href: '#about',
      },
      {
        // 跳转到我的订单页
        text: '退出登录',
        href: 'login',
      },
    ],
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
  },
  getters: {
    seckillEndTimes: state => {
      const seckillEndTimes = []

      for (const article of state.articles) {
        if (
          !article.seckillEndTime ||
          seckillEndTimes.find(seckillEndTime => seckillEndTime.text === article.seckillEndTime)
        ) continue

        const text = article.seckillEndTime

        seckillEndTimes.push({
          text,
          href: '#about',
        })
      }
      // 分类个数
      return seckillEndTimes.sort().slice(0, 0)
    },
    // 导航栏的分类
    links: (state, getters) => {
      //  if (!getters.seckillEndTimes.find('login')) { return state.items.concat(getters.seckillEndTimes) }
      return state.items.concat(getters.seckillEndTimes + '#')
    },
    getValues: (state) => {
      return state.articles
    },
    getUserName: (state) => {
      return state.userName
    },
    getOrder: state => {
      return state.order
    },
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: state => (state.drawer = !state.drawer),
    setValues (state, v) {
      state.articles = v
    },
    setOrder (state, v) {
      state.order = v
    },
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization
      localStorage.setItem('Authorization', user.Authorization)
    },
    setArticles (state, articles) {
      state.articles = articles
    },
    setUser (state, userName) {
      state.userName = userName
      localStorage.setItem('userName', userName)
    },
  },
  actions: {
    setArticles (context) {
      axios.get('http://127.0.0.1:8088/thymeleaf/items').then(response => {
        // eslint-disable-next-line no-unused-vars
        context.commit('setValues', response.data.result)
      })
    },
  },
})
