package com.semesterprojekt.URGripperJava.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
	private JSlider positionSlider = new JSlider();
	private JSlider forceSlider = new JSlider();
	private JButton closeButton = new JButton("Close Gripper");
	private JButton openButton = new JButton("Open Gripper");
	
	@Override
	public void buildUI(JPanel panel, ContributionProvider<URGripperProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
//blaaah bla

		panel.add(createCheckBox("Close to given position"));
		panel.add(createSpacer(5));
		panel.add(createCheckBox("Close with given force"));
		panel.add(createSpacer(20));
		panel.add(createDescription("Choose distance between gripper fingers:"));
		panel.add(createSpacer(5));
		panel.add(createPositionSlider(positionSlider, 0, 10, provider));
		panel.add(createSpacer(5));
		panel.add(createDescription("Choose distance between gripper fingers:"));
		panel.add(createForceSlider(forceSlider, 0 ,10, provider));
		panel.add(createSpacer(20));
		panel.add(createCloseButton(closeButton));
		panel.add(createOpenButton(openButton));
	}
	
	public void setIOComboBoxItems(Integer[] items) {
		ioComboBox.removeAllItems();
		ioComboBox.setModel(new DefaultComboBoxModel<Integer>(items));
	}
	
	public void setIOComboBoxSelection(Integer item) {
		ioComboBox.setSelectedItem(item);
	}
	
	public void setDurationSlider(int value) {
		durationSlider.setValue(value);
	}
	
	private Box createDescription(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(desc);
		
		box.add(label);
		
		return box;
	}
	
	private Box createCheckBox(String desc) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JCheckBox checkbox = new JCheckBox(desc);
		
		box.add(checkbox);
		
		return box;
	}
	
	private Box createIOComboBox(final JComboBox<Integer> combo,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(" digital_out ");
		
		combo.setPreferredSize(new Dimension(104, 30));
		combo.setMaximumSize(combo.getPreferredSize());
		
		combo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					provider.get().onOutputSelection((Integer) e.getItem());
				}
			}
		});
		
		box.add(label);
		box.add(combo);
		
		return box;
	}
	
	private Box createPositionSlider (final JSlider slider, int min, int max,
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
				provider.get().onDurationSelection(newValue);
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
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue)+" N");
				provider.get().onDurationSelection(newValue);
			}
		});
		
		box.add(slider);
		box.add(value);
		
		return box;
	}
	
	private Box createDurationSlider(final JSlider slider, int min, int max,
			final ContributionProvider<URGripperProgramNodeContribution> provider) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setOrientation(JSlider.HORIZONTAL);
		
		slider.setPreferredSize(new Dimension(275, 30));
		slider.setMaximumSize(slider.getPreferredSize());
		
		final JLabel value = new JLabel(Integer.toString(slider.getValue())+" s");
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = slider.getValue();
				value.setText(Integer.toString(newValue)+" s");
				provider.get().onDurationSelection(newValue);
			}
		});
		
		box.add(slider);
		box.add(value);
		
		return box;
	}
	
	private JButton createCloseButton (final JButton button) {
		
		button.setPreferredSize(new Dimension(200, 200));
		button.setMaximumSize(button.getPreferredSize());
		
		return button;
	}
	
	private JButton createOpenButton (final JButton button) {
		
		button.setPreferredSize(new Dimension(200, 200));
		button.setMaximumSize(button.getPreferredSize());
		
		return button;
	}
	
	private Component createSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}

}