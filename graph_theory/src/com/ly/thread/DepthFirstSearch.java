package com.ly.thread;

import java.util.Iterator;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import com.ly.demo.DepthFirstDemo;

public class DepthFirstSearch extends Thread{
	
		public static boolean switchOfSort=true;
		public static DepthFirstSearch instance;
		private DepthFirstDemo wfd ;
		private DirectedGraph<Long, DefaultEdge> dg;
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
			Iterator<Long> iter = new DepthFirstIterator<Long, DefaultEdge>((DirectedGraph<Long, DefaultEdge>)dg);
			int i =1;
			while(iter.hasNext()){
				if(switchOfSort){
					Long temp = iter.next();
					DepthFirstSearch.this.wfd.setColorAt(temp,i++);	
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		}
		public static DepthFirstSearch getInstance(DepthFirstDemo wfd,
				DirectedGraph<Long, DefaultEdge> dg){
				if(DepthFirstSearch.instance==null){
					DepthFirstSearch.instance=new DepthFirstSearch(wfd, dg);
				}
			return DepthFirstSearch.instance;
		}
		
		private DepthFirstSearch(DepthFirstDemo wfd,
				DirectedGraph<Long, DefaultEdge> dg) {
			super();
			this.wfd = wfd;
			this.dg = dg;
		}
}
