package ru.juise.compressor;

/**
 * User: juise
 * Date: 04.06.11
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class View extends JFrame {
	final String TITLE = "Компрессор изображений";

	int width = 250;
	int height = 80;
	
	JButton open = new JButton("Открыть");
	JButton compress = new JButton("Сжать");
	
	JLabel sizeText = new JLabel(" Сжимать до (Kb)");
	JTextField size = new JTextField("800", 4);
	
	String FILENAME = "";
	long SIZE = 1024 * 800;

	public View() {
		final Logger logger = LoggerFactory.getLogger(Main.class);
		
		setTitle(TITLE);

		setSize(width, height);
		setResizable(false);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Panel panel = new Panel();
		
		panel.setBackground(Color.WHITE);

		panel.setLayout(new GridLayout(2,2));

		panel.add(sizeText);
		panel.add(size);


		panel.add(open);
		panel.add(compress);

		compress.setEnabled(false);

		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				if (fileChooser.showOpenDialog(View.this) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						setTitle(file.getName());

						FILENAME = file.getAbsolutePath();
						try {
						SIZE = Integer.parseInt(size.getText());
						} catch (NumberFormatException e) {
							SIZE = 800;
						}
						if (SIZE < 800) {
							SIZE = 800;
						}
						
						size.setText(String.valueOf(SIZE));

						compress.setEnabled(true);
				}
			}
		});

		compress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SIZE = SIZE * 1024;

				compress.setEnabled(false);
				open.setEnabled(false);
				
				setTitle("Идет сжатие");
				
				new Thread(new Runnable() {
					   public void run() {
						   new Command(FILENAME, SIZE);
							setTitle("Готово");
							
							open.setEnabled(true);
							logger.info("Stop");
					   }
					}).start();
			}
		});

		setContentPane(panel);

		setVisible(true);
	}
}
