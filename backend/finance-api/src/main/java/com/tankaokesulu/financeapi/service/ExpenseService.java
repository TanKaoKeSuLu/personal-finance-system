package com.tankaokesulu.financeapi.service;
import com.tankaokesulu.financeapi.dto.ExpenseSearchDTO;
import com.tankaokesulu.financeapi.entity.Expense;

import java.util.List;
import java.util.Map;

/**
 * 费用服务接口
 * 该接口定义了与费用相关的操作方法
 */
public interface ExpenseService {
    void save(Expense expense);
    List<Expense> list(Long userId);
    void delete(Long id);
    void update(Expense expense);
    List<Map<String, Object>> statByType(Long userId);
    List<Map<String, Object>> statByMonth(Long userId);
    // 新增重载
    List<Expense> search(ExpenseSearchDTO dto);
}