package DB2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;


@SuppressWarnings("serial")
public class Conf extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JPasswordField pwdSenha;
	private JTextField txtServidor;
	private JTextField txtPorta;
	private JTextField txtBanco;
	private JPanel panelesquerdo;
	private JPanel panelNovoEditar;
	private JLabel lblNovo;
	private JLabel lblEditar;
	private JList Lista;
	private JLabel lblExcluir;
	private JPanel panelCentro;
	private JPanel panelBancos;
	private JRadioButton radioMysql;
	private JRadioButton radioOracle;
	private JRadioButton radioPostgresql;
	private JPanel panelInfs;
	private JLabel lblUsuario;
	private JLabel lblSenha;
	private JLabel lblServidor;
	private JLabel lblPorta;
	private JLabel lblBanco;
	private JTextField txtUsuario;
	private JButton btnSalvar;
	private ButtonGroup g;
	private JPanel panel;
	private JLabel lblManutenoDeArquivos;

	public Conf() {
		addWindowListener(new WindowAdapter() {public void windowOpened(WindowEvent arg0) {
			listarconexoes();
		}});
		setTitle("Configura\u00E7\u00F5es de Banco");
		setModal(true);
		setBounds(100, 100, 450, 301);
		getContentPane().setLayout(new BorderLayout(2, 2));
		
		panelesquerdo = new JPanel();
		panelesquerdo.setPreferredSize(new Dimension(120, 10));
		panelesquerdo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelesquerdo, BorderLayout.LINE_START);
		panelesquerdo.setLayout(new BorderLayout(0, 0));
		
		panelNovoEditar = new JPanel();
		panelNovoEditar.setPreferredSize(new Dimension(10, 25));
		panelesquerdo.add(panelNovoEditar, BorderLayout.NORTH);
		panelNovoEditar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblNovo = new JLabel("Novo");
		lblNovo.setIcon(new ImageIcon(Conf.class.getResource("/DB2/img/adddatabase.png")));
		lblNovo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNovo.addMouseListener(new MouseAdapter() {	public void mouseClicked(MouseEvent arg0) {
			novaconexao();
		}});
		panelNovoEditar.add(lblNovo);
		
		lblEditar = new JLabel("Editar");
		lblEditar.addMouseListener(new MouseAdapter() {	public void mouseClicked(MouseEvent arg0) {
			editar();
		}});
		lblEditar.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblEditar.setIcon(new ImageIcon(Conf.class.getResource("/DB2/img/edit.png")));
		panelNovoEditar.add(lblEditar);
		
		Lista = new JList();
		Lista.addListSelectionListener(new ListSelectionListener() {public void valueChanged(ListSelectionEvent arg0) {
				carregaInfs();
		}});
		
		Lista.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		panelesquerdo.add(Lista, BorderLayout.CENTER);
		
		lblExcluir = new JLabel("Excluir");
		lblExcluir.addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent arg0) {
			apagar();
		}});
		lblExcluir.setIcon(new ImageIcon(Conf.class.getResource("/DB2/img/removedatabase.png")));
		lblExcluir.setHorizontalAlignment(SwingConstants.RIGHT);
		panelesquerdo.add(lblExcluir, BorderLayout.SOUTH);
		
		panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		
		panelBancos = new JPanel();
		panelCentro.add(panelBancos, BorderLayout.NORTH);
		panelBancos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		radioMysql = new JRadioButton("MySql");
		radioMysql.setEnabled(false);
		radioMysql.setSelected(true);
		panelBancos.add(radioMysql);
		
		radioOracle = new JRadioButton("Oracle");
		radioOracle.setEnabled(false);
		panelBancos.add(radioOracle);
		
		radioPostgresql = new JRadioButton("PostgreSQL");
		radioPostgresql.setEnabled(false);
		panelBancos.add(radioPostgresql);
		contentPanel.setLayout(new FlowLayout());
		
		g = new ButtonGroup();
		g.add(radioMysql);
		g.add(radioOracle);
		g.add(radioPostgresql);
		
		
		panelInfs = new JPanel();
		panelCentro.add(panelInfs, BorderLayout.CENTER);
		panelInfs.setLayout(null);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 11, 46, 14);
		panelInfs.add(lblUsuario);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 36, 46, 14);
		panelInfs.add(lblSenha);
		
		lblServidor = new JLabel("Servidor: ");
		lblServidor.setBounds(10, 61, 56, 14);
		panelInfs.add(lblServidor);
		
		lblPorta = new JLabel("Porta: ");
		lblPorta.setBounds(10, 86, 46, 14);
		panelInfs.add(lblPorta);
		
		lblBanco = new JLabel("Banco: ");
		lblBanco.setBounds(10, 111, 46, 14);
		panelInfs.add(lblBanco);
		
		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(66, 8, 229, 20);
		panelInfs.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		pwdSenha = new JPasswordField();
		pwdSenha.setEditable(false);
		pwdSenha.setColumns(10);
		pwdSenha.setBounds(66, 33, 229, 20);
		panelInfs.add(pwdSenha);
		
		txtServidor = new JTextField();
		txtServidor.setEditable(false);
		txtServidor.setColumns(10);
		txtServidor.setBounds(66, 58, 229, 20);
		panelInfs.add(txtServidor);
		
		txtPorta = new JTextField();
		txtPorta.setEditable(false);
		txtPorta.setColumns(10);
		txtPorta.setBounds(66, 83, 229, 20);
		panelInfs.add(txtPorta);
		
		txtBanco = new JTextField();
		txtBanco.setEditable(false);
		txtBanco.setColumns(10);
		txtBanco.setBounds(66, 108, 229, 20);
		panelInfs.add(txtBanco);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(Conf.class.getResource("/DB2/img/salvar.png")));
		btnSalvar.setEnabled(false);
		btnSalvar.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			salvar();
		}});
		panelCentro.add(btnSalvar, BorderLayout.SOUTH);
		
		JPanel panelConfirm = new JPanel();
		panelConfirm.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfirm.setPreferredSize(new Dimension(10, 32));
		getContentPane().add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		
		JButton btnConfirmarFechar = new JButton("Confirmar e Fechar");
		btnConfirmarFechar.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			dispose();
		}});
		btnConfirmarFechar.setIcon(new ImageIcon(Conf.class.getResource("/DB2/img/confirm.png")));
		btnConfirmarFechar.setAlignmentY(Component.TOP_ALIGNMENT);
		panelConfirm.add(btnConfirmarFechar);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblManutenoDeArquivos = new JLabel("Manuten\u00E7\u00E3o de Arquivos de Configura\u00E7\u00E3o");
		lblManutenoDeArquivos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		lblManutenoDeArquivos.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblManutenoDeArquivos);
	
	}

	public void listarconexoes(){
		File dir;
		File[] conecxoes;
		DefaultListModel bancos = new DefaultListModel();
		try{
			dir = new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB");
			conecxoes = dir.listFiles();
			for(int i=0; i<conecxoes.length ;i++)
				bancos.addElement(conecxoes[i].getName().substring(0, conecxoes[i].getName().indexOf(".")));
			
			Lista.setModel(bancos);
		}
		catch (Exception e) {
			
		}
	}

	public void novaconexao(){
		habilitacampos(true);
		limpacampos();
	}
	
	public void editar(){
		habilitacampos(true);
	}
	
	public void limpacampos(){
		for(int i=0; i<panelInfs.getComponentCount();i++)
			if (panelInfs.getComponent(i) instanceof JTextField || panelInfs.getComponent(i) instanceof JPasswordField)
				((JTextComponent) panelInfs.getComponent(i)).setText("");
	}
	
	public void habilitacampos(boolean flag){
		for(int i=0; i<panelBancos.getComponentCount();i++)
			panelBancos.getComponent(i).setEnabled(flag);
		
		for(int i=0; i<panelInfs.getComponentCount();i++)
			if (panelInfs.getComponent(i) instanceof JTextField || panelInfs.getComponent(i) instanceof JPasswordField)
				((JTextComponent) panelInfs.getComponent(i)).setEditable(flag);
		
		btnSalvar.setEnabled(flag);
	}
	
	public void salvar(){
		try{
			Properties p = new Properties();
			p.setProperty("TipoBanco", Integer.toString(getTipoBanco()));
			p.setProperty("Usuario", txtUsuario.getText());
			p.setProperty("Senha", new String(pwdSenha.getPassword()));
			p.setProperty("Host", txtServidor.getText());
			p.setProperty("Porta", txtPorta.getText());
			p.setProperty("Banco", txtBanco.getText());
			gravarPropriedades(p, txtBanco.getText());
			if (conecta()){ listarconexoes();   habilitacampos(false);	}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao capturar informações \n"+ e.getMessage());
		}
	}
	
	public void gravarPropriedades(Properties p, String nome){
		try{
			File arquivo = new File( FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+nome+".properties");  
            FileOutputStream fos = new FileOutputStream(arquivo);
            p.store(fos, "Dados para conexão com o banco");
            fos.close();
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar informações \n"+ ex.getMessage());
        }catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao capturar e gravar informações \n"+ e.getMessage());
		}
		
	}

	public int getTipoBanco(){
		if (radioOracle.isSelected()) return 1;
		else if (radioMysql.isSelected()) return 2; 
		else if (radioPostgresql.isSelected()) return 3;
		return 0;
	}
	
	public void setTipoBanco(int i){
		if (i==1) radioOracle.setSelected(true);
		else if (i==2) radioMysql.setSelected(true);
		else if (i==3) radioPostgresql.setSelected(true);
	}
	
	public boolean conecta() throws SQLException{
		ConnectDB c;
		if(txtPorta.equals(""))	 c = new ConnectDB(getTipoBanco(), txtServidor.getText(), txtBanco.getText(), txtUsuario.getText(), new String(pwdSenha.getPassword()));
		else c = new ConnectDB(getTipoBanco(), txtServidor.getText(), txtPorta.getText(), txtBanco.getText(), txtUsuario.getText(), new String(pwdSenha.getPassword()));
		
		if(c.isConectado()){
			c.Desconectar();
			return true;
		}
		else return false;
	}

	public void carregaInfs (){	
		habilitacampos(false);
		if (!Lista.isSelectionEmpty())
		if (new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+Lista.getSelectedValue().toString()+".properties").exists())
			try{
				Properties p =new Properties();
				FileInputStream fis = new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+Lista.getSelectedValue().toString()+".properties");
			    p.load(fis);			    
			    setTipoBanco(Integer.parseInt(p.getProperty("TipoBanco")));
			    txtUsuario.setText(p.getProperty("Usuario"));
			    pwdSenha.setText(p.getProperty("Senha"));
			    txtServidor.setText(p.getProperty("Host"));
			    txtPorta.setText(p.getProperty("Porta"));
			    txtBanco.setText(p.getProperty("Banco"));	
			    fis.close();
			}catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao reucperar informações \n"+ ex.getMessage());
	        }catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao recuperar e mostrar informações \n"+ e.getMessage());
			}
		else
			JOptionPane.showMessageDialog(null, "Arquivo de configuração não encontrado!!!", "Atenção!", 1);
	}
	
	public void apagar(){
		try{
			if (!Lista.isSelectionEmpty())
				if ( JOptionPane.showConfirmDialog(null, "Deseja Excluir Configuração de Banco de Dados?")==JOptionPane.OK_OPTION){
					File conexao = new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/GerenciadorDB/"+Lista.getSelectedValue().toString()+".properties");
					conexao.delete();
					listarconexoes();
				}
			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir configuração de banco de dados");
		}
		
	}
}
