<template>
    <div class="login-container">
        <div class="login-box">

            <!-- 左侧图片 -->
            <div class="login-left">
                <div class="overlay"></div>

                <img
                    src="@/assets/login1.png"
                    alt="login background"
                >

                <div class="welcome-text">
                    <h1>欢迎使用本系统</h1>
                    <p>Welcome to use this system</p>
                </div>
            </div>

            <!-- 右侧登录区域 -->
            <div class="login-right">
                <div class="login-form-container">

                    <h2>登录个人记账系统</h2>

                    <p class="subtitle">
                        请输入您的账号信息
                    </p>

                    <el-form :model="formData">

                        <el-form-item>
                            <el-input
                                v-model.trim="formData.username"
                                placeholder="请输入账号"
                                clearable
                            />
                        </el-form-item>

                        <el-form-item>
                            <el-input
                                v-model.trim="formData.password"
                                type="password"
                                show-password
                                placeholder="请输入密码"
                                clearable
                            />
                        </el-form-item>

                        <el-form-item>
                            <el-button
                                type="primary"
                                class="login-btn"
                                :loading="loading"
                                @click="LoginBtn"
                            >
                                {{ loading ? '登录中...' : '登 录' }}
                            </el-button>
                        </el-form-item>

                    </el-form>
                    <div class="register-link">
                        没有账号？
                        <span @click="goRegister">
                            立即注册
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/user'
import { ElMessage } from 'element-plus'

export default {
    setup() {
        const router = useRouter()
        const loading = ref(false)

        const formData = reactive({
            username: '',
            password: ''
        })

        const LoginBtn = async () => {
            if (!formData.username.trim()) {
                ElMessage.warning('请输入账号')
                return
            }
            if (!formData.password.trim()) {
                ElMessage.warning('请输入密码')
                return
            }

            loading.value = true
            try {
                const res = await login(formData)
                console.log('登录结果：', res.data)

                if (res.data.code == 1 || res.data.code == '1') {
                    ElMessage.success('登录成功')

                    // 登录成功后再存储本地数据，放在if内部
                    const data = res.data.data
                    localStorage.setItem("token", data.token)
                    localStorage.setItem("userId", data.userId)
                    localStorage.setItem("username", data.username)

                    router.push('/dashboard')
                } else {
                    ElMessage.error(res.data.msg || '账号或密码错误')
                }
            } catch (error) {
                console.error(error)
                ElMessage.error('服务器异常')
            } finally {
                loading.value = false
            }
        }

        const goRegister = () => {
            router.push('/register')
        }

        return {
            formData,
            loading,
            LoginBtn,
            goRegister
        }
    }
}
</script>

<style scoped>
.login-container {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #ffffff;
}

.login-box {
    width: 1200px;
    height: 600px;
    display: flex;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 15px 25px rgba(0, 0, 0, 0.15);
}

.login-left {
    position: relative;
    flex: 1;
    overflow: hidden;
}

.login-left img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.overlay {
    position: absolute;
    inset: 0;
    background: linear-gradient(
        45deg,
        rgba(26, 35, 126, 0.75),
        rgba(13, 71, 161, 0.75)
    );
    z-index: 1;
}

.welcome-text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 2;
    color: white;
    text-align: center;
}

.welcome-text h1 {
    font-size: 56px;
    font-weight: bold;
    margin-bottom: 20px;
}

.welcome-text p {
    font-size: 18px;
    opacity: 0.9;
}

.login-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    background: white;
}

.login-form-container {
    width: 420px;
}

.login-form-container h2 {
    font-size: 48px;
    font-weight: bold;
    color: #333;
    margin-bottom: 15px;
}

.subtitle {
    color: #666;
    margin-bottom: 35px;
    font-size: 18px;
}

.login-btn {
    width: 100%;
    height: 50px;
    font-size: 18px;
    border: none;
    background: linear-gradient(
        45deg,
        #1a237e,
        #1565c0
    );
}

.login-btn:hover {
    transform: translateY(-1px);
    transition: all 0.3s;
}

:deep(.el-input__wrapper) {
    height: 48px;
}

:deep(.el-form-item) {
    margin-bottom: 25px;
}
.register-link {
    text-align: center;
    margin-top: 15px;
    color: #666;
    font-size: 14px;
}

.register-link span {
    color: #409EFF;
    cursor: pointer;
    font-weight: 600;
    margin-left: 4px;
    transition: all 0.3s;
}

.register-link span:hover {
    color: #337ecc;
    text-decoration: underline;
}
</style>