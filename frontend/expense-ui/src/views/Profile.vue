<template>
  <div class="layout">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="logo">💰 Finance</div>
      <div class="menu-item" @click="$router.push('/dashboard')">🏠 首页</div>
      <div class="menu-item" @click="$router.push('/expense')">📝 记账管理</div>
      <div class="menu-item" @click="$router.push('/statistics')">📊 数据统计</div>
      <div class="menu-item active">👤 用户中心</div>
    </div>
    <!-- 右侧内容区域 -->
    <div class="main-content">
      <h2>用户中心</h2>
      <el-card class="profile-card">
        <div class="avatar-box">
          <el-avatar :size="100">
            {{ username.charAt(0).toUpperCase() }}
          </el-avatar>
        </div>
        <div class="info-item">
          <span>用户名：</span>
          <strong>{{ username }}</strong>
        </div>
        <div class="info-item">
          <span>用户ID：</span>
          <strong>{{ userId }}</strong>
        </div>
        <div class="info-item">
          <span>注册时间：</span>
          <strong>{{ createTime }}</strong>
        </div>
        <div class="btn-box">
          <el-button type="primary" @click="dialogVisible=true">
            修改密码
          </el-button>
          <el-button type="danger" @click="logout">
            退出登录
          </el-button>
        </div>
      </el-card>
    </div>
    <el-dialog
      v-model="dialogVisible"
      title="修改密码"
      width="450px"
    >

      <el-form label-width="90px">

        <el-form-item label="旧密码">
          <el-input
            v-model="oldPassword"
            type="password"
            show-password
          />
        </el-form-item>

        <el-form-item label="新密码">
          <el-input
            v-model="newPassword"
            type="password"
            show-password
          />
        </el-form-item>

        <el-form-item label="确认密码">
          <el-input
            v-model="confirmPassword"
            type="password"
            show-password
          />
        </el-form-item>

      </el-form>

      <template #footer>
        <el-button
          @click="dialogVisible=false"
        >
          取消
        </el-button>

        <el-button
          type="primary"
          @click="changePassword"
        >
          确认修改
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  getUserInfo,
  changePassword as changePasswordApi
} from '@/api/user'

export default {
  setup() {
    const username = ref('')
    const createTime = ref('')
    const router = useRouter()
    const userId = localStorage.getItem('userId')
    const dialogVisible = ref(false)
    const oldPassword = ref('')
    const newPassword = ref('')
    const confirmPassword = ref('')

    // 退出登录：先清空所有本地存储，再跳转
    const logout = () => {
      // 清空全部登录凭证
      localStorage.clear()
      ElMessage.success('退出成功')
      // 延迟一下再跳转，确保存储先清空，避免路由守卫拦截
      setTimeout(()=>{
        router.push('/login')
      }, 100)
    }

    const changePassword = async () => {
      // 前端校验
      if (!oldPassword.value) {
        ElMessage.warning("请输入旧密码")
        return
      }

      if (!newPassword.value) {
        ElMessage.warning("请输入新密码")
        return
      }

      if (newPassword.value.length < 6) {
        ElMessage.warning("密码长度不能小于6位")
        return
      }

      if (newPassword.value !== confirmPassword.value) {
        ElMessage.warning("两次输入密码不一致")
        return
      }

      try {
        await changePasswordApi({
          userId: Number(userId),
          oldPassword: oldPassword.value,
          newPassword: newPassword.value
        })

        ElMessage.success("密码修改成功，请重新登录")
        // 关闭弹窗
        dialogVisible.value = false
        // 修改密码必须清空所有登录信息，不能只删userId
        localStorage.clear()
        // 跳转到登录页
        setTimeout(()=>{
          router.push('/login')
        },100)

      } catch (e) {
        ElMessage.error("修改失败")
      }
    }

    // 获取用户信息
    const fetchUserInfo = async () => {
      try {
        const res = await getUserInfo(userId)
        username.value = res.data.data.username
        createTime.value = res.data.data.createTime
      } catch (e) {
        ElMessage.error('获取用户信息失败')
      }
    }

    onMounted(() => {
      fetchUserInfo()
    })

    return {
      username,
      userId,
      createTime,
      dialogVisible,
      oldPassword,
      newPassword,
      confirmPassword,
      logout,
      changePassword
    }
  }
}
</script>

<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
}
.sidebar {
  width: 220px;
  background: #001529;
  color: white;
  padding-top: 20px;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
}
.logo {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 40px;
}
.menu-item {
  padding: 16px 24px;
  cursor: pointer;
  transition: 0.3s;
}
.menu-item:hover {
  background: #1890ff;
}
.menu-item.active {
  background: #1677ff;
}
.main-content {
  margin-left: 220px;
  flex: 1;
  padding: 20px;
}
.profile-card {
  width: 500px;
  margin-top: 30px;
}
.avatar-box {
  text-align: center;
  margin-bottom: 30px;
}
.info-item {
  margin-bottom: 20px;
  font-size: 16px;
}
.btn-box {
  margin-top: 30px;
  display: flex;
  gap: 15px;
}
</style>