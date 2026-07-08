<template>

<div class="layout">

  <!-- 左侧菜单 -->
  <div class="sidebar">

    <div class="logo">
      💰 Finance
    </div>

    <div
      class="menu-item active"
    >
      🏠 首页
    </div>

    <div
      class="menu-item"
    >
      📝 记账管理
    </div>

    <div
      class="menu-item"
    >
      📊 数据统计
    </div>

    <div
      class="menu-item"
    >
      👤 用户中心
    </div>

  </div>

  <!-- 右侧工作区 -->
  <div class="main-content">

    <!-- 原来整个页面内容放这里 -->
    <div class="container">

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

          <el-avatar :size="60">
            A
          </el-avatar>

          <div class="user-name">
            admin
          </div>

          <el-button
            type="danger"
            plain
            @click="logout"
            >
            退出登录
          </el-button>
  

        </div>

      </div>
      <!-- 主体布局 -->
      <div class="main-layout">

      <!-- 左侧 -->
      <div class="left-panel">

      <!-- 搜索 -->
      <el-card class="panel-card">

      <template #header>
        <span>🔍 搜索记录</span>
      </template>

        <el-form label-position="top">

        <el-form-item label="类型">
          <el-select
            v-model="searchType"
            placeholder="全部"
            style="width:100%"
          >
            <el-option label="吃饭" value="food" />
            <el-option label="交通" value="transport" />
            <el-option label="娱乐" value="amusement" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            v-model="searchRemark"
            placeholder="输入关键字"
          />
        </el-form-item>

        <el-button
          type="primary"
          style="width:100%"
          @click="searchItem"
        >
          搜索
        </el-button>

        <el-button
          style="width:100%;margin-top:10px;margin-left:0"
          @click="resetSearch"
        >
          重置
        </el-button>

        </el-form>

        </el-card>

        <!-- 新增 -->
        <el-card class="panel-card">

        <template #header>
        <span>➕ 新增记录</span>
        </template>

        <el-form label-position="top">

        <el-form-item label="类型">

          <el-select
            v-model="newType"
            placeholder="请选择类型"
            style="width:100%"
          >
            <el-option label="吃饭" value="food" />
            <el-option label="交通" value="transport" />
            <el-option label="娱乐" value="amusement" />
            <el-option label="其他" value="other" />
          </el-select>

        </el-form-item>

        <el-form-item label="金额">

          <el-input-number
            v-model="newAmount"
            :min="1"
            style="width:100%"
          />

        </el-form-item>

        <el-form-item label="备注">

          <el-input
            v-model="newRemark"
            placeholder="请输入备注"
          />

        </el-form-item>

        <el-button
          type="success"
          style="width:100%"
          @click="addItem"
        >
          新增记录
        </el-button>

        </el-form>

        </el-card>

        </div>

        <!-- 右侧 -->
        <div class="right-panel">

        <!-- 统计卡片 -->
        <el-row :gutter="20" class="card-row">

        <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-content">
            <div class="card-title">总金额</div>
            <div class="card-value">
              ￥{{ totalAmount }}
            </div>
          </div>
        </el-card>
        </el-col>

        <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-content">
            <div class="card-title">记录数</div>
            <div class="card-value">
              {{ expenses.length }}
            </div>
          </div>
        </el-card>
        </el-col>

        <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-content">
            <div class="card-title">平均金额</div>
            <div class="card-value">
              ￥{{ averageAmount }}
            </div>
          </div>
        </el-card>
        </el-col>

        </el-row>

        <!-- 图表 -->
        <el-row :gutter="20" class="chart-row">

        <el-col :span="12">
        <ExpensePieChart />
        </el-col>

        <el-col :span="12">
        <ExpenseBarChart />
        </el-col>

        </el-row>
        <!-- 表格 -->
        <el-card class="table-card">

        <template #header>
          <span>📒 记账记录</span>
        </template>

        <el-table
          :data="expenses"
          border
          stripe
          height="350"
          >

          <el-table-column
            prop="id"
            label="ID"
            width="80"
          />

          <el-table-column label="类型">

        <template #default="{row}">

        <el-tag
        v-if="row.type==='food'"
        type="success"
        >
        吃饭
        </el-tag>

        <el-tag
        v-else-if="row.type==='transport'"
        type="warning"
        >
        交通
        </el-tag>

        <el-tag
        v-else-if="row.type==='amusement'"
        type="danger"
        >
        娱乐
        </el-tag>

        <el-tag
        v-else
        >
        其他
        </el-tag>

        </template>

        </el-table-column>

          <el-table-column
            prop="amount"
            label="金额"
          />

          <el-table-column
            prop="remark"
            label="备注"
          />

          <el-table-column
            label="操作"
            width="220"
          >

            <template #default="{ row }">

              <el-button
                size="small"
                type="primary"
                @click="openEdit(row)"
              >
                修改
              </el-button>

              <el-button
                size="small"
                type="danger"
                @click="deleteItem(row.id)"
              >
                删除
              </el-button>

            </template>

          </el-table-column>

        </el-table>
        
        </el-card>
        </div>

        </div>


    
    
        <!-- 编辑 -->
        <el-dialog v-model="dialogVisible" title="修改记录" width="400px">

        <el-form :model="editForm" label-width="80px">

        <el-form-item label="类型">
          <el-select v-model="editForm.type">
            <el-option label="吃饭" value="food" />
            <el-option label="交通" value="transport" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>

        <el-form-item label="金额">
          <el-input-number v-model="editForm.amount" :min="1" />
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="editForm.remark" />
        </el-form-item>

        </el-form>

        <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
        </template>

        </el-dialog>

    </div>
  </div>

</div>

</template>

  


