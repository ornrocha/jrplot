/************************************************************************** 
 * Orlando Rocha (ornrocha@gmail.com)
 *
 * This is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This code is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Public License for more details. 
 * 
 * You should have received a copy of the GNU Public License 
 * along with this code. If not, see http://www.gnu.org/licenses/ 
 *  
 */
package jrplot.plotpackages.interactive.shinywrapper.shinyfeature;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShinyMessageDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel lblMessage;
	private Component parent;
	
	
	public ShinyMessageDialog() {
		initGUI();
	}
	
	
	public ShinyMessageDialog(Component parent) {
		this.parent=parent;
		initGUI();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public ShinyMessageDialog(Component parent, String initmessage) {
		this(parent);
		setMessage(initmessage);
	}
	
	
	
	private void initGUI() {
		
		this.panel = new JPanel();
		getContentPane().add(this.panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1,1};
		gbl_panel.rowHeights = new int[]{1};
		gbl_panel.columnWeights = new double[]{0.2,1.0};
		gbl_panel.rowWeights = new double[]{1.0};
		this.panel.setLayout(gbl_panel);
		
		this.lblNewLabel = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(ShinyMessageDialog.class.getResource("/images/info.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		//ImageIcon image=new ImageIcon(ShinyMessageDialog.class.getResource("/images/info.png"));
		this.lblNewLabel.setIcon(imageIcon);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		this.panel.add(this.lblNewLabel, gbc_lblNewLabel);
		
		this.lblMessage = new JLabel("Message");
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.anchor = GridBagConstraints.WEST;
		gbc_lblMessage.gridx = 1;
		gbc_lblMessage.gridy = 0;
		this.panel.add(this.lblMessage, gbc_lblMessage);
		//setPreferredSize(new Dimension(200, 100));
		setSize(new Dimension(563, 94));
	}
	
	public void display() {
		setAlwaysOnTop(true);
		setModal(true);
		if(parent!=null)
			setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	public void setMessage(String message) {
		lblMessage.setText(message);
	}
	
	
	public void close() {
		setVisible(false);
	}
	
	
	
	

}
