package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JLabel emailLabel;
	private JLabel telefoneLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JTextField emailField;
	private JTextField telefoneField;
	private JButton okBtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JCheckBox ativoCheck;
	private JTextField taxField;
	private JLabel taxLabel;

	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 290;
		setPreferredSize(dim);

		nameLabel      = new JLabel("Nome: ");
		nameLabel.setIcon(createIcon("/images/gear.png"));
		occupationLabel = new JLabel("Ocupacao: ");
		occupationLabel.setIcon(createIcon("/images/price.png"));
		emailLabel     = new JLabel("E-mail: ");
		telefoneLabel  = new JLabel("Telefone: ");

		nameField      = new JTextField(10);
		occupationField = new JTextField(10);
		emailField     = new JTextField(10);
		telefoneField  = new JTextField(10);

		ageList   = new JList();
		empCombo  = new JComboBox();
		ativoCheck = new JCheckBox();
		taxField  = new JTextField(10);
		taxLabel  = new JLabel("CPF: ");
		okBtn     = new JButton("OK");
		okBtn.setIcon(createIcon("/images/1400445397_accept.png"));

		okBtn.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);

		maleRadio   = new JRadioButton("Masculino");
		femaleRadio = new JRadioButton("Feminino");
		maleRadio.setActionCommand("MASCULINO");
		femaleRadio.setActionCommand("FEMININO");

		genderGroup = new ButtonGroup();
		maleRadio.setSelected(true);
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

		// Lista de faixas etarias
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0, "Crianca"));
		ageModel.addElement(new AgeCategory(1, "Adolescente"));
		ageModel.addElement(new AgeCategory(2, "Adulto"));
		ageModel.addElement(new AgeCategory(3, "Meia-Idade"));
		ageModel.addElement(new AgeCategory(4, "Idoso"));
		ageList.setModel(ageModel);
		ageList.setPreferredSize(new Dimension(120, 100));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(2);

		// Combo de emprego
		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("Empregado");
		empModel.addElement("Desempregado");
		empModel.addElement("Autonomo");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name       = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
				String empCat     = (String) empCombo.getSelectedItem();
				String taxId      = taxField.getText();
				boolean ativo     = ativoCheck.isSelected();
				String gender     = genderGroup.getSelection().getActionCommand();
				String email      = emailField.getText();
				String telefone   = telefoneField.getText();

				FormEvent ev = new FormEvent(this, name, occupation,
						ageCat.getId(), empCat, ativo, taxId, gender, email, telefone);

				if (formListener != null) {
					formListener.formEventOccurred(ev);
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Adicionar Pessoa");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);
		if (url == null) System.err.println("Unable to load image: " + path);
		return new ImageIcon(url);
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// Nome
		gc.gridy = 0; gc.weightx = 1; gc.weighty = 0.1;
		gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0,0,0,5);
		add(nameLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = new Insets(0,0,0,0);
		add(nameField, gc);

		// Ocupacao
		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0,0,0,5);
		add(occupationLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = new Insets(0,0,0,0);
		add(occupationField, gc);

		// E-mail
		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0,0,0,5);
		add(emailLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = new Insets(0,0,0,0);
		add(emailField, gc);

		// Telefone
		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0,0,0,5);
		add(telefoneLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = new Insets(0,0,0,0);
		add(telefoneField, gc);

		// Faixa Etaria
		gc.gridy++; gc.weighty = 0.2;
		gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(5,0,0,5);
		JLabel faixaLabel = new JLabel("Faixa Etaria: ");
		faixaLabel.setIcon(createIcon("/images/car.png"));
		add(faixaLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(5,0,0,0);
		add(ageList, gc);

		// Emprego
		gc.gridy++; gc.weighty = 0.2;
		gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0,0,0,5);
		JLabel empLabel = new JLabel("Emprego: ");
		empLabel.setIcon(createIcon("/images/ware.png"));
		add(empLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(0,0,0,0);
		add(empCombo, gc);

		// Ativo
		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0,0,0,5);
		JLabel ativoLabel = new JLabel("Ativo: ");
		ativoLabel.setIcon(createIcon("/images/import1.png"));
		add(ativoLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(0,0,0,0);
		add(ativoCheck, gc);

		// CPF (sempre visivel)
		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0,0,0,5);
		add(taxLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(0,0,0,0);
		add(taxField, gc);

		// Genero
		gc.gridy++; gc.weighty = 0.05;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Genero: "), gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(0,0,0,0);
		add(maleRadio, gc);

		gc.gridy++; gc.weighty = 0.1;
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(0,0,0,0);
		add(femaleRadio, gc);

		// OK
		gc.gridy++; gc.weighty = 2.0;
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(5,0,0,0);
		add(okBtn, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}

class AgeCategory {
	private int id;
	private String text;

	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public String toString() { return text; }
	public int getId() { return id; }
}
