package DB2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class ConnectDB {

	private String host, usuario, senha, NomeBanco, url, porta="", driver, ErroSelect, ErroUpdate, statusSql, erroConexao;
	private int TipoBanco;
	private Connection conn;
	private ResultSet rs;
	private Statement stm;
	private boolean conectado=false;
	public static final int ORACLE=1, MYSQL=2, POSTGRESQL=3, FIREBIRD=4, SQLSERVER=5;
		
	public String getStatusSql() {
		return statusSql;
	}

	public String getErroSelect() {
		return ErroSelect;
	}

	public String getErroUpdate() {
		return ErroUpdate;
	}

	public String getNomeBanco() {
		return NomeBanco;
	}
	
	public String getErroConexao() {
		return erroConexao;
	}

	public Statement getStm() {
		return stm;
	}

	public boolean isConectado() {
		return conectado;
	}

	public ConnectDB(int TipoBanco, String host, String NomeBanco, String usuario, String senha){
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.NomeBanco = NomeBanco;
		this.TipoBanco = TipoBanco;
		StringConnect();
		Connect();
	}
	
	public ConnectDB(int TipoBanco, String host,String porta, String NomeBanco, String usuario, String senha){
		this.host = host;
		this.usuario = usuario;
		this.senha = senha;
		this.TipoBanco =TipoBanco;
		this.NomeBanco =NomeBanco;
		this.porta = porta;
		StringConnect();
		Connect();
	}
	
	public ConnectDB(){
		this.host="192.0.0.16";
		this.usuario ="RamaisMaquinas";
		this.senha="2012";
		this.NomeBanco ="ramais_e_maquinas";
		this.TipoBanco=MYSQL;
		//this.url = "jdbc:mysql://"+host+"/"+NomeBanco;
		StringConnect();
		Connect();
	}
	
	public void StringConnect(){
		
		if (TipoBanco==MYSQL){
			driver="com.mysql.jdbc.Driver";
			if (porta.equals("") || porta.equals(null))
				url = "jdbc:mysql://"+host+"/"+NomeBanco;
			else
				url = "jdbc:mysql://"+host+":"+porta+"/"+NomeBanco;
		}
		
		if (TipoBanco == ORACLE){
			driver="oracle.jdbc.driver.OracleDriver";
			if (porta.equals("") || porta.equals(null))
				url = "jdbc:oracle:thin:@" + host +":1521" + NomeBanco;
			else
				url = "jdbc:oracle:thin:@" + host + ":" + porta + ":" + NomeBanco;
		}		
		if (TipoBanco == SQLSERVER){
			driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			if (porta.equals("") || porta.equals(null))
				url = "jdbc:sqlserver://"+host+":1433;" +"databaseName="+NomeBanco+";user="+usuario+";password="+senha;
			else
				url = "jdbc:sqlserver://"+host+":"+porta+";databaseName="+NomeBanco+";user="+usuario+";password="+senha;
		}
		if (TipoBanco==POSTGRESQL){
			driver="org.postgresql.Driver";
			if (porta.equals("") || porta.equals(null))
				url = "jdbc:postgresql://"+host+":5432/"+NomeBanco;
			else
				url = "jdbc:sqlserver://"+host+":"+porta+"/"+NomeBanco;
		}
		
	}
	
	private boolean Connect(){
		try{
			 Class.forName(driver).newInstance();
			 try{
				 conn = DriverManager.getConnection(url, usuario, senha);
				 erroConexao="";
				 conectado=true;
				 return true;
			 }catch (SQLException e) {
				 erroConexao = "\nConexão Não Realizada! - Erro de SQL\r\n"+e.getMessage()+"\r\n"+e.getSQLState()+" | "+e.getErrorCode();
				 conectado=false;
				 return false;
			}catch (Exception e) {
				 erroConexao = "\nConexão Não Realizada!\n\n"+e.getMessage();
				 conectado=false;
				 return false;
			 }
		}catch (ClassNotFoundException e) {
			erroConexao =  "\nDriver nao pode ser carregado 1!!!\n\n"+e.getMessage();
			conectado=false;
			return false;
		 }catch (Exception e) {
			erroConexao = "\nDriver nao pode ser carregado 2!!!\n\n"+e.getMessage();
			conectado=false;
			return false;
		}
	}//fim da função connect
	
	public void Desconectar() throws SQLException{
		conn.close();
		conectado=false;
	}
	
	protected String Insert (String comando){
		ErroUpdate=null;
		if(isConectado())
		try{
			stm = conn.createStatement();
			try{
				stm.executeUpdate(comando);
				return stm.getUpdateCount()+" Resgistros Alterados";
			}catch (SQLException e) {  
				ErroUpdate="Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
	        	 return ErroUpdate;
	  	    }catch (Exception e) {
	  	    	ErroUpdate="Erro de SQL: "+e.getMessage();
				return ErroUpdate;
			}
		}catch (SQLException e) {  
			ErroUpdate="Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
       	 	return ErroUpdate;
 	    }catch (Exception e) {
 	    	ErroUpdate="Erro de SQL: "+e.getMessage();
			 return ErroUpdate;
		}
		return ErroUpdate;		
	}//fim da função insert
	
	protected String Update(String comando){
		return Insert(comando);		
	}
	
	protected String Delete(String comando){
		return Insert(comando);		
	}
	
	public ResultSet Select(String select){
		if(isConectado())
		try{
			Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			try {
	              rs = stm.executeQuery(select);
	              ErroSelect=null;
	              return rs;
			}catch (SQLException e) {
	        	 ErroSelect="Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
	        	 return null;
	  	    }catch (Exception ex) {
	  	    	 ErroSelect="Erro de SQL: "+ex.getMessage();
	        	  return null;
	        }
		}catch (SQLException e) {
			 ErroSelect="Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
       	 	return null;
		}
		catch (Exception e) {
			ErroSelect="Erro de SQL: "+e.getMessage();
			return null;
		}				
		return null;		
	}//fim da função select
	
	public boolean ExecutarSql(String sql){
		if(isConectado())
			try{
				Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				try {
		              stm.execute(sql);
		              statusSql = "Comando executado.";
		              return true;
		            
				}catch (SQLException e) {
					statusSql = "Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
		        	 return false;
		  	    }catch (Exception ex) {
		  	    	statusSql= "Erro de SQL: "+ex.getMessage(); 
		  	    	return false;
		        }
			}catch (SQLException e) {
				statusSql = "Erro de SQL: "+e.getMessage()+"\nSQL State: "+e.getSQLState()+ " Código do erro: "+e.getErrorCode();
				 return false;
			}
			catch (Exception e) {
				statusSql = "Erro de SQL: "+e.getMessage();
				 return false;
			}			
		statusSql = "Não Conectado ao Banco";
		return false;	
	}
	
	public String[] getTabelas(){
		try{
			String comando="";
			if(TipoBanco==MYSQL)	comando ="select TABLE_NAME from information_schema.tables where TABLE_SCHEMA ='"+NomeBanco+"'";
			if(TipoBanco==ORACLE)	comando="Select TNAME from tab";
			if (TipoBanco==POSTGRESQL) comando ="SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname NOT IN ('pg_catalog', 'information_schema', 'pg_toast') ORDER BY tablename";
			//JOptionPane.showMessageDialog(null, TipoBanco+" " +comando);
			return getRegistrosColuna(Select(comando));
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao obter nome das tabelas: "+e.getMessage());
			return null;
		}
	}
	
	public String[] getCampos(String tabela){
		try{
			String comando="";
			if(TipoBanco==MYSQL)comando="desc "+tabela;
			if(TipoBanco==ORACLE) comando = "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS ac WHERE UPPER(ac.TABLE_NAME) LIKE UPPER('"+tabela+"')";
			if(TipoBanco==POSTGRESQL)comando = "select a.attname as \"Coluna\" from pg_catalog.pg_attribute " +
					"a inner join pg_stat_user_tables c on a.attrelid = c.relid " +
					"WHERE a.attnum > 0 AND c.relname='"+tabela+"' " +
					"AND NOT a.attisdropped order by c.relname";
			return getRegistrosColuna(Select(comando));
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao obter nomes do campo da tabela especificada: \n\n"+e.getMessage());
			return null;
		}
	}
	
	public int getNumRegs(ResultSet rs){
		try{			
			rs.last();
			return rs.getRow();
		}catch (Exception e) {
			return 000;
		}
	}
	
	public int getNumRegs(String select){
		return getNumRegs(select);
	}
	
	public String[] getNomeCampos(ResultSet rs){
		try{
			ResultSetMetaData rsmd;
			String [] campos;
			
			rsmd = rs.getMetaData();
			campos = new String[rsmd.getColumnCount()];
			for(int i=1; i <= rsmd.getColumnCount(); i++)
				campos[i-1] = rsmd.getColumnName(i);
			return campos;
		}catch (Exception e) {
			return new String[]{"Erro"+e.getMessage()};
		}
	}//fim da função getNOmeCampos

	public String[] getNomeCampos(String select){
		return getNomeCampos(Select(select));
	}
	
	public String[][] getResgistrosCompostos(ResultSet rs){
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			String [][] colunas;
			
			rs.last();
			int nreg = rs.getRow();
			rs.first();			
			colunas = new String[nreg][rsmd.getColumnCount()];
			for(int i=0; i< nreg; i++)
			{
				for(int j=1; j <= rsmd.getColumnCount(); j++)
					colunas [i][j-1] = rs.getString(j);				
				rs.next();
			}
			return colunas;
		}catch (Exception e) {
			return new String[][]{{"Erro"+e.getMessage()},{"Erro"+e.getMessage()}};
		}
	}//fim da funlção getColunas
	
	public String[][] getResgistrosCompostos(String select){
		return getResgistrosCompostos(Select(select));
	}
	
	@SuppressWarnings({ })
	public String[] getRegistrosColuna(ResultSet rs){
		try{
			String[] colunas;
			
			rs.last();
			int nreg = rs.getRow();
			rs.first();	
			colunas = new String[nreg];
			for(int i=0; i< nreg; i++){
				colunas [i] = rs.getString(1);
				rs.next();
			}
			return colunas;
		}catch (Exception e) {
			return new String[]{"Erro"+e.getMessage()};
		}
	}//fim da funlção getregistros
	
	public String[] getRegistrosColuna(String select){
		return getRegistrosColuna(Select(select));
	}
	
	@SuppressWarnings({ })
	public String[] getRegistrosColunaAt(ResultSet rs, int coluna){
		try{
			String[] colunas;
			
			rs.last();
			int nreg = rs.getRow();
			rs.first();	
			colunas = new String[nreg];
			for(int i=0; i< nreg; i++){
				colunas [i] = rs.getString(coluna);
				rs.next();
			}
			return colunas;
		}catch (Exception e) {
			return new String[]{"Erro"+e.getMessage()};
		}
	}//fim da funlção getregistros
	
	public String[] getRegistrosColunaAt(String select, int coluna){
		return getRegistrosColunaAt(Select(select), coluna);
	}
	
	public String[] getRegistrosColunaAt(ResultSet rs, String coluna){
		try{
			String[] colunas;
			
			rs.last();
			int nreg = rs.getRow();
			rs.first();	
			colunas = new String[nreg];
			for(int i=0; i< nreg; i++){
				colunas [i] = rs.getString(coluna);
				rs.next();
			}
			return colunas;
		}catch (Exception e) {
			return new String[]{"Erro"+e.getMessage()};
		}
	}//fim da funlção getregistros
	
	public String[] getRegistrosColunaAt(String select, String coluna){
		return getRegistrosColunaAt(Select(select), coluna);
	}
	
	public String[] getRegistrosColunaSelect(ResultSet rs, String firstSelect){
		try{
			String[] colunas;
			
			rs.last();
			int nreg = rs.getRow();
			rs.first();	
			colunas = new String[nreg+1];
			colunas[0]=firstSelect;
			for(int i=0; i< nreg; i++){
				colunas [i+1] = rs.getString(1);
				rs.next();
			}
			return colunas;
		}catch (NullPointerException e) {
			return new String[]{"Erro de Ponto Nulo ao obter resgistros da consulta"+e.getMessage()};
		}catch (ArrayIndexOutOfBoundsException e) {
			return new String[]{"Erro de Estouro de array ao obter resgistros da consulta"+e.getMessage()};
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return new String[]{"Erro ao obter resgistros da consulta"+e.getMessage()};
		}
	}//fim da funlção getregistros

	public String[] getRegistrosColunaSelect(String select, String firstSelect){
		return getRegistrosColunaSelect(Select(select), firstSelect);
	}
	
	public String [] getRegistrosLinha (ResultSet rs){
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			String [] linha = new String[rsmd.getColumnCount()];
			rs.first();			
			for(int j=1; j <= rsmd.getColumnCount(); j++)
				linha[j-1]=rs.getString(j);				
			return linha;
		}catch (Exception e) {
			return null;
		}
	}
	
	public String [] getRegistrosLinha (String select){
		return getRegistrosLinha(Select(select));
	}
	
	public String [] getRegistrosLinhaAt (ResultSet rs, int at){
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.first();	
			for(int i=1; i<=at; i++)rs.next();
			String [] linha = new String[rsmd.getColumnCount()];
					
			for(int j=1; j <= rsmd.getColumnCount(); j++)
				linha[j-1]=rs.getString(j);				
			return linha;
		}catch (Exception e) {
			return null;
		}
	}
	
	public String getRegistoUnico(ResultSet rs){
		try{
				rs.first();
				return rs.getString(1);				
		}catch (Exception e) {
			return "erro!!!";
		}
	}
	
	public String getRegistoUnico(String select){
		return getRegistoUnico(Select(select));
	}
	
	public DefaultTableModel getTabelModelDB(ResultSet rs){
		try{
			return new DefaultTableModel(getResgistrosCompostos(rs), getNomeCampos(rs));
		}catch (Exception e) {
			return new DefaultTableModel();
		}
	}//fim da função getModeloTabela
	
	public DefaultTableModel getTabelModelDB(String select){
		return getTabelModelDB(Select(select));
	}
	
	public JTree getArvoreSimples(JTree arvore){
		try{
			arvore.setModel(null);
			DefaultMutableTreeNode raizArvore = new DefaultMutableTreeNode(NomeBanco);
			String [] tabelas = getTabelas();
			String [] campos = null;
			for(int i=0; i<tabelas.length;i++){
				DefaultMutableTreeNode tabs = new DefaultMutableTreeNode(tabelas[i]);
				raizArvore.add(tabs);
				campos = getCampos(tabelas[i]);
				for(int j=0; j<campos.length; j++){
					DefaultMutableTreeNode campo = new DefaultMutableTreeNode(campos[j]);
					tabs.add(campo);
				}
			}
			DefaultTreeModel model = new DefaultTreeModel(raizArvore);
			arvore.setModel(model);	
			return arvore;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao montar strutura de árvore\n\n"+e.getMessage());
			return null;
		}
	}
	
	public JTable getDbGrid(ResultSet rs){
		try{
			JTable dbgrid = new JTable(new DbGrid(getNomeCampos(rs), getResgistrosCompostos(rs)));
			dbgrid.setAutoCreateRowSorter(true);
			//dbgrid.setAutoResizeMode(dbgrid.AUTO_RESIZE_OFF);
			return dbgrid;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "erro: "+e.getMessage(), "Atenção!!!", 1);
			return null;
		}
	}
	
	public JTable getDbGrid(String select){
		return getDbGrid(Select(select));
	}

	public AbstractTableModel getAbstractModelDB(ResultSet rs){
		try{
			AbstractTableModel model = new DbGrid(getNomeCampos(rs), getResgistrosCompostos(rs));
			return model;
		}catch (Exception e) {
			return null;
		}
	}
	
	public AbstractTableModel getAbstractModelDB(String select){
		return getAbstractModelDB(Select(select));
	}
	
	
	////////////////////////////////classe da bdgrid///////////////////////////////////
	@SuppressWarnings({ "serial", "unused" })
	private class DbGrid extends AbstractTableModel{

	 
		String[] colunas;
		String[][] linhas;
		
		
		public DbGrid (String[] colunas, String[][] linhas){
			this.colunas = colunas;
			this.linhas = linhas;
		}
		
		public int getColumnCount() {
            return colunas.length;
        }

        public int getRowCount() {
            return linhas.length;
        }

        public String getColumnName(int col) {
            return colunas[col];
        }

        public Object getValueAt(int row, int col) {
            return linhas[row][col];
        }
		
        public String[] getColumns(){
        	return colunas;
        }
        
        public String[][] getRows(){
        	return linhas;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
        public void setValueAt(String value, int row, int col) {
        	linhas[row][col] = value;
            fireTableCellUpdated(row, col);
        }
		
        
	}
	////////////////////////////////////fim da classe da dbgrid


}

