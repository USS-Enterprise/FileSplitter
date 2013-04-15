package com.my.splitter;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import com.my.splitter.file.Splitter;
import com.my.splitter.model.SplitPanelModel;
import javax.swing.JSpinner;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private final JFileChooser fc = new JFileChooser();
	private final JToggleButton tglbtnSplit = new JToggleButton("Split");
	private final JToggleButton tglbtnStich = new JToggleButton("Stich");
	private final JButton splitButton = new JButton("Split");
	private final JLabel lblFolder = new JLabel();
	private final JLabel lblFile = new JLabel();
	private final JLabel lblSize = new JLabel();
	private final JSpinner spinner = new JSpinner();
	private final JLabel lblSplitSize = new JLabel("Split size");

	private final SplitPanelModel splitPaneelModel = new SplitPanelModel("Folder: ", "File: ", "Size: ");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 571, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);

		// Tool bar
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(10, 1, 545, 23);
		frame.getContentPane().add(toolBar);
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setBounds(10, 26, 545, 273);
		contentPane.add(mainPanel);

		
		JPanel panel = new JPanel();
		panel.setBounds(43, 90, 10, 10);
		mainPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 6, 425, 20);
		mainPanel.add(textField);
		textField.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				splitPanelReset();
				int result = fc.showDialog(frame, "Pick up the file to split");
				if (result == JFileChooser.APPROVE_OPTION) {
					splitSelectUpdate(fc.getSelectedFile());
//					new Splitter(file).split(50);
				}
			}
		});
		btnBrowse.setBounds(445, 5, 90, 23);
		mainPanel.add(btnBrowse);
		
		JPanel fileInfoPanel = new JPanel();
		fileInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		fileInfoPanel.setBounds(10, 37, 525, 225);
		fileInfoPanel.setLayout(null);
		splitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Splitter(splitPaneelModel.getFile()).split((Long)spinner.getValue());
			}
		});
		splitButton.setEnabled(false);
		splitButton.setBounds(452, 191, 63, 23);
		fileInfoPanel.add(splitButton);
		mainPanel.add(fileInfoPanel);
		
		lblFolder.setBounds(10, 11, 505, 14);
		lblFolder.setVisible(false);
		fileInfoPanel.add(lblFolder);
		
		lblFile.setBounds(10, 36, 505, 14);
		lblFile.setVisible(false);
		fileInfoPanel.add(lblFile);
		
		lblSize.setBounds(10, 61, 180, 14);
		lblSize.setVisible(false);
		fileInfoPanel.add(lblSize);
		
		JTextArea textArea = new JTextArea();
		textArea.setVisible(false);
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(10, 93, 429, 121);
		fileInfoPanel.add(textArea);
		
		lblSplitSize.setBounds(300, 61, 63, 14);
		lblSplitSize.setVisible(false);
		fileInfoPanel.add(lblSplitSize);
		
		spinner.setBounds(373, 61, 100, 20);
		spinner.setVisible(false);
		fileInfoPanel.add(spinner);
		
		
		tglbtnSplit.setSelected(true);
		tglbtnSplit.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnStich.setSelected(false);
					addPanel(contentPane, mainPanel);

				}
			}

		});
		toolBar.add(tglbtnSplit);
		
		tglbtnStich.setSelected(false);
		tglbtnStich.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnSplit.setSelected(false);
					removePanel(contentPane, mainPanel);
				}
			}

		});
		toolBar.add(tglbtnStich);
	}
	
	private void splitPanelReset() {
		splitButton.setEnabled(false);
		lblFolder.setText(splitPaneelModel.getFolderLabel());
		lblFile.setText(splitPaneelModel.getFileLabel());
		lblSize.setText(splitPaneelModel.getSizeLabel());
		lblFolder.setVisible(false);
		lblFile.setVisible(false);
		lblSize.setVisible(false);
		lblSplitSize.setVisible(false);
		spinner.setVisible(false);
	}

	private void splitSelectUpdate(File selectedFile) {
		lblFolder.setText(lblFolder.getText() +  selectedFile.toPath().getParent());
		lblFile.setText(lblFile.getText() +  selectedFile.toPath().getFileName());
		lblSize.setText(lblSize.getText() +  selectedFile.length());
		lblFolder.setVisible(true);
		lblFile.setVisible(true);
		lblSize.setVisible(true);
		lblSplitSize.setVisible(true);
		spinner.setVisible(true);
		spinner.setValue(selectedFile.length()/3);
		splitPaneelModel.setFile(selectedFile);
		splitButton.setEnabled(true);
	}

	private void addPanel(final Container container, final JPanel panel) {
		container.add(panel);
		container.revalidate();
		container.repaint();
	}

	private void removePanel(final Container container, final JPanel panel) {
		container.remove(panel);
		container.revalidate();
		container.repaint();
	}
}
