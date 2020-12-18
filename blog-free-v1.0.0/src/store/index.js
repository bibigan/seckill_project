import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    articles: require('@/data/articles.json'),
    drawer: false,
    items: [
      {
        text: '首页',
        href: '#home',
      },
      {
        // 跳转到首页的正在秒杀栏
        text: '正在秒杀',
        href: '#about',
      },
      {
        // 跳转到首页的即将秒杀栏
        text: '即将秒杀',
        href: '#about',
      },
      {
        // 跳转到我的订单页
        text: '我的订单',
        href: '#about',
      },
    ],
  },
  getters: {
    categories: state => {
      const categories = []

      for (const article of state.articles) {
        if (
          !article.category ||
          categories.find(category => category.text === article.category)
        ) continue

        const text = article.category

        categories.push({
          text,
          href: '#home',
        })
      }
      // 分类个数
      return categories.sort().slice(0, 3)
    },
    // 导航栏的分类
    links: (state, getters) => {
      return state.items.concat(getters.categories)
    },
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: state => (state.drawer = !state.drawer),
  },
  actions: {

  },
})
