/**
 * @author Jacob Beck
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.awt.Canvas;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

/*
 * This app is a blood pressure trends app. It saves and averages blood pressure 
 * in a 5 day period. If your blood pressure is above normal 1 in 10 tips will display  
 * telling you how you can lower your blood pressure. If your blood pressure is normal it will 
 * display 1 in 5 messages congratulating you on your success with smiley faces and a smiley face dancing.
 * 
 * 
 * This app did not go toward all of what you asked, but is most there.
 */

public class blood_pressure_trends extends JFrame {
	// Setting the variables that need to be available for editing 
	private JPanel jPanel;
	private JTextField tfSystolic;
	private JTextField tfDiastolic;
	private JLabel lblDay1Systolic;
	private JLabel lblDay1Diastolic;
	private JLabel lblDay2Systolic;
	private JLabel lblDay2Diastolic;
	private JLabel lblDay3Systolic;
	private JLabel lblDay3Diastolic;
	private JLabel lblDay4Systolic;
	private JLabel lblDay4Diastolic;
	private JLabel lblDay5Systolic;
	private JLabel lblDay5Diastolic;
	private JLabel lblSystolicAvg;
	private JLabel lblDiastolicAvg;
	private Canvas tableCanvas;
	private JLabel lblImageL;
	private JTextPane tpMessage;
	private JLabel lblMessage;
	private JLabel lblImageR;
	private JLabel lblDanceImage;
	
	private Statement stmt;
	private Connection connection;
	
	private Calendar cal = Calendar.getInstance();
	
