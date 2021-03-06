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

package agents;

import config.RobotConfig;
import config.RobotConfig.roletype;
import config.SimConstants;
import environment.OccupancyGrid;
import exploration.rendezvous.Rendezvous;
import exploration.rendezvous.RendezvousAgentData;
import java.awt.Point;

/**
 * The TeammateAgent class is used to store the knowledge of an agent about their teammates. Fields
 * in this class represent what the agent can know about their teammates.
 *
 * @author julh
 */
public class TeammateAgent extends Agent {

    int timeLastCentralCommand;
    /* units of time elapsed since command
                                     received from ComStation */
    int lastContactAreaKnown;
    private boolean communicationLink;
    private int directComLink;
    private boolean baseComLink;

    private int timeSinceLastComm;
    OccupancyGrid occGrid;
    private Rendezvous childRendezvous;
    private Rendezvous parentRendezvous;
    private Point frontierCentre;
    private double pathLength;
    int relayID;
    private int newInfo;

    private RendezvousAgentData rendezvousAgentData;
    private ComStation reference;

    public TeammateAgent(RobotConfig robot) {
        super(robot);

        communicationLink = false;
        timeSinceLastComm = 0;
        pathLength = 0;
        rendezvousAgentData = new RendezvousAgentData(this);
        directComLink = 0;
    }

    public TeammateAgent(TeammateAgent toCopy) {
        super(toCopy.extractConfig());
        this.childRendezvous = toCopy.childRendezvous;
        this.parentRendezvous = toCopy.parentRendezvous;
        this.timeLastCentralCommand = toCopy.timeLastCentralCommand;
        this.lastContactAreaKnown = toCopy.lastContactAreaKnown;
        this.rendezvousAgentData = new RendezvousAgentData(toCopy.rendezvousAgentData);
        this.reference = toCopy.reference;
    }

    public int getTimeLastCentralCommand() {
        return this.timeLastCentralCommand;
    }

    public void setTimeLastCentralCommand(int t) {
        this.timeLastCentralCommand = t;
    }

    public int getTimeSinceLastComm() {
        return this.timeSinceLastComm;
    }

    public void setTimeSinceLastComm(int t) {
        this.timeSinceLastComm = t;
    }

    public int getDirectComLink() {
        return directComLink;
    }

    public void setDirectComLink(int r) {
        directComLink = r;
    }

    public boolean hasCommunicationLink() {
        return communicationLink;
    }

    public boolean hasBaseComLink() {
        return baseComLink;
    }

    public void setBaseComLink(boolean baseComLink) {
        this.baseComLink = baseComLink;
    }

    public boolean isInHandoverRange(Agent agent) {
        return (distanceTo(agent) < SimConstants.HANDOVER_RANGE);
    }

    public void setCommunicationLink(boolean r) {
        communicationLink = r;
    }

    public OccupancyGrid getOccupancyGrid() {
        return occGrid;
    }

    public void setOccupancyGrid(OccupancyGrid og) {
        this.occGrid = og;
    }

    public TeammateAgent copy() {
        return new TeammateAgent(this);
    }

    public boolean isExplorer() {
        return role.equals(roletype.Explorer);
    }

    public boolean isStationary() {
        return role.equals(roletype.RelayStation) || role.equals(roletype.BaseStation);
    }

    public void setExplorer() {
        role = roletype.Explorer;
    }

    public Point getFrontierCentre() {
        return frontierCentre;
    }

    public void setFrontierCentre(Point frontierCentre) {
        this.frontierCentre = frontierCentre;
    }

    public double getPathLength() {
        return pathLength;
    }

    public void setPathLength(double lgth) {
        pathLength = lgth;
    }

    public RendezvousAgentData getRendezvousAgentData() {
        return rendezvousAgentData;
    }

    public void setLastContactAreaKnown(int lastContactArea) {
        lastContactAreaKnown = lastContactArea;
    }

    public int getLastContactAreaKnown() {
        return lastContactAreaKnown;
    }

    public void setRelayID(int relayID) {
        this.relayID = relayID;
    }

    public void setNewInfo(int newInfo) {
        this.newInfo = newInfo;
    }

    public int getNewInfo() {
        return this.newInfo;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Number: " + getRobotNumber() + ", ID: " + getID();
    }

    @Override
    public Point takeStep(int timeElapsed) {
        throw new UnsupportedOperationException("TeamMates should not be moved!");
    }

    @Override
    public void writeStep(Point nextLoc, double[] sensorData, boolean updateSensorData) {
        throw new UnsupportedOperationException("TeamMates should not be moved!");
    }

    @Override
    public void flush() {

    }

    /**
     * Returns the reference to the real COmStation, DO NOT USE FOR ANYTHING BUT LIFTING
     * COMSTATIONS! This Method breaks the information-gap!
     *
     * @return the COmStation this Teammate represents
     */
    public ComStation getReference() {
        return this.reference;
    }

    /**
     * Sets the reference to the real agent
     *
     * @param reference
     */
    public void setReference(ComStation reference) {
        this.reference = reference;
    }

    private boolean insideMethod(String methodName) {
        for (StackTraceElement stackTrace : Thread.currentThread().getStackTrace()) {
            if (stackTrace.getMethodName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}
