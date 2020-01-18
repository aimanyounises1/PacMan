package gui;

import algorithms.*;
import dataStructure.*;
import utils.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class GUI extends Thread {

    public Graph_Algo ga = new Graph_Algo();
    public DGraph g = new DGraph();
    private int modeCount = 0;

    public GUI(){
        StdDraw.g = this;
        modeCount = this.ga.algo.getMC();
        this.start();
    }

    public GUI(DGraph d){
        StdDraw.g=this;
        this.g=d;
        ga.init(d);
        StdDraw.enableDoubleBuffering();
    }
    public void GUIgraph() {
        StdDraw.setCanvasSize(1024, 512);
        DrawGraph(this.g);
    }

    public void DrawGraph(graph d){
        StdDraw.clear();
        this.ga.init(d);
        StdDraw.g=this;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        Iterator it = d.getV().iterator();
        while (it.hasNext()) {
            node_data temp = (node_data) it.next();
            Point3D p = temp.getLocation();
            minX = Math.min(minX, p.x());
            minY = Math.min(minY, p.y());
            maxX = Math.max(maxX, p.x());
            maxY = Math.max(maxY, p.y());
        }
        System.out.println(minX + " ," + maxX);
        System.out.println(minY + "," + maxY);
        double ScaleX =(maxX-minX)*0.04;
        double ScaleY=(maxY-minY)*0.04;
        StdDraw.setXscale(minX - 0.002, maxX +0.002);
        StdDraw.setYscale(minY - 0.002, maxY+ 0.002);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.05);
        Iterator it1 = d.getV().iterator();
        while (it1.hasNext()) {
            node_data temp = (node_data) it1.next();
            Point3D p1 = temp.getLocation();
            StdDraw.filledCircle(p1.x(), p1.y(),ScaleX*0.1);
            StdDraw.text(p1.x(), p1.y() + (((maxX-minX)*0.04)*0.2), "" + temp.getKey());
        }
        StdDraw.setPenRadius(0.004);
        Iterator edge1 = d.getV().iterator();
        while (edge1.hasNext()) {
            node_data temp1 = (node_data) edge1.next();
            if (d.getE(temp1.getKey()) != null) {
                Iterator edge2 = d.getE(temp1.getKey()).iterator();
                while (edge2.hasNext()) {
                    StdDraw.setPenColor(Color.LIGHT_GRAY);
                    edge_data temp2 = (edge_data) edge2.next();
                    node_data n1 = d.getNode(temp2.getSrc());
                    node_data n2 = d.getNode(temp2.getDest());
                    Point3D p1 = n1.getLocation();
                    Point3D p2 = n2.getLocation();
                    StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                    double x = 0.2 * p1.x() + 0.8 * p2.x();
                    double y = 0.2 * p1.y() + 0.8 * p2.y();
                    StdDraw.setPenColor(Color.BLACK);
                    double weight = Math.round(temp2.getWeight()*100.0)/100.0;
                    StdDraw.text(x, y , "" + weight);
                    StdDraw.setPenColor(Color.YELLOW);
                    double x1 = 0.1 * p1.x() + 0.9 * p2.x();
                    double y1 = 0.1 * p1.y() + 0.9 * p2.y();
                    StdDraw.filledCircle(x1, y1, ScaleX*0.1);
                }
            }
        }
    }

    public void GUIPath(List<node_data> l){
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.GREEN);
        for (int i = 0; i <l.size()-1 ; i++) {
            Point3D src = this.ga.algo.getNode(l.get(i).getKey()).getLocation();
            Point3D dest = this.ga.algo.getNode(l.get(i+1).getKey()).getLocation();
            StdDraw.line(src.x(),src.y(),dest.x(),dest.y());
        }
    }

    public void backRed(graph d) {
        StdDraw.setPenRadius(0.004);
        Iterator edge1 = d.getV().iterator();
        while (edge1.hasNext()) {
            node_data temp1 = (node_data) edge1.next();
            if (d.getE(temp1.getKey()) != null) {
                Iterator edge2 = d.getE(temp1.getKey()).iterator();
                while (edge2.hasNext()) {
                    StdDraw.setPenColor(Color.LIGHT_GRAY);
                    edge_data temp2 = (edge_data) edge2.next();
                    node_data n1 = d.getNode(temp2.getSrc());
                    node_data n2 = d.getNode(temp2.getDest());
                    Point3D p1 = n1.getLocation();
                    Point3D p2 = n2.getLocation();
                    StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                    double x = 0.2 * p1.x() + 0.8 * p2.x();
                    double y = 0.2 * p1.y() + 0.8 * p2.y();
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.text(x, y , "" + temp2.getWeight());
                }
            }
        }
    }

    public void run(){
        while(true){
            if(modeCount!=this.ga.algo.getMC()){
                GUIgraph();
                modeCount = ga.algo.getMC();
            }
        }
    }
}
