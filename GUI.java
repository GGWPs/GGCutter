package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private GGCutter ctx;

	public GUI(GGCutter main) {
		getContentPane().setBackground(Color.GREEN);
		setForeground(Color.GREEN);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.GREEN);
		this.ctx = main;
		initComponents();
	}

	private void button1ActionPerformed(ActionEvent e) {
		// if button is clicked this will be activated
		ctx.setStartScript(true);
		this.setVisible(false);
	}

	private void initComponents() {
		comboBox1 = new JComboBox<>();
		comboBox1.setBounds(10, 46, 136, 75);
		
		setTitle("GG's CUTTER");
		comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
				"Free2Play", "Member", }));

		JLabel lblNewLabel = new JLabel("P2P or F2P?");
		lblNewLabel.setBounds(50, 23, 136, 34);
		getContentPane().setPreferredSize(new Dimension(300, 200));
		pack();
		setLocationRelativeTo(getOwner());
		getContentPane().setLayout(null);
		getContentPane().add(comboBox1);
		getContentPane().add(lblNewLabel);
		button1 = new JButton();
		button1.setBounds(159, 15, 81, 106);

		button1.setText("Start");
		button1.addActionListener(e -> button1ActionPerformed(e));
		getContentPane().add(button1);
	}
	public String getway() {
		return comboBox1.getSelectedItem().toString();
	}

	private JButton button1;
	private JComboBox<String> comboBox1;
}
