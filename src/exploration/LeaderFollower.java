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
import config.Constants;
import config.SimulatorConfig;
import java.awt.Point;
import java.util.LinkedList;
import path.Path;

/**
 *
 * @author juliandehoog
 */
public class LeaderFollower extends FrontierExploration implements Exploration {

    int TIME_BETWEEN_PLANS = 10;

    public LeaderFollower(RealAgent agent, SimulatorConfig.frontiertype frontierExpType, RealAgent baseStation) {
        super(agent, frontierExpType, baseStation);
    }

    @Override
    public Point takeStep(int timeElapsed) {
        Point nextStep = new Point(agent.getX(), agent.getY());

        // if env reports error, agent may be stuck in front of a wall and the
        // simulator isn't allowing him to go through.  Taking a random step might
        // help.
        // Update:  this state is never really reached but leave in just in case
        if (agent.getEnvError()) {
            System.out.println(agent.toString() + "LeaderFollower: Env reports error, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.setEnvError(false);
            return nextStep;
        }

        switch (agent.getRole()) {
            case Explorer:
                nextStep = takeStep_Explorer(timeElapsed);
                break;
            case Relay:
                nextStep = takeStep_Relay(timeElapsed);
                break;
            default:
                break;
        }

        if (nextStep == null) {
            nextStep = RandomWalk.randomStep(agent);
        }

        return nextStep;
    }

    public Point takeStep_Relay(int timeElapsed) {
        agent.setMissionComplete(true); // to stop scripted runs when all agents complete mission
        Point nextStep;

        // CHECK 0
        // Wait for a few time steps at start (to let Explorer get some moves in).
        if (timeElapsed < 3) {
            System.out.println(agent.toString() + "LeaderFollower: Starting up, staying stationary for now.");
            nextStep = new Point(agent.getX(), agent.getY());
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 1
        // if agent hasn't moved, then he may be stuck in front of a wall and the
        // simulator isn't allowing him to go through.  Taking a random step might
        // help.
        else if (agent.getEnvError()) {
            System.out.println(agent.toString() + "LeaderFollower: No step taken since last timeStep, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.getStats().setTimeSinceLastPlan(0);
            agent.setEnvError(false);
        } // Check 1.5, make sure parent is in range
        else if (!agent.getParentTeammate().isInRange()) {
            agent.getStats().setTimeSinceLastPlan(0);
            return (new Point(agent.getPrevX(), agent.getPrevY()));
        } // CHECK 2
        // Agent isn't stuck.
        // Is it time to replan?
        else if (agent.getStats().getTimeSinceLastPlan() > TIME_BETWEEN_PLANS) {
            nextStep = replanRelay();
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 3
        // Agent isn't stuck, not yet time to replan.
        // Do we have points left in the previously planned path?
        else if (agent.getPath().found && agent.getPath().getPoints().size() >= 2) {
            nextStep = agent.getNextPathPoint();
        } // CHECK 4
        // Agent isn't stuck, not yet time to replan, but we have no points left
        else {
            nextStep = replanRelay();
            agent.getStats().setTimeSinceLastPlan(0);
        }

        // UPDATE
        if (agent.getTeammate(1).isInRange()) {
            agent.getStats().setTimeLastDirectContactCS(1);
        } else {
            agent.getStats().incrementLastDirectContactCS();
        }
        agent.getStats().incrementTimeSinceLastPlan();

        return nextStep;
    }

    public Point replanRelay() {
        Path P2C = agent.calculatePath(agent.getLocation(), agent.getChildTeammate().getLocation(), false);

        if (agent.getLocation().distance(agent.getParentTeammate().getLocation())
                < agent.getCommRange() - 1 * agent.getSpeed()) {
            agent.setPath(P2C);
            return agent.getNextPathPoint();
        } else {
            return (new Point(agent.getPrevX(), agent.getPrevY()));
        }

        // total hack:  for leader-follower, use "parent" value as number of robots between this relay and comstation, including self
        /*if(agent.getChildTeammate().getLocation().distance(agent.getTeammate(1).getLocation()) <=
           (agent.getParent() * agent.getCommRange() - 2*Constants.DEFAULT_SPEED - 20)) {
                agent.setPath(agent.calculatePath(agent.getLocation(), agent.getChildTeammate().getLocation()));
                System.out.println("LeaderFollower: my child is still in my allowed range (" + (agent.getParent() * agent.getCommRange() - 2*Constants.DEFAULT_SPEED) + ")");
        }
        else {
            System.out.println("LeaderFollower: my child is no longer in my allowed range (" + (agent.getParent() * agent.getCommRange() - 2*Constants.DEFAULT_SPEED) + ")");
            double cDist = 1000000;
            Point cPoint = new Point(agent.getX(), agent.getY());
            // find closest point within allowed range that is near parent
            for(int i=agent.getX()-10; i<= agent.getX()+10; i+=10)
                for(int j=agent.getY()-10; j<=agent.getY()+10; j+=10) {
                    if(agent.getOccupancyGrid().locationExists(i, j) &&
                       //agent.getOccupancyGrid().freeSpaceAt(i, j) &&
                      !agent.getOccupancyGrid().obstacleWithinDistance(i, j, Constants.WALL_DISTANCE) &&
                       agent.getTeammate(1).getLocation().distance(new Point(i,j)) < agent.getParent() * agent.getCommRange() - 2*Constants.DEFAULT_SPEED &&
                       //(new Path(agent, new Point(i,j), agent.getTeammate(1).getLocation())).getLength() < agent.getParent() * agent.getCommRange() - 2*Constants.STEP_SIZE &&
                       agent.getChildTeammate().getLocation().distance(new Point(i,j)) < cDist) {
                            cPoint = new Point(i,j);
                            cDist = agent.getChildTeammate().getLocation().distance(cPoint);
                    }
                }
            System.out.println("Chose point at " + cPoint.x + "," + cPoint.y);
            agent.setPath(agent.calculatePath(agent.getLocation(), cPoint));
        }

        if(agent.getPath() == null || agent.getPath().getPoints().size()<2)
            return RandomWalk.randomStep(agent);

        agent.getPath().getPoints().remove(0);
        return agent.getNextPathPoint();*/
    }

    public Point takeStep_Explorer(int timeElapsed) {
        Point nextStep;

        // CHECK 0
        // Take a couple of random steps to start (just to gather some sensor data).
        if (timeElapsed < 3) {
            System.out.println(agent.toString() + "LeaderFollower: Starting up, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 1
        // if agent hasn't moved, then he may be stuck in front of a wall and the
        // simulator isn't allowing him to go through.  Taking a random step might
        // help.
        else if (agent.getEnvError()) {
            System.out.println(agent.toString() + "LeaderFollower: No step taken since last timeStep, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.getStats().setTimeSinceLastPlan(0);
            agent.setEnvError(false);
        } // CHECK 2
        // Agent isn't stuck.
        // Is it time to replan?
        else if (agent.getStats().getTimeSinceLastPlan() % TIME_BETWEEN_PLANS == 0) {
            nextStep = replanExplorer();
            agent.getStats().setTimeSinceLastPlan(0);
        } // CHECK 3
        // Agent isn't stuck, not yet time to replan.
        // Do we have points left in the previously planned path?
        else if (agent.getPath().found && agent.getPath().getPoints().size() >= 2) {
            nextStep = agent.getNextPathPoint();
        } // CHECK 4
        // Agent isn't stuck, not yet time to replan, but we have no points left
        else {
            nextStep = replanExplorer();
            agent.getStats().setTimeSinceLastPlan(0);
        }

        // UPDATE
        if (agent.getTeammate(1).isInRange()) {
            agent.getStats().setTimeLastDirectContactCS(1);
        } else {
            agent.getStats().incrementLastDirectContactCS();
        }
        agent.getStats().incrementTimeSinceLastPlan();

        return nextStep;
    }

    public Point replanExplorer() {
        if (!agent.getParentTeammate().isInRange()) {
            agent.getStats().setTimeSinceLastPlan(0);
            return (new Point(agent.getPrevX(), agent.getPrevY()));
        }
        Point nextStep;

        // Find frontiers
        calculateFrontiers();

        // If no frontiers found, return to ComStation
        if (frontiers.isEmpty()) {
            System.out.println(agent.toString() + "No frontiers found, returning home.");
            agent.setMissionComplete(true);
            agent.setPathToBaseStation();
            nextStep = agent.getNextPathPoint();
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        }

        // If we reach this point, there are frontiers to explore -- find best one
        long realtimeStart = System.currentTimeMillis();
        System.out.println(agent.toString() + "Choosing a frontier ...");

        boolean foundFrontier = (chooseFrontier(false, new LinkedList<Integer>()) != null);

        // If no best frontier could be assigned (can happen e.g. when more robots than frontiers),
        // then take random step.
        if (!foundFrontier) {
            System.out.println(agent.toString() + "No frontier chosen, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        }

        // Note: Path to best frontier has already been set when calculating
        // utility, no need to recalculate
        // If no path could be found, take random step.
        if (agent.getPath() == null || agent.getPath().getPoints() == null || agent.getPath().getPoints().isEmpty()) {
            System.out.println(agent.toString() + "No path found, taking random step.");
            nextStep = RandomWalk.randomStep(agent);
            agent.getStats().setTimeSinceLastPlan(0);
            return nextStep;
        }

        // If we reach this point, we have a path.  Remove the first point
        // since this is the robot itself.
        agent.getPath().getPoints().remove(0);
        agent.getStats().setTimeSinceLastPlan(0);
        System.out.print(Constants.INDENT + "Chose frontier at " + agent.getLastFrontier().getCentre().x + "," + agent.getLastFrontier().getCentre().y + ". ");
        System.out.println("Took " + (System.currentTimeMillis() - realtimeStart) + "ms.");
        return agent.getNextPathPoint();
    }

}
