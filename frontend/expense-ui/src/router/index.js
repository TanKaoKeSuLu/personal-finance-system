import Register from '@/components/Register.vue'
import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/components/Login.vue'
import Dashboard from '@/views/Dashboard.vue'
import Expense from '@/views/Expense.vue'
import Statistics from '@/views/Statistics.vue'
import Profile from '@/views/Profile.vue'

const routes = [
  {
    path: '/',
    redirect: '/Login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/expense',
    name: 'Expense',
    component: Expense
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: Statistics
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// 全局路由守卫
router.beforeEach((to, from, next) => {

  const token = localStorage.getItem("token")

  const whiteList = [
    "/login",
    "/register"
  ]

  // 未登录
  if (!token) {

    if (whiteList.includes(to.path)) {
      next()
    } else {
      next("/login")
    }

  } else {

    // 已登录，不允许再访问登录页
    if (whiteList.includes(to.path)) {
      next("/dashboard")
    } else {
      next()
    }

  }

})

export default router