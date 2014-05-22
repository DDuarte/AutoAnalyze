package pt.up.fe.comp.fsa;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static junit.framework.TestCase.*;

public class FSAMinimizationTest {
    @Test
    public void MinimizationTest() {
        try {
            FSA newAut = new FSA("aut", "S", new LinkedHashSet<String>());
            newAut.addEdge("S", 'a', "A");
            newAut.addEdge("A", 'b', "B");
            newAut.addEdge("B", 'a', "A");

            newAut.addFinalState("A");

            newAut.minimize();

            assertTrue(newAut.getNodes().containsAll(Arrays.asList("S", "A")));
            assertTrue(newAut.getNodes().size() == 2);

            assertTrue(newAut.getNodeEdges("S").size() == 1);
            assertTrue(newAut.getNodeEdges("A").size() == 1);

            assertTrue(newAut.getNodeEdges("S").contains(new FSA.Edge('a', "A")));
            assertTrue(newAut.getNodeEdges("A").contains(new FSA.Edge('b', "S")));

        } catch (FSAException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Case from http://www.cs.umd.edu/class/fall2009/cmsc330/lectures/discussion2.pdf
     */
    @Test
    public void MinimizationTest2() {
        try {
            FSA newAut = new FSA("aut", "A", new LinkedHashSet<String>());
            newAut.addEdge("A", '1', "B");
            newAut.addEdge("A", '0', "H");
            newAut.addEdge("B", '1', "A");
            newAut.addEdge("B", '0', "H");
            newAut.addEdge("H", '1', "C");
            newAut.addEdge("H", '0', "C");
            newAut.addEdge("C", '0', "E");
            newAut.addEdge("C", '1', "F");
            newAut.addNode("D");
            newAut.addEdge("D", '0', "E");
            newAut.addEdge("D", '1', "F");
            newAut.addEdge("E", '0', "F");
            newAut.addEdge("E", '1', "G");
            newAut.addEdge("F", '0', "F");
            newAut.addEdge("F", '1', "F");
            newAut.addEdge("G", '0', "G");
            newAut.addEdge("G", '1', "G");

            newAut.addFinalState("F");
            newAut.addFinalState("G");

            newAut.minimize();

            assertTrue(newAut.getNodes().size() == 5);
            assertTrue(newAut.getNodes().contains("A") || newAut.getNodes().contains("B"));
            assertFalse(newAut.getNodes().contains("A") && newAut.getNodes().contains("B"));
            assertTrue(newAut.getNodes().contains("C") || newAut.getNodes().contains("D"));
            assertFalse(newAut.getNodes().contains("C") && newAut.getNodes().contains("D"));
            assertTrue(newAut.getNodes().contains("F") || newAut.getNodes().contains("G"));
            assertFalse(newAut.getNodes().contains("F") && newAut.getNodes().contains("G"));
            assertTrue(newAut.getNodes().contains("H"));
            assertTrue(newAut.getNodes().contains("E"));


            assertTrue(newAut.getNodeEdges("A").containsAll(Arrays.asList(new FSA.Edge('0', "H"), new FSA.Edge('1', "A"))));
            assertTrue(newAut.getNodeEdges("A").size() == 2);
            assertTrue(newAut.getNodeEdges("H").containsAll(Arrays.asList(new FSA.Edge('1', "C"), new FSA.Edge('0', "C"))));
            assertTrue(newAut.getNodeEdges("H").size() == 2);
            assertTrue(newAut.getNodeEdges("C").containsAll(Arrays.asList(new FSA.Edge('0', "E"), new FSA.Edge('1', "F"))));
            assertTrue(newAut.getNodeEdges("C").size() == 2);
            assertTrue(newAut.getNodeEdges("E").containsAll(Arrays.asList(new FSA.Edge('0', "F"), new FSA.Edge('1', "F"))));
            assertTrue(newAut.getNodeEdges("E").size() == 2);
            assertTrue(newAut.getNodeEdges("F").containsAll(Arrays.asList(new FSA.Edge('0', "F"), new FSA.Edge('1', "F"))));
            assertTrue(newAut.getNodeEdges("F").size() == 2);

        } catch (FSAException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Case from http://www.cs.umd.edu/class/fall2009/cmsc330/lectures/discussion2.pdf
     */
    @Test
    public void MinimizationTest3() {
        try {
            FSA newAut = new FSA("aut", "A", new LinkedHashSet<String>());
            newAut.addEdge("A", '0', "B");
            newAut.addEdge("A", '1', "C");
            newAut.addEdge("B", '0', "D");
            newAut.addEdge("B", '1', "D");
            newAut.addEdge("C", '0', "D");
            newAut.addEdge("C", '1', "D");
            newAut.addEdge("D", '0', "E");
            newAut.addEdge("D", '1', "E");

            newAut.addFinalState("E");

            newAut.minimize();


            assertTrue(newAut.getNodes().size() == 4);
            assertTrue(newAut.getNodes().contains("A"));
            assertTrue(newAut.getNodes().contains("B") || newAut.getNodes().contains("C"));
            assertFalse(newAut.getNodes().contains("B") && newAut.getNodes().contains("C"));
            assertTrue(newAut.getNodes().contains("D"));
            assertTrue(newAut.getNodes().contains("E"));


            assertTrue(newAut.getNodeEdges("A").containsAll(Arrays.asList(new FSA.Edge('0', "B"), new FSA.Edge('1', "B"))));
            assertTrue(newAut.getNodeEdges("A").size() == 2);
            assertTrue(newAut.getNodeEdges("B").containsAll(Arrays.asList(new FSA.Edge('0', "D"), new FSA.Edge('1', "D"))));
            assertTrue(newAut.getNodeEdges("B").size() == 2);
            assertTrue(newAut.getNodeEdges("D").containsAll(Arrays.asList(new FSA.Edge('0', "E"), new FSA.Edge('1', "E"))));
            assertTrue(newAut.getNodeEdges("D").size() == 2);
            assertTrue(newAut.getNodeEdges("E").isEmpty());

        } catch (FSAException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void TestMaintainLanguage() {
        try {
            FSA a = FSALoader.LoadFromFile("dot_dfa_examples/COMP_HW1.gv");
            FSA automaton = FSALoader.LoadFromFile("dot_dfa_examples/COMP_HW1_NFA.gv");

            a.minimize();


            assertTrue(a.accepts("ef"));
            assertTrue(a.accepts("abc"));
            assertTrue(a.accepts("aaabccccc"));
            assertTrue(a.accepts("aaabbbbbb"));
            assertTrue(a.accepts("abbbb"));
            assertTrue(a.accepts("bbbb"));

            assertFalse(a.accepts(""));
            assertFalse(a.accepts("e"));
            assertFalse(a.accepts("eff"));
            assertFalse(a.accepts("abbc"));
            assertFalse(a.accepts("bcccc"));
            assertFalse(a.accepts("sfgddd"));
            assertFalse(a.accepts("aaacccc"));

            automaton.minimize();

            assertTrue(automaton.accepts("ef"));
            assertTrue(automaton.accepts("abc"));
            assertTrue(automaton.accepts("aaabccccc"));
            assertTrue(automaton.accepts("aaabbbbbb"));
            assertTrue(automaton.accepts("abbbb"));
            assertTrue(automaton.accepts("bbbb"));

            assertFalse(automaton.accepts(""));
            assertFalse(automaton.accepts("e"));
            assertFalse(automaton.accepts("eff"));
            assertFalse(automaton.accepts("abbc"));
            assertFalse(automaton.accepts("bcccc"));
            assertFalse(automaton.accepts("sfgddd"));
            assertFalse(automaton.accepts("aaacccc"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void TestMaintainLanguage2() {
        try {
            FSA a = new FSA("aut", "a{20}");
            FSA a2 = new FSA("aut", "a{20}");

            a.minimize();

            assertTrue(a.equals(a2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
