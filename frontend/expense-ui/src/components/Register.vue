```vue
<template>
  <div class="register-container">

    <div class="register-box">

      <h2>用户注册</h2>

      <el-input
        v-model="form.username"
        placeholder="请输入账号"
        clearable
      />

      <el-input
        v-model="form.password"
        type="password"
        placeholder="请输入密码"
        show-password
        clearable
        style="margin-top:20px"
      />
      <el-input
        v-model="form.confirmPassword"
        type="password"
        placeholder="请再次输入密码"
        show-password
        clearable 
        style="margin-top:20px"
    />
      <el-button
        type="primary"
        class="register-btn"
        :loading="loading"
        @click="registerBtn"
      >
        {{ loading ? '注册中...' : '注 册' }}
      </el-button>

      <div class="login-link">
        已有账号？
        <span @click="goLogin">
          返回登录
        </span>
      </div>

    </div>

  </div>
</template>

<script>
import { reactive, ref } from 'vue'
import { register } from '@/api/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {

  setup() {

    const router = useRouter()

    const loading = ref(false)

    const form = reactive({
      username: '',
      password: '',
      confirmPassword: ''
    })

    const registerBtn = async () => {

      if (!form.username.trim()) {
        ElMessage.warning('请输入账号')
        return
      }

      if (!form.password.trim()) {
        ElMessage.warning('请输入密码')
        return
      }
      if (form.password !== form.confirmPassword) {
        ElMessage.error('两次输入的密码不一致')
        return
      }

      loading.value = true

      try {

        const res = await register({
        username: form.username,
        password: form.password
        })

        if (
          res.data.code == 1 ||
          res.data.code == '1'
        ) {

          ElMessage.success('注册成功')

          setTimeout(() => {
            router.push('/login')
          }, 1000)

        } else {

          ElMessage.error(
            res.data.msg || '注册失败'
          )

        }

      } catch (error) {

        console.error(error)

        ElMessage.error('服务器异常')

      } finally {

        loading.value = false

      }
    }

    const goLogin = () => {
      router.push('/login')
    }

    return {
      form,
      loading,
      registerBtn,
      goLogin
    }
  }
}
</script>

<style scoped>

.register-container{
  height:100vh;
  display:flex;
  justify-content:center;
  align-items:center;
  background:#f5f7fa;
}

.register-box{
  width:420px;
  background:white;
  padding:40px;
  border-radius:12px;
  box-shadow:0 0 20px rgba(0,0,0,.1);
}

.register-box h2{
  text-align:center;
  margin-bottom:30px;
}

.register-btn{
  width:100%;
  margin-top:20px;
}

.login-link{
  margin-top:20px;
  text-align:center;
}

.login-link span{
  color:#409EFF;
  cursor:pointer;
}

</style>
```
