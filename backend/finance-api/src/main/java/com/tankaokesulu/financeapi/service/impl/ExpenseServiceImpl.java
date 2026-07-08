package com.tankaokesulu.financeapi.service.impl;
import com.tankaokesulu.financeapi.dto.ExpenseSearchDTO;
import com.tankaokesulu.financeapi.entity.Expense;
import com.tankaokesulu.financeapi.mapper.ExpenseMapper;
import com.tankaokesulu.financeapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseMapper expenseMapper;

    @Override
    public void save(Expense expense) {
        expenseMapper.insert(expense);
    }

    @Override
    public List<Expense> list(Long userId) {

        ExpenseSearchDTO dto =
                new ExpenseSearchDTO();

        dto.setUserId(userId);

        return expenseMapper.search(dto);
    }

    @Override
    public void delete(Long id) {
        expenseMapper.deleteById(id);
    }

    @Override
    public void update(Expense expense) {
        expenseMapper.update(expense);
    }
    @Override
    public List<Map<String,Object>> statByType(Long userId) {
        return expenseMapper.statByType(userId);
    }

    @Override
    public List<Map<String,Object>> statByMonth(Long userId) {
        return expenseMapper.statByMonth(userId);
    }
    @Override
    public List<Expense> search(ExpenseSearchDTO dto) {
        return expenseMapper.search(dto);
    }
}