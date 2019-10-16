import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Window {

	private JFrame frmLzTool;
	private JTextField compress;
	private JTextField decompress;
	private JTextField txtOutput;
	private JTextField FileName;
	private JButton btnCompressFile;
	private JButton btnDecompressFile;
	private JLabel lblFileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmLzTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLzTool = new JFrame();
		frmLzTool.setTitle("LZ78 Tool");
		frmLzTool.setBounds(100, 100, 450, 300);
		frmLzTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLzTool.getContentPane().setLayout(null);
		
		JButton btnCompress = new JButton("Compress");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Compression compressor = new Compression(compress.getText());
				@SuppressWarnings("unused")
				ArrayList<Tag> tags = compressor.compress();
				txtOutput.setText(compressor.toString());

			}
		});
		btnCompress.setBounds(10, 84, 125, 23);
		frmLzTool.getContentPane().add(btnCompress);
		
		JButton btnDecompress = new JButton("Decompress");
		btnDecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        String inputTags = decompress.getText();
			        inputTags = inputTags.replace("\r", "");
			        ArrayList<Tag> tags = new ArrayList<Tag>();
			        
			        int len = inputTags.length();
					for (int i=0 ; i<len;i++) {
						int indexOfSpace = inputTags.indexOf(" ");
						if (indexOfSpace == -1)
						{
							if (inputTags.length()!=0)
							{
								tags.add(new Tag(Integer.valueOf(inputTags.substring(0,inputTags.length()-1)),Character.valueOf(inputTags.charAt(inputTags.length()-1))) );
							}
							break;
						}
						String tag = inputTags.substring(0,indexOfSpace);
						inputTags = inputTags.substring(indexOfSpace+1);
						tags.add(new Tag(Integer.valueOf(tag.substring(0,tag.length()-1)),Character.valueOf(tag.charAt(tag.length()-1))) );
					}
					DeCompression d = new DeCompression(tags);
					d.deCompress();
					txtOutput.setText(d.toString());
			}
		});
		btnDecompress.setBounds(10, 130, 125, 21);
		frmLzTool.getContentPane().add(btnDecompress);
		
		compress = new JTextField();
		compress.setBounds(156, 85, 268, 22);
		frmLzTool.getContentPane().add(compress);
		compress.setColumns(10);
		
		decompress = new JTextField();
		decompress.setColumns(10);
		decompress.setBounds(156, 131, 268, 20);
		frmLzTool.getContentPane().add(decompress);
		
		txtOutput = new JTextField();
		txtOutput.setText("Output");
		txtOutput.setBounds(10, 11, 414, 61);
		frmLzTool.getContentPane().add(txtOutput);
		txtOutput.setColumns(10);
		
		FileName = new JTextField();
		FileName.setBounds(157, 199, 86, 20);
		frmLzTool.getContentPane().add(FileName);
		FileName.setColumns(10);
		
		btnCompressFile = new JButton("Compress File");
		btnCompressFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String fileName = FileName.getText();
				File file = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\" + fileName + ".txt"); 
				Scanner sc= new Scanner(file);
				
				File fout = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\"+ fileName + "Compressed.txt");
				if (!fout.exists()) {
					fout.createNewFile();
				}
				
				FileOutputStream fos = new FileOutputStream(fout);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				
				while (sc.hasNextLine()) {
					String word = sc.nextLine();
					Compression compressor = new Compression(word);
					ArrayList<Tag> tags = compressor.compress();
					for (int i=0 ; i<tags.size() ; i++)
					{
                        osw.write(String.valueOf(tags.get(i).getIndex()) + String.valueOf(tags.get(i).getCharachter() + "|")) ;
					}
				}
				osw.close();
				sc.close();
				JOptionPane.showMessageDialog(null, "Compressed Successfully!");
				}
				catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid File");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		);
		btnCompressFile.setBounds(10, 198, 130, 23);
		frmLzTool.getContentPane().add(btnCompressFile);
		
		btnDecompressFile = new JButton("Decompress File");
		btnDecompressFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Enter file name: ");
					String fileName = FileName.getText();
					File file = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\" + fileName + ".txt"); 
					Scanner sc = new Scanner(file); 
									
					File fout = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\"+ fileName + "Decompressed.txt");
					if (!fout.exists()) {
						fout.createNewFile();
					}
					
					FileOutputStream fos = new FileOutputStream(fout);
					OutputStreamWriter osw = new OutputStreamWriter(fos);
					
					while (sc.hasNextLine()) {
						String word = sc.nextLine();
						ArrayList<Tag> tags = new ArrayList<Tag>();
						int len = word.length();
						for (int i=0 ; i<len;i++) {
							int indexOfSpace = word.indexOf("|");
							if (indexOfSpace == -1)
							{
								if (word.length()!=0)
								{
									tags.add(new Tag(Integer.valueOf(word.substring(0,word.length()-1)),Character.valueOf(word.charAt(word.length()-1))) );
								}
								break;
							}
							String tag = word.substring(0,indexOfSpace);
							word = word.substring(indexOfSpace+1);
							tags.add(new Tag(Integer.valueOf(tag.substring(0,tag.length()-1)),Character.valueOf(tag.charAt(tag.length()-1))) );
						}
	
						DeCompression d = new DeCompression(tags);
						osw.write(d.deCompress());
					}
					osw.close();
					sc.close();
					JOptionPane.showMessageDialog(null, "Decompressed Successfully!");
				}
				catch (Exception e1) {
					String fileName =FileName.getText();
					JOptionPane.showMessageDialog(null, "Please Enter Valid File");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				

			}
		});
		btnDecompressFile.setBounds(259, 198, 165, 23);
		frmLzTool.getContentPane().add(btnDecompressFile);
		
		lblFileName = new JLabel("File Name");
		lblFileName.setBounds(156, 174, 87, 14);
		frmLzTool.getContentPane().add(lblFileName);
	}
}
