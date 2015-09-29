package DB2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
/* 
 * if (leftOrTopArrow.contains(e.getPoint()))
					JOptionPane.showMessageDialog(null, e.getLocationOnScreen());
 */
@SuppressWarnings("serial")
public class JpanelExpansive extends JPanel {
	private Polygon leftOrTopArrow = new Polygon();
	private Polygon rightOrBottonArrow = new Polygon();
	private boolean status=true;
	private int widthoriginal, heightoriginal;
	
	
	
	
	
	public JpanelExpansive(){
		super();
		setPreferredSize(new Dimension(20,20));
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try{
					if (leftOrTopArrow.contains(e.getPoint())){
						if(getWidth()<getHeight() && status){//panel de pé e aberto
							setVisible(false);
							widthoriginal=getWidth();
							setSize(15, getHeight());
							setPreferredSize(new Dimension(15, getHeight()));						
							setVisible(true);
						}else if(getWidth()>getHeight() && status){//painel deitado e fechado
							setVisible(false);
							heightoriginal = getHeight();
							setSize(getWidth(), 15);
							setPreferredSize(new Dimension(getWidth(), 15));
							setVisible(true);
						}
						
						for (int i=0;i<getComponentCount();i++) getComponent(i).setVisible(false);
						status=false;	
						
						
					}else
						if(rightOrBottonArrow.contains(e.getPoint())){
							if(getWidth()>getHeight() && !status){//painel deitado e aberto
								setVisible(false);
								setSize(getWidth(), heightoriginal);
								setPreferredSize(new Dimension(getWidth(), heightoriginal));
								setVisible(true);
							}
							else if(getWidth()<getHeight() && !status){//painel de pé e fechado
								setVisible(false);
								setSize(widthoriginal, getHeight());
								setPreferredSize(new Dimension(widthoriginal, getHeight()));
								setVisible(true);
							}
							for (int i=0;i<getComponentCount();i++) getComponent(i).setVisible(true);
							status=true;
						}
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
					
			}
		});
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				repaint();		
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				repaint();				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				repaint();		
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				repaint();		
				
			}
		});
	}
	
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Graphics2D gg =(Graphics2D) g;
		 if(getWidth()<getHeight()){
			 leftOrTopArrow.addPoint(5, 12);
			 leftOrTopArrow.addPoint(5, 20);
			 leftOrTopArrow.addPoint(9, 16);
			 gg.fillPolygon(leftOrTopArrow);
			 gg.setColor(Color.BLACK);
			
			 rightOrBottonArrow.addPoint(9, 19);
			 rightOrBottonArrow.addPoint(9, 27);
			 rightOrBottonArrow.addPoint(5, 23);
			 gg.fillPolygon(rightOrBottonArrow);
			 gg.setColor(Color.BLACK);
		 }else{
			 leftOrTopArrow.addPoint(4, 6);
			 leftOrTopArrow.addPoint(12, 6);
			 leftOrTopArrow.addPoint(8, 10);
			 gg.fillPolygon(leftOrTopArrow);
			 gg.setColor(Color.BLACK);
			 
			
			 rightOrBottonArrow.addPoint(11, 10);
			 rightOrBottonArrow.addPoint(20, 10);
			 rightOrBottonArrow.addPoint(15, 6);
			 gg.fillPolygon(rightOrBottonArrow);
			 gg.setColor(Color.BLACK);
		 }
	 }

	 
	

	 

}
