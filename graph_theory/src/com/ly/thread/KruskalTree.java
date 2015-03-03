package com.ly.thread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.ly.demo.KruskalDemo;

public class KruskalTree extends Thread{
	
		public static boolean switchOfSort=true;
		public static KruskalTree instance;
		private KruskalDemo kkd;
		private SimpleWeightedGraph<Long, DefaultWeightedEdge> sg;
		@Override
		public void run() {
			order();
		}
		public void order(){
			KruskalMinimumSpanningTree<Long,DefaultWeightedEdge> kTree = new KruskalMinimumSpanningTree<Long,DefaultWeightedEdge>(sg);
			Set<DefaultWeightedEdge> kEdges=kTree.getMinimumSpanningTreeEdgeSet();
			List<Integer> weights=new ArrayList<Integer>(sg.edgeSet().size());
			for(DefaultWeightedEdge e: kEdges){
				 weights.add((int)sg.getEdgeWeight(e));
			}
			Collections.sort(weights);
			Iterator<Integer> weightIterator = weights.iterator();
			while(weightIterator.hasNext()){
				if(switchOfSort){
					Iterator<DefaultWeightedEdge> it = kEdges.iterator();
					Integer temp=weightIterator.next();
					while(it.hasNext()){
						DefaultWeightedEdge e = it.next();
						if(temp.intValue()==(int)sg.getEdgeWeight(e)){
							kkd.setColorAt(e);
							break;
						}
					}
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}	
		}
		public static KruskalTree getInstance(KruskalDemo kkd,
				SimpleWeightedGraph<Long, DefaultWeightedEdge> sg){
				if(KruskalTree.instance==null){
					KruskalTree.instance=new KruskalTree(kkd, sg);
				}
			return KruskalTree.instance;
		}
		
		private KruskalTree(KruskalDemo kkd,
				SimpleWeightedGraph<Long, DefaultWeightedEdge> sg) {
			super();
			this.kkd = kkd;
			this.sg = sg;
		}
}
