package com.tankaokesulu.financeapi.mapper;
import com.tankaokesulu.financeapi.dto.ExpenseSearchDTO;
import com.tankaokesulu.financeapi.entity.Expense;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * ExpenseMapper接口，用于处理与Expense实体相关的数据库操作
 * 使用@Mapper注解标记为MyBatis的映射接口
 */
@Mapper
public interface ExpenseMapper {

    List<Expense> search(
            ExpenseSearchDTO dto
    );

    @Insert("""
INSERT INTO expense(
    amount,
    type,
    remark,
    create_time,
    user_id
)
VALUES (
    #{amount},
    #{type},
    #{remark},
    #{createTime},
    #{userId}
)
""")
    @Options(useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id")
    void insert(Expense expense);

    @Delete("""
DELETE FROM expense
WHERE id = #{id}
""")
    void deleteById(Long id);

    @Update("""
UPDATE expense
SET amount = #{amount},
    type = #{type},
    remark = #{remark}
WHERE id = #{id}
""")
    void update(Expense expense);

    @Select("""
SELECT type,
       SUM(amount) AS total
FROM expense
WHERE user_id = #{userId}
GROUP BY type
""")
    List<Map<String,Object>> statByType(Long userId);

    @Select("""
SELECT DATE_FORMAT(create_time,'%Y-%m') AS month,
       SUM(amount) AS total
FROM expense
WHERE user_id = #{userId}
GROUP BY month
ORDER BY month
""")
    List<Map<String,Object>> statByMonth(Long userId);

}