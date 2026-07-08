import request from "../utils/request";

// 查询当前用户账单（后端从JWT拿userId，无需传参）
export const getExpenseList = () =>
  request.get("/expense/list");

// 搜索：改为POST请求，参数放请求体（后端自动注入当前登录用户userId）
export const searchExpense = (params) =>
  request.post('/expense/search', params)

// 新增（后端强制绑定当前登录用户，前端不用传userId）
export const addExpense = (data) =>
  request.post("/expense/add", data);

// 删除
export const deleteExpense = (id) =>
  request.delete(`/expense/delete?id=${id}`);

// 修改
export const updateExpense = (data) =>
  request.put("/expense/update", data);

//饼图统计（无需传递userId）
export const getTypeStat = () =>
  request.get('/expense/stat/type')

//柱状图月度统计（无需传递userId）
export const getMonthStat = () =>
  request.get('/expense/stat/month')

// 别名兼容组件里的导入名称
export const statByType = getTypeStat
export const statByMonth = getMonthStat

// 导出Excel（后端自动获取当前登录用户，不用传userId）
export const exportExpense = () => {
  return request({
    url: '/expense/export',
    method: 'get',
    responseType: 'blob'
  })
}