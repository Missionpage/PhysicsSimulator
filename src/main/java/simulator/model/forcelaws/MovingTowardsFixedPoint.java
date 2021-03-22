package simulator.model.forcelaws;

import simulator.misc.Vector2D;
import simulator.model.bodies.FluentBuilder.Body;

import java.util.List;
import java.util.stream.Collectors;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double g = 9.81;
    private Vector2D origin;

    public MovingTowardsFixedPoint() {
        origin = new Vector2D();
    }

    public MovingTowardsFixedPoint(Vector2D point, double g) {
        origin = point;
        this.g = g;
    }

    public double getG() {
        return g;
    }

    public Vector2D getOrigin() {
        return origin;
    }

    @Override
    public void apply(List<? extends Body> bs) {
        List<Body> bodies = bs.stream().filter(body -> body.getMass() > 0).collect(Collectors.toList());
        Vector2D force;

        for (Body b : bodies) {
            force = calculateForceTowardsOrigin(b);
            b.addForce(force);
        }

        // Reset force for no mass bodies:
        bs.stream().filter(body -> body.getMass() <= 0).forEach(Body::resetForce);
    }

    private Vector2D calculateForceTowardsOrigin(Body bi) {
        Vector2D direction = bi.getPosition().minus(origin);
        return direction.scale(-g * bi.getMass());
    }
}
