package pt.up.fe.comp.fsa;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class FSASimpleTest {
    private FSA automaton;

    @Before
    public void init() {
        try {
            automaton = new FSA("lol", "lol", new HashSet<String>());
        } catch (DuplicateElementException e) {
            fail();
        }
    }

    @Test
    public void addNodeTest(){
        try {
            automaton.addNode("q1");
        } catch (DuplicateElementException e) {
            fail();
        }
        Set<String> nodes = automaton.getNodes();
        assertTrue("NodeSet doesn't contain added node.", nodes.contains("q1"));
    }
    
    @Test(expected = DuplicateElementException.class)
    public void addDuplicateNode() throws DuplicateElementException {
        try {
            automaton.addNode("q1");
        } catch (DuplicateElementException e) {
            fail();
        }
        automaton.addNode("q1");
    }

    @Test
    public void hasNodeTest() {
        try {
            automaton.addNode("q1");
        } catch (DuplicateElementException e) {
            fail();
        }
        assertTrue(automaton.getNodes().contains("q1"));
        assertFalse(automaton.getNodes().contains("q2"));
    }

    @Test(expected = NoSuchNodeException.class)
    public void getNodeEdgesThrowsTest() throws NoSuchNodeException {
        automaton.getNodeEdges("q1");
    }

    @Test
    public void addEdgeAndDeterminismTest() {
        try {
            automaton.addNode("q1");
            automaton.addNode("q2");
            automaton.addNode("q3");
        } catch (DuplicateElementException e) {
            fail();
        }
        
        assertTrue(automaton.isDeterministic());
        try {
            automaton.addEdge("q1", 'a', "q2");
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q2")));
            assertTrue(automaton.getNodeEdges("q1").size() == 1);
            assertTrue(automaton.isDeterministic());
            
            automaton.addEdge("q1", 'a', "q3");
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q2")));
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q3")));
            assertTrue(automaton.getNodeEdges("q1").size() == 2);
            assertFalse(automaton.isDeterministic());
            
            
            automaton.addEdge("q1", null, "q3");
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q2")));
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge(null,"q3")));
            assertTrue(automaton.getNodeEdges("q1").size() == 3);
            assertFalse(automaton.isDeterministic());
            
            automaton.removeEdge("q1", 'a', "q3");
            automaton.removeEdge("q1", null, "q3");
            assertFalse(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q3")));
            assertTrue(automaton.getNodeEdges("q1").size() == 1);
            assertTrue(automaton.isDeterministic());
            
            automaton.addEdge("q1", 'a', "q3");
            automaton.removeNode("q3");
            assertFalse(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q3")));
            assertTrue(automaton.getNodeEdges("q1").size() == 1);
            assertTrue(automaton.isDeterministic());
            
            
            automaton.removeEdge("q1", 'a', "q2");
            automaton.addEdges("q1", "abc","q2");
            assertTrue(automaton.getNodeEdges("q1").size() == 1);
            assertTrue(automaton.getNodes().size() == 4);//q1 -> X a -> Y b -> q2 (c)
            assertTrue(automaton.isDeterministic());
            assertTrue(automaton.getNodeEdges("q1").contains(new FSA.Edge('a',"q1_1")));
            assertTrue(automaton.getNodeEdges("q1_1").contains(new FSA.Edge('b',"q1_2")));
            assertTrue(automaton.getNodeEdges("q1_2").contains(new FSA.Edge('c',"q2")));
            
        } catch (NoSuchNodeException e) {
            fail();
        } catch (DuplicateElementException e) {
            fail();
        } catch (NoSuchEdgeException e) {
            fail();
        }
        
    }
}
