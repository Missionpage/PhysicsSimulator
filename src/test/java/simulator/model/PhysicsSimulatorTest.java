package simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulator.misc.Vector2D;
import simulator.model.bodies.Body;
import simulator.model.forcelaws.MovingTowardsFixedPoint;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsSimulatorTest {
    MovingTowardsFixedPoint mtfp;
    Body b1, b2;
    PhysicsSimulator physicsSimulator;

    @BeforeEach
    void before() {
        b1 = new Body.Builder().id("b1").velocity(new Vector2D())
                .position(new Vector2D()).mass(10).build();
        b2 = new Body.Builder().id("b2").velocity(new Vector2D())
                .position(new Vector2D(0,2)).mass(5).build();
        mtfp = new MovingTowardsFixedPoint();
        physicsSimulator = new PhysicsSimulator(1, mtfp);
    }

    private void executeInvalidTime() {
        new PhysicsSimulator(-5, mtfp);
    }

    @Test
    void testPhysicsSimulator() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                this::executeInvalidTime);
        assertEquals("realTimePerStep must be > 0", exception.getMessage());
    }

    private void executeAlreadyAddedBody() {
        physicsSimulator.addBody(b1);
    }

    private void executeInvalidBody() {
        physicsSimulator.addBody(null);
    }

    @Test
    void testAddBody() {
        Throwable exception = assertThrows(NullPointerException.class,
                this::executeInvalidBody);
        assertNotEquals(null, exception);
        physicsSimulator.addBody(b1);
        exception = assertThrows(IllegalArgumentException.class,
                this::executeAlreadyAddedBody);
        assertEquals("The body has already been added!", exception.getMessage());
    }

    @Test
    void testAdvance() {
        Vector2D expectedForce = mtfp.getOrigin().minus(b2.getPosition()).unitVector().scale(b2.getMass() * -9.81);
        physicsSimulator.addBody(b2);
        physicsSimulator.advance();
        assertEquals(expectedForce.magnitude(), b2.getForce().magnitude());
    }

    @Test
    void testReset() {
        physicsSimulator.addBody(b1);
        physicsSimulator.addBody(b2);
        physicsSimulator.reset();
        assertDoesNotThrow(() -> physicsSimulator.addBody(b1));
    }

    private void executeInvalidRealTime() {
        physicsSimulator.setRealTimePerStep(-5);
    }

    @Test
    void testSetRealTimePerStep() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                this::executeInvalidRealTime);
        assertEquals("realTimePerStep must be > 0", exception.getMessage());
        assertDoesNotThrow(() -> physicsSimulator.setRealTimePerStep(5.0));
    }

    private void executeInvalidForceLaw() {
        physicsSimulator.setForceLaw(null);
    }

    @Test
    void testSetForceLaw() {
        Throwable exeption = assertThrows(NullPointerException.class,
                this::executeInvalidForceLaw);
        assertDoesNotThrow(() -> physicsSimulator.setForceLaw(mtfp));
    }
}
