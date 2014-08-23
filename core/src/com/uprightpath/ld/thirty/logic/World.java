package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Geo on 8/22/2014.
 */
public class World {
    private Array<Agent> agents = new Array<Agent>();
    private Array<WorldObject> worldObjects = new Array<WorldObject>();
    private Vector2 gravity;
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

    public void applyPhysics() {
        Agent agent;
        WorldObject worldObject;
        Polygon polygon;
        for (int i = 0; i < agents.size; i++) {
            agent = agents.get(i);
            for (int j = 0; j < worldObjects.size; j++) {
                worldObject = worldObjects.get(j);
                if (worldObject.canCollide(agent.getPolygon())) {
                    for (int k = 0; k < worldObject.getPolygons().size; k++) {
                        polygon = worldObject.getPolygons().get(k);
                        if (Intersector.overlapConvexPolygons(agent.getPolygon(), polygon, mtv)) {
                            agent.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                            if (mtv.depth * mtv.normal.x != 0) {
                                agent.getDelta().x = 0;
                            }
                            if (mtv.depth * mtv.normal.y != 0) {
                                agent.getDelta().y = 0;
                            }
                            agent.setFalling(mtv.depth * mtv.normal.y > 0);
                        }
                    }
                }
            }
        }
    }
}
