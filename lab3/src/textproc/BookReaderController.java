package textproc;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.*;


public class BookReaderController {
	
	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "Book Reader", 100, 300));
	}
	
	private void createWindow(GeneralWordCounter counter, String title, int height, int width) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		SortedListModel<Map.Entry<String,Integer>> model = new SortedListModel<Map.Entry<String,Integer>>(counter.getWordList());
		JList list = new JList(model);
		JScrollPane scrollPane= new JScrollPane(list);
		pane.add(scrollPane);
		
		JPanel panel = new JPanel();
		JRadioButton btnAlpha = new JRadioButton("Alphabetic",true); // the list is sorted alphabetically from the start, therefore this radiobutton should be checked.
		JRadioButton btnFreq = new JRadioButton("Frequency");
		
		ButtonGroup sortButtons = new ButtonGroup();
		sortButtons.add(btnAlpha);
		sortButtons.add(btnFreq);
		
		panel.add(btnAlpha, BorderLayout.WEST);
		panel.add(btnFreq, BorderLayout.EAST);
		pane.add(panel,  BorderLayout.SOUTH);
		
		btnAlpha.addActionListener(p -> model.sort((a,b) -> a.getKey().compareTo(b.getKey())));
		btnFreq.addActionListener(p -> model.sort((a,b) -> b.getValue() - a.getValue()));
		
		JTextField tfSearch = new JTextField();
	    tfSearch.setPreferredSize( new Dimension( 200, 24 ) ); // make textfield larger
		panel.add(tfSearch, BorderLayout.EAST);
		JButton btnSearch = new JButton("Search");
		panel.add(btnSearch,BorderLayout.EAST);
		
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
		
		frame.getRootPane().setDefaultButton(btnSearch);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
}
