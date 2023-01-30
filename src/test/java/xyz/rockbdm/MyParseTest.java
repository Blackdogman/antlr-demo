package xyz.rockbdm;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;
import xyz.rockbdm.antlr.mysql.parser.MySqlGetBloodSourceListener;
import xyz.rockbdm.antlr.mysql.parser.MySqlGetBloodSourceVisitor;
import xyz.rockbdm.antlr.mysql.parser.MySqlLexer;
import xyz.rockbdm.antlr.mysql.parser.MySqlParser;

import java.util.*;

public class MyParseTest {
    // 打印
    public static void main(String[] args) throws Exception {
        String sql = "INSERT INTO T_MENU (a,b,c,d)\n" +
                "SELECT \n" +
                "    a.ID, \n" +
                "    b.NAME AS P_NAME, \n" +
                "    '1' AS status,\n" +
                "    (SELECT a FROM T_BRANCH WHERE a.bid = T_BRANCH.id) AS d\n" +
                "FROM T_MENU2 a, T_USER aa\n" +
                "LEFT JOIN T_MENU3 b ON a.PID = b.ID\n" +
                "JOIN T_MENU5 c ON b.PID = c.ID\n" +
                ";";
        //词法分析器
        MySqlLexer mySqlLexer = new MySqlLexer(CharStreams.fromString(sql.toUpperCase()));
        //词法符号的缓冲区,用于存储词法分析器生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(mySqlLexer);
        //新建一个语法分析器，处理词法符号缓冲区内容
        MySqlParser mySqlParser = new MySqlParser(commonTokenStream);

        MySqlGetBloodSourceListener sourceListener = new MySqlGetBloodSourceListener();
        MySqlGetBloodSourceVisitor sourceVisitor = new MySqlGetBloodSourceVisitor();

//        listener调用
//        ParseTreeWalker.DEFAULT.walk(sourceListener, mySqlParser.sqlStatements());
//        Map<String,List<String>> sourceRes = sourceListener.getRes();

//        visitor调用
        sourceVisitor.visit(mySqlParser.root());
    }

    public static String printSyntaxTree(Parser parser, ParseTree root) {
        StringBuilder buf = new StringBuilder();
        recursive(root, buf, 0, Arrays.asList(parser.getRuleNames()));
        return buf.toString();
    }

    private static void recursive(ParseTree aRoot, StringBuilder buf, int offset, List<String> ruleNames) {
        for (int i = 0; i < offset; i++) {
            buf.append("  ");
        }
        buf.append(Trees.getNodeText(aRoot, ruleNames)).append("\n");
        if (aRoot instanceof ParserRuleContext) {
            ParserRuleContext prc = (ParserRuleContext) aRoot;
            if (prc.children != null) {
                for (ParseTree child : prc.children) {
                    recursive(child, buf, offset + 1, ruleNames);
                }
            }
        }
    }
}
