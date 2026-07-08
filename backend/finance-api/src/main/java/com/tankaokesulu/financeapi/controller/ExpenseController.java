package com.tankaokesulu.financeapi.controller;

import com.tankaokesulu.financeapi.Result;
import com.tankaokesulu.financeapi.dto.ExpenseSearchDTO;
import com.tankaokesulu.financeapi.entity.Expense;
import com.tankaokesulu.financeapi.entity.ExpenseExcel;
import com.tankaokesulu.financeapi.service.ExpenseService;
import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/list")
    public Result<List<Expense>> list(HttpServletRequest request) {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        List<Expense> expenses = expenseService.list(userId);
        return Result.success(expenses);
    }

    @PostMapping("/add")
    public Result<Expense> add(@RequestBody Expense expense, HttpServletRequest request) {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        expense.setUserId(userId);
        expense.setCreateTime(LocalDateTime.now());
        expenseService.save(expense);
        return Result.success(expense);
    }

    @DeleteMapping("/delete")
    public Result<Void> delete(@RequestParam Long id) {
        expenseService.delete(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Expense expense) {
        expenseService.update(expense);
        return Result.success(null);
    }

    @GetMapping("/stat/type")
    public Result<List<Map<String, Object>>> statByType(HttpServletRequest request) {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        return Result.success(expenseService.statByType(userId));
    }

    @GetMapping("/stat/month")
    public Result<List<Map<String, Object>>> statByMonth(HttpServletRequest request) {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        return Result.success(expenseService.statByMonth(userId));
    }

    @PostMapping("/search")
    public Result<List<Expense>> search(@RequestBody ExpenseSearchDTO dto, HttpServletRequest request) {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        dto.setUserId(userId);
        return Result.success(expenseService.search(dto));
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object userIdObj = request.getAttribute("userId");
        Long userId = Long.valueOf(userIdObj.toString());
        List<Expense> list = expenseService.list(userId);
        List<ExpenseExcel> excelList = new ArrayList<>();

        for (Expense item : list) {
            ExpenseExcel excel = new ExpenseExcel();
            excel.setAmount(item.getAmount().toString());
            excel.setType(item.getType());
            excel.setRemark(item.getRemark());
            excel.setCreateTime(item.getCreateTime().toString());
            excelList.add(excel);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = java.net.URLEncoder.encode("个人账单.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        EasyExcel.write(response.getOutputStream(), ExpenseExcel.class)
                .sheet("账单数据")
                .doWrite(excelList);
    }
}