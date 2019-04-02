import java.io.IOException;

public class TestUNCCPath {
	public static void main(String args[]) throws IOException {
		UNCCPath path = new UNCCPath(35.3097141,-80.7422587,35.3127977,-80.7408823);
		System.out.println("Path: " + path.getPath());
	}
}
