package com.github.obsidianarch.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A wall in the game.
 */
public class Wall
{

    //
    // Fields
    //

    /** The color of all walls. */
    public static final Color color = new Color( 1, 1, 1, 1 );

    /** The wall's body. */
    public final Body body;

    /** The size of the body. */
    public final Vector2 size;

    //
    // Constructors
    //

    /**
     * Creates a wall with the given positions.
     *
     * @param x
     *          The x coordinate of the wall.
     * @param y
     *          The y coordinate of the wall.
     * @param width
     *          The width of the wall.
     * @param height
     *          The height of the wall.
     * @param world
     *          The world in which the body will be created.
     */
    public Wall( float x, float y, float width, float height, World world )
    {
        BodyDef bodyDef = new BodyDef(); // create the definition
        bodyDef.position.set( x, y ); // set the position of the wall

        body = world.createBody( bodyDef ); // create the body using the definition

        PolygonShape box = new PolygonShape(); // a rectangle is a polygon, that's what the shape will be
        box.setAsBox( width / 2, height / 2, new Vector2( width / 2, height / 2  ), 0 ); // set the size of the box
        body.createFixture( box, 1.0f ); // create the body with no density
        box.dispose(); // dispose of our shape

        size = new Vector2( width, height ); // set the width and height of the box
    }

    //
    // Actions
    //

    /**
     * Renders the wall.
     *
     * @param renderer
     *          The renderer with which the wall will be renderered.
     */
    public void render( ShapeRenderer renderer )
    {
        renderer.setColor( color );

        Vector2 position = body.getWorldCenter();
        renderer.box( position.x, position.y, 0f, size.x, size.y, 1f );
    }

}
