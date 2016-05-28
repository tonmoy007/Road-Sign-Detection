import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImgShow extends JPanel {
	BufferedImage b;
	static int width = 0;
	static int height = 0;

	public void Show(Mat m) {
		width = m.width();
		height = m.height();
		b = mat2Img(m);

		repaint();
	}

	public void paint(Graphics g) {
		g.drawImage(b, 0, 0, width, height, null);
	}

	// Convert image to Mat
	public Mat matify(BufferedImage im) {
		// Convert INT to BYTE
		// im = new BufferedImage(im.getWidth(),
		// im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		// Convert bufferedimage to byte array
		byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer())
				.getData();

		// Create a Matrix the same size of image
		Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
		// Fill Matrix with image values
		image.put(0, 0, pixels);

		return image;

	}

	public static BufferedImage mat2Img(Mat in) {
		BufferedImage out;
		// System.out.println(in.channels());
		byte[] data = new byte[width * height * (int) in.elemSize()];
		int type;
		in.get(0, 0, data);

		if (in.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;
		System.out.println(type);

		out = new BufferedImage(width, height, type);

		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;
	}

	// Accessing a pixel value
	public Mat aPixel(Mat image, double threshold) {
		Mat img = image.clone();

		Size sizeA = img.size();
		for (int i = 0; i < sizeA.height; i++) {
			for (int j = 0; j < sizeA.width; j++) {
				double[] data = img.get(i, j);
				// System.out.println(data[0]);
				if (data[0] > threshold) {
					data[0] = 255;
				} else {
					data[0] = 0;
				}
				image.put(i, j, data[0]);
			}
		}
		return image;
	}

	Mat dCircle(Mat a) {

		Mat circles = new Mat();
		Imgproc.HoughCircles(a, circles, Imgproc.CV_HOUGH_GRADIENT, 1d,
				a.rows() / 80, 400, 200, 0, 0);

		int radius;
		Point pt;
		for (int x = 0; x < circles.cols(); x++) {
			double vCircle[] = circles.get(0, x);

			if (vCircle == null)
				break;

			pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
			radius = (int) Math.round(vCircle[2]);

			// draw the found circle
			Core.circle(a, pt, radius, new Scalar(0, 255, 0), 3, 8, 0);
			Core.circle(a, pt, radius, new Scalar(0, 0, 255), -1, 8, 0);
			ImgWindow.newWindow(a, "circle image");

		}
		return a;
	}

	Mat plot(Mat a, Mat d) {
		Mat img = a.clone();

		Size sizeA = img.size();
		for (int i = 0; i < sizeA.height; i++) {
			for (int j = 0; j < sizeA.width; j++) {
				double[] data = img.get(i, j);
				// System.out.println(data[0]);
				if (data[0] > 200) {
					d.put(i, j, data[0]);
				}

			}
		}
		return d;
	}
	
	int[][] getBmat(Mat m){
		Mat c=m.clone();
		Size sizeA = c.size();
		int[][] a=new int[(int)sizeA.height+1][(int)sizeA.width+1];
		for (int i = 0; i <= sizeA.height; i++) {
			for (int j = 0; j <= sizeA.width; j++) {
				double[] data = c.get(i, j);
				// System.out.print(data[0]);
				if(i==0||i==sizeA.height){
					a[i][j]=0;
					continue;
				}
				if(j==0||j==sizeA.width){
					a[i][j]=0;
					continue;
				}
				if (data[0] >200) {
					a[i][j] = 1;
					//System.out.print(a[i][j]);
				} else {
				a[i][j] = 0;
				//System.out.print(a[i][j]);
				}
			
			}
			//System.out.println();
		}
		return a;
	
		
	}
}
