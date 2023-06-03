import java.awt.Color;
import java.util.TreeSet;
import java.util.ArrayList;
//import Stacks.BSTSymbolTable.Node;
import java.util.Iterator;


public class PointSET implements PointContainer
{    
	
	TreeSet tree = new TreeSet();
    
	public boolean isEmpty()
    {
        return tree.isEmpty();
    }
    
    public int size()
    {
    	return tree.size();
    }
    
    public void insert(Point2D p)
    {
    	tree.add(p);
    }
    
    public boolean contains(Point2D p)
    {
    	return tree.contains(p);
    }
    
    public void draw(Canvas canvas)
    {
        canvas.setPenColor(Color.BLACK);
        canvas.setPenRadius(.01);
        
        // TODO: Insert code here to call the point() method on canvas
        // for each point that has been inserted into your PointSET
        
        Iterator<Point2D> itr = tree.iterator();
        while(itr.hasNext())
        {
        	Point2D point = itr.next();
        	canvas.point(point.x(), point.y());
        }
        
        // Don't forget to remove this!
    	//throw new UnsupportedOperationException();
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
    	ArrayList<Point2D> inRange = new ArrayList<Point2D>();
    	Iterator<Point2D> itr = tree.iterator();
    	while(itr.hasNext())
    	{
    		Point2D point = itr.next();
    		if(rect.distanceTo(point) == 0)
    		{
    			inRange.add(point);
    		}
    	}
    	return inRange;
    }
    
    public Point2D nearest(Point2D p)
    {
    	Point2D champion = null;
    	Iterator<Point2D> itr = tree.iterator();
    	while(itr.hasNext())
    	{
    		Point2D point = itr.next();
    		if(champion == null || champion.distanceTo(p) > point.distanceTo(p))
    		{
    			champion = point;
    		}
    	}
    	
    	return champion;
    }
}