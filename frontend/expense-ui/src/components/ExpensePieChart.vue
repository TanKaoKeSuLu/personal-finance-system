<template>
  <el-card class="chart-card">
    <template #header>
      <span>消费分类占比</span>
    </template>

    <div ref="chartRef" class="chart"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from "vue"
import * as echarts from "echarts"
import { statByType } from "@/api/expense"

const chartRef = ref()

const initChart = async () => {

  const chart = echarts.init(chartRef.value)

  const userId =
    localStorage.getItem('userId')

  const res =
    await statByType(userId)

  console.log("分类统计", res.data)

  const chartData = res.data.data.map(item => ({
    name: item.type,
    value: item.total
  }))

  chart.setOption({
    title: {
      text: "消费分类占比",
      left: "center"
    },
    tooltip: {
      trigger: "item"
    },
    legend: {
      bottom: 0
    },
    series: [
      {
        type: "pie",
        radius: "60%",
        data: chartData
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