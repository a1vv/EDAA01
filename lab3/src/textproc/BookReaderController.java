package textproc;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;
import java.util.Map.Entry;


public class BookReaderController {
	
	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "Book Reader", 100, 300));
	}
	
	private void createWindow(GeneralWordCounter counter, String title, int height, int width) {
		
		// create new frame
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get the contentPane from frame
		Container pane = frame.getContentPane();
		
		// create model 
		SortedListModel<Map.Entry<String,Integer>> model = new SortedListModel<Map.Entry<String,Integer>>(counter.getWordList());
		
		// create jlist and pass model into it
		JList<Entry<String, Integer>> list = new JList<Entry<String, Integer>>(model);
		
		// create scrollpane that contains the list
		JScrollPane scrollPane= new JScrollPane(list);
		
		// add scrollpane (that contains the list) to the contentpane
		pane.add(scrollPane);
		
		// create buttons and listeners
		JRadioButton btnAlpha = new JRadioButton("Alphabetic",true); // the list is sorted alphabetically from the start, therefore this radiobutton should be checked.
		JRadioButton btnFreq = new JRadioButton("Frequency");
		ButtonGroup sortButtons = new ButtonGroup();
		sortButtons.add(btnAlpha);
		sortButtons.add(btnFreq);
		btnAlpha.addActionListener(p -> model.sort((a,b) -> a.getKey().compareTo(b.getKey())));
		btnFreq.addActionListener(p -> model.sort((a,b) -> b.getValue() - a.getValue()));
		

		// search-box and search-button
		JTextField tfSearch = new JTextField();
	    tfSearch.setPreferredSize( new Dimension( 200, 24 ) ); // make textfield larger
	    JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(p -> {
			String searchTerm = tfSearch.getText().toLowerCase().replaceAll("\\s", ""); // extract typed word and remove spaces.
			int index = list.getNextMatch(searchTerm,0,Position.Bias.Forward); // find typed word in list
			list.setSelectedIndex(index); // select item
			if(index != -1) { 
				list.ensureIndexIsVisible(index);// scroll to item
			} else {
				JOptionPane.showMessageDialog(frame, "Word not found.");
			}
		});
		
		// add buttons and textbox to panel
		JPanel panel = new JPanel();
		panel.add(btnAlpha, BorderLayout.WEST);
		panel.add(btnFreq, BorderLayout.EAST);
		panel.add(tfSearch, BorderLayout.EAST);
		panel.add(btnSearch,BorderLayout.EAST);
		
		// add panel to bottom of pane
		pane.add(panel,  BorderLayout.SOUTH);
		
		// set Enter-key to press search-button
		frame.getRootPane().setDefaultButton(btnSearch);

		frame.pack();
		frame.setVisible(true);
	}
}
