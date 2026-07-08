<template>
  <el-card class="chart-card">
    <template #header>
      <span>月度消费趋势</span>
    </template>

    <div ref="chartRef" class="chart"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from "vue"
import * as echarts from "echarts"
import { statByMonth } from "@/api/expense"

const chartRef = ref()

const initChart = async () => {

  const chart = echarts.init(chartRef.value)

  const userId =
  localStorage.getItem('userId')

const res =
  await statByMonth(userId)
  console.log("月度统计", res.data)

  const monthList = res.data.data.map(
    item => item.month
  )

  const totalList = res.data.data.map(
    item => item.total
  )

  chart.setOption({

    tooltip: {
      trigger: "axis"
    },

    xAxis: {
      type: "category",
      data: monthList
    },

    yAxis: {
      type: "value"
    },

    series: [
      {
        name: "支出金额",
        type: "bar",
        data: totalList
      }
    ]
  })
}

onMounted(() => {
  initChart()
})
</script>

<style scoped>
.chart-card {
  margin-top: 20px;
}

.chart {
  height: 400px;
}

</style>