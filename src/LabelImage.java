import java.util.Stack;

public class LabelImage {

	int[][] label;
	Stack<int[]> stack;
	int nofObj;

	/** Creates a new instance of LabelImage */
	public LabelImage() {
	}

	public int[][] labelImage(int[][] img) {

		int nrow = img.length;
		int ncol = img[0].length;
		int lab = 1;
		int[] pos;
		//System.out.println(nrow);
		//System.out.println(ncol);
		stack = new Stack<int[]>();
		label = new int[nrow][ncol];

		for (int r = 1; r < nrow - 1; r++)
			for (int c = 1; c < ncol - 1; c++) {

				if (img[r][c] == 0)
					continue;
				if (label[r][c] > 0)
					continue;
				/* encountered unlabeled foreground pixel at position r, c */
				/* push the position on the stack and assign label */
				stack.push(new int[] { r, c });
				label[r][c] = lab;

				/* start the float fill */
				while (!stack.isEmpty()) {
					pos = (int[]) stack.pop();
					int i = pos[0];
					int j = pos[1];
					//System.out.print(i+"\t");
					//System.out.println(stack.size());
					
					if (img[i - 1][j - 1] == 1 && label[i - 1][j - 1] == 0) {
						stack.push(new int[] { i - 1, j - 1 });
						label[i - 1][j - 1] = lab;
					}
					if (img[i - 1][j] == 1 && label[i - 1][j] == 0) {
						stack.push(new int[] { i - 1, j });
						label[i - 1][j] = lab;
					}
					if (img[i - 1][j + 1] == 1 && label[i - 1][j + 1] == 0) {
						stack.push(new int[] { i - 1, j + 1 });
						label[i - 1][j + 1] = lab;
					}
					if (img[i][j - 1] == 1 && label[i][j - 1] == 0) {
						stack.push(new int[] { i, j - 1 });
						label[i][j - 1] = lab;
					}
					if (img[i][j + 1] == 1 && label[i][j + 1] == 0) {
						stack.push(new int[] { i, j + 1 });
						label[i][j + 1] = lab;
					}
					if (img[i + 1][j - 1] == 1 && label[i + 1][j - 1] == 0) {
						stack.push(new int[] { i + 1, j - 1 });
						label[i + 1][j - 1] = lab;
					}
					if (img[i + 1][j] == 1 && label[i + 1][j] == 0) {
						stack.push(new int[] { i + 1, j });
						label[i + 1][j] = lab;
					}
					if (img[i + 1][j + 1] == 1 && label[i + 1][j + 1] == 0) {
						stack.push(new int[] { i + 1, j + 1 });
						label[i + 1][j + 1] = lab;
					}
				//	System.out.print(label[i][j]+" ");

				} /* end while */
				lab++;
				//System.out.println();
			}
		nofObj=lab-1;
		return label;
	}
	

}
