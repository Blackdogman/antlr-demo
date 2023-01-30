package xyz.rockbdm.antlr.mysql.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MySqlGetBloodSourceListener extends MySqlParserBaseListener {
    private final Map<String, List<String>> resMap = new HashMap<>();

//    /**
//     * 获取表名
//     * @param ctx the parse tree
//     */
//    @Override
//    public void enterTableSources(MySqlParser.TableSourcesContext ctx) {
//        System.out.println("-----------enterTableSources-----------");
//        List<MySqlParser.TableSourceContext> tableSourceContexts = ctx.getRuleContexts(MySqlParser.TableSourceContext.class);
//        for (MySqlParser.TableSourceContext tableSource : tableSourceContexts) {
//            //通过tableSourceItems获取表名
//            getTableNameByTableSourceItems(tableSource.getRuleContexts(MySqlParser.TableSourceItemContext.class));
//            //获取join部分
//            List<MySqlParser.OuterJoinContext> joinContexts = tableSource.getRuleContexts(MySqlParser.OuterJoinContext.class);
//            for (MySqlParser.OuterJoinContext joinContext : joinContexts) {
//                List<MySqlParser.TableSourceItemContext> tableSourceItemContexts = joinContext.getRuleContexts(MySqlParser.TableSourceItemContext.class);
//                getTableNameByTableSourceItems(tableSourceItemContexts);
//            }
//        }
//    }
//
//    private void getTableNameByTableSourceItems(List<MySqlParser.TableSourceItemContext> tableSourceItems) {
//        for (MySqlParser.TableSourceItemContext tableSourceItem : tableSourceItems) {
//            List<MySqlParser.TableNameContext> tableNameContexts = tableSourceItem.getRuleContexts(MySqlParser.TableNameContext.class);
//            for (MySqlParser.TableNameContext tableNameContext : tableNameContexts) {
//                // 添加至结果集
//                System.out.println("tableNameContext: " + tableNameContext.getText());
//            }
//        }
//    }

    @Override
    public void enterInsertStatement(MySqlParser.InsertStatementContext ctx) {
        String targetTable = "";
        String targetColumn = "";

        System.out.println("-----------enterInsertStatement-----------");
        // insert into的目标表
        System.out.println("-----------TableNameContext-----------");
        MySqlParser.TableNameContext statementContext = ctx.getRuleContext(MySqlParser.TableNameContext.class, 0);
        targetTable = Objects.isNull(statementContext) ? "" : statementContext.getText();
        System.out.println(targetTable);

        // insert into目标表的字段集合, 如果为空则需要使用查询块的字段集合
        System.out.println("-----------FullColumnNameListContext-----------");
        MySqlParser.FullColumnNameListContext columnNameListContext = ctx.getRuleContext(MySqlParser.FullColumnNameListContext.class, 0);
        targetColumn = Objects.isNull(columnNameListContext) ? "" : columnNameListContext.getText();
        System.out.println(targetColumn);

        // 获得血缘数据来源
        System.out.println("-----------insertValuesContext-----------");
        MySqlParser.InsertStatementValueContext insertValuesContext = ctx.getRuleContext(MySqlParser.InsertStatementValueContext.class, 0);
        if (!Objects.isNull(insertValuesContext)) {
            System.out.println("-----------simpleSelectContext-----------");
            MySqlParser.SimpleSelectContext simpleSelectContext = insertValuesContext.getRuleContext(MySqlParser.SimpleSelectContext.class, 0);
            if(!Objects.isNull(simpleSelectContext)) {
                System.out.println("-----------querySpecificationContext-----------");
                MySqlParser.QuerySpecificationContext querySpecificationContext = simpleSelectContext.getRuleContext(MySqlParser.QuerySpecificationContext.class, 0);
                System.out.println(querySpecificationContext.getText());

                System.out.println("-----------selectElementsContexts-----------");
                // source selectElementsContexts
                MySqlParser.SelectElementsContext selectElementsContext = querySpecificationContext.getRuleContext(MySqlParser.SelectElementsContext.class,0);
                System.out.println(selectElementsContext.getText());

                System.out.println("-----------fromClauseContexts-----------");
                // source fromClauseContexts
                List<MySqlParser.FromClauseContext> fromClauseContexts = querySpecificationContext.getRuleContexts(MySqlParser.FromClauseContext.class);
                for (MySqlParser.FromClauseContext fromClauseContext : fromClauseContexts) {
                    System.out.println(fromClauseContext.getText());
                }
            }
        }

        // SimpleSelectContext
        // QuerySpecificationContext
//        System.out.println("---------result---------");
//        System.out.println("targetTable: " + targetTable);
//        System.out.println("targetColumn: " + targetColumn);
    }

    public Map<String, List<String>> getRes() {
        return this.resMap;
    }
}
