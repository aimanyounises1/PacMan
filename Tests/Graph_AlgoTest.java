package Tests;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.graph;
import dataStructure.node_data;
import org.junit.Test;
import utils.Point3D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Graph_AlgoTest {

    @Test
    public void initGraph() {
        Graph_Algo G = new Graph_Algo();
        DGraph g1 = new DGraph();
        Point3D p1 [] = new Point3D[2];
        NodeData n1 [] = new NodeData[2];

        p1[0] = new Point3D(0,0,0);
        p1[1] = new Point3D(5,0,0);

        n1[0] = new NodeData(p1[0]);
        n1[1] = new NodeData(p1[1]);

        g1.addNode(n1[0]);
        g1.addNode(n1[1]);

        g1.connect(n1[0].getKey(), n1[1].getKey(), 4);
        G.init(g1);

        Graph_Algo ans = new Graph_Algo();
        ans.init(g1);

        assertEquals(ans.isConnected(), G.isConnected());

    }

    @Test
    public void save() {
        Graph_Algo G2 = new Graph_Algo();
        DGraph g2 = new DGraph();
        Point3D p2 [] = new Point3D[5];
        NodeData n2 [] = new NodeData[5];

        p2[0] = new Point3D(1, 6, 0);
        p2[1] = new Point3D(0, 2, 3);
        p2[2] = new Point3D(1, 4, 0);
        p2[3] = new Point3D(5, 2, 0);
        p2[4] = new Point3D(6,5, 0);

        n2[0] = new NodeData (p2[0]);
        n2[1] = new NodeData(p2[1]);
        n2[2] = new NodeData(p2[2]);
        n2[3] = new NodeData(p2[3]);
        n2[4] = new NodeData(p2[4]);

        g2.addNode(n2[0]);
        g2.addNode(n2[1]);
        g2.addNode(n2[2]);
        g2.addNode(n2[3]);
        g2.addNode(n2[4]);

        g2.connect(n2[1].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[2].getKey(), n2[3].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[4].getKey(), 11);
        g2.connect(n2[4].getKey(), n2[3].getKey(), 11);

        G2.init(g2);
        G2.save("TestForSave");

        Graph_Algo G3 = new Graph_Algo();
        G3.init("TestForSave");

        Boolean flag = G2.isConnected() == G3.isConnected();
        assertEquals(true,flag);
    }

    @Test
    public void initFile() {
        Graph_Algo G4 = new Graph_Algo();
        DGraph g4 = new DGraph();
        Point3D p4 [] = new Point3D[5];
        NodeData n4 [] = new NodeData[5];

        p4[0] = new Point3D(1, 6, 0);
        p4[1] = new Point3D(0, 2, 3);
        p4[2] = new Point3D(1, 4, 0);
        p4[3] = new Point3D(5, 2, 0);
        p4[4] = new Point3D(6,5, 0);

        n4[0] = new NodeData (p4[0]);
        n4[1] = new NodeData(p4[1]);
        n4[2] = new NodeData(p4[2]);
        n4[3] = new NodeData(p4[3]);
        n4[4] = new NodeData(p4[4]);

        g4.addNode(n4[0]);
        g4.addNode(n4[1]);
        g4.addNode(n4[2]);
        g4.addNode(n4[3]);
        g4.addNode(n4[4]);

        g4.connect(n4[1].getKey(), n4[2].getKey(), 11);
        g4.connect(n4[2].getKey(), n4[3].getKey(), 11);
        g4.connect(n4[3].getKey(), n4[2].getKey(), 11);
        g4.connect(n4[3].getKey(), n4[4].getKey(), 11);
        g4.connect(n4[4].getKey(), n4[3].getKey(), 11);

        G4.init(g4);
        Graph_Algo G5 = new Graph_Algo();
        G5.init("TestForSave");

        assertEquals(G4.isConnected(), G5.isConnected());
        //assertEquals(G4.algo.getNode(n4[0].getKey()).toString(), G5.algo.getNode(n4[0].getKey()).toString());
    }


    @Test
    public void isConnected() {
        ////////////////////////TEST1///////////////////////////
        Graph_Algo G = new Graph_Algo();
        DGraph g1 = new DGraph();
        Point3D p1 [] = new Point3D[2];
        NodeData n1 [] = new NodeData[2];

        p1[0] = new Point3D(0,0,0);
        p1[1] = new Point3D(5,0,0);

        n1[0] = new NodeData(p1[0]);
        n1[1] = new NodeData(p1[1]);

        g1.addNode(n1[0]);
        g1.addNode(n1[1]);

        g1.connect(n1[0].getKey(), n1[1].getKey(), 4);

        G.init(g1);

        assertEquals(false, G.isConnected());
        //System.out.println("success1");


        //////////////////////////TEST2/////////////////////
        Graph_Algo G2 = new Graph_Algo();
        Graph_Algo G3 = new Graph_Algo();
        DGraph g2 = new DGraph();
        DGraph g3 = new DGraph();
        Point3D p2 [] = new Point3D[5];
        NodeData n2 [] = new NodeData[5];

        Graph_Algo Ag = new Graph_Algo();

        p2[0] = new Point3D(1, 6, 0);
        p2[1] = new Point3D(0, 2, 3);
        p2[2] = new Point3D(1, 4, 0);
        p2[3] = new Point3D(5, 2, 0);
        p2[4] = new Point3D(6,5, 0);

        n2[0] = new NodeData (p2[0]);
        n2[1] = new NodeData(p2[1]);
        n2[2] = new NodeData(p2[2]);
        n2[3] = new NodeData(p2[3]);
        n2[4] = new NodeData(p2[4]);

        g2.addNode(n2[0]);
        g2.addNode(n2[1]);
        g2.addNode(n2[2]);
        g2.addNode(n2[3]);
        g2.addNode(n2[4]);

        g3.addNode(n2[0]);
        g3.addNode(n2[1]);
        g3.addNode(n2[2]);
        g3.addNode(n2[3]);
        g3.addNode(n2[4]);

        g2.connect(n2[1].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[2].getKey(), n2[3].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[4].getKey(), 11);
        g2.connect(n2[4].getKey(), n2[3].getKey(), 11);


        g3.connect(n2[0].getKey(), n2[1].getKey(), 9);
        g3.connect(n2[1].getKey(), n2[0].getKey(), 9);
        g3.connect(n2[1].getKey(), n2[4].getKey(), 10);
        g3.connect(n2[4].getKey(), n2[3].getKey(), 11);
        g3.connect(n2[3].getKey(), n2[2].getKey(), 11);
        g3.connect(n2[2].getKey(), n2[1].getKey(), 11);

        G2.init(g2);
        G3.init(g3);

        assertEquals(false, G2.isConnected());
        //System.out.println("success2");

        assertEquals(true, G3.isConnected());
        //System.out.println("success3");

    }

    @Test
    public void shortestPathDist() {
        Graph_Algo G6 = new Graph_Algo();
        DGraph g6 = new DGraph();
        Point3D p6[] = new Point3D[6];
        NodeData n6[] = new NodeData[6];

        p6[0] = new Point3D(100, 200, 3);
        p6[1] = new Point3D(150, 200, 3);
        p6[2] = new Point3D(300, 450, 3);
        p6[3] = new Point3D(450, 500, 3);
        p6[4] = new Point3D(320, 600, 3);
        p6[5] = new Point3D(226, 260, 3);

        n6[0] = new NodeData(p6[0]);
        n6[1] = new NodeData(p6[1]);
        n6[2] = new NodeData(p6[2]);
        n6[3] = new NodeData(p6[3]);
        n6[4] = new NodeData(p6[4]);
        n6[5] = new NodeData(p6[5]);

        g6.addNode(n6[0]);
        g6.addNode(n6[1]);
        g6.addNode(n6[2]);
        g6.addNode(n6[3]);
        g6.addNode(n6[4]);
        g6.addNode(n6[5]);

        g6.connect(n6[0].getKey(), n6[1].getKey(), 2);
        g6.connect(n6[1].getKey(), n6[2].getKey(), 2);
        g6.connect(n6[2].getKey(), n6[3].getKey(), 2);
        g6.connect(n6[3].getKey(), n6[4].getKey(), 2);
        g6.connect(n6[4].getKey(), n6[5].getKey(), 2);
        g6.connect(n6[5].getKey(), n6[0].getKey(), 2);

        G6.init(g6);

        assertEquals(false, G6.shortestPath(n6[0].getKey(), n6[5].getKey()).isEmpty());

        assertEquals(10, G6.shortestPathDist(n6[0].getKey(), n6[5].getKey()), 0);

        g6.connect(n6[0].getKey(), n6[1].getKey(), 1);
        g6.connect(n6[1].getKey(), n6[2].getKey(), 1);
        g6.connect(n6[0].getKey(), n6[5].getKey(), 12);

        assertEquals(false, G6.shortestPath(n6[0].getKey(), n6[5].getKey()).isEmpty());

        assertEquals(8, G6.shortestPathDist(n6[0].getKey(), n6[5].getKey()), 0);
    }

    @Test
    public void shortestPath() {
        Graph_Algo G7 = new Graph_Algo();
        DGraph g7 = new DGraph();
        Point3D p6[] = new Point3D[6];
        NodeData n6[] = new NodeData[6];

        int j = 1;

        p6[0] = new Point3D(100, 200, 3);
        p6[1] = new Point3D(150, 200, 3);
        p6[2] = new Point3D(300, 450, 3);
        p6[3] = new Point3D(450, 500, 3);
        p6[4] = new Point3D(320, 600, 3);
        p6[5] = new Point3D(226, 260, 3);

        n6[0] = new NodeData(p6[0]);
        n6[1] = new NodeData(p6[1]);
        n6[2] = new NodeData(p6[2]);
        n6[3] = new NodeData(p6[3]);
        n6[4] = new NodeData(p6[4]);
        n6[5] = new NodeData(p6[5]);

        g7.addNode(n6[0]);
        g7.addNode(n6[1]);
        g7.addNode(n6[2]);
        g7.addNode(n6[3]);
        g7.addNode(n6[4]);
        g7.addNode(n6[5]);

        g7.connect(n6[0].getKey(),n6[1].getKey(),2);
        g7.connect(n6[1].getKey(),n6[2].getKey(),4);
        g7.connect(n6[2].getKey(),n6[3].getKey(),8);
        g7.connect(n6[3].getKey(),n6[4].getKey(),2);
        G7.init(g7);

        List<node_data> ansList = new LinkedList<>();
        ansList = G7.shortestPath(n6[0].getKey(),n6[5].getKey());
        assertEquals(null,ansList);

        g7.connect(n6[4].getKey(),n6[5].getKey(),0);
        g7.connect(n6[0].getKey(),n6[5].getKey(),30);
        G7.init(g7);

        ansList = G7.shortestPath(n6[0].getKey(),n6[5].getKey());

        assertEquals(false,ansList.isEmpty());
        assertEquals(6,ansList.size(), 0);
        assertEquals (16,G7.shortestPathDist(n6[0].getKey(),n6[5].getKey()),0);

        String stringAns ="";
        for (int i=0; i<6; i++){
            stringAns += n6[i].getKey();
        }

        String check="";
        Iterator<node_data> itList = ansList.iterator();
        while (itList.hasNext())
        {
            node_data c = (node_data)itList.next();
            check+=(c.getKey());
        }
        assertEquals(stringAns.toString(), check.toString());

        g7.connect(n6[0].getKey(),n6[5].getKey(),0);
        G7.init(g7);
        ansList = (List<node_data>) G7.shortestPath(n6[0].getKey(),n6[5].getKey());
        assertEquals(false,ansList.isEmpty());
        assertEquals(2,ansList.size(), 0);
    }

    @Test
    public void TSP() {
        Graph_Algo G9 = new Graph_Algo();
        graph g9 = new DGraph();
        Point3D p9[] = new Point3D[5];
        NodeData n9[] = new NodeData[5];

        p9[0] = new Point3D(1, 6, 0);
        p9[1] = new Point3D(0, 2, 3);
        p9[2] = new Point3D(1, 4, 0);
        p9[3] = new Point3D(5, 2, 0);
        p9[4] = new Point3D(6,5, 0);

        n9[0] = new NodeData(p9[0]);
        n9[1] = new NodeData(p9[1]);
        n9[2] = new NodeData(p9[2]);
        n9[3] = new NodeData(p9[3]);
        n9[4] = new NodeData(p9[4]);


        g9.addNode(n9[0]);
        g9.addNode(n9[1]);
        g9.addNode(n9[2]);
        g9.addNode(n9[3]);
        g9.addNode(n9[4]);

        g9.connect(n9[0].getKey(), n9[1].getKey(), 9);
        g9.connect(n9[1].getKey(), n9[2].getKey(),3);
        g9.connect(n9[2].getKey(), n9[3].getKey(), 5);
        g9.connect(n9[3].getKey(), n9[0].getKey(), 4);
        g9.connect(n9[1].getKey(), n9[0].getKey(), 2);
        g9.connect(n9[1].getKey(), n9[4].getKey(), 1);
        g9.connect(n9[4].getKey(), n9[3].getKey(), 2);
        g9.connect(n9[3].getKey(), n9[2].getKey(), 6);
        g9.connect(n9[2].getKey(), n9[1].getKey(), 5);

        G9.init(g9);

        LinkedList<Integer> l1 = new LinkedList<Integer>();
        l1.add(n9[1].getKey());
        l1.add(n9[3].getKey());
        l1.add(n9[4].getKey());

        String ansl1 = "";
        ansl1+= n9[1].getKey();
        ansl1+= n9[4].getKey();
        ansl1+= n9[3].getKey();

        List<node_data> List = new LinkedList<>();
        List = G9.TSP(l1);

        String check="";
        Iterator<node_data> itList = List.iterator();
        while (itList.hasNext())
        {
            node_data c = (node_data)itList.next();
            check+=(c.getKey());
        }

        assertEquals(ansl1.toString(),check.toString());
    }

    @Test
    public void copy() {
        Graph_Algo G7 = new Graph_Algo();
        DGraph g7 = new DGraph();
        Point3D p6[] = new Point3D[6];
        NodeData n6[] = new NodeData[6];

        int j =1;
        p6[0] = new Point3D(100, 200, 3);
        p6[1] = new Point3D(150, 200, 3);

        p6[2] = new Point3D(300, 450, 3);
        p6[3] = new Point3D(450, 500, 3);
        p6[4] = new Point3D(320, 600, 3);
        p6[5] = new Point3D(226, 260, 3);

        n6[0] = new NodeData(p6[0]);
        n6[1] = new NodeData(p6[1]);
        n6[2] = new NodeData(p6[2]);
        n6[3] = new NodeData(p6[3]);
        n6[4] = new NodeData(p6[4]);
        n6[5] = new NodeData(p6[5]);

        g7.addNode(n6[0]);
        g7.addNode(n6[1]);
        g7.addNode(n6[2]);
        g7.addNode(n6[3]);
        g7.addNode(n6[4]);
        g7.addNode(n6[5]);

        g7.connect(n6[0].getKey(),n6[1].getKey(),2);
        g7.connect(n6[1].getKey(),n6[2].getKey(),4);
        g7.connect(n6[2].getKey(),n6[3].getKey(),8);
        g7.connect(n6[3].getKey(),n6[4].getKey(),2);
        G7.init(g7);

        Graph_Algo G8 = new Graph_Algo();
        graph copyGragh = new DGraph();
        copyGragh = G7.copy();
        G8.init(copyGragh);


        assertEquals(G7.isConnected(), G8.isConnected());
        assertEquals(G7.shortestPathDist(n6[0].getKey(),n6[5].getKey()), G8.shortestPathDist(n6[0].getKey(),n6[5].getKey()),0);
        g7.connect(n6[4].getKey(),n6[5].getKey(),0);
        g7.connect(n6[0].getKey(),n6[5].getKey(),30);
        G7.init(g7);
        assertNotEquals(G7.shortestPathDist(n6[0].getKey(),n6[5].getKey()), G8.shortestPathDist(n6[0].getKey(),n6[5].getKey()),0);
    }
}