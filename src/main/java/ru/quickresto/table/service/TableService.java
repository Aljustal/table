package ru.quickresto.table.service;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TableService {
    public double calculateFormula(String formula, Map<String, String> cells) throws Exception {
        for (Map.Entry<String, String> entry : cells.entrySet()) {
            String cellReference = entry.getKey();
            String cellValue = entry.getValue();
            formula = formula.replaceAll("(?<![A-Za-z0-9_])" + cellReference + "(?![A-Za-z0-9_])", cellValue);
        }

        Expression expression = new ExpressionBuilder(formula).build();
        return expression.evaluate();
    }
}
