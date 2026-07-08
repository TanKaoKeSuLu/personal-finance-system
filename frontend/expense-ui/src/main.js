import { createApp } from 'vue'
import App from './App.vue'
// 新增：引入路由
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(router) // 新增：挂载路由

app.mount('#app')