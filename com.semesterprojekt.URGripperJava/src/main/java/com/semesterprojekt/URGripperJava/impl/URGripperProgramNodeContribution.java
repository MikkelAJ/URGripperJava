package com.semesterprojekt.URGripperJava.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

public class URGripperProgramNodeContribution implements ProgramNodeContribution{

	private final ProgramAPIProvider apiProvider;
	private final URGripperProgramNodeView view;
	private final DataModel model;
	private final UndoRedoManager undoRedoManager;
	
	private static final String OUTPUT_KEY = "output";
	private static final String DURATION_KEY = "duration";
	
	private static final Integer DEFAULT_OUTPUT = 0;
	private static final int DEFAULT_DURATION = 1;
	
	public URGripperProgramNodeContribution(ProgramAPIProvider apiProvider, URGripperProgramNodeView view,
			DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();
	}
	
	public void onOutputSelection(final Integer output) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(OUTPUT_KEY, output);
			}
		});
	}
	
	public void onDurationSelection(final int duration) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DURATION_KEY, duration);
			}
		});
	}
	
	private Integer getOutput() {
		return model.get(OUTPUT_KEY, DEFAULT_OUTPUT);
	}
	
	private int getDuration() {
		return model.get(DURATION_KEY, DEFAULT_DURATION);
	}
	
	private Integer[] getOutputItems() {
		Integer[] items = new Integer[8];
		for(int i = 0; i<8; i++) {
			items[i] = i;
		}
		return items;
	}
	
	@Override
	public void openView() {
		view.setIOComboBoxItems(getOutputItems());
		
		view.setIOComboBoxSelection(getOutput());
		view.setDurationSlider(getDuration());
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "URGripper";
		//return "LightUp: DO"+getOutput()+" t="+getDuration();
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		writer.appendLine("set_standard_digital_out("+getOutput()+",True)");
		writer.sleep(getDuration());
		writer.appendLine("set_standard_digital_out("+getOutput()+",False)");
	}

}