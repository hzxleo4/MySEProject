package lars.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

import javax.swing.GroupLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * 主界面类，通过选项卡可以选择编辑面板和运行面板，编辑面板用于编辑源文件，
 * 运行面板用于展示指令序列，运行时的寄存器和内存值。
 * 通过菜单进行交互。
 * @author yoolatbec
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -7655684072440354963L;
	private TopContentPane topPane;
	private File openedFile;
	private boolean fileSavedFlag = true;
	private JDialog authorListDialog = null;
	private vCPU vc = null;
	public void setvCPU(vCPU vc){
		this.vc = vc;
	}
	class TopContentPane extends JTabbedPane {
		private static final long serialVersionUID = 9216056669227766103L;
		private JTextArea textArea;
		private RunningPanel runningPanel;

		class RunningPanel extends JPanel {
			private static final long serialVersionUID = 1988605539471512365L;
			private JTable instructionTable;
			private JTable registerTable;
			private JTable memoryTable;
			private GridBagLayout layout;

			//指令表的表头
			private Object[] instructionTableHeaders = { "", "指令", "二进制码"
			};

			//寄存器表的表头
			private Object[] registerTableHeaders = { "寄存器", "值"
			};

			//寄存器表的初始值
			private Object[][] registerTableValues = { { "PC", "0"
					}, { "r0", "0"
					}, { "r1", "0"
					}, { "r2", "0"
					}, { "r3", "0"
					}, { "r4", "0"
					}, { "r5", "0"
					}, { "r6", "0"
					}, { "r7", "0"
					}, { "r8", "0"
					}, { "r9", "0"
					}, { "r10", "0"
					}, { "r11", "0"
					}, { "r12", "0"
					}, { "r13", "0"
					}, { "r14", "0"
					}, { "r15", "0"
					}, { "r16", "0"
					}, { "r17", "0"
					}, { "r18", "0"
					}, { "r19", "0"
					}, { "r20", "0"
					}, { "r21", "0"
					}, { "r22", "0"
					}, { "r23", "0"
					}, { "r24", "0"
					}, { "r25", "0"
					}, { "r26", "0"
					}, { "r27", "0"
					}, { "r28", "0"
					}, { "r29", "0"
					}, { "r30", "0"
					}, { "r31", "0"
					},
			};

			//内存表的表头
			private Object[] memoryTableHeaders = { "地址", "值"
			};

			private Object memoryTableValue = "0";

			/**
			 * 初始化所有面板
			 */
			RunningPanel() {
				layout = new GridBagLayout();
				setLayout(layout);
				initInstructionPane();
				initRegisterPane();
				initMemoryPane();
			}

			/**
			 * 将所有面板的值恢复为初始状态
			 */
			public void clear() {
				TableModel registerModel = registerTable.getModel();

				for (int i = 0; i < registerTableValues[1].length; i++) {
					registerModel.setValueAt(registerTableValues[1][i], i, 1);
				}

				TableModel memoryModel = memoryTable.getModel();
				int size = memoryModel.getRowCount();
				for (int i = 0; i < size; i++) {
					memoryModel.setValueAt(memoryTableValue, i, 1);
				}

				DefaultTableModel instructionModel = (DefaultTableModel) instructionTable.getModel();
				size = instructionModel.getRowCount();
				for (int i = 0; i < size; i++) {
					instructionModel.removeRow(0);
				}
			}

			/**
			 * 该函数用于设置指令面板中展示的指令及其对应的机器码
			 * @param size 将要展示的指令的数量
			 * @param instructions 将要展示的指令及对应的机器码的数组，第i个元素为第i条指令及对应的机器码
			 */
			public void setInstructionValues(int size, Object[][] instructions) {
				DefaultTableModel instructionModel = (DefaultTableModel) instructionTable.getModel();
				for (int i = 0; i < size; i++) {
					instructionModel.addRow(instructions[i]);
				}
			}

			/**
			 * 设置内存面板中的第i行的值
			 * @param index 行索引
			 * @param value 值
			 */
			public void setMemoryValue(int index, int value) {
				TableModel model = memoryTable.getModel();

				model.setValueAt(Integer.toString(value), index, 1);
			}

			/**
			 * 设置寄存器面板中某个寄存器的值。由于r0寄存器始终为0,故当索引为0时，将修改PC寄存器的值，
			 * 当索引不为0时，将修改rx寄存器的值，其中x为索引
			 * @param index 索引
			 * @param value 值
			 */
			public void setRegisterValue(int index, long value) {
				TableModel model = registerTable.getModel();
				if (index != 0) {
					++index;
				}

				model.setValueAt(Long.toString(value), index, 1);
			}

			/**
			 * 初始化指令面板
			 */
			private void initInstructionPane() {
				JScrollPane instructionPane = new JScrollPane();
				instructionTable = new JTable(0, instructionTableHeaders.length);
				instructionTable.setShowHorizontalLines(true);
				instructionTable.setShowVerticalLines(true);

				TableColumn column = instructionTable.getColumnModel().getColumn(0);
				column.setHeaderValue(instructionTableHeaders[0]);
				column.setMinWidth(40);
				column.setMaxWidth(40);

				column = instructionTable.getColumnModel().getColumn(1);
				column.setHeaderValue(instructionTableHeaders[1]);
				column.sizeWidthToFit();

				column = instructionTable.getColumnModel().getColumn(2);
				column.setHeaderValue(instructionTableHeaders[2]);
				column.sizeWidthToFit();

				instructionPane.setViewportView(instructionTable);

				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridwidth = 4;
				constraint.gridheight = 4;
				constraint.gridx = 0;
				constraint.gridy = 0;
				constraint.fill = GridBagConstraints.BOTH;
				constraint.weightx = 1;
				constraint.weighty = 1;

				layout.addLayoutComponent(instructionPane, constraint);
				add(instructionPane);
			}

			/**
			 * 初始化寄存器面板
			 */
			private void initRegisterPane() {
				JScrollPane registerPane = new JScrollPane();
				registerTable = new JTable(registerTableValues, registerTableHeaders);
				registerTable.setShowVerticalLines(true);
				registerTable.setShowHorizontalLines(true);

				TableColumn column = registerTable.getColumnModel().getColumn(0);
				column.setMinWidth(80);
				column.sizeWidthToFit();

				column = registerTable.getColumnModel().getColumn(1);
				column.setMinWidth(80);
				column.sizeWidthToFit();

				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridwidth = 2;
				constraint.gridheight = 2;
				constraint.gridx = 4;
				constraint.gridy = 0;
				constraint.fill = GridBagConstraints.BOTH;

				registerPane.setViewportView(registerTable);

				layout.addLayoutComponent(registerPane, constraint);
				add(registerPane);
			}

			/**
			 * 初始化内存面板
			 */
			private void initMemoryPane() {
				JScrollPane memoryPane = new JScrollPane();
				memoryTable = new JTable(0, memoryTableHeaders.length);
				memoryTable.setShowVerticalLines(true);
				memoryTable.setShowHorizontalLines(true);

				TableColumn column = memoryTable.getColumnModel().getColumn(0);
				column.setHeaderValue(memoryTableHeaders[0]);
				column.sizeWidthToFit();

				column = memoryTable.getColumnModel().getColumn(1);
				column.setHeaderValue(memoryTableHeaders[1]);
				column.sizeWidthToFit();

				DefaultTableModel model = (DefaultTableModel) memoryTable.getModel();
				for (int i = 0; i < 1024; i += 4) {
					model.addRow(new Object[] { "0X" + Integer.toString(i, 16), memoryTableValue
					});
				}

				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridwidth = 2;
				constraint.gridheight = 2;
				constraint.gridx = 4;
				constraint.gridy = 2;
				constraint.fill = GridBagConstraints.BOTH;

				memoryPane.setViewportView(memoryTable);

				layout.addLayoutComponent(memoryPane, constraint);
				add(memoryPane);
			}
		}

		/**
		 * 初始化顶级面板容器
		 */
		public TopContentPane() {
			JScrollPane textPanel = new JScrollPane();
			textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setTabSize(2);
			textArea.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					MainFrame.this.fileSavedFlag = false;
				}

				public void insertUpdate(DocumentEvent e) {
					MainFrame.this.fileSavedFlag = false;
				}

				public void changedUpdate(DocumentEvent e) {
					MainFrame.this.fileSavedFlag = false;
				}
			});
			textPanel.setViewportView(textArea);

			runningPanel = new RunningPanel();

			add("编辑", textPanel);
			add("运行", runningPanel);
		}

		/**
		 * 替换编辑面板的内容
		 * @param content 替换内容
		 */
		public void setText(String content) {
			textArea.setText(content);
		}

		/**
		 * 获得当前编辑面板的内容
		 * @return 当前编辑面板的内容
		 */
		public String getText() {
			return textArea.getText();
		}

		public void clear(){
			runningPanel.clear();
		}

		public void setMemoryValue(int index, int value){
			runningPanel.setMemoryValue(index, value);
		}

		public void setRegisterValue(int index, long value){
			runningPanel.setMemoryValue(index, value);
		}

		public void setInstructionValues(int size, Object[][] instructions){
			runningPanel.setInstructionValues(size, instructions);
		}
	}

	/**
	 * 初始化主页面
	 */
	public MainFrame() {
		addMenu();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		topPane = new TopContentPane();
		setContentPane(topPane);
		setTitle("LARS");

		initAuthorListDialog();

		pack();
		setResizable(false);

		// setVisible(true);
	}

	/**
	 * 初始化一个窗口用于展示制作者名单
	 */
	private void initAuthorListDialog() {
		authorListDialog = new JDialog();
		authorListDialog.setTitle("制作者名单");
		authorListDialog.setSize(300, 200);
		authorListDialog.setResizable(false);
		authorListDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

		JPanel panel = new JPanel();
		authorListDialog.add(panel);
		panel.setLayout(new GridLayout(3, 1, 5, 5));

		panel.add(new JLabel("UI模块：梁恒中"));
		panel.add(new JLabel("VCPU模块："));
		panel.add(new JLabel("编译器模块："));
	}

	/**
	 * 为主界面添加菜单
	 */
	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("文件");
		JMenu run = new JMenu("运行");
		JMenu about = new JMenu("关于");

		menuBar.add(file);
		menuBar.add(run);
		menuBar.add(about);

		JMenuItem fileOpen = new JMenuItem("打开");
		JMenuItem fileClose = new JMenuItem("关闭");
		JMenuItem fileSave = new JMenuItem("保存");
		JMenuItem fileSaveOther = new JMenuItem("另存为");
		JMenuItem fileExit = new JMenuItem("退出");

		file.add(fileOpen);
		file.add(fileClose);
		file.add(fileSave);
		file.add(fileSaveOther);
		file.addSeparator();
		file.add(fileExit);

		fileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.openFile();
			}
		});

		fileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.saveFile();
			}
		});

		fileClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.closeFile();
			}
		});

		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.exit();
			}
		});

		JMenuItem runCompile = new JMenuItem("编译");
		JMenuItem runStep = new JMenuItem("单步运行");
		JMenuItem runAll = new JMenuItem("全部运行");
		JMenuItem runReset = new JMenuItem("重置");
		run.add(runCompile);
		run.add(runStep);
		run.add(runAll);
		run.add(runReset);

		runCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.compile();
			}
		});

		runStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.runStep();
			}
		});

		runStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.runAll();
			}
		});

		runReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.reset();
			}
		});

		JMenuItem aboutAuthorList = new JMenuItem("制作者名单");
		about.add(aboutAuthorList);

		aboutAuthorList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.showAuthorList();
			}
		});

		setJMenuBar(menuBar);
	}

	/**
	 * 当选中关闭文件菜单时将调用这个函数
	 */
	private void closeFile() {
		if (!fileSavedFlag) {
			saveFile();
		}

		setText("");
		openedFile = null;
		fileSavedFlag = true;
	}

	/**
	 * 当选中打开文件菜单时将调用这个函数
	 */
	private void openFile() {
		if (openedFile != null) {
			closeFile();
		}

		openedFile = null;

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			openedFile = chooser.getSelectedFile();
		}

		if (openedFile != null) {
			StringBuilder content = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(openedFile))) {
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					content.append(line).append('\n');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			setText(content.toString());
			fileSavedFlag = true;
			setTitle(openedFile.getPath());
			topPane.setSelectedIndex(0);
		}
	}

	/**
	 * 当选中保存文件菜单时将调用这个函数
	 */
	private void saveFile() {
		if (openedFile == null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				openedFile = chooser.getSelectedFile();
			}
		}

		if (openedFile != null) {
			String text = topPane.getText();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(openedFile))) {
				writer.write(text);
			} catch (IOException e) {

			}

			fileSavedFlag = true;
		}
	}

	/**
	 * 当用户选中退出菜单且用户的修改尚未保存时弹出一个窗口提示用户保存
	 */
	private void unsavedFileWarning() {
		JDialog dialog = new JDialog(this);
		dialog.setTitle("未保存的文件");
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setSize(300, 200);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JLabel warning = new JLabel("还有文件尚未保存，确定要退出吗？");
		JButton negate = new JButton("保存后退出");
		JButton cancel = new JButton("取消");
		JButton affirm = new JButton("退出且不保存");

		JPanel panel = new JPanel();
		dialog.add(panel);
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup hSeqGroup1 = layout.createSequentialGroup().addComponent(negate)
				.addComponent(cancel).addComponent(affirm);
		GroupLayout.SequentialGroup hSeqGroup2 = layout.createSequentialGroup().addComponent(warning);

		GroupLayout.ParallelGroup hParGroup = layout.createParallelGroup().addGroup(hSeqGroup2).addGroup(hSeqGroup1);
		layout.setHorizontalGroup(hParGroup);

		GroupLayout.ParallelGroup vParGroup2 = layout.createParallelGroup().addComponent(warning);
		GroupLayout.ParallelGroup vParGroup1 = layout.createParallelGroup().addComponent(negate).addComponent(cancel)
				.addComponent(affirm);

		GroupLayout.SequentialGroup vSeqGroup = layout.createSequentialGroup().addGroup(vParGroup2)
				.addGroup(vParGroup1);
		layout.setVerticalGroup(vSeqGroup);

		negate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.closeFile();
				System.exit(0);
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		affirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		dialog.setVisible(true);
	}

	/**
	 * 当用户的程序编译失败时弹出一个窗口提示用户编译失败
	 */
	private void compileFailedWarning() {
		JDialog dialog = new JDialog(this);
		dialog.setResizable(false);
		dialog.setTitle("编译失败");
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setSize(300, 200);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		dialog.add(panel);

		JLabel label = new JLabel("编译失败，请检查输入");
		panel.add(label, BorderLayout.CENTER);

		dialog.setVisible(true);
	}

	/**
	 * 当选中退出菜单时调用这个函数
	 */
	private void exit() {
		if (!fileSavedFlag) {
			unsavedFileWarning();
		} else {
			System.exit(0);
		}
	}

	/**
	 * 当选中编译菜单时调用这个函数
	 */
	private void compile() {
		String text = topPane.getText();
		
	}

	/**
	 * 当选中运行菜单时调用这个函数
	 */
	private void runStep() {
		vc.runStep();
	}

	/**
	 * 当选中全部运行菜单时调用这个函数
	 */
	private void runAll() {
		vc.runAll();
	}

	/**
	 * 当选中重置菜单时调用这个函数
	 */
	private void reset() {
		vc.reset();
	}

	/**
	 * 当选中制作者名单菜单时调用这个函数
	 */
	private void showAuthorList() {
		authorListDialog.setVisible(true);
	}
	/**
	 * 替换编辑面板的内容
	 * @param text 将要替换的内容
	 */
	private void setText(String text) {
		topPane.setText(text);
	}

	public void clear(){
		topPane.clear();
	}

	public void setMemoryValue(int index, int value){
		topPane.setMemoryValue(index, value);
	}

	public void setRegisterValue(int index, long value){
		topPane.setRegisterValue(index, value);
	}

	public void setInstructionValues(int size, Object[][] instructions){
		topPane.setInstructionValues(size, instructions);
	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		vCPU vcpu = new vCPU(?,mf);



		mf.setVisible(true);
	}
}
