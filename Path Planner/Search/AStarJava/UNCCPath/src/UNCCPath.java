import java.util.Comparator;
import java.util.LinkedList;
import java.io.*; //needed for File and IOException
import java.util.*; //needed for Scanner class

class point {

    private point parent;
    private double[] cord = new double[2];
    private double g, h, f;

    //Create point object with self, a parent point, x,y coordinates, and a heuristic value
    point(point p,double lat,double lon,double heu) {
        parent = p;
        cord[0] = lat;
        cord[1] = lon;
        g = 1;
        h = heu;
        f = g + h;
    }

    point(point p){
        parent = p.parent;
        cord[0] = p.cord[0]; cord[1] = p.cord[1];
        g = 1; h = p.h; f = p.f;
    }
    
    public String toString(){
    	return "[" + String.valueOf(cord[0]) + "," + String.valueOf(cord[1]) + "]";
    }

    public boolean equals(point p) {
        return (cord[0] == p.cord[0] && cord[1] == p.cord[1]);
    }

    public boolean equals(double[] p) {
        return (cord[0] == p[0] && cord[1] == p[1]);
    }

    //Return the parent point of the calling point
    point getParent() { return parent; }

    //Return (x,y) coordinates of calling point
    double[] getCord(){ return cord; }

    double getH(){ return h; }
}

public class UNCCPath {

    private double[] goal = new double[2];
    private point start;

    UNCCPath(double startLat, double startLon, double endLat, double endLon) {
        goal[0] = endLat;
        goal[1] = endLon;
        double[] startLoc = {startLat, startLon};
        start = new point(null, startLat, startLon, calcH(startLoc, goal)); //starting building
    }

    private double calcH(double[] cord1, double[] cord2) {
        double dlon = cord2[1] - cord1[1];
        double dlat = cord2[0] - cord1[0];
        dlon = dlon * Math.PI / 180.0;
        dlat = dlat * Math.PI / 180.0;
        double a = Math.pow((Math.sin(dlat / 2)), 2) + Math.cos(cord1[0]) * Math.cos(cord2[0]) * Math.pow((Math.sin(dlon / 2)), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3961 * c;  //3961 is the approximate radius of Earth in miles
    }

    private LinkedList<double[]> getChildren(double[] loc) throws IOException {
    
        LinkedList<double[]> children = new LinkedList<>();
        //QUERY DATABASE FOR CLOSE POINTS//
        
        //FIXME - temporary, for testing until we get database up and running
        Scanner input = new Scanner(new File("B:\\Github\\NinerJaunt\\Path Planner\\ReadingFiles\\Points.txt"));
        StringTokenizer st;
        double[] kid;
        
        while (input.hasNext()){
        	st = new StringTokenizer(input.nextLine(), ",");
            kid = new double[2];
        	kid[0] = Double.parseDouble(st.nextToken());
        	kid[1] = Double.parseDouble(st.nextToken());
        	
        	if (	(Math.abs(kid[0] - loc[0]) < 0.0007) && (Math.abs(kid[1] - loc[1]) < 0.0007)	){
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

    private LinkedList<double[]> genPath(point node, point start) {
        LinkedList<double[]> path = new LinkedList<>();
        while (!node.equals(start)) {
            path.add(node.getCord());
            //get next node
            node = node.getParent();
        }
        return path;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //														BEGIN A*												    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkedList<double[]> getPath() throws IOException {

        LinkedList<point> openlist = new LinkedList<>();
        LinkedList<point> closedlist = new LinkedList<>();

        point kid;

        point node = new point(start);
        int states = 0;

        while(!node.equals(goal)){
            states += 1;

            if (!node.equals(goal)){
                closedlist.add(node);
                
                LinkedList<double[]> family;
                
                try {
                	family = getChildren(node.getCord());
                } catch (IOException e) {
                	return null;
                }
            	
            	for (int i = 0; i < family.size(); i++){
                    double[] c = family.pop();
                    if (listDoesNotContain(openlist,c) && listDoesNotContain(closedlist,c)){
                        kid = new point(node, c[0], c[1], calcH(c, goal));
                        openlist.add(kid);
                    }
                }
                openlist.sort(new Comparator<point>() {
                    @Override
                    public int compare(point o1, point o2) {
                        return (int)Math.round(o1.getH() - o2.getH());
                    }
                });
            }
            node = openlist.pop();
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //														END A*												    //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return genPath(node, start);
    }


}