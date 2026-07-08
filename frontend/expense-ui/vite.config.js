import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path"; // 新增

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // 配置 @ 等价于 src 目录
      "@": path.resolve(__dirname, "./src")
    }
  },
  server: {
    port: 5173
  }
});