	private int id;
	private int systolic;
	private int diastolic;
	private int day;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					blood_pressure_trends frame = new blood_pressure_trends();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public blood_pressure_trends() throws SQLException {
		// GUI Settings ////////////////////////////////////////////////////////////////////////////////////
		setResizable(false);
		setFont(new Font("Times New Roman", Font.BOLD, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\img\\blood_drop.jpg"));
		setTitle("Blood PressureTrends");
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 610);
		jPanel = new JPanel();
		jPanel.setBackground(Color.WHITE);
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jPanel);
		jPanel.setLayout(null);
		
		JLabel lblSystolic = new JLabel("Systolic");
		lblSystolic.setBackground(Color.WHITE);
		lblSystolic.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSystolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblSystolic.setBounds(60, 11, 75, 20);
		jPanel.add(lblSystolic);
		
		tfSystolic = new JTextField();
		tfSystolic.setBounds(60, 36, 75, 20);
		jPanel.add(tfSystolic);
		tfSystolic.setColumns(10);
		
		JLabel lblDiastolic = new JLabel("Diastolic");
		lblDiastolic.setBackground(Color.WHITE);
		lblDiastolic.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDiastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiastolic.setBounds(365, 11, 75, 20);
		jPanel.add(lblDiastolic);
		
		tfDiastolic = new JTextField();
		tfDiastolic.setBounds(365, 36, 75, 20);
		jPanel.add(tfDiastolic);
		tfDiastolic.setColumns(10);
		
		JButton btnStore = new JButton("Store");
		btnStore.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnStore.setBounds(212, 86, 75, 20);
		jPanel.add(btnStore);
		
		JLabel lblTable = new JLabel("");
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTable.setBackground(Color.WHITE);
		lblTable.setBounds(60, 128, 55, 20);
		jPanel.add(lblTable);
		
		JLabel lblTableSystolic = new JLabel("Systolic");
		lblTableSystolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableSystolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTableSystolic.setBackground(Color.WHITE);
		lblTableSystolic.setBounds(60, 148, 55, 20);
		jPanel.add(lblTableSystolic);
		
		JLabel lblTableDiastolic = new JLabel("Diastolic");
		lblTableDiastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableDiastolic.setBackground(Color.WHITE);
		lblTableDiastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTableDiastolic.setBounds(60, 168, 55, 20);
		jPanel.add(lblTableDiastolic);
		
		JLabel lblDay1 = new JLabel("Day 1");
		lblDay1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay1.setBackground(Color.WHITE);
		lblDay1.setBounds(115, 128, 55, 20);
		jPanel.add(lblDay1);
		
		lblDay1Systolic = new JLabel("");
		lblDay1Systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay1Systolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay1Systolic.setBackground(Color.WHITE);
		lblDay1Systolic.setBounds(115, 148, 55, 20);
		jPanel.add(lblDay1Systolic);
		
		lblDay1Diastolic = new JLabel("");
		lblDay1Diastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay1Diastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay1Diastolic.setBackground(Color.WHITE);
		lblDay1Diastolic.setBounds(115, 168, 55, 20);
		jPanel.add(lblDay1Diastolic);
		
		JLabel lblDay2 = new JLabel("Day 2");
		lblDay2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay2.setBackground(Color.WHITE);
		lblDay2.setBounds(170, 128, 55, 20);
		jPanel.add(lblDay2);
		
		lblDay2Systolic = new JLabel("");
		lblDay2Systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay2Systolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay2Systolic.setBackground(Color.WHITE);
		lblDay2Systolic.setBounds(170, 148, 55, 20);
		jPanel.add(lblDay2Systolic);
		
		lblDay2Diastolic = new JLabel("");
		lblDay2Diastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay2Diastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay2Diastolic.setBackground(Color.WHITE);
		lblDay2Diastolic.setBounds(170, 168, 55, 20);
		jPanel.add(lblDay2Diastolic);
		
		JLabel lblDay3 = new JLabel("Day 3");
		lblDay3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay3.setBackground(Color.WHITE);
		lblDay3.setBounds(225, 128, 55, 20);
		jPanel.add(lblDay3);
		
		lblDay3Systolic = new JLabel("");
		lblDay3Systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay3Systolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay3Systolic.setBackground(Color.WHITE);
		lblDay3Systolic.setBounds(225, 148, 55, 20);
		jPanel.add(lblDay3Systolic);
		
		lblDay3Diastolic = new JLabel("");
		lblDay3Diastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay3Diastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay3Diastolic.setBackground(Color.WHITE);
		lblDay3Diastolic.setBounds(225, 168, 55, 20);
		jPanel.add(lblDay3Diastolic);
		
		JLabel lblDay4 = new JLabel("Day 4");
		lblDay4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay4.setBackground(Color.WHITE);
		lblDay4.setBounds(280, 128, 55, 20);
		jPanel.add(lblDay4);
		
		lblDay4Systolic = new JLabel("");
		lblDay4Systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay4Systolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay4Systolic.setBackground(Color.WHITE);
		lblDay4Systolic.setBounds(280, 148, 55, 20);
		jPanel.add(lblDay4Systolic);
		
		lblDay4Diastolic = new JLabel("");
		lblDay4Diastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay4Diastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay4Diastolic.setBackground(Color.WHITE);
		lblDay4Diastolic.setBounds(280, 168, 55, 20);
		jPanel.add(lblDay4Diastolic);
		
		JLabel lblDay5 = new JLabel("Day 5");
		lblDay5.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay5.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay5.setBackground(Color.WHITE);
		lblDay5.setBounds(335, 128, 55, 20);
		jPanel.add(lblDay5);
		
		lblDay5Systolic = new JLabel("");
		lblDay5Systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay5Systolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay5Systolic.setBackground(Color.WHITE);
		lblDay5Systolic.setBounds(335, 148, 55, 20);
		jPanel.add(lblDay5Systolic);
		
		lblDay5Diastolic = new JLabel("");
		lblDay5Diastolic.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay5Diastolic.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDay5Diastolic.setBackground(Color.WHITE);
		lblDay5Diastolic.setBounds(335, 168, 55, 20);
		jPanel.add(lblDay5Diastolic);
		
		JLabel lblAvg = new JLabel("AVG");
		lblAvg.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvg.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblAvg.setBackground(Color.WHITE);
		lblAvg.setBounds(390, 128, 55, 20);
		jPanel.add(lblAvg);
		
		lblSystolicAvg = new JLabel("");
		lblSystolicAvg.setHorizontalAlignment(SwingConstants.CENTER);
		lblSystolicAvg.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblSystolicAvg.setBackground(Color.WHITE);
		lblSystolicAvg.setBounds(390, 148, 55, 20);
		jPanel.add(lblSystolicAvg);
		
		lblDiastolicAvg = new JLabel("");
		lblDiastolicAvg.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiastolicAvg.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblDiastolicAvg.setBackground(Color.WHITE);
		lblDiastolicAvg.setBounds(390, 168, 55, 20);
		jPanel.add(lblDiastolicAvg);
		
		JLabel lblBlue = new JLabel("BLUE: Pressure too low");
		lblBlue.setForeground(Color.BLACK);
		lblBlue.setBackground(Color.BLUE);
		lblBlue.setOpaque(true);
		lblBlue.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblBlue.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlue.setBounds(28, 202, 150, 20);
		jPanel.add(lblBlue);
		
		JLabel lblGreen = new JLabel("GREEN: Ideal pressure");
		lblGreen.setForeground(Color.BLACK);
		lblGreen.setBackground(Color.GREEN);
		lblGreen.setOpaque(true);
		lblGreen.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblGreen.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreen.setBounds(178, 192, 150, 20);
		jPanel.add(lblGreen);
		
		JLabel lblYellow = new JLabel("YELLOW: Pressure pre-high");
		lblYellow.setForeground(Color.BLACK);
		lblYellow.setBackground(Color.YELLOW);
		lblYellow.setOpaque(true);
		lblYellow.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblYellow.setHorizontalAlignment(SwingConstants.CENTER);
		lblYellow.setBounds(178, 212, 150, 20);
		jPanel.add(lblYellow);
		
		JLabel lblRed = new JLabel("RED: Pressure too high");
		lblRed.setForeground(Color.BLACK);
		lblRed.setBackground(Color.RED);
		lblRed.setOpaque(true);
		lblRed.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblRed.setHorizontalAlignment(SwingConstants.CENTER);
		lblRed.setBounds(328, 202, 150, 20);
		jPanel.add(lblRed);
		
		tpMessage = new JTextPane();
		tpMessage.setEditable(false);
		tpMessage.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		tpMessage.setBackground(Color.WHITE);
		StyledDocument doc = tpMessage.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		tpMessage.setBounds(69, 236, 365, 65);
		jPanel.add(tpMessage);
		
		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMessage.setBounds(69, 236, 365, 65);
		jPanel.add(lblMessage);
		
		lblImageL = new JLabel("");
		lblImageL.setBackground(Color.WHITE);
		lblImageL.setOpaque(true);
		lblImageL.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageL.setBounds(5, 236, 55, 65);
		jPanel.add(lblImageL);
		
		lblImageR = new JLabel("");
		lblImageR.setBackground(Color.WHITE);
		lblImageR.setOpaque(true);
		lblImageR.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageR.setBounds(444, 236, 55, 65);
		jPanel.add(lblImageR);
		
		tableCanvas = new Canvas();
		tableCanvas.setForeground(Color.WHITE);
		tableCanvas.setBackground(Color.WHITE);
		tableCanvas.setBounds(0, 112, 504, 213);
		jPanel.add(tableCanvas);
		
		lblDanceImage = new JLabel("");
		lblDanceImage.setBackground(Color.WHITE);
		lblDanceImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanceImage.setBounds(0, 322, 504, 259);
		jPanel.add(lblDanceImage);
		// GUI Settings ////////////////////////////////////////////////////////////////////////////////////
		
		// The Store button Action listener
		btnStore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Setting the variables that will be stored in the database
				systolic = Integer.parseInt(tfSystolic.getText());
				diastolic = Integer.parseInt(tfDiastolic.getText());
				day = cal.get(Calendar.DAY_OF_MONTH) + 0; //Add to make future
				
				try {
					// Method that does the storing logic
					store();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	
		// Method that updates the images, table, and messages
		updateDisplay();
	}
	
	// Setting up the database
	private void initializeDB() { 
		try {  // Load the JDBC driver 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			// Establish a connection 
			connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "root", ""); // May need to alter to match your DB 
			
			// Create a statement 
			stmt = connection.createStatement();
			
			/*
			 	In a real world application I would make the user create an account.
			 	Once they have an account I would create a table that is linked to 
				their account to store the blood test.
				This would cause more database set up as well.
			*/
			// Create the table if does not exist
			stmt.execute("CREATE TABLE IF NOT EXISTS Tests (" +
					"id CHAR(1) NOT NULL," +
					"systolic CHAR(3) NOT NULL," +
					"diastolic CHAR(3) NOT NULL," +
					"day CHAR(2) NOT NULL)"
			);
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		} 
	}
	
