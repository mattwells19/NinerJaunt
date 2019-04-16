import java.io.IOException;
import java.util.LinkedList;

public class TestUNCCPath {
	public static void main(String args[]) throws IOException {
		UNCCPath path = new UNCCPath(35.31241,-80.74187,35.3114559,-80.7417126);

        LinkedList<double[]> p = path.getPath();
		for (double[] c : p) System.out.println("" + String.valueOf(c[0]) + "," + String.valueOf(c[1]) + "");
	}
}
