package com.github.obsidianarch.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A paddle in the game of pong, only two exist in the game,
 * one controlled by a computer player and one by the human.
 */
public class Paddle
{

    //
    // Fields
    //

    /** The color of the paddle */
    public final Color color = new Color( 1, 1, 1, 1 );

    /** The paddle's physics body. */
    public final Body body;

    /** The size of the paddle. */
    public final Vector2 size;

    //
    // Constructors
    //

    /**
     * Creates a new paddle.
     *
     * @param x
     *          The x coordinate of the paddle, this will never change throughout the game.
     * @param height
     *          The height of the paddle, this will never change throughout the game.
     * @param world
     *          The world in which the paddle will be created.
     */
    public Paddle( float x, float height, World world )
    {
        size = new Vector2( 1, height ); // create the size of the paddle

        BodyDef bodyDef = new BodyDef(); // the definition of the paddle
        bodyDef.type = BodyDef.BodyType.KinematicBody; // paddles are kinematic, controlled by forces and impulses
        bodyDef.position.set( x, ( 50 - height ) / 2 ); // center it at the x position defined

        body = world.createBody( bodyDef ); // create the body

        PolygonShape shape = new PolygonShape(); // this is also a box, so it's a polygon
        shape.setAsBox( size.x / 2, height / 2, new Vector2( size. x / 2, height / 2 ), 0 ); // create the box
        body.createFixture( shape, 1.0f ); // create our body

        shape.dispose(); // dispose of the shape
    }

    //
    // Actions
    //

    /**
     * Renders the paddle using the provided renderer.
     *
     * @param renderer
     *          The renderer with which the paddle will be drawn.
     */
    public void render( ShapeRenderer renderer )
    {
        renderer.setColor( color ); // set the color of the renderer
        Vector2 position = body.getPosition(); // get the position of the paddle

        renderer.box( position.x * 10, position.y * 10, 0f, size.x * 10, size.y * 10, 1f );
    }

    /**
     * Moves the paddle up, if it can.
     */
    public void moveUp()
    {
        if ( getTopPosition() < 45 )
        {
            body.setLinearVelocity( 0, 15f );
        }
        else
        {
            stopMovement();
        }
    }

    /**
     * Moves the paddle up, if it can.
     */
    public void moveDown()
    {
        if ( getBottomPosition() > 5 )
        {
            body.setLinearVelocity( 0, -15f );
        }
        else
        {
            stopMovement();
        }
    }

    /**
     * Stops the paddle's movement.
     */
    public void stopMovement()
    {
        body.setLinearVelocity( 0, 0 );
    }

    //
    // Getters
    //

    /**
     * Gets the top position and adds a number within the buffer range to it.
     * This is for the margin of error in the Computer's AI.
     *
     * @param buffer
     *          The buffer zone for the paddle's top.
     * @return The top position of the paddle.
     */
    public float getTopPosition( float buffer )
    {
        return getTopPosition() + ( ( float ) ( Math.random() * buffer ) );
    }

    /**
     * @return The position of the top of the paddle.
     */
    public float getTopPosition()
    {
        return body.getPosition().y + size.y;
    }

    /**
     * Gets the bottom position and adds a number within the buffer range to it.
     * This is for the margin of error in the Computer's AI.
     *
     * @param buffer
     *          The buffer zone for the paddle's bottom.
     * @return The bottom position of the paddle.
     */
    public float getBottomPosition( float buffer )
    {
        return getBottomPosition() + ( ( float ) ( Math.random() * buffer ) );
    }

    /**
     * @return The position of the bottom of the paddle.
     */
    public float getBottomPosition()
    {
        return body.getPosition().y;
    }

}
