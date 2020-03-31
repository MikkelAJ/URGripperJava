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
	
	private int i;
	
	private static final String IP_KEY = "ip";
	private static final String PORT_KEY = "port";
	
	private static final String DEFAULT_IP = "192.168.3.31";
	private static final int DEFAULT_PORT = 1234;
	
	public URGripperProgramNodeContribution(ProgramAPIProvider apiProvider, URGripperProgramNodeView view,
			DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();
		i = 0;
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
	
	private String getIP() {
		return model.get(IP_KEY, DEFAULT_IP);
	}
	
	private int getPort() {
		return model.get(PORT_KEY, DEFAULT_PORT);
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
		
//		view.setIP(getIP());
//		view.setPort(getPort());
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
		writer.appendLine("socket_open(\"" + getIP() + "\", " + getPort() + ", \"socket_0\")");
		writer.appendLine("socket_send_string(\"Executed send\")");
		writer.sleep(1);
		writer.appendLine("socket_close(\"socket_0\")");
		writer.sleep(1);
	}

}