<script>
import ExpenseBarChart from './ExpenseBarChart.vue'
import ExpensePieChart from './ExpensePieChart.vue'
import { useRouter } from 'vue-router'
import { ref, computed, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getExpenseList,
  addExpense,
  deleteExpense,
  updateExpense,
  searchExpense
} from "../api/expense";

export default {
  // 注册组件
  components: {
    ExpensePieChart,
    ExpenseBarChart
  },
  setup() {
    //搜索
    const searchType = ref('')
    const searchRemark = ref('')

    const router = useRouter();
    const expenses = ref([]);

    // 新增表单
    const newType = ref("");
    const newAmount = ref(1);
    const newRemark = ref("");

    // 编辑弹窗
    const dialogVisible = ref(false);

    const editForm = ref({
      id: null,
      type: "",
      amount: 0,
      remark: ""
    });

    // 获取列表
    const fetchList = async () => {
      try {
        const userId = localStorage.getItem('userId')
        const res = await getExpenseList(userId)
        expenses.value = Array.isArray(res.data.data)
          ? res.data.data
          : [];
      } catch (e) {
        ElMessage.error("获取数据失败");
      }
    };

    // 新增
    const addItem = async () => {
      if (!newType.value) {
        ElMessage.warning("请选择类型");
        return;
      }
      try {
        await addExpense({
          amount: newAmount.value,
          type: newType.value,
          remark: newRemark.value,
          userId: Number(localStorage.getItem('userId'))
        });
        ElMessage.success("新增成功");
        newType.value = "";
        newAmount.value = 1;
        newRemark.value = "";
        fetchList();
      } catch (e) {
        console.error("新增失败原因：", e);
        ElMessage.error("新增失败");
      }
    };
    //搜索
    const searchItem = async () => {

    try {

      const res = await searchExpense({

        userId: localStorage.getItem('userId'),

        type: searchType.value,

        remark: searchRemark.value

     })

      expenses.value = res.data.data

    } catch (e) {

      ElMessage.error('搜索失败')

    }
}
//重置
  const resetSearch = () => {

  searchType.value = ''

  searchRemark.value = ''

  fetchList()

}
    // 删除
    const deleteItem = async (id) => {
      try {
        await ElMessageBox.confirm("确定删除吗？", "提示", {
          type: "warning"
        });
        await deleteExpense(id);
        ElMessage.success("删除成功");
        fetchList();
      } catch (e) {}
    };

    // 打开编辑
    const openEdit = (row) => {
      editForm.value = { ...row };
      dialogVisible.value = true;
    };

    // 保存编辑
    const saveEdit = async () => {
      try {
        await updateExpense(editForm.value);
        ElMessage.success("修改成功");
        dialogVisible.value = false;
        fetchList();
      } catch (e) {
        ElMessage.error("修改失败");
      }
    };

    // 总金额
    const totalAmount = computed(() => {
      if (!Array.isArray(expenses.value)) return 0;
      return expenses.value.reduce((sum, item) => {
        return sum + Number(item.amount || 0);
      }, 0);
    });

    // 平均值
    const averageAmount = computed(() => {
      if (!expenses.value.length) return 0;
      return (totalAmount.value / expenses.value.length).toFixed(2);
    });

    // 退出登录（移除多余右括号）
    const logout = () => {

  ElMessageBox.confirm(
    '确定退出登录吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(() => {

      localStorage.removeItem('userId')

      ElMessage.success('退出成功')

      router.push('/login')

    })
    .catch(() => {})
}

    onMounted(fetchList);

    // 补充逗号
    return {
      expenses,
      newType,
      newAmount,
      newRemark,
      dialogVisible,
      editForm,
      addItem,
      deleteItem,
      openEdit,
      saveEdit,
      totalAmount,
      averageAmount,
      logout,
      searchType,
      searchRemark,
      searchItem,
      resetSearch,
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 30px auto;
  padding: 20px;
}
.title {
  text-align: center;
  margin-bottom: 30px;
}
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
  font-size: 28px;
  font-weight: bold;
}
.table-card {
  margin-top: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.chart-row {
  margin-top: 20px;
}
.hero {
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 30px;

  border-radius: 20px;

  color: white;

  margin-bottom: 25px;

  background: linear-gradient(
    135deg,
    #667eea 0%,
    #764ba2 100%
  );
}

.hero h1 {
  margin: 0;
  font-size: 32px;
}

.hero p {
  margin-top: 10px;
  opacity: 0.9;
}
/* 新增卡片悬浮动画 */
.card-row .el-card {
  transition: all 0.3s;
}
.card-row .el-card:hover {
  transform: translateY(-5px);
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
.main-layout {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.left-panel {
  width: 240px;
  flex-shrink: 0;
}

.right-panel {
  flex: 1;
}

.panel-card {
  margin-bottom: 20px;
}

.panel-card :deep(.el-card__header) {
  font-weight: 600;
  font-size: 16px;
}

.table-card {
  margin-top: 20px;
}

.chart-row {
  margin-top: 20px;
}

.card-row .el-card {
  border-radius: 16px;
}

.panel-card {
  border-radius: 16px;
}

.table-card {
  border-radius: 16px;
}

.card-value {
  font-size: 30px;
  font-weight: bold;
  color: #409EFF;
}

.card-title {
  color: #666;
  margin-bottom: 10px;
}

.left-panel .el-button {
  border-radius: 10px;
}

.hero {
  box-shadow: 0 10px 30px rgba(102,126,234,.25);
}
.layout {
  display: flex;
  min-height: 100vh;
}

/* 左侧菜单 */
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

/* 右侧工作区 */
.main-content {
  margin-left: 220px;

  flex: 1;

  padding: 20px;
}
</style>