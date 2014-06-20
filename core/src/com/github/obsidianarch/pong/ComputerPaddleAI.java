package com.github.obsidianarch.pong;

/**
 * The rudimentary AI which controls the second paddle.
 */
public class ComputerPaddleAI
{

    //
    // Fields
    //

    private static long lastDirectionChange = 0L;

    //
    // Actions
    //

    /**
     * Moves the paddle.
     *
     * @param paddle
     *          The paddle to control.
     * @param ball
     *          The ball to hit.
     */
    public static void movePaddle( Paddle paddle, Ball ball )
    {
        // the ball is on the opposite side of the net OR the ball is moving away from him
        if ( ball.body.getPosition().x < 37.5f || ball.body.getLinearVelocity().x < 0 )
        {
            long timeSinceLastMovement = System.currentTimeMillis() - lastDirectionChange; // the time since the last movement change

            // to change direction again, there must be at least 0.5 seconds since the last time, and a 30% chance when that is true
            if ( timeSinceLastMovement > 500 && Math.random() > 0.7 )
            {
                lastDirectionChange = System.currentTimeMillis(); // reset the time since the last direction change

                // generate a number [0, 1)
                if ( Math.random() >= 0.6 ) // if the number is [0.6, 1)
                {
                    paddle.moveUp();
                }
                else if ( Math.random() <= 0.4 ) // if the number is [0, 0.4]
                {
                    paddle.moveDown();
                }
                else // if the number is (0.4, 0.6)
                {
                    paddle.stopMovement();
                }
            }
        }
        else
        {
            if ( ball.body.getPosition().y > ( paddle.getTopPosition( 1 ) + paddle.getBottomPosition( 1 ) ) / 2 )
            {
                paddle.moveUp();
            }
            else if ( ball.body.getPosition().y < ( paddle.getTopPosition( 1 ) + paddle.getBottomPosition( 1 ) ) / 2 )
            {
                paddle.moveDown();
            }
            else
            {
                paddle.stopMovement();
            }
        }
    }

}
