import java.util.Comparator;
import java.util.LinkedList;
import java.io.*; //needed for File and IOException
import java.util.*; //needed for Scanner class

class point {

    private point parent;
    private double[] cord;
    private double g, h, f;
    private final double radius = 0.0001;

    //Create point object with self, a parent point, x,y coordinates, and a heuristic value
    point(point p,double lat,double lon, double gcost, double heu) {
        parent = p;
        cord = new double[] {lat, lon};
        g = 1;
        h = heu;
        f = g + h;
    }

    point(point p){
        parent = p.parent;
        cord = new double[] {p.getCord()[0], p.getCord()[1]};
        g = 1; h = p.h; f = p.f;
    }
    
    public String toString(){
    	return "" + String.valueOf(cord[0]) + "," + String.valueOf(cord[1]) + "";
    }

    public boolean equals(point p) {
    	return ( (cord[0] - p.cord[0]) == 0.0 && (cord[1] - p.cord[1]) == 0.0 );
    }
    
    public double calcDist(double[] cord1) {
        return Math.sqrt( Math.pow(cord[0] - cord1[0],2) +  Math.pow(cord[1] - cord1[1],2) );
    }

    public boolean equals(double[] p) {
    	return calcDist(p) < radius;
    }

    //Return the parent point of the calling point
    point getParent() { return parent; }

    //Return (x,y) coordinates of calling point
    double[] getCord(){ return cord; }

    double getF(){ return f; }
}


public class UNCCPath {

	final double radius = 0.0002;

    private double[] goal, startLoc;
    private point start;

    UNCCPath(double startLat, double startLon, double endLat, double endLon) {
        goal = new double[] {endLat, endLon};
        startLoc = new double[] {startLat, startLon};
        start = new point(null, startLat, startLon, 1, calcH(startLoc, goal)); //starting building
    }

    public double calcH(double[] cord1, double[] cord2) {
        return Math.sqrt( Math.pow(cord2[0] - cord1[0],2) +  Math.pow(cord2[1] - cord1[1],2) );
    }

    private LinkedList<double[]> getChildren(double[] loc) throws IOException {
    
        LinkedList<double[]> children = new LinkedList<>();
        double distOfKid = 0;
        
        Scanner input = new Scanner(new File("/home/matt19/Documents/Github/NinerJaunt/Path Planner/ReadingFiles/Points.txt"));
        StringTokenizer st;
        double[] kid;

        while (input.hasNext()) {
            st = new StringTokenizer(input.nextLine(), ",");
            kid = new double[2];
            
        	kid[0] = Double.parseDouble(st.nextToken());
        	kid[1] = Double.parseDouble(st.nextToken());
        	
        	distOfKid = calcH(kid, loc);
        	        	
        	if (distOfKid <= radius){
        		children.push(kid);
        	}
            
        }
        input.close();
        return children;
    }
    
    private boolean listDoesNotContain(LinkedList<point> l, double[] loc){
        for (point p : l)
    		if (p.equals(loc)) return false;
    	return true;
    }

    private LinkedList<double[]> genPath(point node, point start, double[] goal) {
        LinkedList<double[]> path = new LinkedList<>();
        path.addFirst(goal);
        while (!node.equals(start)) {
            path.addFirst(node.getCord());
            //get next node
            node = node.getParent();
        }
        path.addFirst(start.getCord());
        return path;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //														BEGIN A*												    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkedList<double[]> getPath() throws IOException {

        LinkedList<point> openlist = new LinkedList<>();
        LinkedList<point> closedList = new LinkedList<>();

        point kid;
        LinkedList<double[]> family;

        point node = new point(start);

        while(!node.equals(goal)){

            if (!node.equals(goal)){
                closedList.add(node);
                             
                try {
                    family = getChildren(node.getCord());
                } catch (IOException e) {
                	return null;
                }
                            	            	
            	while (family.size() != 0){
                    double[] c = family.pop();
                    
                    if (listDoesNotContain(openlist,c) && listDoesNotContain(closedList,c)){
                        kid = new point(node, c[0], c[1], calcH(c, node.getCord()), calcH(c, goal));
                        openlist.add(kid);
                    }
                    
                }
                                
                openlist.sort(new Comparator<point>() {
                    @Override
                    public int compare(point o1, point o2) {

                        return (o1.getF() - o2.getF()) <= 0.0 ? -1 : 1;

                    }
                });
            }
            
            //Debugging openlist
            /*for (point x : openlist){
            	System.out.println(x.toString());
            }
            System.out.println();*/
            
            try {
            	node = openlist.removeFirst();
            } catch(Exception e) {
            	break;
            }

        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //														END A*												    //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return genPath(node, start, goal);
    }


}
