package com.semesterprojekt.URGripperJava.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class URGripperProgramNodeView implements SwingProgramNodeView<URGripperProgramNodeContribution> {

	private final ViewAPIProvider apiProvider;

	public URGripperProgramNodeView(ViewAPIProvider apiProvider) {
		this.apiProvider = apiProvider;
	}

	private JTextField ipTextField = new JTextField();
	private JTextField portTextField = new JTextField();
	private JSlider distanceSlider = new JSlider();
	private JSlider forceSlider = new JSlider();
	private JCheckBox forceCheckbox = new JCheckBox();
	private JCheckBox DistanceCheckbox = new JCheckBox();
	private JRadioButton openRadioButton = new JRadioButton();
	private JRadioButton closeRadioButton = new JRadioButton();

	@Override
	public void buildUI(JPanel panel, ContributionProvider<URGripperProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDescription("Please input IP-adress:"));
		panel.add(createIPTextField(ipTextField, provider));
		panel.add(createSpacer(5));
		panel.add(createDescription("Please input port number:"));
		panel.add(createPortTextField(portTextField, provider));
		panel.add(createSpacer(5));
		panel.add(createDistanceBox(DistanceCheckbox, provider));
		panel.add(createSpacer(5));
		panel.add(createForceBox(forceCheckbox, provider));
		panel.add(createSpacer(20));
		panel.add(createDescription("Choose distance between gripper fingers:"));
		panel.add(createSpacer(5));
		panel.add(createDistanceSlider(distanceSlider, 0, 65, provider));
		panel.add(createSpacer(5));
		panel.add(createDescription("Choose force between gripper fingers:"));
		panel.add(createForceSlider(forceSlider, 0, 10, provider));
		panel.add(createSpacer(20));
		panel.add(createOpenCloseRadioButton(openRadioButton, closeRadioButton, provider));
	}

	public void setIPTextField(String ip) {
		ipTextField.setText(ip);
	}

	public void setPortTextField(String port) {
		portTextField.setText(port);
	}

	public void setForceCheckBox(Boolean b) {
		forceCheckbox.setSelected(b);
	}

	public void setDistanceCheckBox(Boolean b) {
		DistanceCheckbox.setSelected(b);
	}

	public void setDistanceSlider(int value) {
		distanceSlider.setValue(value);
	}

	public void setForceSlider(int value) {
		forceSlider.setValue(value);
	}

	public void setGripperStatus(Boolean b) {
		openRadioButton.setSelected(b);
		closeRadioButton.setSelected(!b);
	}

	/**
	 * Set function to enable or disable forceCheckBox
	 * 
	 * @param b boolean type, where true enables check box and false disables check
	 *          box
	 */
	public void setForceCheckBoxEnable(Boolean b) {
		forceCheckbox.setEnabled(b);

	}

	/**
	 * Creates an area with a text field to write IP in and a button to set the IP
	 * 
	 * @param ipTextField type JTextField, text field for IP input
	 * @param provider    couples the View with the Contribution class
	 */
	private Box createIPTextField(final JTextField ipTextField,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		final JButton sendButton = new JButton("Set IP");
		final JButton defaultButton = new JButton("Set as default");

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provider.get().onIPSelection((String) ipTextField.getText());
			}
		});
		
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provider.get().onDefaultIPSelection((String) ipTextField.getText());
				provider.get().onIPSelection((String) ipTextField.getText());
			}
		});

		box.add(ipTextField);
		box.add(sendButton);
		box.add(defaultButton);
		box.setMaximumSize(new Dimension(500, 20));

		return box;

	}

	/**
	 * Creates an area with a text field to write port in and a button to set the
	 * port
	 * 
	 * @param portTextField type JTextField, text field for IP input
	 * @param provider      couples the View with the Contribution class
	 */
	private Box createPortTextField(final JTextField portTextField,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		final JButton sendButton = new JButton("Set port");
		final JButton defaultButton = new JButton("Set as default");
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provider.get().onPortSelection((String) portTextField.getText());
			}
		});
		
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provider.get().onPortSelection((String) portTextField.getText());
				provider.get().onDefaultPortSelection((String) portTextField.getText());
			}
		});

		box.add(portTextField);
		box.add(sendButton);
		box.add(defaultButton);
		box.setMaximumSize(new Dimension(500, 20));

		return box;
	}

	/**
	 * Creates an area with a check box and a label text for the check box
	 * 
	 * @param forceBox type JCheckBox, an instantiated JChckBox
	 * @param provider couples the View with the Contribution class
	 */
	private Box createForceBox(final JCheckBox forceBox,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		final JLabel description = new JLabel("Close by force");

		forceBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				provider.get().onForceSelection(e.getStateChange());

			}
		});

		box.add(forceBox);
		box.add(description);

		return box;
	}

	/**
	 * Creates an area with a check box and a label text for the check box
	 * 
	 * @param distanceBox type JCheckBox, an instantiated JChckBox
	 * @param provider    couples the View with the Contribution class
	 */
	private Box createDistanceBox(final JCheckBox distanceBox,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		final JLabel description = new JLabel("Close to given distance");

		distanceBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				provider.get().onDistanceSelection(e.getStateChange());

			}
		});

		box.add(distanceBox);
		box.add(description);

		return box;
	}

	/**
	 * Creates an area with a slider for choosing distance between gripper fingers
	 * and a label for unit
	 * 
	 * @param slider   type JSlider, an instantiated JSlider
	 * @param min      type int, minimum value of slider
	 * @param max      type int, maximum value of slider
	 * @param provider couples the View with the Contribution class
	 */
	private Box createDistanceSlider(final JSlider slider, int min, int max,
			final ContributionProvider<URGripperProgramNodeContribution> provider) { // Currently using Duration Slider
																						// provider
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);

		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());

		final JLabel value = new JLabel(Integer.toString(slider.getValue()) + " mm");

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue) + " mm");
				provider.get().onDistanceSliderSelection(newValue);
			}
		});

		box.add(slider);
		box.add(value);

		return box;
	}

	/**
	 * Creates an area with a slider for choosing force of gripper fingers and a
	 * label for unit
	 * 
	 * @param slider   type JSlider, an instantiated JSlider
	 * @param min      type int, minimum value of slider
	 * @param max      type int, maximum value of slider
	 * @param provider couples the View with the Contribution class
	 */
	private Box createForceSlider(final JSlider slider, int min, int max,
			final ContributionProvider<URGripperProgramNodeContribution> provider) { // Currently using Duration Slider
																						// provider
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);

		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());

		final JLabel value = new JLabel(Integer.toString(slider.getValue()) + " N");

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue) + " N");
				provider.get().onForceSliderSelection(newValue);
			}
		});

		box.add(slider);
		box.add(value);

		return box;
	}

	/**
	 * Creates an area with two radio buttons for opening and closing gripper and a
	 * label text for description
	 * 
	 * @param openRadioButton  type JRadioButton, an instantiated button for opening
	 *                         gripper
	 * @param closeRadioButton type JRadioButton, an instantiated button for closing
	 *                         gripper
	 * @param provider         couples the View with the Contribution class
	 */
	private Box createOpenCloseRadioButton(final JRadioButton openRadioButton, final JRadioButton closeRadioButton,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel label = new JLabel("Choose to open or close the gripper: ");

		openRadioButton.setText("Open");
		closeRadioButton.setText("Close");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(openRadioButton);
		buttonGroup.add(closeRadioButton);

		openRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				provider.get().onCloseSelection((boolean) openRadioButton.isSelected());
				// setting enabled status of forceCheckBox object according to open/close
				// status, as a gripper can't open by a given force
				forceCheckbox.setEnabled(!openRadioButton.isSelected());
				// setting forceCheckBox to false, if button is disabled, to update data in data
				// model
				forceCheckbox.setSelected(false);
			}
		});

		closeRadioButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				provider.get().onCloseSelection((boolean) openRadioButton.isSelected());
				// Re-enables forceCheckBox if close gripper is selected
				forceCheckbox.setEnabled(closeRadioButton.isSelected());
			}
		});

		box.add(label);
		box.add(openRadioButton);
		box.add(closeRadioButton);

		return box;
	}

	/**
	 * Creates an area with text for description of other components
	 * 
	 * @param desc type String, the text you want displayed
	 */
	private Box createDescription(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel label = new JLabel(desc);

		box.add(label);

		return box;
	}

	/**
	 * Creates an empty area for spacing with given height
	 * 
	 * @param heright type int, height of the spacer
	 */
	private Component createSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
}