	// Closing the database connection and statement
	private void closeDB() throws SQLException {
		connection.close();
		stmt.close();
	}
	
	private void prepStore(int dbID, int dbDay) throws SQLException {
		/*
		 * Since the blood pressure is only check for trends in a 5 day period,
		 * I build the logic to keep the app in the scope
		 * */
		// Checking the database to find what the id will be
		if(dbDay == day) {
			id = dbID;
		} else {
			if(dbID >= 5 && dbDay != day) {
				stmt.execute("DROP TABLE Tests");// Drop the table and recreate it. Keeps it clean and keeps it in the 5 day scope.
				closeDB();
				initializeDB();
				id = 1;
			} else if(dbID != 5 && dbDay != day) {
				switch(dbID) {
					case 1:
					case 2:
					case 3:
					case 4:
						id = dbID + 1;
						break;
				}
			}
		}
	}
	
	// Storing the trend in the database
	private void store() throws SQLException {
		initializeDB(); 
		String queryString = "SELECT id, day FROM tests";
		ResultSet rset = stmt.executeQuery(queryString);
		rset.afterLast(); // Getting ready to retrieve the last row inserted
		
		if(rset.previous()) { // Retrieving the last row
			int dbID = rset.getInt(1);
			int dbDay = rset.getInt(2);
			prepStore(dbID, dbDay);
		} else {
			id = 1;
		}
	 
		// Creating the query
		queryString = "INSERT INTO tests (id, systolic, diastolic, day) VALUES (" +
				"'" + id + "', " + 
				"'" + systolic + "', " +
				"'" + diastolic + "', " +
				"'" + day + "')";
	
		// Executing the query
		stmt.execute(queryString);
		closeDB();
		tfSystolic.setText("");
		tfDiastolic.setText("");
		updateDisplay();
	}

