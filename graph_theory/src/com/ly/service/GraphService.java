package com.ly.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;



public class GraphService {
	public static final int SIZE = 12;
	/**
	 * 这是有向无环图
	 * 
	 */
	private DirectedAcyclicGraph<Long, DefaultEdge> dag;
	//图的生成器
	private RandomGraphGenerator<Long, DefaultEdge> randomGraphGenerator = null;
	long seed = System.currentTimeMillis();
	
	
	/**
	 * 这是有向图
	 * 
	 */
	private DefaultDirectedGraph<Long, DefaultEdge> dg;
	private SimpleWeightedGraph<Long, DefaultWeightedEdge> sg;
	public static void main(String[] args) {
		new GraphService().initSimpleGraph();
	}
	public void topologicalSort(){
        Iterator<Long> iter = new TopologicalOrderIterator<Long, DefaultEdge>((DirectedGraph<Long, DefaultEdge>)dag);
        while(iter.hasNext()){
        	System.out.println(iter.next());
        }
	}
	public void init() {
		dag = new DirectedAcyclicGraph<Long, DefaultEdge>(DefaultEdge.class);
		
		randomGraphGenerator = new RepeatableRandomGraphGenerator<Long, DefaultEdge>(
				11, 15, seed);
		randomGraphGenerator.generateGraph(dag, new LongVertexFactory(), null);
		// System.out.println(dag.toString());

	}
	public void initDirectedGraph(){
		dg = new DefaultDirectedGraph<Long, DefaultEdge>(DefaultEdge.class);
		randomGraphGenerator = new RepeatableRandomGraphGenerator<Long, DefaultEdge>(
				11, 20, seed);
		randomGraphGenerator.generateGraph(dg, new LongVertexFactory(), null);
//		System.out.println(dg);
	}
	public void initSimpleGraph(){
		sg = new SimpleWeightedGraph<Long, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		RepeatableRandomGraphGenerator<Long, DefaultWeightedEdge> randomGraphGenerator2 = new RepeatableRandomGraphGenerator<Long, DefaultWeightedEdge>(
				11, 20, System.currentTimeMillis());
		randomGraphGenerator2.generateGraph(sg, new LongVertexFactory(), null);
//		System.out.println(sg);
		for(DefaultWeightedEdge de :sg.edgeSet()){
			Random r = new Random();
			int we = r.nextInt(20)+1;
			sg.setEdgeWeight(de, we);
//			double weight = sg.getEdgeWeight(de);
//			System.out.println(weight);
		}
		ConnectivityInspector<Long, DefaultWeightedEdge> c = new ConnectivityInspector<Long, DefaultWeightedEdge>(sg);
//		System.out.println(c.isGraphConnected());
		if(c.isGraphConnected()){
//			PrimMinimumSpanningTree<Long, DefaultWeightedEdge> t =new PrimMinimumSpanningTree<Long, DefaultWeightedEdge>(sg);
//			for(Object dwe : t.getMinimumSpanningTreeEdgeSet()){
//				double weight = sg.getEdgeWeight(((DefaultWeightedEdge)dwe));
//				System.out.println(weight);
//			}
		}else{
			initSimpleGraph();
		}
	}
	
	public DirectedAcyclicGraph<Long, DefaultEdge> getDag() {
		return dag;
	}

	public void setDag(DirectedAcyclicGraph<Long, DefaultEdge> dag) {
		this.dag = dag;
	}
	public DefaultDirectedGraph<Long, DefaultEdge> getDg() {
		return dg;
	}
	public void setDg(DefaultDirectedGraph<Long, DefaultEdge> dg) {
		this.dg = dg;
	}
	public SimpleWeightedGraph<Long, DefaultWeightedEdge> getSg() {
		return sg;
	}
	public void setSg(SimpleWeightedGraph<Long, DefaultWeightedEdge> sg) {
		this.sg = sg;
	}
	
	

}

class LongVertexFactory implements VertexFactory<Long> {
	private long nextVertex = 0;

	public Long createVertex() {
		return nextVertex++;
	}
	
}

class RepeatableRandomGraphGenerator<V, E> extends RandomGraphGenerator<V, E> {
	public RepeatableRandomGraphGenerator(int vertices, int edges, long seed) {
		super(vertices, edges);
		randomizer = new Random(seed);
	}
	public void generateDGraph(Graph<V, E> graph,
			VertexFactory<V> vertexFactory, Map<String, V> namedVerticesMap){
		super.generateGraph(graph, vertexFactory, namedVerticesMap);
	}
	
	@Override
	public void generateGraph(Graph <V,E> graph,
			VertexFactory<V> vertexFactory, Map<String, V> namedVerticesMap) {
		List<V> vertices = new ArrayList<V>(numOfVertexes);
		Set<Integer> edgeGeneratorIds = new HashSet<Integer>();

		for (int i = 0; i < numOfVertexes; i++) {
			V vertex = vertexFactory.createVertex();
			vertices.add(vertex);
			graph.addVertex(vertex);
		}

		for (int i = 0; i < numOfEdges; i++) {
			Integer edgeGeneratorId;
			do {
				edgeGeneratorId = randomizer.nextInt(numOfVertexes
						* (numOfVertexes - 1));
			} while (edgeGeneratorIds.contains(edgeGeneratorId));

			int fromVertexId = edgeGeneratorId / numOfVertexes;
			int toVertexId = edgeGeneratorId % (numOfVertexes - 1);
			if (toVertexId >= fromVertexId) {
				++toVertexId;
			}

			try {
				graph.addEdge(vertices.get(fromVertexId),
						vertices.get(toVertexId));
			} catch (IllegalArgumentException e) {

			}
		}
	}
	
}

