package com.ly.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.ly.service.GraphService;
import com.ly.thread.PrimTree;


public class PrimDemo extends JApplet {

	private static final long serialVersionUID = -1282539475228630787L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(650, 500);
	private JGraphModelAdapter<Long,DefaultWeightedEdge> m_jgAdapter;
	//service and directed graph
	GraphService graph = new GraphService();
	SimpleWeightedGraph<Long, DefaultWeightedEdge> sg;
	Label aaa = new Label("顶点的顺序",Label.CENTER);
	List<Label> weightsLabel= new ArrayList<Label>();
	JButton start = new JButton("开始");
	JButton pause = new JButton("暂停");
	PrimTree th=null;
	/**
	 * @see java.applet.Applet#init().
	 */
	public void init() {
		//初始化有向无环图,并加入到界面中
		graph.initSimpleGraph();
		sg = graph.getSg();
		ListenableGraph<Long, DefaultWeightedEdge> g = new ListenableUndirectedGraph<Long, DefaultWeightedEdge>(
				sg);
		m_jgAdapter = new JGraphModelAdapter<Long, DefaultWeightedEdge>(g);
		JGraph jgraph = new JGraph(m_jgAdapter);
		
		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
		//初始化界面上图的各个顶点
		positionVertexAt(Long.valueOf(1l), 250, 400);
		positionVertexAt(Long.valueOf(2l), 60, 200);
		positionVertexAt(Long.valueOf(3l), 310, 230);
		positionVertexAt(Long.valueOf(4l), 440, 40);
		positionVertexAt(Long.valueOf(5l), 555, 110);
		positionVertexAt(Long.valueOf(6l), 470, 300);
		positionVertexAt(Long.valueOf(7l), 200, 340);
		positionVertexAt(Long.valueOf(8l), 40, 370);
		positionVertexAt(Long.valueOf(9l), 230, 75);
		positionVertexAt(Long.valueOf(10l), 470, 220);
		positionVertexAt(Long.valueOf(0l), 33, 33);
		this.setSize(new Dimension(1024,768));
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(DEFAULT_BG_COLOR);
//		//设置title
		setTitleOfIndegree();
//		//设置label表
		setLabelList();
//		//初始化开始按钮
		initStartButton();
//			普里姆算法演示算法原型
//		PrimMinimumSpanningTree<Long, DefaultWeightedEdge> pTree = new PrimMinimumSpanningTree<Long,DefaultWeightedEdge>(sg);
//		Set<DefaultWeightedEdge> pEdges = pTree.getMinimumSpanningTreeEdgeSet();
//        Set<Long> unspanned = new HashSet<Long>(sg.vertexSet());
//        Iterator<DefaultWeightedEdge> treeIt1 = pEdges.iterator();
//        DefaultWeightedEdge pEdge = treeIt1.next();
//        unspanned.remove(sg.getEdgeSource(pEdge));
//        unspanned.remove(sg.getEdgeTarget(pEdge));
//        setColorAt(pEdge);
//        while(unspanned.size()!=0){	
//        	System.out.println(unspanned.size());
//        	Iterator<DefaultWeightedEdge> treeIt = pEdges.iterator();
//	        List<DefaultWeightedEdge> temp = new ArrayList<DefaultWeightedEdge>();
//	        while(treeIt.hasNext()){
//	        	pEdge=treeIt.next();
//	        	Long source = sg.getEdgeSource(pEdge);
//	        	Long target = sg.getEdgeTarget(pEdge);
//	        	if( !unspanned.contains(source) ^ !unspanned.contains(target) ){
//	        		temp.add(pEdge);
//	        	}
//	        }
//	        if(temp.size()!=0){
//	        	Collections.sort(temp,new Comparator<DefaultWeightedEdge>() {
//	        		@Override 
//	        		public int compare(DefaultWeightedEdge lop, DefaultWeightedEdge rop)
//	        		{
//	        			return Double.valueOf(sg.getEdgeWeight(lop))
//	        			.compareTo(sg.getEdgeWeight(rop));
//	        		}
//	        	});
//	        	DefaultWeightedEdge edge = temp.get(0);
//	        	Long source = sg.getEdgeSource(edge);
//	        	Long target = sg.getEdgeTarget(edge);
//	        	setColorAt(edge);
//        		unspanned.remove(target);
//        		unspanned.remove(source);
//	        }
//        }
	}

