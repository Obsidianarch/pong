package com.github.obsidianarch.pong;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Random;

/**
 * The ball in our game of pong.
 */
public class Ball
{

    //
    // Fields
    //

    /** The radius of the ball. */
    public final float radius = 6.0f;

    /** The body of the pong ball. */
    public Body body;

    //
    // Constructors
    //

    /**
     * Creates the PongBall with all the Box2D settings it needs in
     * order to function correctly.
     *
     * @param world
     *          The world in which the pong ball will be created.
     */
    public Ball( World world )
    {
        generateBody( world );
    }

    //
    // Actions
    //

    /**
     * Renders the PongBall.
     *  @param  renderer
     *              The renderer used to draw the pong ball.
     */
    public void render( ShapeRenderer renderer )
    {
        Vector2 position = body.getWorldCenter();

        renderer.setColor( 1, 1, 1, 1 ); // solid white
        renderer.circle( position.x, position.y, radius );
    }

    /**
     * Generates a new body for the ball to use.
     *
     * @param world
     *          The world in which the pong ball will be created.
     */
    private void generateBody( World world )
    {
        float yPos = ( float ) ( ( Math.random() * 400 ) + 50 );

        BodyDef bodyDef = new BodyDef(); // create the body definition
        bodyDef.type = BodyDef.BodyType.DynamicBody; // the ball must be affected by all types of bodies
        bodyDef.position.set( 375, yPos ); // set the ball somewhat in the center of the screen

        body = world.createBody( bodyDef ); // create our body using our body definition

        CircleShape circle = new CircleShape(); // create the circle shape
        circle.setRadius( radius ); // set it's radius

        FixtureDef fixtureDef = new FixtureDef(); // create the fixture definition
        fixtureDef.shape = circle; // set the shape of the definition
        fixtureDef.density = 0.5f; // set the density
        fixtureDef.friction = 0.0f; // set the friction (none because it shouldn't lose any speed in this game)
        fixtureDef.restitution = 1.0f; // it bounces off of everything perfectly

        body.createFixture( fixtureDef ); // create the fixture and attach it to the pong ball

        circle.dispose(); // dispose of all shapes when we are done with them

        // choose random directions for the ball to move in
        int dX = Math.random() >= 0.5 ? 1 : -1;
        int dY = Math.random() >= 0.5 ? 1 : -1;

        body.setLinearVelocity( 200.0f * dX, 200.0f * dY );
    }

    /**
     * Destroys the current body and creates a new one for the ball.
     *
     * @param world
     *          The world in which the pong ball will be created.
     */
    public void reset( World world )
    {
        world.destroyBody( body );
        generateBody( world );
    }

}
