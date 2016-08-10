package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.List;
import java.util.Map.Entry;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.flatironschool.javacs.Runner;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {
	
	IView2ModelAdapter view2Model;
	public MainFrame(IView2ModelAdapter view2Model) {
		this.view2Model = view2Model;
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(20);
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_3.add(textField);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Entry<String, Integer>> entries = view2Model.search(textField.getText());
				System.out.println(textField.getText());
				String[] results = new String[10];
				int index = 0;
				if (entries.isEmpty()) {
					label1.setText("No result found!");
					label2.setText("");
					label3.setText("");
					label4.setText("");

				} else {
					for (Entry<String, Integer> entry: entries) {
						results[index] = entry.getKey();
						index++;
					}
					label1.setText(results[0]);
					label2.setText(results[1]);
					label3.setText(results[2]);
					label4.setText(results[3]);
				}
			}
		});
		
		panel_3.add(btnSubmit);
		
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label1);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label2);
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label3);
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_1.add(label4);
	}

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -3256914973903308813L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel() {
		/**
		 * Generate a UID.
		 */
		private static final long serialVersionUID = -872444218515942499L;

		/**
		* Overridden paintComponent method to paint a shape in the panel.
		* @param g The Graphics object to paint on.
		**/
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);   // Do everything normally done first, e.g. clear the screen.
		    g.setColor(Color.RED);  // Set the color to use when drawing
		}
	};
	private final JPanel panel_3 = new JPanel();
	private final JTextField textField = new JTextField();
	private final JButton btnSubmit = new JButton("Submit");
	private final JPanel panel_1 = new JPanel();
	private final JLabel label1 = new JLabel("");
	private final JLabel label2 = new JLabel("");
	private final JLabel label3 = new JLabel("");
	private final JLabel label4 = new JLabel("");
	
	/*
	 * Encapsulate the start-up process of the frame.
	 * */
	public void start(){
		setVisible(true);
	}
}
