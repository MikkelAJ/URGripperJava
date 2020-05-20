package com.semesterprojekt.URGripperJava.impl;

import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class URGripperProgramNodeService
		implements SwingProgramNodeService<URGripperProgramNodeContribution, URGripperProgramNodeView> {

	@Override
	public String getId() {
		return "URGripperNode";
	}

	@Override

	public void configureContribution(ContributionConfiguration configuration) {

		configuration.setChildrenAllowed(false);

	}

	@Override

	public String getTitle(Locale locale) {

		return "URGripper";

	}

	@Override

	public URGripperProgramNodeView createView(ViewAPIProvider apiProvider) {

		return new URGripperProgramNodeView(apiProvider);

	}

	@Override

	public URGripperProgramNodeContribution createNode(ProgramAPIProvider apiProvider, URGripperProgramNodeView view,

			DataModel model, CreationContext context) {

		return new URGripperProgramNodeContribution(apiProvider, view, model);

	}

}