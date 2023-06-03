import java.awt.Color;
import java.util.ArrayList;

import java.util.Scanner;

public class KdTree implements PointContainer
{
	private static class Node
	{
		private Point2D key;
		private RectHV rect;
		private int level;
		
		private Node left;
		private Node right;
	}
	
	private Node root;
	
    public boolean isEmpty()
    {
    	return (root == null);
    }
    
    public int size()
    {
		if(root == null)
		{
			return 0;
		}
		return size(root);
    }
    
    private int size(Node n)
	{
		if(n == null)
		{
			return 0;
		} 
		return 1 + size(n.right) + size(n.left);
	}
    
    public void insert(Point2D p)
    {
    	Node newNode = new Node();
		newNode.key = p;
		if(root == null)
		{
			root = newNode;
			root.level = 0;
			root.rect = new RectHV(0,0,1,1);
		}
		else if(!contains(p))
		{
			insert(root, newNode, root.rect, root.key);
		}
    }
    
    private void insert(Node current, Node newNode, RectHV rect, Point2D p)
    {
    	//System.out.println("here");
    	// Check to see if on even level or odd level
    	if(current.level == 0)
    	{
    		// Set level to other
    		newNode.level = 1;
    		// If to left, then go to left node or if to right or equal go to right node.
    		if(current.left == null && newNode.key.x() < current.key.x())
    		{
    			newNode.rect = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
    			current.left = newNode;
    			//System.out.println("Success");
    		}
    		else if(current.right == null && newNode.key.x() >= current.key.x())
    		{
    			newNode.rect = new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
    			current.right = newNode;
    			//System.out.print("Success part 2");
    		}
    		// If not null slots then recurse
    		else
    		{
	    		if(newNode.key.x() < current.key.x())
	    		{
	    			//check
	    			insert(current.left, newNode, current.left.rect, current.left.key);
	    		}
	    		else
	    		{
	    			//check
	    			insert(current.right, newNode, current.right.rect, current.right.key);
	    		}
    		}
    	}
    	else if (current.level == 1)
    	{
    		// Set level to other 
    		newNode.level = 0;
    		// If to down, then go to left node or if up or equal go to right node.
    		if(current.left == null && newNode.key.y() < current.key.y())
    		{	
    			newNode.rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
    			current.left = newNode;
    		}
    		else if(current.right == null && newNode.key.y() >= current.key.y())
    		{
    			newNode.rect = new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
    			current.right = newNode;
    		}
    		// If not null slots then recurse
    		else
    		{
    		if(newNode.key.y() < current.key.y())
    		{
    			insert(current.left, newNode, current.left.rect, current.left.key);
    		}
    		else
    		{
    			insert(current.right, newNode, current.right.rect, current.right.key);
    		}
    		}
    	}
    }
    
    public boolean contains(Point2D p)
    {
    	// See if no points
        if(root == null)
        {
        	return false;
        }
        //if has points
        return contains(root, p);
    }
    
    private boolean contains(Node n, Point2D p)
    {
    	// Found key
    	if(n.key.x() == p.x() && n.key.y() == p.y())
    	{
    		return true;
    	}
    	//Even level key search
    	if(n.level == 0)
    	{
    		//Key in left Node
    		if(n.key.x() > p.x())
    		{
    			//If key not in tree
    			if(n.left == null)
    			{
    				return false;
    			}
    			//Recurse left
    			return contains(n.left, p);
    		}
    		//key in right node
    		else
    		{
    			//if key not in tree
    			if(n.right == null)
    			{
    				return false;
    			}
    			//recurse right
    			return contains(n.right, p);
    		}
    	}
    	//Odd level key search
    	else
    	{
    		//key in left node
	    	if(n.key.y() > p.y())
			{
	    		//if key not in tree
				if(n.left == null)
				{
					return false;
				}
				//recurse left
				return contains(n.left, p);
			}
	    	//if key in right node
	    	//if key not in tree
			if(n.right == null)
			{
				return false;
			}
			//recurse right
			return contains(n.right, p);
	    	}
    }

    public void draw(Canvas canvas)
    {
    	// Use canvas to draw your points and dividing lines
    	//
    	// For points, use these calls:
        //    canvas.setPenRadius(.01);
    	//    canvas.setPenColor(Color.BLACK);
    	//    canvas.point(put your parameters here)
    	//
    	// For dividing lines, use these calls:
    	//    canvas.setPenRadius(.002);
    	//	  canvas.setPenColor(Color.RED); (for vertical dividing lines)
    	//	  canvas.setPenColor(Color.BLUE); (for horizontal dividing lines)
    	//    canvas.line(put your parameters here)
    	
        // Don't forget to remove this!
    	//throw new UnsupportedOperationException();
    	
    	if(root != null)
    	{
    		drawPoint(root, canvas);
    	}
    }
    
