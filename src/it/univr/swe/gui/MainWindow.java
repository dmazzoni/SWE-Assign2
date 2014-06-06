package it.univr.swe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.Car;
import it.univr.swe.Simulator;
import it.univr.swe.Tower;
import it.univr.swe.communication.CarChannel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	/***/
	private Car[] cars = null;
	/***/
	private Tower tower;
	/***/
	private CarChannel[] carChannel;
	/***/
	private Simulator sim;
	/***/
	private JTable table;
	
	
	public MainWindow(Simulator sim){
		super();
		
		this.sim = sim;
		
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable(new DefaultTableModel(new String[]{"Car ID", "Speed","Display","Tipo"},150));
		
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BorderLayout());
			
			/*LABEL*/
			JPanel topPanel = new JPanel();
				JLabel label = new JLabel("SWE-Assign2");
				
				topPanel.add(label);
			
			/*TABLE*/
			JScrollPane scroll = new JScrollPane(table);
			table.setFillsViewportHeight(true); 
			
			/*TOWER*/
			JPanel leftPanel = new JPanel();
				leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.PAGE_AXIS));
			
				JLabel tower = new JLabel("Tower Actions");
				tower.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				JLabel channels = new JLabel("Channels");
				channels.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				leftPanel.add(tower);
				leftPanel.add(channels);
			
			internalPanel.add(topPanel,BorderLayout.NORTH);
			internalPanel.add(scroll,BorderLayout.CENTER);
			internalPanel.add(leftPanel,BorderLayout.LINE_START);
		
		this.getContentPane().add(internalPanel);
		
		Timer time = new Timer();
		time.scheduleAtFixedRate(new UploadUI(), 0, 20);
		
	}
	
	
	
	public void setCars(Car[] cars) {
		this.cars = cars;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public void setCarChannel(CarChannel[] carChannel) {
		this.carChannel = carChannel;
	}
	
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 *
	 */
	private class UploadUI extends TimerTask{

		@Override
		public void run() {
			//MainWindow.this.sim.refresh(MainWindow.this);
			MainWindow.this.refresh();
		}
		
	}
	

	public static void main(String args[]){
		
		Simulator sim = new Simulator();
		sim.start();
		
		MainWindow main = new MainWindow(sim);
		main.setVisible(true);
		
	}

}
