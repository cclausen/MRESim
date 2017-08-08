/*
 *     Copyright 2010, 2015, 2017 Julian de Hoog (julian@dehoog.ca),
 *     Victor Spirin (victor.spirin@cs.ox.ac.uk),
 *     Christian Clausen (christian.clausen@uni-bremen.de
 *
 *     This file is part of MRESim 2.3, a simulator for testing the behaviour
 *     of multiple robots exploring unknown environments.
 *
 *     If you use MRESim, I would appreciate an acknowledgement and/or a citation
 *     of our papers:
 *
 *     @inproceedings{deHoog2009,
 *         title = "Role-Based Autonomous Multi-Robot Exploration",
 *         author = "Julian de Hoog, Stephen Cameron and Arnoud Visser",
 *         year = "2009",
 *         booktitle =
 *     "International Conference on Advanced Cognitive Technologies and Applications (COGNITIVE)",
 *         location = "Athens, Greece",
 *         month = "November",
 *     }
 *
 *     @incollection{spirin2015mresim,
 *       title={MRESim, a Multi-robot Exploration Simulator for the Rescue Simulation League},
 *       author={Spirin, Victor and de Hoog, Julian and Visser, Arnoud and Cameron, Stephen},
 *       booktitle={RoboCup 2014: Robot World Cup XVIII},
 *       pages={106--117},
 *       year={2015},
 *       publisher={Springer}
 *     }
 *
 *     MRESim is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     MRESim is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with MRESim.
 *     If not, see <http://www.gnu.org/licenses/>.
 */
package gui;

import config.SimulatorConfig;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;

/**
 *
 * @author julh
 */
public class ExplorationDialog extends javax.swing.JDialog {

    SimulatorConfig simConfig;

    String logFilename;
    String batchFilename;

    /**
     * Creates new form CommunicationDialog
     *
     * @param parent
     * @param modal
     * @param sc
     */
    public ExplorationDialog(java.awt.Frame parent, boolean modal, SimulatorConfig sc) {
        super(parent, modal);

        simConfig = sc;

        initComponents();

        // Select current model
        for (Enumeration e = groupExplorationAlgorithm.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel().getMnemonic() - 48 == simConfig.getExpAlgorithm().ordinal()) {
                b.setSelected(true);
            }
        }

