package textproc;

import javax.swing.*;

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
		frame.pack();
		frame.setVisible(true);
	}
}
