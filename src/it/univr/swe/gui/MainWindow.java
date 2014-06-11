package it.univr.swe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.Car;
import it.univr.swe.ManualCar;
import it.univr.swe.Simulator;
import it.univr.swe.Tower;
import it.univr.swe.communication.CarChannel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

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
	
	
	public MainWindow(Simulator sim){
		super();
		
		/*Get objects from Simulator*/
		tower = sim.getTower();
		
		this.setSize(900, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable(new MyTableModel());
		
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
					progress[I].setValue(0);
					
					leftPanel.add(progress[I]);
				}
			
			internalPanel.add(topPanel,BorderLayout.NORTH);
			internalPanel.add(scroll,BorderLayout.CENTER);
			internalPanel.add(leftPanel,BorderLayout.LINE_START);
		
		this.getContentPane().add(internalPanel);
		
		/*Create and Start UploadUI Timer Task*/
		Timer time = new Timer();
		time.scheduleAtFixedRate(new UploadUI(), 0, 10);
		
	}
	
	/**
	 * Refreshes the UI using new values of cars,tower and carChannel
	 */
	public void refresh() {
		
		List<String> actions = tower.getActions();
		for(String s : actions){
			towerActions.setText(s+"\n"+towerActions.getText());
			//towerActions.setCaretPosition(s.length()+towerActions.getCaretPosition());
		}
		
		List<CarChannel> channels = tower.getCarChannels();
		for(int I = 0;I<channels.size();I++){
			progress[I].setValue(channels.get(I).getTraffic());
		}
		
		MyTableModel myTableModel = (MyTableModel) table.getModel();
		myTableModel.updateCars(tower.getTowerChannel().getCars());
		
	}
	
	/**
	 * The Main
	 * @param args
	 */
	public static void main(String args[]){
		
		Simulator sim = new Simulator();
		
		MainWindow main = new MainWindow(sim);
		main.setVisible(true);
		
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
	
	private class MyTableModel extends AbstractTableModel{

		private static final int ID = 0;
		private static final int SPEED = 1;
		private static final int DISPLAY = 2;
		private static final int AUTOMAN = 3;
		
		private final String[] HEADERNAMES = {"ID","Speed","Display","Auto-Man"};
		
		private List<Car> cars;
		
		public MyTableModel(){
			cars = new ArrayList<Car>();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return cars.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			return getCarData(cars.get(row),column);
		}

		private String getCarData(Car car,int column) {
			String result = "";
			
			switch(column){
			case ID:{
				result = ""+car.getId();
				break;
			}
			case SPEED:{
				result = ""+car.getSpeed();
				break;
			}
			case DISPLAY:{
				result = ""+car.getDisplay();
				break;
			}
			case AUTOMAN:{
				if( car instanceof ManualCar){
					result = "Manual";
				}
				else{
					result = "Automatic";
				}
				break;
			}
			}
			
			return result;
		}

		public void updateCars(List<Car> cars) {
			
			this.cars = cars;
			this.fireTableDataChanged();
			
		}
		
		public String getColumnName(int column)
		{ 
			return HEADERNAMES[column];  
		}
		
		
	}

}
