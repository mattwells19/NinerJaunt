import java.io.IOException;
import java.util.LinkedList;

public class TestUNCCPath {
	public static void main(String args[]) throws IOException {
		UNCCPath path = new UNCCPath(35.3096309,-80.7423472,35.3103992,-80.7384969);

        LinkedList<double[]> p = path.getPath();
		for (double[] c : p) System.out.println("[" + String.valueOf(c[0]) + "," + String.valueOf(c[1]) + "]");
	}
}
