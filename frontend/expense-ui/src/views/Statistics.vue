<template>
  <div class="layout">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="logo">💰 Finance</div>
      <div class="menu-item" @click="$router.push('/dashboard')">🏠 首页</div>
      <div class="menu-item" @click="$router.push('/expense')">📝 记账管理</div>
      <div class="menu-item active">📊 数据统计</div>
      <div class="menu-item" @click="$router.push('/profile')">👤 用户中心</div>
    </div>

    <!-- 右侧内容区域 -->
    <div class="main-content">
      <div class="page-header">
        <h2>📊 数据统计分析</h2>
        <p class="desc">查看消费趋势、分类占比以及历史统计数据</p>
      </div>

      <!-- 四张带主题色+图标统计卡片 -->
      <el-row :gutter="20" class="card-row">
        <el-col :span="6">
          <el-card shadow="hover" class="stat-card card-blue">
            <div class="card-icon">💰</div>
            <div class="card-label">总消费</div>
            <div class="card-value">￥{{ totalAmount }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="stat-card card-green">
            <div class="card-icon">📅</div>
            <div class="card-label">本月消费</div>
            <div class="card-value">￥{{ currentMonthAmount }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="stat-card card-orange">
            <div class="card-icon">📈</div>
            <div class="card-label">平均消费</div>
            <div class="card-value">￥{{ averageAmount }}</div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="stat-card card-red">
            <div class="card-icon">🔥</div>
            <div class="card-label">最大消费</div>
            <div class="card-value">￥{{ maxAmount }}</div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 饼图 + 柱状图区域 -->
      <el-row :gutter="20" class="chart-row">
        <el-col :span="12">
          <ExpensePieChart />
        </el-col>
        <el-col :span="12">
          <ExpenseBarChart />
        </el-col>
      </el-row>

      <!-- 折线图区域 -->
      <el-row class="chart-row">
        <el-col :span="24">
          <el-card shadow="hover">
            <template #header>
              <span>消费趋势</span>
            </template>
            <div class="empty-tip" style="height: 350px;display: flex;align-items: center;justify-content: center;color:#999;">
              折线图开发中...
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 底部：排行榜 + 月度统计表 -->
      <el-row :gutter="20" class="chart-row">
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <span>消费排行榜</span>
            </template>
            <div class="empty-tip" style="height: 350px;display: flex;align-items: center;justify-content: center;color:#999;">
              开发中...
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <span>月度统计</span>
            </template>
            <div class="empty-tip" style="height: 350px;display: flex;align-items: center;justify-content: center;color:#999;">
              开发中...
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import ExpensePieChart from '@/components/ExpensePieChart.vue'
import ExpenseBarChart from '@/components/ExpenseBarChart.vue'
import { ref, computed, onMounted } from "vue"
import { getExpenseList } from "@/api/expense"
import { ElMessage } from "element-plus"

export default {
  components: {
    ExpensePieChart,
    ExpenseBarChart
  },
  setup() {
    const expenses = ref([])

    // 获取所有账单数据
    const fetchList = async () => {
      try {
        const res = await getExpenseList()
        expenses.value = res.data.data || []
      } catch (e) {
        ElMessage.error("获取统计数据失败")
      }
    }

    // 总消费
    const totalAmount = computed(() => {
      return expenses.value.reduce(
        (sum, item) => sum + Number(item.amount),
        0
      )
    })

    // 最大消费
    const maxAmount = computed(() => {
      if (expenses.value.length == 0) {
        return 0
      }
      return Math.max(
        ...expenses.value.map(item => Number(item.amount))
      )
    })

    // 平均消费
    const averageAmount = computed(() => {
      if (expenses.value.length == 0) {
        return 0
      }
      return (
        totalAmount.value /
        expenses.value.length
      ).toFixed(2)
    })

    // 本月消费
    const currentMonthAmount = computed(() => {
      const now = new Date()
      const year = now.getFullYear()
      const month = now.getMonth()
      return expenses.value
        .filter(item => {
          const d = new Date(item.createTime)
          return (
            d.getFullYear() == year &&
            d.getMonth() == month
          )
        })
        .reduce(
          (sum, item) => sum + Number(item.amount),
          0
        )
    })

    onMounted(() => {
      fetchList()
    })

    return {
      totalAmount,
      currentMonthAmount,
      averageAmount,
      maxAmount
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
.page-header {
  margin-bottom: 24px;
}
.page-header h2 {
  margin: 0 0 8px 0;
}
.desc {
  color: #666;
  margin: 0;
}
.card-row {
  margin-bottom: 20px;
}
.stat-card {
  text-align: center;
  border-radius: 12px;
  padding: 20px 10px;
  transition: all 0.3s;
}
.stat-card:hover {
  transform: translateY(-4px);
}
/* 卡片顶部图标 */
.card-icon {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 50%;
  margin: 0 auto 14px;
  font-size: 22px;
}
.card-label {
  font-size: 15px;
  color: #666;
  margin-bottom: 12px;
}
.card-value {
  font-size: 32px;
  font-weight: bold;
}
/* 四种主题色 */
.card-blue .card-icon {
  background: rgba(64, 158, 255, 0.12);
  color: #409EFF;
}
.card-blue .card-value {
  color: #409EFF;
}

.card-green .card-icon {
  background: rgba(103, 194, 58, 0.12);
  color: #67C23A;
}
.card-green .card-value {
  color: #67C23A;
}

.card-orange .card-icon {
  background: rgba(230, 162, 60, 0.12);
  color: #E6A23C;
}
.card-orange .card-value {
  color: #E6A23C;
}

.card-red .card-icon {
  background: rgba(245, 108, 108, 0.12);
  color: #F56C6C;
}
.card-red .card-value {
  color: #F56C6C;
}

.chart-row {
  margin-bottom: 20px;
}
</style>