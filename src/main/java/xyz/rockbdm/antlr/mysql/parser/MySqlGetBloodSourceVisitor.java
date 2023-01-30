package xyz.rockbdm.antlr.mysql.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import xyz.rockbdm.antlr.utils.Finder;

import java.util.*;

public class MySqlGetBloodSourceVisitor extends MySqlParserBaseVisitor{

    // 目标集合
    private final Map<String, List<ParseTree>> targetMap = new HashMap<>();
    private final Map<String, List<ParseTree>> sourceMap = new HashMap<>();

    @Override
    public Object visitInsertStatement(MySqlParser.InsertStatementContext ctx) {
        String targetTable;
        String targetColumn;
        // insert into的目标表
        System.out.println("-----------targetTable-----------");
        MySqlParser.TableNameContext statementContext = ctx.getRuleContext(MySqlParser.TableNameContext.class, 0);
        targetTable = Objects.isNull(statementContext) ? "" : statementContext.getText();
        System.out.println(targetTable);

        // insert into目标表的字段集合, 如果为空则需要使用查询块的字段集合
        System.out.println("-----------targetColumn-----------");
        MySqlParser.FullColumnNameListContext columnNameListContext = ctx.getRuleContext(MySqlParser.FullColumnNameListContext.class, 0);
        targetColumn = Objects.isNull(columnNameListContext) ? "" : columnNameListContext.getText();
        System.out.println(targetColumn);

        // 获得血缘数据来源
        System.out.println("-----------insertValuesContext-----------");
        MySqlParser.InsertStatementValueContext insertValuesContext = ctx.getRuleContext(MySqlParser.InsertStatementValueContext.class, 0);
        Finder soutceFinder = new Finder(this.targetMap);
        if (!Objects.isNull(insertValuesContext)) {
            soutceFinder.find(insertValuesContext, MySqlParser.TableSourcesContext.class);
            soutceFinder.find(insertValuesContext, MySqlParser.SelectColumnElementContext.class);
            soutceFinder.getFind(MySqlParser.TableSourcesContext.class).forEach(item -> {
                soutceFinder.find(item, MySqlParser.AtomTableItemContext.class);
                soutceFinder.find(item, MySqlParser.FullColumnNameContext.class);
            });
            soutceFinder.getFind(MySqlParser.TableSourcesContext.class).forEach(item -> {
                System.out.println(item.getText());
            });
            soutceFinder.getFind(MySqlParser.SelectColumnElementContext.class).forEach(item -> {
                System.out.println(item.getText());
            });
            soutceFinder.getFind(MySqlParser.AtomTableItemContext.class).forEach(item -> {
                System.out.println(item.getText());
            });
            soutceFinder.getFind(MySqlParser.FullColumnNameContext.class).forEach(item -> {
                System.out.println(item.getText());
            });
        }
        return null;
    }

    public Map<String, List<ParseTree>> getTargetMap() {
        return this.targetMap;
    }
}