/*
 * CLASSE ConnectDB
 *Connect
 *  Faz a conexão ao NomeBanco
 *  
 *Insert
 *	realiza um comando Insert no banco  
 * 
 *Update
 * 	realiza um update nos registro da tabela de acordo com o comando
 * 
 *Delete
 * 	realiza um update nos registro da tabela de acordo com o comando
 * 
 * 	Obs.: Update e Delete utilizarão o mesmo código, por isso a chamada ao método Insert retornando uma string com o numero de linhas alteradas da tabeça
 * 
 *Select
 *	realiza a instrução de select e retorna o ResultSet do coamdno
 * 
 *getStm
 *	retorna o statmente do ultimo comando realizado com sucesso
 * 
 *getErroSelect, getErroUpdade
 *	retorna uma string com o erro retornado
 * 
 *getTabelas()
 *	retorna um array de string com todas as tabelas no banco 
 * 
 *getNomeCampos
 * 	retorna um array de String com os nomes dos campos do retorno sql
 * 
 *getResgistrosCompostos
 * 	retorna um array de duas dimensões one haverão ncolunas (referente ao numero de campos restornados no select)
 * 	por nlinhas (referentes ao numero de registros retornados da sql)
 * 
 *getNumRegs
 * 	Retorna o numero de registro retonados pelo comando SQL
 * 
 *getRegistoUnico
 *	retorna um registo simples com apenas um campo e uma linha retornado de um comando sql
 * 
 *getRegistrosColunas
 * 	retorna um array com registros retornados de uma sql onde há apenas um campo a ser mostrado ex: "select nome from empregado"; ou a primeira coluna da consulta
 * 
 *getRegistrosColunaAt(ResultSet rs, int coluna)
 *	retorna somente a coluna referida no parâmetro retornada do comando sql
 * 
 * getRegeistosLinha
 * 	retorna uma array de String com os valores resultantes da primeira linha do resultset
 * 
 *getRegistrosLinhaAt
 * 	retorna uma array de String com os valores resultantes da linha passada por paramerto
 *getTabelModelDB
 *	Retorna um DefaulTableModel com as colunas e registros devidamnte prenchidos
 * 
 *getArvoreSimples()
 * 	recebe uma jtree por parâmetro e atualiza ela de acordo com o banco
 * 
 * 
 *getDbGrid
 *	retorna um Jtable já com um TableModel preenchido e com ordenação das colunas pelo clique do mouse
 *
 *getAbstractModelDB
 *	retona um TableModel do tipo AbstractTableModel já devidamente preecnhido com valores retornados em um resultset
 * 
 * 
 * 
 * SUBCLASSE DBGrid 
 * 
 */
