package pt.up.fe.comp.fsa;

import static junit.framework.TestCase.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class FSAFromRegExp {

    @Test
    public void TestGenericRegExp() {
        try {
            FSA automaton = new FSA("COMP_HW1", "a*bb*|aa*bc*|ef");

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
    public void TestAddAutomatonAsEdge() {
        try {
            FSA automaton = new FSA("aut1", "q0", new LinkedHashSet<String>()); //ef
            automaton.addEdge("q0", 'e', "q1");
            automaton.addEdge("q1", 'f', "q2");
            automaton.addFinalState("q2");

            FSA aut2 = new FSA("aut1", "q0", new LinkedHashSet<String>()); //a*bb*
            aut2.addEdge("q0", 'a', "q0");
            aut2.addEdge("q0", 'b', "q1");
            aut2.addEdge("q1", 'b', "q1");
            aut2.addFinalState("q1");

            FSA aut3 = new FSA("aut1", "q0", new LinkedHashSet<String>()); //aa*bc*
            aut3.addEdge("q0", 'a', "q1");
            aut3.addEdge("q1", 'a', "q1");
            aut3.addEdge("q1", 'b', "q2");
            aut3.addEdge("q2", 'c', "q2");
            aut3.addFinalState("q2");

            automaton.insertFSA("q0", aut2, "q2");
            automaton.insertFSA("q0", aut3, "q2");

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

        } catch (FSAException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void TestInsertFSAWithoutRename() {
        try {
            FSA automaton = new FSA("aut1", "q0", new LinkedHashSet<String>()); //ef
            automaton.addEdge("q0", 'e', "q1");
            automaton.addEdge("q1", 'f', "q2");
            automaton.addFinalState("q2");

            FSA aut2 = new FSA("aut1", "A", new LinkedHashSet<String>()); //a*bb*
            aut2.addEdge("A", 'a', "A");
            aut2.addEdge("A", 'b', "B");
            aut2.addEdge("B", 'b', "B");
            aut2.addFinalState("B");

            FSA aut3 = new FSA("aut1", "S", new LinkedHashSet<String>()); //aa*bc*
            aut3.addEdge("S", 'a', "S1");
            aut3.addEdge("S1", 'a', "S1");
            aut3.addEdge("S1", 'b', "S2");
            aut3.addEdge("S2", 'c', "S2");
            aut3.addFinalState("S2");

            automaton.insertFSA("q0", aut2, "q2");
            automaton.insertFSA("q0", aut3, "q2");

            assertTrue(automaton.getNodes().containsAll(Arrays.asList("q0", "q1", "q2", "A", "B", "S", "S1", "S2")));

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

        } catch (FSAException e) {
            e.printStackTrace();
            fail();
        }
    }
}
