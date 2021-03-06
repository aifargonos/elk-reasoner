package org.semanticweb.elk.protege.ui;

/*
 * #%L
 * ELK Reasoner Protege Plug-in
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.semanticweb.elk.protege.preferences.ElkGeneralPreferences;

public class ElkGeneralPreferencesPanel extends ElkPanel {

	private static final long serialVersionUID = -1327423520858030577L;

	private SpinnerNumberModel numberOfWorkersModel_;

	private JCheckBox incrementalCheckbox_, syncCheckbox_;

	@Override
	public ElkGeneralPreferencesPanel initialize() {
		ElkGeneralPreferences prefs = new ElkGeneralPreferences().load();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(buildNumberOfWorkersComponent(prefs.numberOfWorkers));
		add(buildIncrementalReasoningComponent(prefs.incrementalMode));
		add(buildAutoSyncComponent(prefs.autoSynchronization));
		add(Box.createVerticalGlue());
		add(buildResetComponent());
		return this;
	}

	@Override
	public ElkGeneralPreferencesPanel applyChanges() {
		ElkGeneralPreferences prefs = new ElkGeneralPreferences().load();
		prefs.numberOfWorkers = numberOfWorkersModel_.getNumber().intValue();
		prefs.incrementalMode = incrementalCheckbox_.isSelected();
		prefs.autoSynchronization = syncCheckbox_.isSelected();
		prefs.save();
		return this;
	}

	private Component buildNumberOfWorkersComponent(int numberOfWorkers) {
		JPanel workersPane = new JPanel();
		workersPane.setLayout(new BoxLayout(workersPane, BoxLayout.LINE_AXIS));
		JLabel label = new JLabel("Number of working threads:");
		numberOfWorkersModel_ = new SpinnerNumberModel(numberOfWorkers, 1, 999,
				1);
		JComponent spinner = new JSpinner(numberOfWorkersModel_);
		spinner.setMaximumSize(spinner.getPreferredSize());
		workersPane.add(label);
		workersPane.add(Box.createRigidArea(new Dimension(10, 0)));
		workersPane.add(spinner);
		label.setLabelFor(spinner);
		String tooltip = "The number of threads that ELK can use for performing parallel computations.";
		workersPane.setToolTipText(tooltip);
		spinner.setToolTipText(tooltip);
		workersPane.setAlignmentX(LEFT_ALIGNMENT);

		return workersPane;
	}

	private Component buildIncrementalReasoningComponent(boolean incrementalMode) {
		incrementalCheckbox_ = new JCheckBox("Incremental reasoning",
				incrementalMode);
		incrementalCheckbox_
				.setToolTipText("If checked, ELK tries to recompute only the results caused by the changes in the ontology");
		return incrementalCheckbox_;
	}

	private Component buildAutoSyncComponent(boolean autoSynchronization) {
		syncCheckbox_ = new JCheckBox("Auto-syncronization",
				autoSynchronization);
		syncCheckbox_
				.setToolTipText("If checked, ELK will always be in sync with the ontology (requires reasoner restart)");
		syncCheckbox_.setEnabled(incrementalCheckbox_.isSelected());
		incrementalCheckbox_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				syncCheckbox_.setEnabled(incrementalCheckbox_.isSelected());
			}
		});

		return syncCheckbox_;
	}

	private Component buildResetComponent() {
		JButton resetButton = new JButton(new AbstractAction() {
			private static final long serialVersionUID = 6257131701636338334L;

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		resetButton.setText("Reset");
		resetButton
				.setToolTipText("Resets all general ELK settings to default values");

		return resetButton;
	}

	private void reset() {
		ElkGeneralPreferences prefs = new ElkGeneralPreferences().reset();
		numberOfWorkersModel_.setValue(prefs.numberOfWorkers);
		incrementalCheckbox_.setSelected(prefs.incrementalMode);
		syncCheckbox_.setEnabled(incrementalCheckbox_.isSelected());
		syncCheckbox_.setSelected(prefs.autoSynchronization);
	}

}
