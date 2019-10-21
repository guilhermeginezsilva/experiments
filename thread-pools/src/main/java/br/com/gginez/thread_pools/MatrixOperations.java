package br.com.gginez.thread_pools;

public class MatrixOperations {

	public static int[][] sum(int[][] matrixA, int[][] matrixB) {
		if(matrixA.length == 0 || matrixA[0].length == 0) {
			throw new RuntimeException("Matrix lenght cant be 0");
		}
		
		if(matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
			throw new RuntimeException("Matrix A and B must have same size");
		}
		
		int[][] summedMatrix = new int[matrixA.length][matrixA[0].length];
		
		for(int indexA = 0; indexA < matrixA.length; indexA ++) {
			for(int indexB = 0; indexB < matrixA[0].length; indexB ++) {
				summedMatrix[indexA][indexB] = matrixA[indexA][indexB] + matrixB[indexA][indexB];
			}
		}
		return summedMatrix;
	}
	
	public static void printMatrix(int[][] matrix, String title) {
		System.out.println(title);
		for(int indexA = 0; indexA < matrix.length; indexA ++) {
			for(int indexB = 0; indexB < matrix[0].length; indexB ++) {
				System.out.println(matrix[indexA][indexB]);
			}
		}
	}
	
	
	public static void main(String[] args) {
		int[][] a = new int[2][2];
		
		a[0][0] = 1;
		a[0][1] = 1;
		a[1][0] = 1;
		a[1][1] = 1;
		
		int[][] b = new int[2][2];
		
		b[0][0] = 1;
		b[0][1] = 1;
		b[1][0] = 1;
		b[1][1] = 1;
		
		MatrixOperations.printMatrix(MatrixOperations.sum(a, b), "Resultado");
	}
	
}
