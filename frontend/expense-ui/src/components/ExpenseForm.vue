<template>
  <div>
    <h2>{{ form.id ? "编辑账单" : "添加账单" }}</h2>

    <div>
      <input v-model="form.amount" placeholder="金额" />
    </div>

    <div>
      <input v-model="form.type" placeholder="类型" />
    </div>

    <div>
      <input v-model="form.remark" placeholder="备注" />
    </div>

    <button @click="submit">提交</button>
    <button @click="$emit('close')">取消</button>
  </div>
</template>

<script>
import { addExpense, updateExpense } from "../api/expense";

export default {
  props: ["data"],
  data() {
    return {
      form: {
        id: null,
        amount: "",
        type: "",
        remark: ""
      }
    };
  },
  watch: {
    data: {
      immediate: true,
      handler(val) {
        if (val) this.form = { ...val };
      }
    }
  },
  methods: {
    async submit() {
      if (this.form.id) {
        await updateExpense(this.form);
      } else {
        await addExpense(this.form);
      }
      this.$emit("success");
    }
  }
};
</script>