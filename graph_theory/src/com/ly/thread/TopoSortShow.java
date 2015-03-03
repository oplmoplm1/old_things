package com.ly.thread;

import java.awt.Label;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.ly.demo.TopoSortDemo;

public class TopoSortShow extends Thread {
	public static boolean switchOfSort=true;
	public static TopoSortShow instance;
	private TopoSortDemo jad ;
	private DirectedGraph<Long, DefaultEdge> dag;
	private List<Label> indegrees;
	@Override
	public void run() {
		order();
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void order(){
		Iterator<Long> iter = new TopologicalOrderIterator<Long, DefaultEdge>((DirectedGraph<Long, DefaultEdge>)dag);
		int i =1;
		while(iter.hasNext()){
			if(switchOfSort){
				Long temp = iter.next();
				//ÐÞ¸ÄlabelÑÕÉ«
				TopoSortShow.this.jad.setColorAt(temp,i++);	
				//ÐÞ¸ÄÓÒ²àlabel
				Set<DefaultEdge> edgeSet = dag.outgoingEdgesOf(temp);
				for(DefaultEdge edge:edgeSet){
					Long target = dag.getEdgeTarget(edge);
					Label currentLabel = indegrees.get(target.intValue());
					String text = currentLabel.getText();
					String indegree = ""+text.charAt(text.length()-1);
					int intIndegree = Integer.parseInt(indegree);
					text = text.substring(0,text.length()-1)+(--intIndegree);
					currentLabel.setText(text);
				}
			}
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}				
	}
	public static TopoSortShow getInstance(TopoSortDemo jad,
			DirectedGraph<Long, DefaultEdge> dag, List<Label> indegrees){
			if(TopoSortShow.instance==null){
				TopoSortShow.instance=new TopoSortShow(jad, dag, indegrees);
			}
		return TopoSortShow.instance;
	}
	private TopoSortShow(TopoSortDemo jad,
			DirectedGraph<Long, DefaultEdge> dag, List<Label> indegrees) {
		super();
		this.jad = jad;
		this.dag = dag;
		this.indegrees = indegrees;
		
	}

}
