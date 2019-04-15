import java.io.IOException;
import java.util.LinkedList;

public class TestUNCCPath {
	public static void main(String args[]) throws IOException {
		UNCCPath path = new UNCCPath(35.31152,-80.74282,35.30880,-80.73976);

        LinkedList<double[]> p = path.getPath();
		for (double[] c : p) System.out.println("" + String.valueOf(c[0]) + "," + String.valueOf(c[1]) + "");
	}
}
