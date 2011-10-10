/**
 * Helper class for determining Circle-Square intersections.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class CircleSquare
{
    /**
     * Test if there is an intersection between a square with upper left hand
     * point (sq_x, sq_y) with side length sq_size and a circle centered at the
     * point (cir_x, cir_y) with radius cir_radius.
     *
     * @param sq_x The x coordinate of the upper left hand point of the square.
     * @param sq_y The y coordinate of the upper left hand point of the square.
     * @param sq_size The side length of the square.
     * @param cir_x The x coordinate of the center of the circle.
     * @param cir_y The y coordinate of the center of the circle.
     * @param cir_radius The radius of the circle.
     * @return If there is any intersection between the square and the circle.
     */
    public static boolean intersection( int sq_x, int sq_y, int sq_size,
                                        int cir_x, int cir_y, int cir_radius )
    {
    	int half = (sq_size << 1);
    	
    	int cd_x = Math.abs(cir_x - sq_x - half);
    	int cd_y = Math.abs(cir_y - sq_y - half);
    	
    	if (cd_x > half + cir_radius) { return false; }
    	if (cd_y > half + cir_radius) { return false; }
    	
    	if (cd_x <= half) { return true; }
    	if (cd_y <= half) { return true; }
    	
    	int corner_dist_sq = ((cd_x - half) * (cd_x - half)) +
    					     ((cd_y - half) * (cd_y - half));
    	
    	return (corner_dist_sq <= (cir_radius * cir_radius));	
    }
}