	private void initStartButton() {
		start.setLocation(60, 530);
		start.setSize(70, 40);
		start.setBackground(Color.decode("#96CDCD"));
		pause.setLocation(140, 530);
		pause.setSize(70, 40);
		pause.setBackground(Color.decode("#96CDCD"));
		this.getContentPane().add(start);
		this.getContentPane().add(pause);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(th==null||!th.isAlive())
					showSearch(1500);
				else if(th.isAlive())
					PrimTree.switchOfSort =true;
			}
		});
		
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(th!=null && th.isAlive()){
					wait4thread();
				}else{
					JOptionPane.showMessageDialog(null, "排序演示尚未开始", "提醒", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	public void wait4thread(){
		PrimTree.switchOfSort =false;
	}
	private void showSearch(final int interval) {
		if(th!=null)return;
		th = PrimTree.getInstance(this,sg);
		th.start();
	}
	private void setLabelList() {
		Set<DefaultWeightedEdge> edges =sg.edgeSet();
		int i =0;
		for(DefaultWeightedEdge edge: edges){
			int weight =(int)sg.getEdgeWeight(edge);
			Long source = sg.getEdgeSource(edge);
			Long target = sg.getEdgeTarget(edge);
			Label temp = new Label("边("+source+":"+target+")的权重为:"+weight);
			temp.setSize(120, 30);
			temp.setBackground(Color.decode("#BFEFFF"));
			temp.setLocation(700, (int) (73+i*33));
			this.getContentPane().add(temp);
			weightsLabel.add(temp);
			i++;
		}
	}
	private void setTitleOfIndegree(){
		aaa.setFont(new Font("宋体",Font.BOLD,13));
		aaa.setLocation(700, 33);
		aaa.setBackground(Color.decode("#96CDCD"));
		aaa.setSize(120	,33);
		this.getContentPane().add(aaa);
	}
	public void changeLabelColor(DefaultWeightedEdge edge){
		if(edge==null) return;
		Long source = sg.getEdgeSource(edge);
		Long target = sg.getEdgeTarget(edge);
		String clip ="("+source+":"+target+")";
		for(Label weightLabel:weightsLabel){
			if(weightLabel.getText().contains(clip)){
				weightLabel.setBackground(Color.RED);
			}
		}
	}
	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;
		try {
			colorStr = getParameter("bgcolor");
		} catch (Exception e) {
		}

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}
		jg.setBackground(c);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setColorAt(Object vertex,int index){
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		Set<DefaultWeightedEdge> edgeSet = sg.outgoingEdgesOf((Long) vertex);
		Map attr = cell.getAttributes();
		GraphConstants.setForeground(attr, Color.BLUE);// 字体颜色设置
		GraphConstants.setBackground(attr, Color.RED);// 背景颜色设置
//		Label temp = new Label("  第 "+index+" 个顶点为:  "+(((Long)vertex<10)?("0"+vertex):vertex));
		Label current = weightsLabel.get(((Long)vertex).intValue());
		current.setBackground(Color.RED);
		current.setText("  顶点  "+(((Long)vertex<10)?("0"+vertex):vertex)+"   :       "+index);
		for(DefaultWeightedEdge edge:edgeSet){
			GraphConstants.setLineColor(m_jgAdapter.getEdgeCell(edge).getAttributes(), Color.RED);
//			GraphConstants.setLabelAlongEdge(arg0, arg1)
		}
		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setColorAt(Object vertex){
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		Map attr = cell.getAttributes();
		GraphConstants.setForeground(attr, Color.BLUE);// 字体颜色设置
		GraphConstants.setBackground(attr, Color.RED);// 背景颜色设置
		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setColorAt(DefaultWeightedEdge edge){
		DefaultGraphCell cell =m_jgAdapter.getEdgeCell(edge);
		sg.getEdgeSource(edge);
		Map attr = cell.getAttributes();
		if( GraphConstants.getLineColor(attr)==Color.RED){
			int weight=(int) sg.getEdgeWeight(edge);
			for(DefaultWeightedEdge e:sg.edgeSet()){
				if((int)sg.getEdgeWeight(e)==weight){
					DefaultGraphCell c =m_jgAdapter.getEdgeCell(e);
					Map a = c.getAttributes();
					if( GraphConstants.getLineColor(a)!=Color.RED){
						GraphConstants.setLineColor(a, Color.RED);
						Long source = sg.getEdgeSource(e);
						setColorAt(source);
						Long target = sg.getEdgeTarget(e);
						setColorAt(target);
						break;
					}
				}
			}
		}else{
			GraphConstants.setLineColor(attr, Color.RED);
			Long source = sg.getEdgeSource(edge);
			setColorAt(source);
			Long target = sg.getEdgeTarget(edge);
			setColorAt(target);
		}
		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}

	@SuppressWarnings({  "unchecked", "rawtypes" })
	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		Map attr = cell.getAttributes();
		Rectangle2D b = GraphConstants.getBounds(attr);
		GraphConstants.setBounds(attr, new Rectangle(x, y, (int) b.getWidth(),
				(int) b.getHeight()));
		GraphConstants.setForeground(attr, Color.RED);// 字体颜色设置
		GraphConstants.setBackground(attr, Color.BLUE);// 背景颜色设置
		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}
}