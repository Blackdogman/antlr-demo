package xyz.rockbdm;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import xyz.rockbdm.antlr.demo.parser.HelloLexer;
import xyz.rockbdm.antlr.demo.parser.HelloParser;

public class HelloAntlrTest {
    public static void main(String[] args) {
        HelloLexer lexer = new HelloLexer(CharStreams.fromString("1sf23 hello hellob"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(tokens);
        ParseTree tree = parser.r();
        System.out.println(tree.toStringTree(parser));
    }
}
