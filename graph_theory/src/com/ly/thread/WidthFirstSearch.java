package com.ly.thread;

import java.util.Iterator;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.ly.demo.WidthFirstDemo;

public class WidthFirstSearch extends Thread{
	
		public static boolean switchOfSort=true;
		public static WidthFirstSearch instance;
		private WidthFirstDemo wfd ;
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
			Iterator<Long> iter = new BreadthFirstIterator<Long, DefaultEdge>((DirectedGraph<Long, DefaultEdge>)dg);
			int i =1;
			while(iter.hasNext()){
				if(switchOfSort){
					Long temp = iter.next();
					WidthFirstSearch.this.wfd.setColorAt(temp,i++);	
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		}
		public static WidthFirstSearch getInstance(WidthFirstDemo wfd,
				DirectedGraph<Long, DefaultEdge> dg){
				if(WidthFirstSearch.instance==null){
					WidthFirstSearch.instance=new WidthFirstSearch(wfd, dg);
				}
			return WidthFirstSearch.instance;
		}
		
		private WidthFirstSearch(WidthFirstDemo wfd,
				DirectedGraph<Long, DefaultEdge> dg) {
			super();
			this.wfd = wfd;
			this.dg = dg;
		}
		


	


}
