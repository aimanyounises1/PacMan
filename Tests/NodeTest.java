package Tests;

import dataStructure.NodeData;
import dataStructure.node_data;
import org.junit.Test;
import org.junit.Before;
import utils.Point3D;
import static org.junit.Assert.*;

public class NodeTest {

    static node_data[] checkforBuildNode = new node_data[8];
    static Point3D[] checkPoint3D = new Point3D[8];

    @Before
    public void BeforeEach() {

        checkPoint3D[0] = new Point3D(1,2,0);
        checkPoint3D[1] = new Point3D(3,4,0);
        checkPoint3D[2] = new Point3D(5,6,0);
        checkPoint3D[3] = new Point3D(7,8,0);
        checkPoint3D[4] = new Point3D(9,10,0);
        checkPoint3D[5] = new Point3D(11,12,0);
        checkPoint3D[6] = new Point3D(13,14,0);
        checkPoint3D[7] = new Point3D(15,16,0);

        checkforBuildNode[0] = new NodeData(checkPoint3D[0]);
        checkforBuildNode[1] = new NodeData(checkPoint3D[1]);
        checkforBuildNode[2] = new NodeData(checkPoint3D[2]);
        checkforBuildNode[3] = new NodeData(checkPoint3D[3]);
        checkforBuildNode[4] = new NodeData(checkPoint3D[4]);
        checkforBuildNode[5] = new NodeData(checkPoint3D[5]);
        checkforBuildNode[6] = new NodeData(checkPoint3D[6]);
        checkforBuildNode[7] = new NodeData(checkPoint3D[7]);
    }

    @Test
    public void getKey() {
        int ans[] = {1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 0; i < checkforBuildNode.length; i++) {
            assertEquals(ans[i], checkforBuildNode[i].getKey());
        }
    }

    @Test
    public void getLocation() {
        Point3D ans[] = {checkPoint3D[0],
                checkPoint3D[1],
                checkPoint3D[2],
                checkPoint3D[3],
                checkPoint3D[4],
                checkPoint3D[5],
                checkPoint3D[6],
                checkPoint3D[7]
        };
        for (int i = 0; i < checkforBuildNode.length; i++) {
            assertEquals(ans[i], checkforBuildNode[i].getLocation());
        }
    }


    @Test
    public void setLocation() {
        Point3D ans[] = new Point3D[8];
        for (int i = 0; i <checkPoint3D.length ; i++) {
            checkPoint3D[i].add(1,1,0);
            checkforBuildNode[i].setLocation(checkPoint3D[i]);
        }
        for (int i = 0; i <checkforBuildNode.length ; i++) {
            assertEquals(checkPoint3D[i],checkforBuildNode[i].getLocation());
        }
    }

    @Test
    public void getWeight() {
        for (int i = 0; i < checkforBuildNode.length; i++) {
            assertEquals(0, checkforBuildNode[i].getWeight(), 0.0001);
        }
    }

    @Test
    public void setWeight() {
        for (int i = 0; i <checkforBuildNode.length; i++) {
            checkforBuildNode[i].setWeight(1);
        }
        for (int i = 0; i <checkforBuildNode.length; i++) {
            assertEquals(1, checkforBuildNode[i].getWeight(), 0.0001);
        }
    }

    @Test
    public void getInfo() {
        for (int i = 0; i <checkforBuildNode.length ; i++) {
            assertEquals(checkforBuildNode[i].getInfo(), null);
        }
    }

    @Test
    public void setInfo() {
        for (int i = 0; i <checkforBuildNode.length; i++) {
            checkforBuildNode[i].setInfo("after");
        }
        for (int i = 0; i <checkforBuildNode.length; i++) {
            assertEquals("after", checkforBuildNode[i].getInfo());
        }
    }

    @Test
    public void getTag() {
        for (int i = 0; i <checkforBuildNode.length; i++) {
            assertEquals(0, checkforBuildNode[i].getTag(), 0.0001);
        }
    }

    @Test
    public void setTag() {
        for (int i = 0; i <checkforBuildNode.length; i++) {
            checkforBuildNode[i].setTag(1);
        }
        for (int i = 0; i <checkforBuildNode.length; i++) {
            assertEquals(1, checkforBuildNode[i].getTag(), 0.0001);
        }
    }
}