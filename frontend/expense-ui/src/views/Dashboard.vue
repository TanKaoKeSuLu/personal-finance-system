<template>
  <div class="layout">
    <!-- 左侧菜单 -->
    <div class="sidebar">
      <div class="logo">
        💰 Finance
      </div>

      <div class="menu-item active">
        🏠 首页
      </div>
      <div class="menu-item" @click="$router.push('/expense')">
        📝 记账管理
      </div>
      <div class="menu-item" @click="$router.push('/statistics')">
        📊 数据统计
      </div>
      <div class="menu-item" @click="$router.push('/profile')">
        👤 用户中心
      </div>
    </div>

    <!-- 右侧工作区 -->
    <div class="main-content">
      <div class="container">
        <!-- Hero区域 -->
        <div class="hero">
          <div class="hero-left">
            <h1>💰 Personal Finance</h1>
            <p>欢迎回来,admin</p>
            <div class="hero-info">
              <span>📅 {{ currentDate }}</span>
              <span>📊 共 {{ expenses.length }} 条记录</span>
            </div>
          </div>
          <div class="hero-right">
            <el-avatar :size="60">A</el-avatar>
            <div class="user-name">admin</div>
            <el-button type="danger" plain @click="logout">退出登录</el-button>
          </div>
        </div>

        <!-- 统计卡片 -->
        <el-row :gutter="20" class="card-row">
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="card-content">
                <div class="card-title">总金额</div>
                <div class="card-value">￥{{ totalAmount }}</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="card-content">
                <div class="card-title">记录数</div>
                <div class="card-value">{{ expenses.length }}</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <div class="card-content">
                <div class="card-title">平均金额</div>
                <div class="card-value">￥{{ averageAmount }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 饼图、柱状图 -->
        <el-row :gutter="20" class="chart-row">
          <el-col :span="12">
            <ExpensePieChart />
          </el-col>
          <el-col :span="12">
            <ExpenseBarChart />
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import ExpenseBarChart from '@/components/ExpenseBarChart.vue'
import ExpensePieChart from '@/components/ExpensePieChart.vue'
import { useRouter } from 'vue-router'
import { ref, computed, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { getExpenseList } from "@/api/expense";

export default {
  components: {
    ExpensePieChart,
    ExpenseBarChart
  },
  setup() {
    const router = useRouter();
    const expenses = ref([]);
    const currentDate = ref(new Date().toLocaleDateString());

    // 获取账单数据：已删除userId传参
    const fetchList = async () => {
      try {
        const res = await getExpenseList()
        expenses.value = Array.isArray(res.data.data) ? res.data.data : [];
      } catch (e) {
        ElMessage.error("获取数据失败");
      }
    };

    // 总金额
    const totalAmount = computed(() => {
      if (!Array.isArray(expenses.value)) return 0;
      return expenses.value.reduce((sum, item) => sum + Number(item.amount || 0), 0);
    });

    // 平均金额
    const averageAmount = computed(() => {
      if (!expenses.value.length) return 0;
      return (totalAmount.value / expenses.value.length).toFixed(2);
    });

    // 退出登录【已修复】
    const logout = () => {
      ElMessageBox.confirm('确定退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清空本地所有登录凭证：token、userId、username
        localStorage.clear()
        ElMessage.success('退出成功')
        // 延迟跳转，确保存储先清空，防止路由守卫拦截
        setTimeout(() => {
          router.push('/login')
        }, 100)
      }).catch(() => {})
    }

    onMounted(fetchList)

    return {
      expenses,
      currentDate,
      totalAmount,
      averageAmount,
      logout
    }
  }
}
</script>

<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
}

/* 左侧菜单样式 */
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

.active {
  background: #1677ff;
}

/* 右侧区域 */
.main-content {
  margin-left: 220px;
  flex: 1;
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

/* Hero样式 */
.hero {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px;
  border-radius: 20px;
  color: white;
  margin-bottom: 25px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 10px 30px rgba(102,126,234,.25);
}

.hero h1 {
  margin: 0;
  font-size: 32px;
}

.hero p {
  margin-top: 10px;
  opacity: 0.9;
}

.hero-info {
  margin-top: 15px;
  display: flex;
  gap: 20px;
  font-size: 14px;
  opacity: 0.9;
}

.hero-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-name {
  color: white;
  font-size: 16px;
  font-weight: bold;
}

/* 统计卡片样式 */
.card-row {
  margin-bottom: 20px;
}

.card-content {
  text-align: center;
}

.card-title {
  color: #666;
  margin-bottom: 10px;
}

.card-value {
  font-size: 30px;
  font-weight: bold;
  color: #409EFF;
}

.card-row .el-card {
  transition: all 0.3s;
  border-radius: 16px;
}

.card-row .el-card:hover {
  transform: translateY(-5px);
}

.chart-row {
  margin-top: 20px;
}
</style>