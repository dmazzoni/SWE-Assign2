package it.univr.swe.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import it.univr.swe.Car;
import it.univr.swe.ManualCar;
import it.univr.swe.Simulator;

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


/**
 * 
 * This is the main class for SWE-Assign2.
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	///Refresh time of the MainWindow
	private static final int REFRESH_TIME = 20;
	
	
	private Simulator sim;

	private JTable table;

	private JProgressBar[] progress;
	
	private JTextArea towerActions;
	
	
	public MainWindow(Simulator sim){
		super();
		
		this.sim = sim;
		
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
		time.scheduleAtFixedRate(new UploadUI(), 0, REFRESH_TIME);
		
	}
	
	/**
	 * Refreshes the UI using the values of Cars,Actions and Traffics coming from Simulator
	 * 
	 */
	public void refresh() {
		
		InfoBean infoBean = sim.getInfoBean();
		
		List<String> actions = infoBean.getNewActions();
		for(String s : actions){
			towerActions.setText(s+"\n"+towerActions.getText());
		}
		
		List<Integer> traffics = infoBean.getTraffics();
		for(int I = 0;I<traffics.size();I++){
			progress[I].setValue(traffics.get(I));
		}
		
		MyTableModel myTableModel = (MyTableModel) table.getModel();
		myTableModel.updateCars(infoBean.getCars());
		
	}
	
	/**
	 * The Main
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		
		Simulator sim = new Simulator();
		
		MainWindow main = new MainWindow(sim);
		main.setVisible(true);
		
	}
	
	/**
	 * Calls MainWindow.Refresh every REFRESH_TIME
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
			cars = new CopyOnWriteArrayList<Car>();
		}

		/**
		 * Returns the number of columns in the table
		 * 
		 * @return The column count
		 */
		@Override
		public int getColumnCount() {
			return 4;
		}

		/**
		 * Returns the number of rows in the table
		 * 
		 * @return The row count
		 */
		@Override
		public int getRowCount() {
			return cars.size();
		}

		/**
		 * Returns the value of a cell in the table
		 * 
		 * @param row The cell row
		 * @param column The cell column
		 * @return The value of the cell
		 */
		@Override
		public Object getValueAt(int row, int column) {
			try {
				return getCarData(cars.get(row),column);
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
		}

		/**
		 * Returns the value of a column in a selected car
		 * 
		 * @param car The selected car
		 * @param column The column 
		 * @return The value of a column in a selected car
		 */
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

		/**
		 * Sets the new cars list and forces the table to refresh
		 * 
		 * @param cars The new car list
		 */
		public void updateCars(List<Car> cars) {
			
			this.cars = cars;
			this.fireTableDataChanged();
			
		}
		
		/**
		 * Returns the header name of a selected column
		 * 
		 * @param column The Selected column
		 * @return A string that contains the name of the coloumn
		 */
		public String getColumnName(int column)
		{ 
			return HEADERNAMES[column];  
		}
		
		
	}

}
