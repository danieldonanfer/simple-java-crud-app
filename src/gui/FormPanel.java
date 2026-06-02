package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel cpfLabel;
	private JLabel nascimentoLabel;
	private JLabel occupationLabel;
	private JLabel emailLabel;
	private JLabel telefoneLabel;
	private JLabel contratoLabel;
	private JLabel ativoLabel;
	private JLabel generoLabel;

	private JTextField nameField;
	private JTextField cpfField;
	private JTextField nascimentoField;
	private JTextField occupationField;
	private JTextField emailField;
	private JTextField telefoneField;

	private JComboBox contratoCombo;
	private JCheckBox ativoCheck;

	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;

	private JButton okBtn;
	private FormListener formListener;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);

		nameLabel       = new JLabel("Imie:");
		cpfLabel        = new JLabel("PESEL:");
		nascimentoLabel = new JLabel("Data urodzenia:");
		occupationLabel = new JLabel("Stanowisko:");
		emailLabel      = new JLabel("E-mail:");
		telefoneLabel   = new JLabel("Telefon:");
		contratoLabel   = new JLabel("Umowa:");
		ativoLabel      = new JLabel("Aktywny:");
		generoLabel     = new JLabel("Plec:");

		nameField       = new JTextField(12);
		cpfField        = new JTextField(12);
		nascimentoField = new JTextField(12);
		nascimentoField.setToolTipText("DD/MM/AAAA");
		occupationField = new JTextField(12);
		emailField      = new JTextField(12);
		telefoneField   = new JTextField(12);

		DefaultComboBoxModel contratoModel = new DefaultComboBoxModel();
		contratoModel.addElement("Umowa o prace");
		contratoModel.addElement("B2B");
		contratoModel.addElement("Staztysta");
		contratoModel.addElement("Podwykonawca");
		contratoCombo = new JComboBox(contratoModel);
		contratoCombo.setSelectedIndex(0);

		ativoCheck = new JCheckBox();
		ativoCheck.setSelected(true);

		maleRadio   = new JRadioButton("Mezczyzna");
		femaleRadio = new JRadioButton("Kobieta");
		maleRadio.setActionCommand("MASCULINO");
		femaleRadio.setActionCommand("FEMININO");
		maleRadio.setSelected(true);

		genderGroup = new ButtonGroup();
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

		okBtn = new JButton("Dodaj");
		okBtn.setMnemonic(KeyEvent.VK_C);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name           = nameField.getText();
				String cpf            = cpfField.getText();
				String nascimento     = nascimentoField.getText();
				String occupation     = occupationField.getText();
				String email          = emailField.getText();
				String telefone       = telefoneField.getText();
				String contrato       = (String) contratoCombo.getSelectedItem();
				boolean ativo         = ativoCheck.isSelected();
				String gender         = genderGroup.getSelection().getActionCommand();

				FormEvent ev = new FormEvent(this, name, occupation,
						nascimento, contrato, ativo, cpf, gender, email, telefone);

				if (formListener != null) {
					formListener.formEventOccurred(ev);
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Dane Pracownika");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		Insets labelInsets = new Insets(4, 0, 4, 6);
		Insets fieldInsets = new Insets(4, 0, 4, 0);

		int row = 0;

		// Nome
		gc.gridy = row; gc.weightx = 1; gc.weighty = 0.1;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(nameLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(nameField, gc);

		// CPF
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(cpfLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(cpfField, gc);

		// Nascimento
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(nascimentoLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(nascimentoField, gc);

		// Genero
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(generoLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(maleRadio, gc);

		gc.gridy = ++row;
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(femaleRadio, gc);

		// Email
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(emailLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(emailField, gc);

		// Telefone
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(telefoneLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(telefoneField, gc);

		// Cargo
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(occupationLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(occupationField, gc);

		// Tipo de Contrato
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(contratoLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(contratoCombo, gc);

		// Ativo
		gc.gridy = ++row;
		gc.gridx = 0; gc.anchor = GridBagConstraints.LINE_END; gc.insets = labelInsets;
		add(ativoLabel, gc);
		gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START; gc.insets = fieldInsets;
		add(ativoCheck, gc);

		// Botao
		gc.gridy = ++row; gc.weighty = 2.0;
		gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets = new Insets(8, 0, 0, 0);
		add(okBtn, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}
