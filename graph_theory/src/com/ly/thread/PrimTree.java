package com.ly.thread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.ly.demo.PrimDemo;

public class PrimTree extends Thread{
	
		public static boolean switchOfSort=true;
		public static PrimTree instance;
		private PrimDemo prd;
		private SimpleWeightedGraph<Long, DefaultWeightedEdge> sg;
		@Override
		public void run() {
			order();
		}
		public void order(){
			PrimMinimumSpanningTree<Long, DefaultWeightedEdge> pTree = new PrimMinimumSpanningTree<Long,DefaultWeightedEdge>(sg);
			Set<DefaultWeightedEdge> pEdges = pTree.getMinimumSpanningTreeEdgeSet();
	        Set<Long> unspanned = new HashSet<Long>(sg.vertexSet());
	        Iterator<DefaultWeightedEdge> treeIt1 = pEdges.iterator();
	        DefaultWeightedEdge pEdge = treeIt1.next();
	        unspanned.remove(sg.getEdgeSource(pEdge));
	        unspanned.remove(sg.getEdgeTarget(pEdge));
	        prd.setColorAt(pEdge);
	        prd.changeLabelColor(pEdge);
	        while(unspanned.size()!=0){	
	        	try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	if(switchOfSort){
	        		Iterator<DefaultWeightedEdge> treeIt = pEdges.iterator();
	        		List<DefaultWeightedEdge> temp = new ArrayList<DefaultWeightedEdge>();
	        		while(treeIt.hasNext()){
	        			pEdge=treeIt.next();
	        			Long source = sg.getEdgeSource(pEdge);
	        			Long target = sg.getEdgeTarget(pEdge);
	        			if( !unspanned.contains(source) ^ !unspanned.contains(target) ){
	        				temp.add(pEdge);
	        			}
	        		}
	        		if(temp.size()!=0){
	        			Collections.sort(temp,new Comparator<DefaultWeightedEdge>() {
	        				@Override 
	        				public int compare(DefaultWeightedEdge lop, DefaultWeightedEdge rop)
	        				{
	        					return Double.valueOf(sg.getEdgeWeight(lop))
	        					.compareTo(sg.getEdgeWeight(rop));
	        				}
	        			});
	        			DefaultWeightedEdge edge = temp.get(0);
	        			Long source = sg.getEdgeSource(edge);
	        			Long target = sg.getEdgeTarget(edge);
	        			prd.setColorAt(edge);
	        			prd.changeLabelColor(edge);
	        			unspanned.remove(target);
	        			unspanned.remove(source);
	        		}
	        	}
	        	
	        }
		}
		public static PrimTree getInstance(PrimDemo prd,
				SimpleWeightedGraph<Long, DefaultWeightedEdge> sg){
				if(PrimTree.instance==null){
					PrimTree.instance=new PrimTree(prd, sg);
				}
			return PrimTree.instance;
		}
		
		private PrimTree(PrimDemo prd,
				SimpleWeightedGraph<Long, DefaultWeightedEdge> sg) {
			super();
			this.prd = prd;
			this.sg = sg;
		}
}
