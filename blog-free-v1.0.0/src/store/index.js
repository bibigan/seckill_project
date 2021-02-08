import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
// mutations

export default new Vuex.Store({
  state: {
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
        // 跳转到我的订单页
        text: '登录',
        href: 'login',
      },
      {
        // 跳转到我的订单页
        text: '退出',
        href: 'home',
      },
    ],
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
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: state => (state.drawer = !state.drawer),
    setValues (state, v) {
      state.articles = v
    },
  },
  actions: {

  },
})
