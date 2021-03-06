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
package exploration;

import agents.RealAgent;
import config.RobotConfig.roletype;
import config.SimConstants;
import config.SimulatorConfig;
import exploration.Frontier.FrontierUtility;
import java.awt.Point;
import path.Path;

/**
 *
 * @author juliandehoog
 */
public class LeaderFollower extends FrontierExploration implements Exploration {

    int TIME_BETWEEN_PLANS = 10;
    int frontier_com_failure_counter = 0;
    int frontier_wipe_counter = -1;
    double last_percentage_known = 0;
    boolean backtracking = false;

    public LeaderFollower(RealAgent agent, SimulatorConfig simConfig, RealAgent baseStation) {
        super(agent, simConfig, baseStation);
    }

    @Override
    public Point takeStep(int timeElapsed) {
        Point nextStep = new Point(agent.getX(), agent.getY());

        // CHECK 0
        // Wait for a few time steps at start (to let Explorer get some moves in).
        if (timeElapsed < 3) {
            //System.out.println(agent.toString() + "LeaderFollower: Starting up, staying stationary for now.");
            nextStep = RandomWalk.randomStep(agent, 10);
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 1
        // if agent hasn't moved, then he may be stuck in front of a wall and the
        // simulator isn't allowing him to go through.  Taking a random step might
        // help.
        else if (agent.getEnvError()) {
            //System.out.println(agent.toString() + "LeaderFollower: No step taken since last timeStep, taking random step.");
            nextStep = RandomWalk.randomStep(agent, 4);
            agent.getStats().setTimeSinceLastPlan(0);
            agent.setEnvError(false);
        } // Check 1.5, make sure parent is in range
        else if (agent.getRole().equals(roletype.Relay) && !agent.getParentTeammate().hasCommunicationLink()) {
            //System.out.println(agent.toString() + "LeaderFollower: Out of Comandcenter-range, go to last position.");
            agent.getStats().setTimeSinceLastPlan(0);
            nextStep = replanRelay();
            //nextStep = last_com_point;
            //last_com_point = prelast_com_point;
        } // CHECK 2
        // Agent isn't stuck.
        // Is it time to takeStep_explore?
        else if (agent.getStats().getTimeSinceLastPlan() > TIME_BETWEEN_PLANS || backtracking) {
            //System.out.println(agent.toString() + "LeaderFollower: Timed replanning.");
            if (agent.getRole().equals(roletype.Relay)) {
                nextStep = replanRelay();
            } else if (agent.getRole().equals(roletype.Explorer)) {
                nextStep = replanExplorer();
            }
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 3
        // Agent isn't stuck, not yet time to takeStep_explore.
        // Do we have points left in the previously planned path?
        else if (agent.getPath() != null && agent.getPath().found
                && agent.getPath().getPoints().size() >= 2) {
            //System.out.println(agent.toString() + "LeaderFollower: Just go on.");
            nextStep = agent.getPath().nextPoint();
        } // CHECK 4
        // Agent isn't stuck, not yet time to takeStep_explore, but we have no points left
        else {
            //System.out.println(agent.toString() + "LeaderFollower: Replanning.");
            if (agent.getRole().equals(roletype.Relay)) {
                nextStep = replanRelay();
            } else if (agent.getRole().equals(roletype.Explorer)) {
                nextStep = replanExplorer();
            }
            agent.getStats().setTimeSinceLastPlan(0);
        }

        // UPDATE
        if (agent.getTeammate(1).hasCommunicationLink()) {
            agent.getStats().setTimeLastDirectContactCS(1);
        } else {
            agent.getStats().incrementLastDirectContactCS();
        }
        agent.getStats().incrementTimeSinceLastPlan();

        if (nextStep == null) {
            nextStep = RandomWalk.randomStep(agent, 4);
        }

        return nextStep;
    }

    public Point replanRelay() {

        if (!agent.getParentTeammate().hasCommunicationLink()) {
            Path P2P = agent.calculatePath(agent.getLocation(), agent.getParentTeammate().getLocation(), false, false);
            agent.setPath(P2P);
            backtracking = true;
            return agent.getPath().nextPoint();
        } else {
            Path P2C = agent.calculatePath(agent.getLocation(), agent.getChildTeammate().getLocation(), false, false);
            agent.setPath(P2C);
            backtracking = false;
            return agent.getPath().nextPoint();
        }
        /*if (agent.getLocation().distance(agent.getParentTeammate().getLocation())
                < agent.getCommRange() - 1 * agent.getSpeed()) {
            agent.setPath(P2C);
            return agent.getPath().nextPoint();
        } else {
            return (new Point(agent.getPrevX(), agent.getPrevY()));
        }*/
    }

    public Point replanExplorer() {
        Point nextStep;
        if (!agent.getParentTeammate().hasCommunicationLink()) {
            frontier_com_failure_counter++;
            if (frontier_com_failure_counter > 5) {
                frontier_com_failure_counter = 0;
                frontiers.remove(agent.getFrontier());

            }
            agent.getStats().setTimeSinceLastPlan(0);
            //System.out.println(agent.toString() + "UseComPoint: " + last_com_point + " now: " + agent.getLocation());
            Path P2P = agent.calculatePath(agent.getLocation(), agent.getParentTeammate().getLocation(), false, false);
            agent.setPath(P2P);
            backtracking = true;
            return agent.getPath().nextPoint();
        }
        backtracking = false;

        // Find frontiers
        if (frontiers.isEmpty()) {
            calculateFrontiers();
            if (last_percentage_known != agent.getStats().getPercentageKnown()) {
                frontier_wipe_counter = 0;
            }
            frontier_wipe_counter++;
        }
        last_percentage_known = agent.getStats().getPercentageKnown();

        // If no frontiers found, return to ComStation
        if (frontiers.isEmpty() || frontier_wipe_counter > 5 || (agent.getStats().getPercentageKnown() >= SimConstants.TERRITORY_PERCENT_EXPLORED_GOAL)) {
            //System.out.println(agent.toString() + "No frontiers found, returning home.");
            agent.setMissionComplete(true);
            agent.setPathToBaseStation(false);
            nextStep = agent.getPath().nextPoint();
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        }

        // If we reach this point, there are frontiers to explore -- find best one
        long realtimeStart = System.currentTimeMillis();
        //System.out.println(agent.toString() + "Choosing a frontier ...");

        FrontierUtility frontierUtil = chooseFrontier(true);

        // If no best frontier could be assigned (can happen e.g. when more robots than frontiers),
        // then take random step.
        if (frontierUtil == null) {
            //System.out.println(agent.toString() + "No frontier chosen, taking random step.");
            nextStep = RandomWalk.randomStep(agent, 4);
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        } else {
            agent.setFrontier(frontierUtil.getFrontier());
            agent.setPath(frontierUtil.getPath(agent));

        }

        // Note: Path to best frontier has already been set when calculating
        // utility, no need to recalculate
        // If no path could be found, take random step.
        if (agent.getPath() == null || agent.getPath().getPoints() == null || agent.getPath().getPoints().isEmpty()) {
            //System.out.println(agent.toString() + "No path found, taking random step.");
            nextStep = RandomWalk.randomStep(agent, 4);
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        }

        // If we reach this point, we have a path.  Remove the first point
        // since this is the robot itself.
        //agent.getPath().getPoints().remove(0);
        agent.getStats().setTimeSinceLastPlan(0);
        //System.out.print(SimConstants.INDENT + "Chose frontier at " + agent.getLastFrontier().getCentre().x + "," + agent.getLastFrontier().getCentre().y + ". ");
        //System.out.println("Took " + (System.currentTimeMillis() - realtimeStart) + "ms.");
        return agent.getPath().nextPoint();
    }

}
