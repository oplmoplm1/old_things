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
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.ly.service.GraphService;
import com.ly.thread.WidthFirstSearch;


public class WidthFirstDemo extends JApplet {

	private static final long serialVersionUID = -1282539475228630787L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(650, 500);
	private JGraphModelAdapter<Long,DefaultEdge> m_jgAdapter;
// 服务层对象
	GraphService graph = new GraphService();
//有向图
	DefaultDirectedGraph<Long, DefaultEdge> dg;
//列表说明
	Label aaa = new Label("顶点的顺序",Label.CENTER);
//标签列表
	List<Label> indegrees= new ArrayList<Label>();
//演示按钮
	JButton start = new JButton("开始");
	JButton pause = new JButton("暂停");
//宽度演示线程
	WidthFirstSearch th=null;
//初始化图形界面
	public void init() {
		//初始化有向无环图,并加入到界面中
		graph.initDirectedGraph();
		dg = graph.getDg();
		ListenableGraph<Long, DefaultEdge> g = new ListenableDirectedGraph<Long, DefaultEdge>(
				dg);
		m_jgAdapter = new JGraphModelAdapter<Long, DefaultEdge>(g);
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
		
		
	}
//初始化按钮
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
					WidthFirstSearch.switchOfSort =true;
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
//让排序线程等待
	public void wait4thread(){
		WidthFirstSearch.switchOfSort =false;
	}
//开始排序
	private void showSearch(final int interval) {
		if(th!=null)return;
		th = WidthFirstSearch.getInstance(this, (DirectedGraph<Long, DefaultEdge>)dg);
		th.start();		
	}
//初始化标签列表
	private void setLabelList() {
		Set<Long> vetexes= dg.vertexSet();
		for(Long vetex:vetexes){
			Label temp = new Label("  顶点  "+(vetex<10?("0"+vetex):vetex)+"   :       "+0);
			temp.setSize(120, 30);
			temp.setBackground(Color.decode("#BFEFFF"));
			temp.setLocation(700, (int) (73+vetex*33));
			this.getContentPane().add(temp);
			indegrees.add(temp);
		}
	}
//设置标签列表的值
	private void setTitleOfIndegree(){
		aaa.setFont(new Font("宋体",Font.BOLD,13));
		aaa.setLocation(700, 33);
		aaa.setBackground(Color.decode("#96CDCD"));
		aaa.setSize(120	,33);
		this.getContentPane().add(aaa);
	}
//设置初始化显示情况
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
//更改标签颜色	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setColorAt(Object vertex,int index){
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		Set<DefaultEdge> edgeSet = dg.outgoingEdgesOf((Long) vertex);
		Map attr = cell.getAttributes();
		GraphConstants.setForeground(attr, Color.BLUE);// 字体颜色设置
		GraphConstants.setBackground(attr, Color.RED);// 背景颜色设置
//		Label temp = new Label("  第 "+index+" 个顶点为:  "+(((Long)vertex<10)?("0"+vertex):vertex));
		Label current = indegrees.get(((Long)vertex).intValue());
		current.setBackground(Color.RED);
		current.setText("  顶点  "+(((Long)vertex<10)?("0"+vertex):vertex)+"   :       "+index);
		for(DefaultEdge edge:edgeSet){
			GraphConstants.setLineColor(m_jgAdapter.getEdgeCell(edge).getAttributes(), Color.RED);
		}
		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}
//设置顶点坐标
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