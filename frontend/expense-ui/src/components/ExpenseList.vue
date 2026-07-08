<template>
  <div>
    <h2>账单列表</h2>

    <button @click="$emit('add')">➕ 添加</button>

    <table border="1" cellpadding="6">
      <tr>
        <th>ID</th>
        <th>金额</th>
        <th>类型</th>
        <th>备注</th>
        <th>时间</th>
        <th>操作</th>
      </tr>

      <tr v-for="item in list" :key="item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.amount }}</td>
        <td>{{ item.type }}</td>
        <td>{{ item.remark }}</td>
        <td>{{ item.createTime }}</td>
        <td>
          <button @click="$emit('edit', item)">编辑</button>
          <button @click="remove(item.id)">删除</button>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { listExpense, deleteExpense } from "../api/expense";

export default {
  data() {
    return {
      list: []
    };
  },
  methods: {
    async load() {
      const res = await listExpense();
      this.list = res.data.data;
    },
    async remove(id) {
      await deleteExpense(id);
      this.load();
    }
  },
  mounted() {
    this.load();
  }
};
</script>