import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import LoginPage from '../views/LoginPage.vue'
import SignupPage from '../views/SignupPage.vue'
import OrderPage from '../views/OrderPage.vue'
Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/home',
      name: 'home',
      component: Home,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginPage,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupPage,
    },
    {
      path: '/order',
      name: 'order',
      component: OrderPage,
    },
  ],
})

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else {
    const token = localStorage.getItem('Authorization')
    if (token === 'null' || token === '') {
      next('/login')
    } else {
      next()
    }
  }
})
export default router
