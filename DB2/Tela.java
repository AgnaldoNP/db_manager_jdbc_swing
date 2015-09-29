package DB2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class Tela extends JFrame {

	private final JPanel CP;
	private final JPanel esqueda;
	private final JPanel splittop;
	private final JPanel splitbottom;
	private final JPanel paneloptxtcomando;
	private final JScrollPane panetxtcomando;
	private final JSplitPane splitcentro;
	private final JTextArea txtcomando;
	private final MatteBorder borda = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
	private final JPanel paneloptable;
	private final JScrollPane scrollDBGrid;
	private final JTable DBGgrid;
	private final JpanelExpansive baixo;
	private final JpanelExpansive direita;
	private final JMenuBar barrademenu;
	private final JMenu mnArquivo;
	private final JLabel excelDBGrid;
	private final JLabel reloadArvoreBanco;
	private final JLabel executeComando;
	private final JLabel stopComando;
	private final JLabel pdfDBGrid;
	private final JLabel imprimirComando;
	private final JLabel imprimirDBGrid;
	private final JTree ArvoreBanco;
	private final JPanel panel;
	private final JPanel cimacentro;
	private final JPanel cimadir;
	private final JLabel lblSair;
	private final JLabel salvarComandosSql;
	private final JLabel salvarComandos;
	private final JPanel cima;
	private final JButton btnSelect;
	private final JButton btnSelectALL;
	private final JButton btnInsert;
	private final JButton btnUpdate;
	private final JLabel lblConectar;
	private final JLabel lblDesconectar;
	private final JLabel lblNovaconxao;
	private final JLabel csvDBGrid;
	private final JSplitPane centro;
	private int cursor;
	private final JComboBox<String> comboBancos;
	private ConnectDB con;
	private final JScrollPane scrollPane;
	private final JTextArea txtRetorno;
	@SuppressWarnings("rawtypes")
	private SwingWorker worker;
	private final JButton btnDelete;
	private final JLabel lblImportSql;


	public Tela() {
		addWindowListener(new WindowAdapter() {	@Override
		public void windowOpened(final WindowEvent arg0) {
			verificardir();
			listarconexoes();
		}});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/DB2/img/icone.png")));
		setTitle("Gerenciador de Banco de Dados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 508);

		barrademenu = new JMenuBar();
		setJMenuBar(barrademenu);

		mnArquivo = new JMenu("Arquivo");
		mnArquivo.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/arquivo.gif")));
		barrademenu.add(mnArquivo);

		final JMenu mnSobre = new JMenu("Sobre");
		mnSobre.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/sobre.png")));
		barrademenu.add(mnSobre);
		CP = new JPanel();
		CP.setBorder(new EmptyBorder(3, 3, 3, 3));
		setContentPane(CP);
		CP.setLayout(new BorderLayout(2, 2));



		cima = new JPanel();
		cima.setPreferredSize(new Dimension(10, 28));
		cima.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CP.add(cima, BorderLayout.NORTH);
		cima.setLayout(new BorderLayout(0, 0));

		cimacentro = new JPanel();
		final FlowLayout flowLayout_1 = (FlowLayout) cimacentro.getLayout();
		flowLayout_1.setHgap(15);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		flowLayout_1.setVgap(2);
		cima.add(cimacentro, BorderLayout.CENTER);

		lblConectar = new JLabel("");
		lblConectar.setToolTipText("Conectar com BD Selecionado");
		lblConectar.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			conectar();
		}});
		lblConectar.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/connect.png")));
		cimacentro.add(lblConectar);

		lblDesconectar = new JLabel("");
		lblDesconectar.setToolTipText("Desconectar-se");
		lblDesconectar.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			desconectar();
		}});
		lblDesconectar.setEnabled(false);
		lblDesconectar.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/disconnect.png")));
		cimacentro.add(lblDesconectar);

		lblNovaconxao = new JLabel("");
		lblNovaconxao.setToolTipText("Nova Conex\u00E3o");
		lblNovaconxao.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			novaConexao();
		}});
		lblNovaconxao.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/novaconexao.png")));
		cimacentro.add(lblNovaconxao);

		cimadir = new JPanel();
		final FlowLayout flowLayout = (FlowLayout) cimadir.getLayout();
		flowLayout.setVgap(2);
		cimadir.setPreferredSize(new Dimension(40, 10));
		cima.add(cimadir, BorderLayout.EAST);

		lblSair = new JLabel("");
		lblSair.setToolTipText("Fechar Programas");
		lblSair.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {System.exit(EXIT_ON_CLOSE);}});
		lblSair.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/sair.png")));
		cimadir.add(lblSair);

		centro = new JSplitPane();
		centro.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CP.add(centro, BorderLayout.CENTER);

		esqueda = new JPanel();
		esqueda.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		esqueda.setPreferredSize(new Dimension(150, 10));
		esqueda.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centro.setLeftComponent(esqueda);
		esqueda.setLayout(new BorderLayout(0, 0));

		ArvoreBanco = new JTree();
		ArvoreBanco.setToggleClickCount(3);
		ArvoreBanco.setShowsRootHandles(true);
		ArvoreBanco.setModel(new DefaultTreeModel(	new DefaultMutableTreeNode("N\u00E3o Conectado") ));
		ArvoreBanco.setBorder(borda);
		ArvoreBanco.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent e) {
			cliqueArvore(e);
		}});
		esqueda.add(ArvoreBanco, BorderLayout.CENTER);

		panel = new JPanel();
		panel.setBorder(borda);
		panel.setPreferredSize(new Dimension(10, 25));
		esqueda.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		reloadArvoreBanco = new JLabel("");
		reloadArvoreBanco.setToolTipText("Recarregar Arvore");
		reloadArvoreBanco.setHorizontalAlignment(SwingConstants.CENTER);
		reloadArvoreBanco.setPreferredSize(new Dimension(25, 0));
		reloadArvoreBanco.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/reload.png")));
		panel.add(reloadArvoreBanco, BorderLayout.LINE_START);

		comboBancos = new JComboBox<String>();
		comboBancos.setToolTipText("Escolha um Banco para se Conectar");
		comboBancos.setPreferredSize(new Dimension(50, 20));
		comboBancos.setMinimumSize(new Dimension(28, 18));
		comboBancos.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione"}));
		panel.add(comboBancos, BorderLayout.CENTER);

		splitcentro = new JSplitPane();
		splitcentro.setOrientation(JSplitPane.VERTICAL_SPLIT);
		centro.setRightComponent(splitcentro);

		splittop = new JPanel();
		splittop.setAlignmentY(Component.TOP_ALIGNMENT);
		splittop.setAlignmentX(Component.LEFT_ALIGNMENT);
		splitcentro.setLeftComponent(splittop);
		splittop.setLayout(new BorderLayout(0, 0));

		txtcomando = new JTextArea();
		txtcomando.addFocusListener(new FocusAdapter() {@Override
		public void focusLost(final FocusEvent arg0) { getcursor(); }});
		txtcomando.addKeyListener(new KeyAdapter() {@Override
		public void keyPressed(final KeyEvent evt) {
			if((evt.isControlDown() && evt.getKeyCode()==KeyEvent.VK_ENTER) || (evt.getKeyCode()==KeyEvent.VK_F5)) {
				executar();
			}
		}});
		txtcomando.setBorder(borda);

		panetxtcomando = new JScrollPane(txtcomando);
		panetxtcomando.setBorder(null);
		splittop.add(panetxtcomando, BorderLayout.CENTER);

		paneloptxtcomando = new JPanel();
		paneloptxtcomando.setBorder(borda);
		paneloptxtcomando.setPreferredSize(new Dimension(10, 25));
		splittop.add(paneloptxtcomando, BorderLayout.NORTH);
		paneloptxtcomando.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));

		executeComando = new JLabel("");
		executeComando.setToolTipText("Executar Comando Selecionado (CTRL+Enter)");
		executeComando.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			executar();
		}});
		executeComando.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/execute.png")));
		paneloptxtcomando.add(executeComando);

		stopComando = new JLabel("");
		stopComando.setEnabled(false);
		stopComando.setToolTipText("Parar Consulta");
		stopComando.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/stop.png")));
		paneloptxtcomando.add(stopComando);

		salvarComandos = new JLabel("");
		salvarComandos.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent e) {
			salvarComandos();
		}});
		salvarComandos.setToolTipText("Salvar comandos para txt");
		salvarComandos.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/salvar.png")));
		paneloptxtcomando.add(salvarComandos);

		salvarComandosSql = new JLabel("");
		salvarComandosSql.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent e) {
				salvarComandosSQL();
		}});
		salvarComandosSql.setToolTipText("Salvar comandos para sql");
		salvarComandosSql.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/savesql.png")));
		paneloptxtcomando.add(salvarComandosSql);

		lblImportSql = new JLabel("");
		lblImportSql.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			imporatSql();
		}});
		lblImportSql.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/importsql.png")));
		paneloptxtcomando.add(lblImportSql);

		imprimirComando = new JLabel("");
		imprimirComando.setToolTipText("Imprimir Comandos");
		imprimirComando.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/print.png")));
		paneloptxtcomando.add(imprimirComando);

		splitbottom = new JPanel();
		splitbottom.setBorder(borda);
		splitcentro.setRightComponent(splitbottom);
		splitbottom.setLayout(new BorderLayout(0, 0));

		paneloptable = new JPanel();
		paneloptable.setPreferredSize(new Dimension(10, 25));
		paneloptable.setBorder(borda);
		splitbottom.add(paneloptable, BorderLayout.NORTH);
		paneloptable.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));

		excelDBGrid = new JLabel("");
		excelDBGrid.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			salvarXls();
		}});
		excelDBGrid.setToolTipText("Salvar para Excel");
		excelDBGrid.setHorizontalAlignment(SwingConstants.LEFT);
		excelDBGrid.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/excel.gif")));
		paneloptable.add(excelDBGrid);

		pdfDBGrid = new JLabel("");
		pdfDBGrid.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			salvarPdf();
		}});
		pdfDBGrid.setToolTipText("Salvar para PDF");
		pdfDBGrid.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/pdf.png")));
		paneloptable.add(pdfDBGrid);

		csvDBGrid = new JLabel("");
		csvDBGrid.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			salvarCsv();
		}});
		csvDBGrid.setToolTipText("Salvar para CSV");
		csvDBGrid.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/csv.png")));
		paneloptable.add(csvDBGrid);

		imprimirDBGrid = new JLabel("");
		imprimirDBGrid.addMouseListener(new MouseAdapter() {@Override
		public void mouseClicked(final MouseEvent arg0) {
			imprimirDBGrid();
		}});
		imprimirDBGrid.setToolTipText("Imprimir Tabela");
		imprimirDBGrid.setIcon(new ImageIcon(Tela.class.getResource("/DB2/img/print.png")));
		paneloptable.add(imprimirDBGrid);

		DBGgrid = new JTable();
		DBGgrid.setAutoscrolls(true);
		DBGgrid.setModel(new DefaultTableModel(new Object[][] {{null, null, null, null},},new String[] {" ", " ", " ", " "	}));
		DBGgrid.getColumnModel().getColumn(0).setPreferredWidth(79);
		DBGgrid.setAutoCreateRowSorter(true);

		scrollDBGrid = new JScrollPane(DBGgrid);
		splitbottom.add(scrollDBGrid, BorderLayout.CENTER);
		splitcentro.setDividerLocation(150);

		direita = new JpanelExpansive();
		direita.setPreferredSize(new Dimension(100, 30));
		direita.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CP.add(direita, BorderLayout.EAST);
		direita.setLayout(null);

		btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {@Override
		public void actionPerformed(final ActionEvent arg0) {
			inserirSelect();
		}});
		btnSelect.setBounds(13, 11, 80, 23);
		direita.add(btnSelect);

		btnSelectALL = new JButton("Select *");
		btnSelectALL.addActionListener(new ActionListener() {@Override
		public void actionPerformed(final ActionEvent e) {
			inserirSelectAll();
		}});
		btnSelectALL.setBounds(13, 35, 80, 23);
		direita.add(btnSelectALL);

		btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {@Override
		public void actionPerformed(final ActionEvent e) {
			inserirInsert();
		}});
		btnInsert.setBounds(13, 59, 80, 23);
		direita.add(btnInsert);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {@Override
		public void actionPerformed(final ActionEvent e) {
			inserirUpdate();
		}});
		btnUpdate.setBounds(13, 83, 80, 23);
		direita.add(btnUpdate);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {@Override
		public void actionPerformed(final ActionEvent e) {
			InserirDelete();
		}});
		btnDelete.setBounds(13, 107, 80, 23);
		direita.add(btnDelete);

		baixo = new JpanelExpansive();
		baixo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		baixo.setPreferredSize(new Dimension(20, 90));
		CP.add(baixo, BorderLayout.SOUTH);
		baixo.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		txtRetorno = new JTextArea();

		scrollPane = new JScrollPane(txtRetorno);
		baixo.add(scrollPane, "4, 1, 1, 2, fill, fill");


	}

	@SuppressWarnings("rawtypes")
	private void executar(){
		if (con!=null && con.isConectado()){
			worker = new SwingWorker(){
		        @Override
				protected Object doInBackground() throws Exception {
	     	   		executarcomando();
	     	   		stopComando.setEnabled(true);
	                return null;
		        }
		        @Override
				protected void done() {
		        	stopComando.setEnabled(false);
				}
			};
			worker.execute();
			worker=null;
		} else {
			txtRetorno.append("Não Conectado\r\n");
		}
	}

	private void executarcomando(){
		final String comando=txtcomando.getSelectedText();
		final String[] cmds = comando.split(";");
		for (final String cmd : cmds) {
			//cmds[i]= cmds[i].replace("\r\n", " ").trim();
			txtRetorno.append("Comando executado: "+cmd+"\r\n");
			if((cmd.toLowerCase().trim().startsWith("select"))){
				final ResultSet rs = con.Select(cmd);
				carregarDBGrid(rs);
			}else
				if( (cmd.toLowerCase().trim().startsWith("insert")) ||
						(cmd.toLowerCase().trim().startsWith("update") ) ||
							(cmd.toLowerCase().trim().startsWith("delete") )){
					txtRetorno.append("\r\n"+con.Update(cmd)+"\r\n");
				}else{
					con.ExecutarSql(cmd);
					txtRetorno.append("\r\n"+con.getStatusSql());
					con.getArvoreSimples(ArvoreBanco);
				}
		}
	}

	public void carregarDBGrid(final ResultSet rs){
		if (con.getNumRegs(rs)>0  && rs!=null){
			DBGgrid.setModel(con.getTabelModelDB(rs));
		}else{
			DBGgrid.setModel(new DefaultTableModel(new Object[][] {{null, null, null, null}},new String[] {" ", " ", " ", " "	}));
			if(rs==(null)) {
				txtRetorno.append(con.getErroSelect()+"\r\n");
			}
		}
	}

	public void parar(){
		worker.cancel(true);
	}

	private void getcursor(){
		cursor = txtcomando.getSelectionStart();
	}

	public void salvarPdf(){
		try{
			final Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
			final TableModel modelGrid = DBGgrid.getModel();
			//Document doc = new Document(PageSize.A4, 72, 72, 72, 72);//documento com margens

			final JFileChooser FC = new JFileChooser();
			FC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Documento PDF", "PDF");
			FC.addChoosableFileFilter(extensao);
			if(FC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				OutputStream os;
				if (FC.getSelectedFile().getAbsolutePath().endsWith(".pdf")) {
					os = new FileOutputStream(FC.getSelectedFile().getAbsolutePath());
				} else {
					os = new FileOutputStream(FC.getSelectedFile().getAbsolutePath()+".pdf");
				}

				PdfWriter.getInstance(doc, os);
				doc.open();

				final java.awt.FontMetrics font = getGraphics().getFontMetrics();
				final float[] widthcelulas = new float[modelGrid.getColumnCount()];
				for(int i=0;i<widthcelulas.length;i++) {
					widthcelulas[i] = font.stringWidth(modelGrid.getColumnName(i).toString());
				}

				final PdfPTable grid = new PdfPTable(widthcelulas);
				grid.setWidthPercentage(95f);
				final PdfPCell header = new PdfPCell(new Paragraph("Relatório", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));

				header.setColspan(modelGrid.getColumnCount());
				grid.addCell(header);

				for(int i=0;i<modelGrid.getColumnCount();i++) {
					grid.addCell(new  PdfPCell(new Paragraph(modelGrid.getColumnName(i), new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD))));
				}

				for (int i = 0; i < modelGrid.getRowCount(); i++) {
					for (int j = 0; j < modelGrid.getColumnCount(); j++) {
						if (modelGrid.getValueAt(i, j)!=null) {
							grid.addCell(new  PdfPCell(new Paragraph(modelGrid.getValueAt(i, j).toString(), new Font(FontFamily.HELVETICA, 7, Font.NORMAL))));
						} else {
							grid.addCell(" ");
						}
					}
				}

				doc.add(grid);
				doc.close();
			}

		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
		}
	}

	public void salvarXls(){
		try{
			final JFileChooser FC = new JFileChooser();
			FC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Planília Excel", "xls");
			FC.addChoosableFileFilter(extensao);
			if(FC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				File arquivo;
				if (FC.getSelectedFile().getAbsolutePath().endsWith(".xls")) {
					arquivo = new File(FC.getSelectedFile().getAbsolutePath());
				} else {
					arquivo = new File(FC.getSelectedFile().getAbsolutePath()+".xls");
				}

				final WritableWorkbook workbook = Workbook.createWorkbook(arquivo);
				final WritableSheet sheet = workbook.createSheet("Planília 1", 0);
				final TableModel modelGrid = DBGgrid.getModel();

				final WritableFont titulo = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
				final WritableCellFormat tituloFormat = new WritableCellFormat (titulo);
				tituloFormat.setWrap(true);

				final java.awt.FontMetrics font = getGraphics().getFontMetrics();
				for (int i=0; i<modelGrid.getColumnCount();i++){
					sheet.addCell(new jxl.write.Label(i, 0,modelGrid.getColumnName(i), tituloFormat));
					sheet.setColumnView(i, font.stringWidth(modelGrid.getColumnName(i).toString())/4);
				}

				final WritableFont conteudo = new WritableFont(WritableFont.ARIAL, 10);
				final WritableCellFormat conteudoFormat = new WritableCellFormat (conteudo);

				 for (int i = 0; i < modelGrid.getRowCount(); i++) {
					for (int j = 0; j < modelGrid.getColumnCount(); j++) {
						if (modelGrid.getValueAt(i, j)!=null) {
							sheet.addCell(new jxl.write.Label(j, i + 1, modelGrid.getValueAt(i, j).toString(), conteudoFormat));
						}
					}
				}


				 workbook.write();
		         workbook.close();
			}
		} catch (final RowsExceededException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Exportar tabela para Excel1: \n"+e.getMessage());
		} catch (final WriteException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Exportar tabela para Excel2: \n"+e.getMessage());
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Exportar tabela para Excel3: \n"+e.getMessage());
		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao Exportar tabela para Excel4: \n"+e.getMessage());
		}

	}

	public void salvarCsv(){
		try{
			String csv = "";
			final TableModel modelGrid = DBGgrid.getModel();
			final JFileChooser FC = new JFileChooser();
			FC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Arquivos CSV", "CSV");
			FC.addChoosableFileFilter(extensao);
			if(FC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				BufferedWriter bw;

				if (FC.getSelectedFile().getAbsolutePath().endsWith(".csv")) {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile())); //nome e  extensão do arquivo q vc vai escrever
				} else {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile().getAbsolutePath()+".csv"));
				}

				for (int i=0; i<modelGrid.getColumnCount();i++) {
					csv+=modelGrid.getColumnName(i)+";";
				}
				csv+="\r\n";
				for (int i = 0; i < modelGrid.getRowCount(); i++){
		             for (int j = 0; j < modelGrid.getColumnCount(); j++) {
						if (modelGrid.getValueAt(i, j)!=null) {
							csv+= modelGrid.getValueAt(i, j).toString()+";";
						} else {
							csv+=";";
						}
					}
		             csv+="\r\n";
				 }

		        bw.write(csv); // coloca aki o nome do seu text area, aí jah era!!
	    	    bw.close(); // não eskeça de fechar o arquivo

			}
		}catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Erro1: "+e.getMessage());
		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro2: "+e.getMessage());
		}
	}

	public void salvarComandos(){
		try{
			String comandos="";
			final JFileChooser FC = new JFileChooser();
			FC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Arquivos TXT", "TXT");
			FC.addChoosableFileFilter(extensao);
			if(FC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				BufferedWriter bw;
				if (FC.getSelectedFile().getAbsolutePath().endsWith(".txt")) {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile())); //nome e  extensão do arquivo q vc vai escrever
				} else {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile().getAbsolutePath()+".txt"));
				}

				comandos=txtcomando.getText();

		        bw.write(comandos);
	    	    bw.close();
			}
		}catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Erro1: "+e.getMessage());
		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro2: "+e.getMessage());
		}
	}

	public void imporatSql(){
		try{
			final JFileChooser JFC = new JFileChooser();
			JFC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Arquivos SQL", "SQL");
			JFC.addChoosableFileFilter(extensao);
			if(JFC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				final File sqlfile = JFC.getSelectedFile();
				final BufferedReader buffer = new BufferedReader(new FileReader(sqlfile.getAbsolutePath()));
				String sql="";txtcomando.append("\r\n");
				while ((sql = buffer.readLine()) != null) {
					txtcomando.append(sql+"\r\n");
				}
				txtcomando.append("\r\n");
				buffer.close();
			}

		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao Importar arquivo SQL: \n"+e.getMessage());
		}
	}

	public void salvarComandosSQL(){
		try{
			String comandos="";
			final JFileChooser FC = new JFileChooser();
			FC.setMultiSelectionEnabled(false);
			final FileFilter extensao = new FileNameExtensionFilter ("Arquivos SQL", "SQL");
			FC.addChoosableFileFilter(extensao);
			if(FC.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				BufferedWriter bw;
				if (FC.getSelectedFile().getAbsolutePath().endsWith(".sql")) {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile())); //nome e  extensão do arquivo q vc vai escrever
				} else {
					bw = new BufferedWriter(new FileWriter(FC.getSelectedFile().getAbsolutePath()+".sql"));
				}

				comandos=txtcomando.getText();

		        bw.write(comandos);
	    	    bw.close();
			}
		}catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Erro1: "+e.getMessage());
		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro2: "+e.getMessage());
		}
	}

	public void imprimirComandos(){

	}

	public void imprimirDBGrid(){
		try {
		    DBGgrid.print(JTable.PrintMode.FIT_WIDTH, // FIT_WIDTH todas as colunas em uma page
		      new MessageFormat(""),
		       new MessageFormat(""));
		} catch (final PrinterException e) {
		    JOptionPane.showMessageDialog(this, "Falha na impressão: "+ e.getMessage());
		}

	}

	public void novaConexao(){
		new Conf().setVisible(true);
		listarconexoes();
	}

	public boolean verificardir(){
		File dir;
		try{
			dir =  new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB");
			if (!dir.exists()) {
				dir.mkdir();
			}
			return true;
		}catch (final Exception e) {
			return false;
		}
	}

	public void listarconexoes(){
		File dir;
		File[] conecxoes;
		try{
			dir = new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB");
			conecxoes = dir.listFiles();
			for (final File conecxoe : conecxoes) {
				comboBancos.addItem(conecxoe.getName().substring(0, conecxoe.getName().indexOf(".")));
			}
		}
		catch (final Exception e) {

		}
	}

	public void conectar(){
		if (!comboBancos.getSelectedItem().equals("Selecione")) {
			;
		}
			if (new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+comboBancos.getSelectedItem().toString()+".properties").exists()) {
				try{
					final Properties p =new Properties();
					final FileInputStream fis = new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+comboBancos.getSelectedItem().toString()+".properties");
				    p.load(fis);
					if(p.getProperty("Porta").equals("")) {
						con= new ConnectDB(Integer.parseInt(p.getProperty("TipoBanco")), p.getProperty("Host"), p.getProperty("Banco"), p.getProperty("Usuario"), p.getProperty("Senha"));
					} else {
						con= new ConnectDB(Integer.parseInt(p.getProperty("TipoBanco")), p.getProperty("Host"), p.getProperty("Porta"), p.getProperty("Banco"), p.getProperty("Usuario"), p.getProperty("Senha"));
					}
				    fis.close();

				    if(con.isConectado()){
				    	carregararvore();
				    	lblDesconectar.setEnabled(true);
				    	lblConectar.setEnabled(false);
				    	comboBancos.setEnabled(false);
				    }else{
				    	txtRetorno.append("Erro na Conexão com o Banco de Dados: " + con.getErroConexao()+"\r\n");
				    	novaConexao();
				    }

				}catch (final IOException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao reucperar informações do banco \n"+ ex.getMessage());
		        }catch (final Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao recuperar e mostrar informações do banco \n"+ e.getMessage());
				}
			}
	}

	public void desconectar(){
		try{
			con.Desconectar();
			lblDesconectar.setEnabled(false);
			lblConectar.setEnabled(true);
			comboBancos.setEnabled(true);
			ArvoreBanco.setModel(new DefaultTreeModel(	new DefaultMutableTreeNode("N\u00E3o Conectado") ));
		}catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao desconectar do Banco!!!\n"+e.getMessage());
		}
	}

	public void carregararvore(){
		con.getArvoreSimples(ArvoreBanco);
	}

	public void inserirSelect(){
		txtcomando.append("\r\nSELECT ");
		String campos="";
		for(int i = 0; i<ArvoreBanco.getSelectionPaths().length;i++) {
			if (ArvoreBanco.getSelectionPaths()[i].getPathCount()==3) {
				campos+=(ArvoreBanco.getSelectionPaths()[i].getLastPathComponent()+", ");
			}
		}
		txtcomando.append(campos.substring(0, campos.length()-2)+" FROM "+ArvoreBanco.getSelectionPaths()[0].getPathComponent(1)+"\r\nWHERE <condição>");
	}

	public void inserirSelectAll(){
		txtcomando.append("\r\nSELECT * FROM "+ArvoreBanco.getSelectionPath().getPathComponent(1)+"\r\nWHERE <condição>");
	}

	public void inserirInsert(){
		final String tab = ArvoreBanco.getSelectionPath().getPathComponent(1).toString();
		final String[] campos = con.getCampos(tab);
		String campos2="", nulos="";
		for (final String campo : campos) {
			campos2+=campo+", ";
			nulos+="\"\", ";
		}
		campos2=campos2.substring(0,campos2.length()-2);
		nulos= nulos.substring(0, nulos.length()-2);
		txtcomando.append("\r\nINSERT INTO "+tab+" ("+campos2+") \r\nVALUES ("+nulos+")");
	}

	public void inserirUpdate(){
		final String tab = ArvoreBanco.getSelectionPath().getPathComponent(1).toString();
		txtcomando.append("\r\nUPDATE "+tab+" \r\nSET <campo 1>=<novo valor>, <campo 2>=<novovalor 2> \r\nWHERE <condição>");
	}

	public void InserirDelete(){
		final String tab = ArvoreBanco.getSelectionPath().getPathComponent(1).toString();
		txtcomando.append("\r\nDELETE FROM "+tab+"\r\nWHERE <condição>");
	}

	public void cliqueArvore(final MouseEvent e){
		if (e.getClickCount()==2){
			if (ArvoreBanco.getSelectionPath().getPathCount()==2){
				final String ant = txtcomando.getText().substring(0,cursor);
				final String prox = txtcomando.getText().substring(cursor, txtcomando.getText().length());
				txtcomando.setText(ant+ArvoreBanco.getSelectionPath().getLastPathComponent()+prox);
				txtcomando.requestFocus();
			}else{
				if (ArvoreBanco.getSelectionPath().getPathCount()==3){
					final String ant = txtcomando.getText().substring(0,cursor);
					final String prox = txtcomando.getText().substring(cursor, txtcomando.getText().length());
					txtcomando.setText(ant+ArvoreBanco.getSelectionPath().getLastPathComponent()+prox);
					txtcomando.requestFocus();
				}
			}

		}
	}
}//fim da classe
