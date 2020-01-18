package Tests;

import dataStructure.EdgeData;
import dataStructure.edge_data;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class EdgeTest {

    static edge_data[] checkforBuildEdge = new edge_data[8];

    @Before
    public void BeforeEach() {
        checkforBuildEdge [0] = new EdgeData(1,2,4);
        checkforBuildEdge [1] = new EdgeData(5,6,8);
        checkforBuildEdge [2] = new EdgeData(9,10,12);
        checkforBuildEdge [3] = new EdgeData(13,14,16);
        checkforBuildEdge [4] = new EdgeData(17,18,20);
        checkforBuildEdge [5] = new EdgeData(21,22,24);
        checkforBuildEdge [6] = new EdgeData(25,26,28);
        checkforBuildEdge [7] = new EdgeData(29,30,32);

    }

    @Test
    public void getSrc() {
        int [] ans = {1,5,9,13,17,21,25,29};
        for (int i = 0; i < checkforBuildEdge.length; i++) {
            assertEquals(ans[i], checkforBuildEdge[i].getSrc());
        }
    }

    @Test
    public void getDest() {
        int [] ans = {2,6,10,14,18,22,26,30};
        for (int i = 0; i < checkforBuildEdge.length; i++) {
            assertEquals(ans[i], checkforBuildEdge[i].getDest());
        }
    }

    @Test
    public void getWeight() {
        double [] ans = {4,8,12,16,20,24,28,32};
        for (int i = 0; i < checkforBuildEdge.length; i++) {
            assertEquals(ans[i], checkforBuildEdge[i].getWeight(), 0.0001);
        }
    }

    @Test
    public void getInfo() {
        for (int i = 0; i <checkforBuildEdge.length ; i++) {
            assertEquals(checkforBuildEdge[i].getInfo(), null);
        }
    }

    @Test
    public void setInfo() {
        for (int i = 0; i <checkforBuildEdge.length; i++) {
            checkforBuildEdge[i].setInfo("after");
        }
        for (int i = 0; i <checkforBuildEdge.length; i++) {
            assertEquals("after", checkforBuildEdge[i].getInfo());
        }
    }

    @Test
    public void getTag() {
        for (int i = 0; i < checkforBuildEdge.length; i++) {
            assertEquals(0, checkforBuildEdge[i].getTag());
        }
    }

    @Test
    public void setTag() {
        for (int i = 0; i <checkforBuildEdge.length; i++) {
            checkforBuildEdge[i].setTag(1);
        }
        for (int i = 0; i <checkforBuildEdge.length; i++) {
            assertEquals(1, checkforBuildEdge[i].getTag(), 0.0001);
        }
    }
}
