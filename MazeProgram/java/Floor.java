import java.awt.Polygon;

public class Floor
{
	private int[] x,y;

	public Floor(int[] x,int[] y)
	{
		this.x=x;
		this.y=y;
	}
	public int[] getX()
	{
		return x;
	}
	public int[] getY()
	{
		return y;
	}
	public Polygon getPolygon() {
		return new Polygon(x,y,4);
	}
	//maybe make a method to return a polygon....it will simplify things
}