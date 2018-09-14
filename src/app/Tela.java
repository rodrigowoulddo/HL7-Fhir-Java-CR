package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JSlider;

public class Tela extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtSobrenome;
	private JTextField TxtCEP;
	private JTextField txtCidade;
	private JTextField txtPais;
	private JTextField txtIDPesquisa;
	private JTextField txtNomePesquisa;
	private JTextField txtSobrenomePesquisa;
	private JLabel lblIdPesquisa;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 675);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 286, 475, 2);
		contentPane.add(separator);
		
		JLabel lblPesquisa = new JLabel("Pesquisa");
		lblPesquisa.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesquisa.setFont(new Font("Calibri Light", Font.BOLD, 18));
		lblPesquisa.setBounds(10, 286, 475, 34);
		contentPane.add(lblPesquisa);
		
		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastro.setFont(new Font("Calibri Light", Font.BOLD, 18));
		lblCadastro.setBounds(10, 0, 475, 34);
		contentPane.add(lblCadastro);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(81, 61, 86, 14);
		contentPane.add(lblNome);
		
		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setBounds(81, 86, 86, 14);
		contentPane.add(lblSobrenome);
		
		JLabel lblCEP = new JLabel("CEP:");
		lblCEP.setBounds(81, 149, 86, 14);
		contentPane.add(lblCEP);
		
		JLabel lblCidadde = new JLabel("Cidade:");
		lblCidadde.setBounds(81, 174, 86, 14);
		contentPane.add(lblCidadde);
		
		JLabel lblPas = new JLabel("Pa\u00EDs:");
		lblPas.setBounds(81, 199, 86, 14);
		contentPane.add(lblPas);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereo.setBounds(81, 124, 66, 14);
		contentPane.add(lblEndereo);
		
		txtNome = new JTextField();
		txtNome.setBounds(177, 58, 239, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtSobrenome = new JTextField();
		txtSobrenome.setColumns(10);
		txtSobrenome.setBounds(177, 83, 239, 20);
		contentPane.add(txtSobrenome);
		
		TxtCEP = new JTextField();
		TxtCEP.setColumns(10);
		TxtCEP.setBounds(177, 146, 239, 20);
		contentPane.add(TxtCEP);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(177, 171, 239, 20);
		contentPane.add(txtCidade);
		
		txtPais = new JTextField();
		txtPais.setColumns(10);
		txtPais.setBounds(177, 196, 239, 20);
		contentPane.add(txtPais);
		
		JLabel lblDadosGerais = new JLabel("Dados Gerais");
		lblDadosGerais.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDadosGerais.setBounds(81, 39, 120, 14);
		contentPane.add(lblDadosGerais);
		
		lblIdPesquisa = new JLabel("ID:");
		lblIdPesquisa.setBounds(366, 334, 50, 14);
		contentPane.add(lblIdPesquisa);
		
		JLabel label = new JLabel("Nome:");
		label.setBounds(32, 337, 86, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Sobrenome:");
		label_1.setBounds(32, 365, 102, 14);
		contentPane.add(label_1);
		
		txtIDPesquisa = new JTextField();
		txtIDPesquisa.setColumns(10);
		txtIDPesquisa.setBounds(399, 331, 58, 20);
		contentPane.add(txtIDPesquisa);
		
		txtNomePesquisa = new JTextField();
		txtNomePesquisa.setColumns(10);
		txtNomePesquisa.setBounds(128, 334, 136, 20);
		contentPane.add(txtNomePesquisa);
		
		txtSobrenomePesquisa = new JTextField();
		txtSobrenomePesquisa.setColumns(10);
		txtSobrenomePesquisa.setBounds(127, 362, 137, 20);
		contentPane.add(txtSobrenomePesquisa);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = txtNome.getText().toString();
				String sobrenome = txtSobrenome.getText().toString();
				String cep = TxtCEP.getText().toString();
				String cidade = txtCidade.getText().toString();
				String pais = txtPais.getText().toString();
				
				String[] msg = HL7Provider.insertPatient(HL7Provider.createPacient(nome, sobrenome, cep, cidade, pais));
				
			    String[] buttons = { "Ver JSON", "Ver XML", "Fechar" };

			    int rc = JOptionPane.showOptionDialog(null, "Paciente inserido com ID "+msg[0], "Sucesso",
			        JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[2]);
				
			    //JSON
			    if(rc == 0) {
			    	
			    	String json = HL7Provider.getPacient(msg[0],"json");
			    	JOptionPane.showMessageDialog(null,json, "JSON", JOptionPane.PLAIN_MESSAGE);
			    }
			    
			    //XML
			    if(rc == 1) {
			    	String xml = HL7Provider.getPacient(msg[0],"xml");
			    	JOptionPane.showMessageDialog(null,xml, "XML", JOptionPane.PLAIN_MESSAGE);
			    }
			}
		});
		btnCadastrar.setBounds(309, 241, 148, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String id = txtIDPesquisa.getText().toString();
				
				String nome = null;
				if(txtNomePesquisa.getText().toString()!= "")
					nome = txtNomePesquisa.getText().toString();

				String sobrenome = null;
				if(txtSobrenomePesquisa.getText().toString() != "")
					sobrenome = txtSobrenomePesquisa.getText().toString();

				
				ArrayList<String[]> result = HL7Provider.searchPatient(nome, sobrenome, id);
								
				DefaultTableModel model = new DefaultTableModel(); 
				JTable table = new JTable(model); 
				
				model.addColumn("ID"); 
				table.getColumn("ID").setMaxWidth(15);
				model.addColumn("Nome");
				model.addColumn("Endereço"); 
				
				for (String[] row : result) {
					model.addRow(row);
				}
				
				scrollPane.setViewportView(table);
				
			}
		});
		btnPesquisar.setBounds(375, 361, 110, 23);
		contentPane.add(btnPesquisar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 395, 465, 221);
		contentPane.add(scrollPane);
				
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblInformticaBiomdica = new JLabel("Inform\u00E1tica Biom\u00E9dica | UFCSPA | Uso permitido para fins educacionais");
		lblInformticaBiomdica.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformticaBiomdica.setBounds(10, 627, 471, 14);
		contentPane.add(lblInformticaBiomdica);
	}
}
