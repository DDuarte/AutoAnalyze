package pt.up.fe.comp.fsa;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Test;
import pt.up.fe.comp.dot.DotVisitor;
import pt.up.fe.comp.dot.ir.DotGraph;
import pt.up.fe.comp.dot.parser.dotLexer;
import pt.up.fe.comp.dot.parser.dotParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

public class FSAWriteTest {

    @Test
    public void testToDot() {
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(new FileInputStream("dot_dfa_examples/fsm.gv"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        dotLexer lex = new dotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        dotParser parser = new dotParser(tokens);
        ParseTree tree = parser.graph();

        DotVisitor eva = new DotVisitor();
        DotGraph graph = eva.visit(tree);

        FSA automaton = FSABuilder.buildFrom(graph);


        automaton.writeDot("dot_dfa_examples/testToDot.gv");

        ANTLRInputStream newInput = null;
        try {
            newInput = new ANTLRInputStream(new FileInputStream("dot_dfa_examples/testToDot.gv"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        dotLexer newLex = new dotLexer(newInput);
        CommonTokenStream newTokens = new CommonTokenStream(newLex);
        dotParser newParser = new dotParser(newTokens);
        ParseTree newTree = newParser.graph();

        DotVisitor newEva = new DotVisitor();
        DotGraph newGraph = newEva.visit(newTree);

        FSA newAut = FSABuilder.buildFrom(newGraph);

        assertTrue(compareSets(automaton.getFinalStates(), newAut.getFinalStates()));
        assertTrue(compareSets(automaton.getNodes(),newAut.getNodes()));

        for (String node: automaton.getNodes()) {
            try {
                assertTrue(compareSets(automaton.getNodeEdges(node),newAut.getNodeEdges(node)));
            } catch (NoSuchNodeException e) {
                e.printStackTrace();
                fail();
            }
        }

    }

    @Test
    public void testToHaskell() {
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(new FileInputStream("dot_dfa_examples/COMP_HW1.gv"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        dotLexer lex = new dotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        dotParser parser = new dotParser(tokens);
        ParseTree tree = parser.graph();

        DotVisitor eva = new DotVisitor();
        DotGraph graph = eva.visit(tree);

        FSA automaton = FSABuilder.buildFrom(graph);


        automaton.writeHaskell("COMP_HW1.hs");
        System.out.println(automaton);
        System.out.println("To test this you must run the Haskell code");
    }

    @Test
    public void testToHaskellNonDeterministic() {
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(new FileInputStream("dot_dfa_examples/COMP_HW1_NFA.gv"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        dotLexer lex = new dotLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        dotParser parser = new dotParser(tokens);
        ParseTree tree = parser.graph();

        DotVisitor eva = new DotVisitor();
        DotGraph graph = eva.visit(tree);

        FSA automaton = FSABuilder.buildFrom(graph);


        automaton.writeHaskell("COMP_HW1_NFA.hs");
        automaton.makeDeterministic();
        System.out.println(automaton);
        System.out.println("To test this you must run the Haskell code");
    }

    private static <T> boolean compareSets(Set<T> s1, Set<T> s2){
        if (s1.size() != s2.size())
            return false;

        LinkedList<T> newS2 = new LinkedList<>(s2);

        for (T elem : s1) {
            if (!newS2.contains(elem))
                return false;
            newS2.removeFirstOccurrence(elem);
        }

        return newS2.isEmpty();
    }
}