	private void updateDisplay() throws SQLException {
		int systolicAVG = 0;
		int diastolicAVG = 0;
		int avgCount = 0;
		
		// Smiley Face icons
		ArrayList<String> smileIcons = new ArrayList<String>();
		String smileDir = System.getProperty("user.dir") + "\\img\\smileIcons";
		String[] addSmileIcons = {smileDir + "\\smile1.png", smileDir + "\\smile2.png", smileDir + "\\smile3.png", 
				smileDir + "\\smile4.png", smileDir + "\\smile5.png"};
		Collections.addAll(smileIcons, addSmileIcons);
		
		// Tips received from http://www.prevention.com/health/how-lower-blood-pressure-naturally
		// How to lower blood pressure tips
		ArrayList<String> lowerBPTips = new ArrayList<String>();
		String[] addLowerBPTips = {
				"Go for power walks: Hypertensive patients who went for fitness walks at a brisk pace lowered pressure by almost 8 mmhg over 6 mmhg", 
				"Breathe deeply: Slow breathing and meditative practices such as qigong, yoga, and tai chi decrease stress hormones, which elevate renin, a kidney enzyme that raises blood pressure.",
				"Pick potatoes: Loading up on potassium-rich fruits and vegetables is an important part of any blood pressure-lowering program, says Linda Van Horn, PhD, RD, professor of preventive medicine at Northwestern University Feinberg School of Medical.",
				"Be salt smart: Certain groups of people—the elderly, African Americans, and those with a family history of high blood pressure—are more likely than others to have blood pressure that's particularly salt (or sodium) sensitive.",
				"Indulge in dark chocolate: Dark chocolate varieties contain flavanols that make blood vessels more elastic. In one study, 18% of patients who ate it every day saw blood pressure decrease. Have ½ ounce daily (make sure it contains at least 70% cocoa, like these Cacao Wafers).",
				"Take a supplement: In a review of 12 studies, researchers found that coenzyme Q10 reduced blood pressure by up to 17 mmhg over 10 mmhg. ",
				"Drink (a little) alcohol: A study of women at Boston's Brigham and Women's Hospital, for example, found that light drinking (defined as one-quarter to one-half a drink per day for a woman) may actually reduce blood pressure more than no drinks per day.",
				"Switch to decaf coffee: Some studies have shown no effect, but one from Duke University Medical Center found that caffeine consumption of 500 mg—roughly three 8-ounce cups of coffee—increased blood pressure by 4 mmhg, and that effect lasted until bedtime.",
				"Take up tea: Study participants who sipped 3 cups of a hibiscus tea daily lowered systolic blood pressure by 7 points in 6 weeks on average, say researchers from Tufts University—results on par with many prescription medications.",
				"Work (a bit) less: Putting in more than 41 hours per week at the office raises your risk of hypertension by 15%, according to a University of California, Irvine, study of 24,205 California residents."
		};
		Collections.addAll(lowerBPTips, addLowerBPTips);
		
		// Congrats messages
		ArrayList<String> congrats = new ArrayList<String>();
		String[] addcongrats = {
				"There you go. That is looking good.", 
				"Just right. Time to go party. Just not to hard now.",
				"Well done. I think you just earned yourself some suger free pie.",
				"Well that wasn't too hard now was it.",
				"That is looking better than mine. Maybe you should give me some tips."
		};
		Collections.addAll(congrats, addcongrats);
		
		// Raise Blood Pressure Tips
		ArrayList<String> raiseBPTips = new ArrayList<String>();
		String[] addRaiseBPTips = {
				"Hold on. You should eat a little sweets.", 
				"Are you eating the right amount of calaries?",
				"Come you can do better than that."
		};
		Collections.addAll(congrats, addcongrats);
		
		// Medical icons
		ArrayList<String> medIcons = new ArrayList<String>();
		String medDir = System.getProperty("user.dir") + "\\img\\medIcons";
		String[] addMedIcons = { medDir + "\\bag.png", medDir + "\\caduceus.png", medDir + "\\clinic.png", medDir + "\\heart.png", medDir + "\\hospital.png", 
				medDir + "\\nurse.png", medDir + "\\patient.png", medDir + "\\pharmacy.png", medDir + "\\pill.png", medDir + "\\syringe.png"};
		Collections.addAll(medIcons, addMedIcons);
		
		initializeDB();
		
		// Updating Table, Color, Icons, and Message area //////////////////////////////////////////////////////////////////
		String queryString = "SELECT systolic, diastolic FROM tests WHERE id = 1";
		ResultSet rset = stmt.executeQuery(queryString);
		if(rset.next()) {
			do {
				int dbSystolic = rset.getInt(1);
				int dbDiastolic = rset.getInt(2);
				
				systolicAVG = systolicAVG + dbSystolic;
				diastolicAVG = diastolicAVG + dbDiastolic;
				avgCount++;
				
			} while(rset.next());
			systolicAVG = systolicAVG / avgCount;
			diastolicAVG = diastolicAVG / avgCount;
			lblDay1Systolic.setText(String.valueOf(systolicAVG));
			lblDay1Diastolic.setText(String.valueOf(diastolicAVG));
		} else {
			lblDay1Systolic.setText("");
			lblDay1Diastolic.setText("");
		}
		
		queryString = "SELECT systolic, diastolic FROM tests WHERE id = 2";
		rset = stmt.executeQuery(queryString);
		if(rset.next()) {
			systolicAVG = 0;
			diastolicAVG = 0;
			avgCount = 0;
			do {
				int dbSystolic = rset.getInt(1);
				int dbDiastolic = rset.getInt(2);
				
				systolicAVG = systolicAVG + dbSystolic;
				diastolicAVG = diastolicAVG + dbDiastolic;
				avgCount++;
				
			} while(rset.next());
			systolicAVG = systolicAVG / avgCount;
			diastolicAVG = diastolicAVG / avgCount;
			lblDay2Systolic.setText(String.valueOf(systolicAVG));
			lblDay2Diastolic.setText(String.valueOf(diastolicAVG));
		} else {
			lblDay2Systolic.setText("");
			lblDay2Diastolic.setText("");
		}
		
		queryString = "SELECT systolic, diastolic FROM tests WHERE id = 3";
		rset = stmt.executeQuery(queryString);
		if(rset.next()) {
			systolicAVG = 0;
			diastolicAVG = 0;
			avgCount = 0;
			do {
				int dbSystolic = rset.getInt(1);
				int dbDiastolic = rset.getInt(2);
				
				systolicAVG = systolicAVG + dbSystolic;
				diastolicAVG = diastolicAVG + dbDiastolic;
				avgCount++;
				
			} while(rset.next());
			systolicAVG = systolicAVG / avgCount;
			diastolicAVG = diastolicAVG / avgCount;
			lblDay3Systolic.setText(String.valueOf(systolicAVG));
			lblDay3Diastolic.setText(String.valueOf(diastolicAVG));
		} else {
			lblDay3Systolic.setText("");
			lblDay3Diastolic.setText("");
		}
		
		queryString = "SELECT systolic, diastolic FROM tests WHERE id = 4";
		rset = stmt.executeQuery(queryString);
		if(rset.next()) {
			systolicAVG = 0;
			diastolicAVG = 0;
			avgCount = 0;
			do {
				int dbSystolic = rset.getInt(1);
				int dbDiastolic = rset.getInt(2);
				
				systolicAVG = systolicAVG + dbSystolic;
				diastolicAVG = diastolicAVG + dbDiastolic;
				avgCount++;
				
			} while(rset.next());
			systolicAVG = systolicAVG / avgCount;
			diastolicAVG = diastolicAVG / avgCount;
			lblDay4Systolic.setText(String.valueOf(systolicAVG));
			lblDay4Diastolic.setText(String.valueOf(diastolicAVG));
		} else {
			lblDay4Systolic.setText("");
			lblDay4Diastolic.setText("");
		}
		
		queryString = "SELECT systolic, diastolic FROM tests WHERE id = 5";
		rset = stmt.executeQuery(queryString);
		if(rset.next()) {
			systolicAVG = 0;
			diastolicAVG = 0;
			avgCount = 0;
			do {
				int dbSystolic = rset.getInt(1);
				int dbDiastolic = rset.getInt(2);
				
				systolicAVG = systolicAVG + dbSystolic;
				diastolicAVG = diastolicAVG + dbDiastolic;
				avgCount++;
				
			} while(rset.next());
			systolicAVG = systolicAVG / avgCount;
			diastolicAVG = diastolicAVG / avgCount;
			lblDay5Systolic.setText(String.valueOf(systolicAVG));
			lblDay5Diastolic.setText(String.valueOf(diastolicAVG));
		} else {
			lblDay5Systolic.setText("");
			lblDay5Diastolic.setText("");
		}
		
		/*
		 * I am sure there could be an easier way to test the logic of the fields,
		 * but this is the easiest way I could come up with.
		 * */
		if(lblDay1Systolic.getText() == "" && lblDay1Diastolic.getText() == "" &&
		   lblDay2Systolic.getText() == "" && lblDay2Diastolic.getText() == "" &&
		   lblDay3Systolic.getText() == "" && lblDay3Diastolic.getText() == "" &&
		   lblDay4Systolic.getText() == "" && lblDay4Diastolic.getText() == "" &&
		   lblDay5Systolic.getText() == "" && lblDay5Diastolic.getText() == "") {
			lblSystolicAvg.setText("");
			lblDiastolicAvg.setText("");
		} else if(lblDay1Systolic.getText() != "" && lblDay1Diastolic.getText() != "" &&
				   lblDay2Systolic.getText() == "" && lblDay2Diastolic.getText() == "" &&
				   lblDay3Systolic.getText() == "" && lblDay3Diastolic.getText() == "" &&
				   lblDay4Systolic.getText() == "" && lblDay4Diastolic.getText() == "" &&
				   lblDay5Systolic.getText() == "" && lblDay5Diastolic.getText() == "") {
			int day1Systolic = Integer.parseInt(lblDay1Systolic.getText());
			int day1Diastolic = Integer.parseInt(lblDay1Diastolic.getText());
			lblSystolicAvg.setText(String.valueOf(day1Systolic / 1));
			lblDiastolicAvg.setText(String.valueOf(day1Diastolic / 1));
		} else if(lblDay1Systolic.getText() != "" && lblDay1Diastolic.getText() != "" &&
				   lblDay2Systolic.getText() != "" && lblDay2Diastolic.getText() != "" &&
				   lblDay3Systolic.getText() == "" && lblDay3Diastolic.getText() == "" &&
				   lblDay4Systolic.getText() == "" && lblDay4Diastolic.getText() == "" &&
				   lblDay5Systolic.getText() == "" && lblDay5Diastolic.getText() == "") {
			int day1Systolic = Integer.parseInt(lblDay1Systolic.getText());
			int day1Diastolic = Integer.parseInt(lblDay1Diastolic.getText());
			int day2Systolic = Integer.parseInt(lblDay2Systolic.getText());
			int day2Diastolic = Integer.parseInt(lblDay2Diastolic.getText());
			lblSystolicAvg.setText(String.valueOf((day1Systolic + day2Systolic) / 2));
			lblDiastolicAvg.setText(String.valueOf((day1Diastolic + day2Diastolic) / 2));
		} else if(lblDay1Systolic.getText() != "" && lblDay1Diastolic.getText() != "" &&
				   lblDay2Systolic.getText() != "" && lblDay2Diastolic.getText() != "" &&
				   lblDay3Systolic.getText() != "" && lblDay3Diastolic.getText() != "" &&
				   lblDay4Systolic.getText() == "" && lblDay4Diastolic.getText() == "" &&
				   lblDay5Systolic.getText() == "" && lblDay5Diastolic.getText() == "") {
			int day1Systolic = Integer.parseInt(lblDay1Systolic.getText());
			int day1Diastolic = Integer.parseInt(lblDay1Diastolic.getText());
			int day2Systolic = Integer.parseInt(lblDay2Systolic.getText());
			int day2Diastolic = Integer.parseInt(lblDay2Diastolic.getText());
			int day3Systolic = Integer.parseInt(lblDay3Systolic.getText());
			int day3Diastolic = Integer.parseInt(lblDay3Diastolic.getText());
			lblSystolicAvg.setText(String.valueOf((day1Systolic + day2Systolic + day3Systolic) / 3));
			lblDiastolicAvg.setText(String.valueOf((day1Diastolic + day2Diastolic + day3Diastolic) / 3));
		} else if(lblDay1Systolic.getText() != "" && lblDay1Diastolic.getText() != "" &&
				   lblDay2Systolic.getText() != "" && lblDay2Diastolic.getText() != "" &&
				   lblDay3Systolic.getText() != "" && lblDay3Diastolic.getText() != "" &&
				   lblDay4Systolic.getText() != "" && lblDay4Diastolic.getText() != "" &&
				   lblDay5Systolic.getText() == "" && lblDay5Diastolic.getText() == "") {
			int day1Systolic = Integer.parseInt(lblDay1Systolic.getText());
			int day1Diastolic = Integer.parseInt(lblDay1Diastolic.getText());
			int day2Systolic = Integer.parseInt(lblDay2Systolic.getText());
			int day2Diastolic = Integer.parseInt(lblDay2Diastolic.getText());
			int day3Systolic = Integer.parseInt(lblDay3Systolic.getText());
			int day3Diastolic = Integer.parseInt(lblDay3Diastolic.getText());
			int day4Systolic = Integer.parseInt(lblDay4Systolic.getText());
			int day4Diastolic = Integer.parseInt(lblDay4Diastolic.getText());
			lblSystolicAvg.setText(String.valueOf((day1Systolic + day2Systolic + day3Systolic + day4Systolic) / 4));
			lblDiastolicAvg.setText(String.valueOf((day1Diastolic + day2Diastolic + day3Diastolic + day4Diastolic) / 4));
		} else if(lblDay1Systolic.getText() != "" && lblDay1Diastolic.getText() != "" &&
				   lblDay2Systolic.getText() != "" && lblDay2Diastolic.getText() != "" &&
				   lblDay3Systolic.getText() != "" && lblDay3Diastolic.getText() != "" &&
				   lblDay4Systolic.getText() != "" && lblDay4Diastolic.getText() != "" &&
				   lblDay5Systolic.getText() != "" && lblDay5Diastolic.getText() != "") {
			int day1Systolic = Integer.parseInt(lblDay1Systolic.getText());
			int day1Diastolic = Integer.parseInt(lblDay1Diastolic.getText());
			int day2Systolic = Integer.parseInt(lblDay2Systolic.getText());
			int day2Diastolic = Integer.parseInt(lblDay2Diastolic.getText());
			int day3Systolic = Integer.parseInt(lblDay3Systolic.getText());
			int day3Diastolic = Integer.parseInt(lblDay3Diastolic.getText());
			int day4Systolic = Integer.parseInt(lblDay4Systolic.getText());
			int day4Diastolic = Integer.parseInt(lblDay4Diastolic.getText());
			int day5Systolic = Integer.parseInt(lblDay5Systolic.getText());
			int day5Diastolic = Integer.parseInt(lblDay5Diastolic.getText());
			lblSystolicAvg.setText(String.valueOf((day1Systolic + day2Systolic + day3Systolic + day4Systolic + day5Systolic) / 5));
			lblDiastolicAvg.setText(String.valueOf((day1Diastolic + day2Diastolic + day3Diastolic + day4Diastolic + day5Diastolic) / 5));
		}
		// Updating Table, Color, Icons, and Message area //////////////////////////////////////////////////////////////////
		
		// Updating Color, Icons, and Message area //////////////////////////////////////////////////////////////////
		if(lblSystolicAvg.getText() != "" && lblDiastolicAvg.getText() != "") {
			if(Integer.parseInt(lblSystolicAvg.getText()) <= 90 || Integer.parseInt(lblDiastolicAvg.getText()) <= 60) {
				tableCanvas.setBackground(Color.BLUE);
				
				Collections.shuffle(medIcons);
				lblImageL.setBackground(Color.BLUE);
				lblImageL.setIcon(new ImageIcon(medIcons.get(0)));
				lblImageR.setBackground(Color.BLUE);
				lblImageR.setIcon(new ImageIcon(medIcons.get(0)));
				
				Collections.shuffle(raiseBPTips);
				tpMessage.setText(raiseBPTips.get(0));
				
				lblDanceImage.setIcon(new ImageIcon(""));
			} else if(Integer.parseInt(lblSystolicAvg.getText()) >= 90 && Integer.parseInt(lblSystolicAvg.getText()) <= 120 ||
					Integer.parseInt(lblDiastolicAvg.getText()) >= 60 && Integer.parseInt(lblDiastolicAvg.getText()) <= 80) {
				tableCanvas.setBackground(Color.GREEN);
				
				Collections.shuffle(smileIcons);
				lblImageL.setBackground(Color.GREEN);
				lblImageL.setIcon(new ImageIcon(smileIcons.get(0)));
				lblImageR.setBackground(Color.GREEN);
				lblImageR.setIcon(new ImageIcon(smileIcons.get(0)));
				
				Collections.shuffle(congrats);
				tpMessage.setText(congrats.get(0));
				
				lblDanceImage.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\dance.gif"));
			} else if(Integer.parseInt(lblSystolicAvg.getText()) >= 120 && Integer.parseInt(lblSystolicAvg.getText()) <= 140 ||
					Integer.parseInt(lblDiastolicAvg.getText()) >= 80 && Integer.parseInt(lblDiastolicAvg.getText()) <= 90) {
				tableCanvas.setBackground(Color.YELLOW);
				
				Collections.shuffle(medIcons);
				lblImageL.setBackground(Color.YELLOW);
				lblImageL.setIcon(new ImageIcon(medIcons.get(0)));
				lblImageR.setBackground(Color.YELLOW);
				lblImageR.setIcon(new ImageIcon(medIcons.get(0)));
				
				Collections.shuffle(lowerBPTips);
				tpMessage.setText(lowerBPTips.get(0));
				
				lblDanceImage.setIcon(new ImageIcon(""));
			} else if(Integer.parseInt(lblSystolicAvg.getText()) >= 140 || Integer.parseInt(lblDiastolicAvg.getText()) >= 90) {
				tableCanvas.setBackground(Color.RED);
				
				Collections.shuffle(medIcons);
				lblImageL.setBackground(Color.RED);
				lblImageL.setIcon(new ImageIcon(medIcons.get(0)));
				lblImageR.setBackground(Color.RED);
				lblImageR.setIcon(new ImageIcon(medIcons.get(0)));
				
				Collections.shuffle(lowerBPTips);
				tpMessage.setText(lowerBPTips.get(0));
				
				lblDanceImage.setIcon(new ImageIcon(""));
			}
		}
		// Updating Color, Icons, and Message area //////////////////////////////////////////////////////////////////	
	}
}
