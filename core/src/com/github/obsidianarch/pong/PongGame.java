package com.github.obsidianarch.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.Input.Keys;

import java.util.ArrayList;

/**
 * Pong! :D
 */
public class PongGame extends ApplicationAdapter
{

    //
    // Fields
    //

    /** The player's score. */
    private int playerScore = 0;

    /** The computer's score. */
    private int computerScore = 0;

    /** True to draw the shapes, false to not. */
    private boolean renderShapes = true;

    /** The camera used in the world */
    private OrthographicCamera camera;

    /** The Box2D world the game takes place in. */
    private World world;

    /** The debug renderer, do not include this in the final release! */
    private Box2DDebugRenderer debugRenderer;

    /** The renderer used to draw the game objects. */
    private ShapeRenderer renderer;

    /** The ball in the game of pong */
    private Ball ball;

    /** The walls in the game. */
    private ArrayList< Wall > walls;

    /** The paddle controlled by the human player. */
    private Paddle humanPaddle;

    /** The paddle controlled by the computer player. */
    private Paddle computerPaddle;

    /** Used to draw text. */
    private SpriteBatch spriteBatch;

    /** Used for drawing text. */
    private BitmapFont font;

    //
    // Actions
    //

    /**
     * Renders the shapes of everything in the game, doesn't use
     * the debug renderer.
     */
    private void renderShapes()
    {
        // render the shapes
        renderer.begin( ShapeRenderer.ShapeType.Filled );
        {
            ball.render( renderer ); // render the ball

            // render all the walls
            for ( Wall wall : walls )
            {
                wall.render( renderer );
            }

            humanPaddle.render( renderer );
            computerPaddle.render( renderer );
        }
        renderer.end();

        // render the scores
        spriteBatch.begin();
        {
            font.draw( spriteBatch, Integer.toString( playerScore ), 188, 475 );
            font.draw( spriteBatch, Integer.toString( computerScore ), 562, 475 );
        }
        spriteBatch.end();
    }

    //
    // Overrides
    //

    @Override
    public void create()
    {
        camera = new OrthographicCamera( 750, 500 ); // create the OrthographicCamera
        camera.position.set( 375, 250, 0 ); // move it so that the center of the screen is at the bottom left corner
        camera.update();

        world = new World( new Vector2( 0, 0 ), true ); // no gravity at all (it's pong)
        debugRenderer = new Box2DDebugRenderer(); // create the debug renderer

        renderer = new ShapeRenderer(); // create our renderer

        ball = new Ball( world ); // create the pong ball

        walls = new ArrayList< Wall >(); // create the list of walls

        // populate the list with the walls
        walls.add( new Wall( 0, 0, 75, 1, world ) ); // the bottom wall
        walls.add( new Wall( 0, 49, 75, 1, world ) ); // the top wall
        walls.add( new Wall( 0, 0, 1, 5, world  ) ); // the bottom left wall
        walls.add( new Wall( 0, 45, 1, 5, world ) ); // the top left wall
        walls.add( new Wall( 74, 0, 1, 5, world ) ); // the bottom right wall
        walls.add( new Wall( 74, 45, 1, 5, world ) ); // the top right wall

        humanPaddle = new Paddle( 0, 7.5f, world ); // set up the human paddle
        computerPaddle = new Paddle( 74, 7.5f, world ); // set up the computer paddle

        spriteBatch = new SpriteBatch(); // create the sprite batch
        font = new BitmapFont(); // create the bitmap font
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT ); // clear the previous frame

        // only render using one method
        if ( renderShapes )
        {
            renderShapes(); // render using the filled rectangles
        }
        else
        {
            debugRenderer.render( world, camera.combined ); // render bodies and their boundaries
        }

        ComputerPaddleAI.movePaddle( computerPaddle, ball ); // move the computer's paddle

        // move the paddle
        if ( Gdx.input.isKeyPressed( Keys.W ) || Gdx.input.isKeyPressed( Keys.UP ) )
        {
            humanPaddle.moveUp();
        }
        else if ( Gdx.input.isKeyPressed( Keys.S ) || Gdx.input.isKeyPressed( Keys.DOWN ) )
        {
            humanPaddle.moveDown();
        }
        else
        {
            humanPaddle.stopMovement();
        }

        // switch between the rendering methods
        if ( Gdx.input.isKeyPressed( Keys.NUM_1 ) )
        {
            renderShapes = false; // disable rendering the shapes
        }
        else if ( Gdx.input.isKeyPressed( Keys.NUM_2 ) )
        {
            renderShapes = true; // enable rendering the shapes
        }

        // check for scoring
        if ( ball.body.getPosition().x + ( ball.radius / 2 ) < 1 )
        {
            computerScore++;
            ball.reset( world );
        }
        else if ( ball.body.getPosition().x + ( ball.radius / 2 ) > 74 )
        {
            playerScore++;
            ball.reset( world );
        }

        world.step( ( 1/ 60f ), 6, 2 ); // step the world forward
    }

}