        // Select current model
        for (Enumeration e = subgroupFrontierBasedType.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel().getMnemonic() - 48 == simConfig.getFrontierAlgorithm().ordinal()) {
                b.setSelected(true);
            }
        }
        // Select current model
        for (Enumeration e = subgroupRelay.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel().getMnemonic() - 48 == simConfig.getRelayAlgorithm().ordinal()) {
                b.setSelected(true);
            }
        }

        logFilename = simConfig.getRunFromLogFilename();
        batchFilename = simConfig.getBatchFilename();
        labelLog.setText(logFilename);
        labelBatch.setText(batchFilename);
        radioLogActionPerformed(null);

        radioLeaderFollowerActionPerformed(null);

        radioFrontierActionPerformed(null);

        checkboxRendezvous.setSelected(simConfig.useImprovedRendezvous());
        checkboxReplanning.setSelected(simConfig.replanningAllowed());
        checkboxRoleswitch.setSelected(simConfig.roleSwitchAllowed());
        checkboxRoleswitchCriterion.setSelected(simConfig.strictRoleSwitch());
        //checkboxRVCommRange.setSelected(simConfig.RVCommRangeEnabled());
        checkboxRVThroughWalls.setSelected(simConfig.RVThroughWallsEnabled());
        checkBoxUseComStations.setSelected(simConfig.useComStations());
        jTextRatio.setText(String.valueOf(simConfig.TARGET_INFO_RATIO));
        radioRoleActionPerformed(null);

        // Let window X being clicked be handled by windowClosing method
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // center on screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - getSize().width) / 2;
        int y = (dim.height - getSize().height) / 2;
        setLocation(x, y);

        this.addWindowListener(windowListener);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupExplorationAlgorithm = new javax.swing.ButtonGroup();
        subgroupFrontierBasedType = new javax.swing.ButtonGroup();
        subgroupRelay = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        radioLog = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        buttonLog = new javax.swing.JButton();
        labelLog = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        radioLeaderFollower = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        radioFrontier = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        radioFrontierType2 = new javax.swing.JRadioButton();
        radioFrontierType3 = new javax.swing.JRadioButton();
        radioFrontierType4 = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jTextRatio = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        radioRole = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        checkboxRoleswitch = new javax.swing.JCheckBox();
        checkboxRendezvous = new javax.swing.JCheckBox();
        checkboxReplanning = new javax.swing.JCheckBox();
        checkboxRoleswitchCriterion = new javax.swing.JCheckBox();
        checkboxRVThroughWalls = new javax.swing.JCheckBox();
        buttonCancel = new javax.swing.JButton();
        buttonOK = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        radioBatch = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        buttonBatch = new javax.swing.JButton();
        labelBatch = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        radioTesting = new javax.swing.JRadioButton();
        checkBoxUseComStations = new javax.swing.JCheckBox();
        comStationDropChanceField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        radioWallFollow = new javax.swing.JRadioButton();
        radioRandom = new javax.swing.JRadioButton();
        radioRelay1 = new javax.swing.JRadioButton();
        radioRelay2 = new javax.swing.JRadioButton();
        radioRelay3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exploration");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Please choose the exploration algorithm:");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioLog);
        radioLog.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        radioLog.setMnemonic('1');
        radioLog.setText("Run From Log");
        radioLog.setFocusable(false);
        radioLog.setNextFocusableComponent(buttonOK);
        radioLog.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioLogStateChanged(evt);
            }
        });
        radioLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioLogActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Repeats a previous run.");
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea3.setOpaque(false);
        jScrollPane3.setViewportView(jTextArea3);

        buttonLog.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        buttonLog.setText("Change");
        buttonLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogActionPerformed(evt);
            }
        });

        labelLog.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        labelLog.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelLog.setText("Filename");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(radioLog)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLog)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioLog)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLog)
                    .addComponent(labelLog))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioLeaderFollower);
        radioLeaderFollower.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        radioLeaderFollower.setMnemonic('2');
        radioLeaderFollower.setText("Leader-Follower");
        radioLeaderFollower.setFocusable(false);
        radioLeaderFollower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioLeaderFollowerActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("First robot leads, the remaining ones follow, making sure that there is always a multi-hop connection to the base station (similar to Nguyen2004).");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea2.setOpaque(false);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioLeaderFollower)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioLeaderFollower))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioFrontier);
        radioFrontier.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        radioFrontier.setMnemonic('3');
        radioFrontier.setText("Frontier-based");
        radioFrontier.setFocusable(false);
        radioFrontier.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioFrontierStateChanged(evt);
            }
        });
        radioFrontier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFrontierActionPerformed(evt);
            }
        });

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea4.setEditable(false);
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Multi-robot frontier exploration using Yamauchi's frontier based method.");
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea4.setOpaque(false);
        jScrollPane4.setViewportView(jTextArea4);

        subgroupFrontierBasedType.add(radioFrontierType2);
        radioFrontierType2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioFrontierType2.setMnemonic('1');
        radioFrontierType2.setSelected(true);
        radioFrontierType2.setText("Periodic return");
        radioFrontierType2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFrontierType2ActionPerformed(evt);
            }
        });

        subgroupFrontierBasedType.add(radioFrontierType3);
        radioFrontierType3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioFrontierType3.setMnemonic('2');
        radioFrontierType3.setText("Return when done");
        radioFrontierType3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFrontierType3ActionPerformed(evt);
            }
        });

        subgroupFrontierBasedType.add(radioFrontierType4);
        radioFrontierType4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioFrontierType4.setMnemonic('3');
        radioFrontierType4.setText("Utility based return with ratio:");
        radioFrontierType4.setToolTipText("");
        radioFrontierType4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFrontierType4ActionPerformed(evt);
            }
        });

        jScrollPane5.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea5.setEditable(false);
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Uses approach described in V. Spirin, S. Cameron, and J. de Hoog: Time Preference for Information in Multi-Agent Exploration with Limited Communication (TAROS 2013).");
        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea5.setOpaque(false);
        jScrollPane5.setViewportView(jTextArea5);

        jTextRatio.setText("0.9");
        jTextRatio.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioFrontier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(radioFrontierType3))
                            .addContainerGap())
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(radioFrontierType2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(radioFrontierType4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(46, 46, 46)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addComponent(radioFrontier))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addComponent(radioFrontierType2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioFrontierType3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioFrontierType4)
                    .addComponent(jTextRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioRole);
        radioRole.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        radioRole.setMnemonic('4');
        radioRole.setText("Role-Based");
        radioRole.setFocusable(false);
        radioRole.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        radioRole.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioRoleStateChanged(evt);
            }
        });
        radioRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRoleActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Uses approach described in J. de Hoog, S. Cameron and A. Visser: Selection of Rendezvous Points for Multi-Robot Exploration in Dynamic Environments (AAMAS 2010).");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        checkboxRoleswitch.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        checkboxRoleswitch.setText("Allow dynamic role switches");
        checkboxRoleswitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoleswitchActionPerformed(evt);
            }
        });

        checkboxRendezvous.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        checkboxRendezvous.setText("Use improved rendezvous point calculation");
        checkboxRendezvous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRendezvousActionPerformed(evt);
            }
        });

        checkboxReplanning.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        checkboxReplanning.setText("Allow replanning in dynamic environments");
        checkboxReplanning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxReplanningActionPerformed(evt);
            }
        });

        checkboxRoleswitchCriterion.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        checkboxRoleswitchCriterion.setText("Use strict role switch criterion\n(likely doesn't work: check code)");
        checkboxRoleswitchCriterion.setToolTipText("");
        checkboxRoleswitchCriterion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRoleswitchCriterionActionPerformed(evt);
            }
        });

        checkboxRVThroughWalls.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        checkboxRVThroughWalls.setText("Attempt RV through walls");
        checkboxRVThroughWalls.setToolTipText("");
        checkboxRVThroughWalls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxRVThroughWallsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkboxRendezvous)
                    .addComponent(checkboxReplanning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkboxRVThroughWalls)
                    .addComponent(checkboxRoleswitch)
                    .addComponent(checkboxRoleswitchCriterion))
                .addGap(12, 60, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioRole)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxRendezvous)
                    .addComponent(checkboxRoleswitch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxReplanning)
                    .addComponent(checkboxRoleswitchCriterion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkboxRVThroughWalls))
        );

        buttonCancel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        buttonCancel.setText("Cancel");
        buttonCancel.setDefaultCapable(false);
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonOK.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        buttonOK.setText("OK");
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioBatch);
        radioBatch.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        radioBatch.setMnemonic('0');
        radioBatch.setText("Run batch");
        radioBatch.setFocusable(false);
        radioBatch.setNextFocusableComponent(buttonOK);
        radioBatch.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioBatchStateChanged(evt);
            }
        });
        radioBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBatchActionPerformed(evt);
            }
        });

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTextArea6.setEditable(false);
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Runs a JSON batch script");
        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jTextArea6.setOpaque(false);
        jScrollPane6.setViewportView(jTextArea6);

        buttonBatch.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        buttonBatch.setText("Change");
        buttonBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBatchActionPerformed(evt);
            }
        });

        labelBatch.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        labelBatch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelBatch.setText("Filename");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(radioBatch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(labelBatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonBatch)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioBatch)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBatch)
                    .addComponent(labelBatch))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupExplorationAlgorithm.add(radioTesting);
        radioTesting.setMnemonic('5');
        radioTesting.setText("Testing");

        checkBoxUseComStations.setText("Use ComStations");

        comStationDropChanceField.setText("0.02");
        comStationDropChanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStationDropChanceFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("ComStation-DropChance");

        groupExplorationAlgorithm.add(radioWallFollow);
        radioWallFollow.setMnemonic('7');
        radioWallFollow.setText("WallFollow");

        groupExplorationAlgorithm.add(radioRandom);
        radioRandom.setMnemonic('6');
        radioRandom.setText("Random");
        radioRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRandomActionPerformed(evt);
            }
        });

        subgroupRelay.add(radioRelay1);
        radioRelay1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioRelay1.setMnemonic('0');
        radioRelay1.setText("KeyPoints");
        radioRelay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRelay1ActionPerformed(evt);
            }
        });

        subgroupRelay.add(radioRelay2);
        radioRelay2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioRelay2.setMnemonic('1');
        radioRelay2.setText("RangeBorder");
        radioRelay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRelay2ActionPerformed(evt);
            }
        });

        subgroupRelay.add(radioRelay3);
        radioRelay3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        radioRelay3.setMnemonic('2');
        radioRelay3.setText("Random");
        radioRelay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRelay3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioWallFollow)
                    .addComponent(radioTesting)
                    .addComponent(radioRandom))
                .addGap(90, 90, 90)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBoxUseComStations)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comStationDropChanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioRelay1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioRelay2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioRelay3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkBoxUseComStations)
                            .addComponent(radioRelay1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comStationDropChanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(radioRelay2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioRelay3))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(radioTesting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioRandom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioWallFollow)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addComponent(buttonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonOK))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(640, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioLogActionPerformed
        if (radioLog.isSelected()) {
            labelLog.setEnabled(true);
            buttonLog.setEnabled(true);
        } else {
            labelLog.setEnabled(false);
            buttonLog.setEnabled(false);
        }
}//GEN-LAST:event_radioLogActionPerformed

    private void radioLeaderFollowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioLeaderFollowerActionPerformed

}//GEN-LAST:event_radioLeaderFollowerActionPerformed

    private void radioRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRoleActionPerformed
        if (radioRole.isSelected()) {
            checkboxRendezvous.setEnabled(true);
            checkboxReplanning.setEnabled(true);
            checkboxRoleswitch.setEnabled(true);
            checkboxRoleswitchCriterion.setEnabled(true);
            checkboxRVThroughWalls.setEnabled(true);
        } else {
            checkboxRendezvous.setEnabled(false);
            checkboxReplanning.setEnabled(false);
            checkboxRoleswitch.setEnabled(false);
            checkboxRoleswitchCriterion.setEnabled(false);
            checkboxRVThroughWalls.setEnabled(false);
        }
}//GEN-LAST:event_radioRoleActionPerformed

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        //Since developed in netbeans designer, this returns ASCII character, we want integers starting at 0.
        simConfig.setExpAlgorithm(groupExplorationAlgorithm.getSelection().getMnemonic() - 48);
        simConfig.setBatchFilename(batchFilename);
        simConfig.setFrontierAlgorithm(subgroupFrontierBasedType.getSelection().getMnemonic() - 48 - 1); // extra -1 because one option of frontierbased is gone and the nmonic starts at 1 now!
        simConfig.setRelayAlgorithm(subgroupRelay.getSelection().getMnemonic() - 48);
        simConfig.setRunFromLogFilename(logFilename);
        simConfig.setUseImprovedRendezvous(checkboxRendezvous.isSelected());
        simConfig.setReplanningAllowed(checkboxReplanning.isSelected());
        simConfig.setRoleSwitchAllowed(checkboxRoleswitch.isSelected());
        simConfig.setStrictRoleSwitch(checkboxRoleswitchCriterion.isSelected());
        simConfig.setUseComStations(checkBoxUseComStations.isSelected());
        simConfig.setComStationDropChance(Double.parseDouble(comStationDropChanceField.getText()));
        //simConfig.setRVCommRangeEnabled(checkboxRVCommRange.isSelected());
        simConfig.setRVThroughWallsEnabled(checkboxRVThroughWalls.isSelected());
        simConfig.TARGET_INFO_RATIO = Double.parseDouble(jTextRatio.getText());
        this.dispose();
}//GEN-LAST:event_buttonOKActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        this.dispose();
}//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/logs/"));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logFilename = file.getPath();
            labelLog.setText(logFilename);
        }
}//GEN-LAST:event_buttonLogActionPerformed

    private void checkboxRoleswitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRoleswitchActionPerformed

    }//GEN-LAST:event_checkboxRoleswitchActionPerformed

    private void radioRoleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioRoleStateChanged
        radioRoleActionPerformed(null);
    }//GEN-LAST:event_radioRoleStateChanged

    private void radioLogStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioLogStateChanged
        radioLogActionPerformed(null);
    }//GEN-LAST:event_radioLogStateChanged

    private void checkboxRendezvousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRendezvousActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkboxRendezvousActionPerformed

    private void checkboxReplanningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxReplanningActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkboxReplanningActionPerformed

    private void checkboxRoleswitchCriterionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRoleswitchCriterionActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkboxRoleswitchCriterionActionPerformed

    private void radioFrontierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFrontierActionPerformed
        if (radioFrontier.isSelected()) {
            radioFrontierType2.setEnabled(true);
            radioFrontierType3.setEnabled(true);
            radioFrontierType4.setEnabled(true);
            radioRelay1.setEnabled(false);
            radioRelay2.setEnabled(false);
            radioRelay3.setEnabled(false);
        } else if (radioTesting.isSelected()) {
            radioFrontierType2.setEnabled(false);
            radioFrontierType3.setEnabled(false);
            radioFrontierType4.setEnabled(false);
            radioRelay1.setEnabled(true);
            radioRelay2.setEnabled(true);
            radioRelay3.setEnabled(true);
        } else {
        }
}//GEN-LAST:event_radioFrontierActionPerformed

    private void radioRelay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRelay1ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_radioRelay1ActionPerformed

    private void radioFrontierType2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFrontierType2ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_radioFrontierType2ActionPerformed

    private void radioFrontierType3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFrontierType3ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_radioFrontierType3ActionPerformed

    private void radioFrontierStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioFrontierStateChanged
        radioFrontierActionPerformed(null);
    }//GEN-LAST:event_radioFrontierStateChanged

    private void radioFrontierType4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFrontierType4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioFrontierType4ActionPerformed

    private void checkboxRVThroughWallsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxRVThroughWallsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkboxRVThroughWallsActionPerformed

    private void radioBatchStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioBatchStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBatchStateChanged

    private void radioBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBatchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBatchActionPerformed

    private void buttonBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBatchActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/scripts/"));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            batchFilename = file.getPath();
            labelBatch.setText(batchFilename);
        }
    }//GEN-LAST:event_buttonBatchActionPerformed

    private void comStationDropChanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStationDropChanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comStationDropChanceFieldActionPerformed

    private void radioRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRandomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioRandomActionPerformed

    private void radioRelay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRelay2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioRelay2ActionPerformed

    private void radioRelay3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRelay3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioRelay3ActionPerformed

    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent w) {
            dispose();
        }
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBatch;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonLog;
    private javax.swing.JButton buttonOK;
    private javax.swing.JCheckBox checkBoxUseComStations;
    private javax.swing.JCheckBox checkboxRVThroughWalls;
    private javax.swing.JCheckBox checkboxRendezvous;
    private javax.swing.JCheckBox checkboxReplanning;
    private javax.swing.JCheckBox checkboxRoleswitch;
    private javax.swing.JCheckBox checkboxRoleswitchCriterion;
    private javax.swing.JTextField comStationDropChanceField;
    private javax.swing.ButtonGroup groupExplorationAlgorithm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextField jTextRatio;
    private javax.swing.JLabel labelBatch;
    private javax.swing.JLabel labelLog;
    private javax.swing.JRadioButton radioBatch;
    private javax.swing.JRadioButton radioFrontier;
    private javax.swing.JRadioButton radioFrontierType2;
    private javax.swing.JRadioButton radioFrontierType3;
    private javax.swing.JRadioButton radioFrontierType4;
    private javax.swing.JRadioButton radioLeaderFollower;
    private javax.swing.JRadioButton radioLog;
    private javax.swing.JRadioButton radioRandom;
    private javax.swing.JRadioButton radioRelay1;
    private javax.swing.JRadioButton radioRelay2;
    private javax.swing.JRadioButton radioRelay3;
    private javax.swing.JRadioButton radioRole;
    private javax.swing.JRadioButton radioTesting;
    private javax.swing.JRadioButton radioWallFollow;
    private javax.swing.ButtonGroup subgroupFrontierBasedType;
    private javax.swing.ButtonGroup subgroupRelay;
    // End of variables declaration//GEN-END:variables

}
