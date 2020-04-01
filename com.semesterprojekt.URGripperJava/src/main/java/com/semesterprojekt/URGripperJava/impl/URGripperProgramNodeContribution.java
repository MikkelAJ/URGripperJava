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
	
	private static final String IP_KEY = "ip";
	private static final String PORT_KEY = "port";
	private static final String GRIPSTATUS_KEY = "gripStatus";
	
	private static final String DEFAULT_IP = "192.168.3.31";
	private static final int DEFAULT_PORT = 12345;
	private static final boolean DEFAULT_GRIPSTATUS = false;
	
	public URGripperProgramNodeContribution(ProgramAPIProvider apiProvider, URGripperProgramNodeView view,
			DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();
	}
	
	public void onIPSelection(final String ip) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(IP_KEY, ip);
			}
		});
	}
	
	public void onPortSelection(final int port) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(PORT_KEY, port);
			}
		});
	}
	
	
	public void onCloseSelection(final boolean gripStatus) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(GRIPSTATUS_KEY, gripStatus);
			}
		});
	}
	
	private String getIP() {
		return model.get(IP_KEY, DEFAULT_IP);
	}
	
	private int getPort() {
		return model.get(PORT_KEY, DEFAULT_PORT);
	}
	
	private boolean getGripStatus() {
		return model.get(GRIPSTATUS_KEY, DEFAULT_GRIPSTATUS);
	}
	
//	private Integer[] getOutputItems() {
//		Integer[] items = new Integer[8];
//		for(int i = 0; i<8; i++) {
//			items[i] = i;
//		}
//		return items;
//	}
	
	@Override
	public void openView() {
//		view.setIOComboBoxItems(getOutputItems());
		
		view.setIPTextField(getIP());
		view.setPortTextField(getPort());
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "IP: " + getIP() + " Port: " + getPort();
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		//Remember this is actual code to be run at runtime of robot execution.
		writer.appendLine("socket_open(\"" + getIP() + "\", " + getPort() + ", \"socket_0\")");
		writer.appendLine("socket_send_string(\"Executed send " + getGripStatus() + "\", \"socket_0\")"); //"execute send" er den string der sendes;
		writer.appendLine("socket_close(\"socket_0\")");
	}

}