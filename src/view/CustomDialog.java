package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
    * This class creates and manages a network connection UI.  Add yourself an extra @author line.
    * @author Daniel Cormier
    * @version 1.3.1
    * @since 1.8.0_291
    * @see OthelloViewController
    */

public class CustomDialog extends JDialog implements ActionListener
{
    //You may need more than one field.  This mini-demo only uses one.
	JTextField addressInput;
	JTextField portInput;
	
	JLabel addressLabel = new JLabel("Enter IP Address: ");
	JLabel portLabel = new JLabel("Enter Port Number: ");
	

    public CustomDialog (JFrame mainView)
    {
        //The superclass needs to know what JFrame it's intercepting.
        //The title doesn't matter, nobody will see it.
        super(mainView,"Enter Network Information",true);
        
        //Uncomment the below line ONLY when you're just about done.
        //It will be much easier to manage your dialog when you have the bar at the top.
        
        /*setUndecorated(true);*/ // -decided to keep this commented
        
        //Seriously, uncomment the above only when you're just about done.
        //It is a monumental hassle otherwise.
        
        //This will hold your UI.  Of course you may rename it.
        Container networkPanel = getContentPane();
        //Add all your UI to networkPanel.
        

        //Your UI code goes here.  Add everything, ultimately, to networkPanel.
        
        //You don't need to use any of this code, it's just got the utmost basics.
        JPanel innerPanel = new JPanel();

        innerPanel.setLayout(new FlowLayout());

                
        addressInput = new JTextField(20);
        portInput = new JTextField(5);
        innerPanel.add(addressLabel);
        innerPanel.add(addressInput);
        innerPanel.add(portLabel);
        innerPanel.add(portInput);

        networkPanel.add(innerPanel);
        
        JButton button=new JButton("GO!");
        innerPanel.add(button);
        
        button.addActionListener(this);
        
        //And when you're done, of course you'll pack it.
        pack();
        
        
    }
    

    
    public String getAddress()
    {
    	return addressInput.getText();
    }
    
    public String getPort()
    {
    	return portInput.getText();
    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// The button has been pressed, let's just close down the shop.
		setVisible(false);// Hides window, object can still be called.
		//Control passes back to calling code.
	}
  
}
