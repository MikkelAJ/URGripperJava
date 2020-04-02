package com.semesterprojekt.URGripperJava.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class URGripperProgramNodeView implements SwingProgramNodeView<URGripperProgramNodeContribution>{

	private final ViewAPIProvider apiProvider;
	
	public URGripperProgramNodeView(ViewAPIProvider apiProvider) {
		this.apiProvider = apiProvider;
	}
	
	private JComboBox<Integer> ioComboBox = new JComboBox<Integer>();
	private JSlider durationSlider = new JSlider();
	private JSlider distanceSlider = new JSlider();
	private JSlider forceSlider = new JSlider();
	private JButton closeButton = new JButton("Close Gripper");
	private JButton openButton = new JButton("Open Gripper");
	private JTextField ipTextField = new JTextField();
	private JTextField portTextField = new JTextField();
	JCheckBox forceCheckbox = new JCheckBox();
	JCheckBox DistanceCheckbox = new JCheckBox();
	
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
		panel.add(createSpacer(5));
//		panel.add(createCheckBox("Close with given force"));
		panel.add(createSpacer(20));
		panel.add(createDescription("Choose distance between gripper fingers:"));
		panel.add(createSpacer(5));
		panel.add(createDistanceSlider(distanceSlider, 0, 20, provider));
		panel.add(createSpacer(5));
		panel.add(createDescription("Choose force between gripper fingers:"));
		panel.add(createForceSlider(forceSlider, 0 ,10, provider));
		panel.add(createSpacer(20));
		panel.add(createCloseButton(closeButton,provider));
		panel.add(createSpacer(5));
		panel.add(createOpenButton(openButton, provider));
	}
	
	public void setIPTextField(String ip) {
		ipTextField.setText(ip);
	}
	
	public void setPortTextField(int Port) {
		portTextField.setText(Integer.toString(Port));
	}
	
	public void setIOComboBoxItems(Integer[] items) {
		ioComboBox.removeAllItems();
		ioComboBox.setModel(new DefaultComboBoxModel<Integer>(items));
	}
	
	public void setIOComboBoxSelection(Integer item) {
		ioComboBox.setSelectedItem(item);
	}
	
	public void setDistanceSlider(int value) {
		distanceSlider.setValue(value);
	}
	
	private Box createDescription(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(desc);
		
		box.add(label);
		
		return box;
	}
	
	private Box createForceBox(final JCheckBox forceBox, final ContributionProvider<URGripperProgramNodeContribution> provider) {
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
	
	private Box createDistanceBox(final JCheckBox distanceBox, final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JLabel description = new JLabel("Close to position");
		
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
	
	private Box createDistanceSlider (final JSlider slider, int min, int max,
			final ContributionProvider<URGripperProgramNodeContribution> provider) { //Currently using Duration Slider provider
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());
		
		final JLabel value = new JLabel(Integer.toString(slider.getValue())+ " mm");
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue)+" mm");
				provider.get().onDistanceSliderSelection(newValue);
			}
		});
		
		box.add(slider);
		box.add(value);
		
		return box;
	}
	
	private Box createForceSlider (final JSlider slider, int min, int max,
			final ContributionProvider<URGripperProgramNodeContribution> provider) { //Currently using Duration Slider provider
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());
		
		final JLabel value = new JLabel(Integer.toString(slider.getValue())+ " N");
		
//		slider.addChangeListener(new ChangeListener() {
//			
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				int newValue = slider.getValue();
//				value.setText(Integer.toString(newValue)+" N");
//				provider.get().onDurationSelection(newValue);
//			}
//		});
		
		box.add(slider);
		box.add(value);
		
		return box;
	}
	
	private JButton createCloseButton (final JButton button, final ContributionProvider<URGripperProgramNodeContribution> provider) {
		
		button.setPreferredSize(new Dimension(200, 100));
		button.setMaximumSize(button.getPreferredSize());
		
		Action action = new AbstractAction() {
			public void actionPerformed (ActionEvent e) {
				provider.get().onCloseSelection(false);
				button.getModel().setPressed(true);
			}
		};
		
		button.addActionListener(action);
		
		return button;
	}
	
	private JButton createOpenButton (final JButton button, final ContributionProvider<URGripperProgramNodeContribution> provider) {
		
		button.setPreferredSize(new Dimension(200, 100));
		button.setMaximumSize(button.getPreferredSize());
		
		Action action = new AbstractAction() {
			public void actionPerformed (ActionEvent e) {
				provider.get().onCloseSelection(true);
				button.getModel().setPressed(true);
			}
		};
		
		button.addActionListener(action);
		
		return button;
	}
	
	private Box createIPTextField (final JTextField ipTextField, final ContributionProvider<URGripperProgramNodeContribution> provider)	{
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JButton sendButton = new JButton("Set IP");
		
		Action action = new AbstractAction() {
			public void actionPerformed (ActionEvent e) {
				provider.get().onIPSelection((String) ipTextField.getText());
			}
		};
		
		ipTextField.addActionListener(action);
		sendButton.addActionListener(action);
			
		box.add(ipTextField);
		box.add(sendButton);
		
		return box;
	
 	}
	
	private Box createPortTextField (final JTextField portTextField, final ContributionProvider<URGripperProgramNodeContribution> provider)	{
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JButton sendButton = new JButton("Set port");
		
		Action action = new AbstractAction() {
			public void actionPerformed (ActionEvent e) {
				provider.get().onPortSelection(Integer.parseInt(portTextField.getText()));
			}
		};
		
		portTextField.addActionListener(action);
		sendButton.addActionListener(action);
				
		box.add(portTextField);
		box.add(sendButton);
		
		return box;
	}
	
	private Component createSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
//	private Box createIOComboBox(final JComboBox<Integer> combo,
//	final ContributionProvider<URGripperProgramNodeContribution> provider) {
//Box box = Box.createHorizontalBox();
//box.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//JLabel label = new JLabel(" digital_out ");
//
//combo.setPreferredSize(new Dimension(104, 30));
//combo.setMaximumSize(combo.getPreferredSize());
//
//combo.addItemListener(new ItemListener() {
//	
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		if(e.getStateChange() == ItemEvent.SELECTED) {
//			provider.get().onOutputSelection((Integer) e.getItem());
//		}
//	}
//});
//
//box.add(label);
//box.add(combo);
//
//return box;
//}

	
//	private Box createDurationSlider(final JSlider slider, int min, int max,
//	final ContributionProvider<URGripperProgramNodeContribution> provider) {
//Box box = Box.createHorizontalBox();
//box.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//slider.setMinimum(min);
//slider.setMaximum(max);
//slider.setOrientation(JSlider.HORIZONTAL);
//
//slider.setPreferredSize(new Dimension(275, 30));
//slider.setMaximumSize(slider.getPreferredSize());
//
//final JLabel value = new JLabel(Integer.toString(slider.getValue())+" s");
//
//slider.addChangeListener(new ChangeListener() {
//	
//	@Override
//	public void stateChanged(ChangeEvent e) {
//		int newValue = slider.getValue();
//		value.setText(Integer.toString(newValue)+" s");
//		provider.get().onDurationSelection(newValue);
//	}
//});
//
//box.add(slider);
//box.add(value);
//
//return box;
//}
}