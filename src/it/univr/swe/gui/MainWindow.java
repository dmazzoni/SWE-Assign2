package it.univr.swe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.Car;
import it.univr.swe.Simulator;
import it.univr.swe.Tower;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	/***/
	private Tower tower;
	/***/
	private JTable table;
	/***/
	private JProgressBar[] progress;
	/***/
	private JTextArea towerActions;
	/***/
	private Map<Integer,Car> rowMap;
	
	
	public MainWindow(Simulator sim){
		super();
		
		/*Get objects from Simulator*/
		tower = sim.getTower();
		
		this.setSize(900, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable(new DefaultTableModel(new String[]{"Car ID", "Speed","Display","Tipo"},0));
		
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BorderLayout());
			
			/*LABEL*/
			JPanel topPanel = new JPanel();
				JLabel label = new JLabel("SWE-Assign2");
				label.setFont(new Font("Serif", Font.BOLD, 24));
				
				topPanel.add(label);
			
			/*TABLE*/
			JScrollPane scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			table.setFillsViewportHeight(true); 
			
			/*LEFT PANEL*/
			JPanel leftPanel = new JPanel();
				leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.PAGE_AXIS));
				leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			
				/*TOWER*/
				JLabel tower = new JLabel("Tower Actions");
				tower.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				towerActions = new JTextArea(1,30);
				JScrollPane towerActionsScroll = new JScrollPane(towerActions,
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		        towerActions.setLineWrap(true);
		        towerActions.setWrapStyleWord(true);
		        towerActions.setEditable(false);
				
		        /*CHANNELS*/
				JLabel channels = new JLabel("Channels");
				channels.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				leftPanel.add(tower);
				leftPanel.add(towerActionsScroll);
				leftPanel.add(channels);
				
				progress = new JProgressBar[5];
				for(int I = 0; I<5; I++){
					progress[I] = new JProgressBar();
					progress[I].setMaximum(100);
					progress[I].setMinimum(0);
					progress[I].setValue((5-I)*20);
					
					leftPanel.add(progress[I]);
				}
			
			internalPanel.add(topPanel,BorderLayout.NORTH);
			internalPanel.add(scroll,BorderLayout.CENTER);
			internalPanel.add(leftPanel,BorderLayout.LINE_START);
		
		this.getContentPane().add(internalPanel);
		
		Timer time = new Timer();
		time.scheduleAtFixedRate(new UploadUI(), 0, 20);
		
	}
	
	/**
	 * Refreshes the UI using new values of cars,tower and carChannel
	 */
	public void refresh() {
		
		towerActions.append("\n"+tower.getActions());
		
		ArrayList<CarChannel> channels = tower.getCarChannels();
		
		List<Car> cars = tower.getTowerChannel().getCars();
		
		
	}
	
	/**
	 * Calls MainWindow.Refresh every 20ms
	 */
	private class UploadUI extends TimerTask{

		@Override
		public void run() {
			MainWindow.this.refresh();
		}
		
	}

}