    private void drawPoint(Node n, Canvas canvas)
    {
    	//Draw point
    	canvas.setPenRadius(.01);
    	canvas.setPenColor(Color.BLACK);
    	canvas.point(n.key.x(), n.key.y());
    	
    	//Draw line
    	canvas.setPenRadius(.002);
    	//vertical line (even)
    	if(n.level == 0)
    	{
        	canvas.setPenColor(Color.RED);
        	//Use rect to draw line
        	canvas.line(n.key.x(), n.rect.ymax(), n.key.x(), n.rect.ymin());
        	
    	}
    	//horizontal line (odd)
    	else
    	{
    		canvas.setPenColor(Color.BLUE);
    		//Use rect to draw line
    		canvas.line(n.rect.xmin(), n.key.y(), n.rect.xmax(), n.key.y());
    	}
    	
    	//Recurse left
    	if(n.left != null)
    	{
    		drawPoint(n.left, canvas);
    	}
    	//Recurse right
    	if(n.right != null)
    	{
    		drawPoint(n.right, canvas);
    	}
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
    	if(root == null)
    	{
    		return new ArrayList<Point2D>();
    	}
    	return range(rect, root, new ArrayList<Point2D>());
    }      
    
    private Iterable<Point2D> range(RectHV rect, Node n, ArrayList<Point2D> list)
    {
    	//if you hit the end of the branch, return the list
    	if(n == null)
    	{
    		return list;
    	}
    	ArrayList<Point2D> inRange = list;
    	//If the point is in the rectangle, then add it to the array list
    	if(rect.contains(n.key))
    	{
    		inRange.add(n.key);
    	}
    	// Even level comparisons
    	if(n.level == 0)
    	{
    		//left child
    		if(rect.xmin() < n.key.x())
    		{
    			range(rect, n.left, inRange);
    		}
    		//right child
    		if(rect.xmax() >= n.key.x())
    		{
    			range(rect, n.right, inRange);
    		}
    	}
    	//odd level comparisons
    	else if(n.level == 1)
    	{
    		//left child
    		if(rect.ymin() < n.key.y())
    		{
    			range(rect, n.left, inRange);
    		}
    		//right child
    		if(rect.ymax() >= n.key.y())
    		{
    			range(rect, n.right, inRange);
    		}
    	}
    	return inRange;
    }
    
    public Point2D nearest(Point2D p)
    {
    	
    	if(root == null)
    	{
    		return null;
    	}
    	//Returning key here instead of tracking because easier to track winner by root
    	return nearest(root, p, root).key;
    }
    
    private Node nearest(Node n, Point2D queryPoint, Node winner)
    {
    	
		
    	//If you cant recurse further since the node doesnt exist, just return winner
    	if(n == null)
    	{
    		return winner;
    	}
    	// If a node's point is closer than the point of winner, set winner to new node
       	if(queryPoint.distanceTo(n.key) < queryPoint.distanceTo(winner.key))
    	{
    		winner = n;
    	}
       	
       	double psX = queryPoint.x();
       	double psY = queryPoint.y();
       	
       	double nX = n.key.x();
       	double nY = n.key.y();
       	
    	//Even level
    	if(n.level == 0)
    	{
    		//If the point has a smaller x than the node's key or the node's left child's rect is closer to the point than the node itself
    		Point2D point = new Point2D(nX, psY);
    		
    		double distanceToPoint = queryPoint.distanceTo(point);
    		
    		//if(psX < nX || (n.left != null && n.left.rect.distanceTo(queryPoint) < queryPoint.distanceTo(winner.key)))
    		if ((psX < nX || distanceToPoint < queryPoint.distanceTo(winner.key)) && n.left!=null)
    		{
    			winner = nearest(n.left, queryPoint, winner);
    		}
    		//If the point has a bigger x than the node's key or the node's right child's rect is closer to the point than the node itself
    		
    		//if(psX >= nX || (n.right != null && n.right.rect.distanceTo(queryPoint) < queryPoint.distanceTo(winner.key)))
    		if ((psX >= nX || distanceToPoint < queryPoint.distanceTo(winner.key)) && n.right!=null)
    		{
    			winner = nearest(n.right, queryPoint, winner);
    		}
    	}
    	else
    	{
    		//If the point has a smaller y than the node's key or the node's left child's rect is closer to the point than the node itself
    		Point2D point = new Point2D(psX, nY);
    		double distanceToPoint = queryPoint.distanceTo(point);
    		//if(p.y() < n.key.y() || (n.left != null && n.left.rect.distanceTo(p) < p.distanceTo(n.key)))
    		if((psY < nY ||	distanceToPoint < queryPoint.distanceTo(winner.key)) && n.left!=null)
    		{
    			winner = nearest(n.left, queryPoint, winner);
    		}
    		//If the point has a bigger y than the node's key or the node's right child's rect is closer to the point than the node itself
    		
    		//if(p.y() >= n.key.y() || (n.right != null && n.right.rect.distanceTo(p) < p.distanceTo(n.key)))
    		if((psY >= nY || distanceToPoint < queryPoint.distanceTo(winner.key)) && n.right !=null)
    		{
    			winner = nearest(n.right, queryPoint, winner);
    		}
    	}
    	//returning the closest point
    	return winner;
    	
    	
    }
    
    public static void main(String[] args)
    {
    	KdTree tree = new KdTree();
    	
    	tree.insert(new Point2D(.2,.2));
    	tree.insert(new Point2D(.2,.2));
    	//tree.insert(new Point2D(.2,.5));
    	
    	Point2D point = tree.nearest(new Point2D(.2,.2));
    	
    	System.out.print(point.x() + ", " + point.y());
    	
    	
    	
    }
}