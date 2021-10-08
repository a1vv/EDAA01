package textproc;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.*;


public class BookReaderController {
	
	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "Book Reader", 100, 300));
	}
	
	private void createWindow(GeneralWordCounter counter, String title, int height, int width) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		JList list = new JList(new SortedListModel<>(counter.getWordList()));
		JScrollPane scrollPane= new JScrollPane(list);
		pane.add(scrollPane);
		
		JPanel panel = new JPanel();
		JButton btnAlpha = new JButton("Alphabetic");
		JButton btnFreq = new JButton("Frequency");
		
		panel.add(btnAlpha, BorderLayout.WEST);
		panel.add(btnFreq, BorderLayout.EAST);
		pane.add(panel,  BorderLayout.SOUTH);
		
		btnAlpha.addActionListener(p -> {
			System.out.println("Du har tryckt på en knapp!");
			sortAlph();
		});
		btnFreq.addActionListener(p -> {
			System.out.println("Du har tryckt på en annan knapp!");
			sortFreq();
		});
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private void sortAlph() {
		
	}
	
	private void sortFreq() {
		
	}
}
