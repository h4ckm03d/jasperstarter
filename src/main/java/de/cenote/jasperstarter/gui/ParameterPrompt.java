/*
 * Copyright 2013 Cenote GmbH.
 *
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
 */
package de.cenote.jasperstarter.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.sf.jasperreports.engine.JRParameter;
import de.cenote.jasperstarter.Report;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

/**
 *
 * @author Volker Voßkämper <vvo at cenote.de>
 * @version $Revision$
 */
public class ParameterPrompt {

    Component parent;
    JRParameter[] jrParameters;
    Map params;
    JScrollPane scrollPane;
    String reportName;

    public ParameterPrompt(Component parent, JRParameter[] jrParameters,
            Map params, String reportName, boolean isForPromptingOnly,
            boolean isUserDefinedOnly, boolean emptyOnly) {

        this.parent = parent;
        this.jrParameters = jrParameters;
        this.params = params;
        this.reportName = reportName;

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(600, 250));

        for (JRParameter param : jrParameters) {
            if (!param.isSystemDefined() || !isUserDefinedOnly) {
                if (param.isForPrompting() || !isForPromptingOnly) {
                    if (params.get(param.getName()) == null || !emptyOnly) {
                        panel.add(new ParameterPanel(param, params));
                    }
                }
            }
        }
        panel.add(new javax.swing.JSeparator());
    }

    public int show() {

        return JOptionPane.showConfirmDialog(parent, scrollPane,
                "JasperStarter - Parameter Prompt: " + reportName,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}