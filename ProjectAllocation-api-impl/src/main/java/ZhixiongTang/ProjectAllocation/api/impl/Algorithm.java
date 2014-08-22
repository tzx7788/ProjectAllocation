package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.Student;

public class Algorithm {

	private static Double MAXIUM = 999.0;

	protected List<Student> students;
	protected List<Professor> professors;
	protected Map<Integer, Student> indexStudents;
	protected Map<Integer, Professor> indexProfessors;
	protected double[][] happiness;
	protected double[][] cost;
	protected int[][] mask;
	protected int[] rowCover;
	protected int[] colCover;
	protected int[] zero_RC;
	protected int[][] path;

	public Algorithm(List<Student> students, List<Professor> professors) {
		this.students = students;
		this.professors = professors;
	}

	private Double getHappiness(Student student, Professor professor) {
		Double studentScore = MAXIUM;
		Double professorScore = MAXIUM;
		if (student.preferProfessorsList().contains(professor))
			studentScore = (double) student.preferProfessorsList().indexOf(
					professor);
		if (professor.preferStudentsList().contains(student))
			studentScore = (double) professor.preferStudentsList().indexOf(
					student);
		return studentScore + professorScore;
	}

	public void initialization() {
		int size = 0;
		for (Professor professor : professors) {
			size += professor.getLimit();
		}
		this.indexStudents = new HashMap<Integer, Student>();
		for (int index = 0; index < students.size(); index++)
			this.indexStudents.put(index, students.get(index));
		this.indexProfessors = new HashMap<Integer, Professor>();
		int index = 0;
		for (Professor professor : professors) {
			for (int i = 0; i < professor.getLimit(); i++) {
				this.indexProfessors.put(index, professor);
				index++;
			}
		}
		this.happiness = new double[students.size()][size];
		for (int i = 0; i < happiness.length; i++)
			for (int j = 0; j < happiness[0].length; j++) {
				Student student = this.indexStudents.get(i);
				Professor professor = this.indexProfessors.get(j);
				this.happiness[i][j] = this.getHappiness(student, professor);
			}
	}

	private double findLargest(double[][] array) {
		double largest = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] > largest) {
					largest = array[i][j];
				}
			}
		}

		return largest;
	}

	public static double[][] copyOf(double[][] original) {
		double[][] copy = new double[original.length][original[0].length];
		for (int i = 0; i < original.length; i++) {
			System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
		}
		return copy;
	}

	private double[][] copyToSquare(double[][] original, double padValue) {
		int rows = original.length;
		int cols = original[0].length;
		double[][] result = null;

		if (rows == cols) {
			result = copyOf(original);
		} else if (rows > cols) {
			result = new double[rows][rows];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < rows; j++) {
					if (j >= cols) {
						result[i][j] = padValue;
					} else {
						result[i][j] = original[i][j];
					}
				}
			}
		} else {
			result = new double[cols][cols];
			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < cols; j++) {
					if (i >= rows) {
						result[i][j] = padValue;
					} else {
						result[i][j] = original[i][j];
					}
				}
			}
		}

		return result;
	}

	public void firstStep() {
		double maxWeightPlusOne = findLargest(happiness) + 1;

		cost = copyToSquare(happiness, maxWeightPlusOne);

		mask = new int[cost.length][cost[0].length];
		rowCover = new int[cost.length];
		colCover = new int[cost[0].length];
		zero_RC = new int[2];
		path = new int[cost.length * cost[0].length + 2][2];
		double minval;

		for (int i = 0; i < cost.length; i++) {
			minval = cost[i][0];
			for (int j = 0; j < cost[i].length; j++) {
				if (minval > cost[i][j]) {
					minval = cost[i][j];
				}
			}
			for (int j = 0; j < cost[i].length; j++) {
				cost[i][j] = cost[i][j] - minval;
			}
		}
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost[i].length; j++) {
				if ((cost[i][j] == 0) && (colCover[j] == 0)
						&& (rowCover[i] == 0)) {
					mask[i][j] = 1;
					colCover[j] = 1;
					rowCover[i] = 1;
				}
			}
		}

		clearCovers();
	}

	private int step1() {
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[i].length; j++) {
				if (mask[i][j] == 1) {
					colCover[j] = 1;
				}
			}
		}

		int count = 0;
		for (int j = 0; j < colCover.length; j++) {
			count = count + colCover[j];
		}

		if (count >= mask.length) {
			return 5;
		} else {
			return 2;
		}
	}

	private int step2() {

		int step = 0;
		int[] row_col = new int[2];
		boolean done = false;
		while (done == false) {
			row_col = findUncoveredZero(row_col);
			if (row_col[0] == -1) {
				done = true;
				step = 4;
			} else {
				mask[row_col[0]][row_col[1]] = 2;

				boolean starInRow = false;
				for (int j = 0; j < mask[row_col[0]].length; j++) {
					if (mask[row_col[0]][j] == 1) {
						starInRow = true;
						row_col[1] = j;
					}
				}

				if (starInRow == true) {
					rowCover[row_col[0]] = 1;
					colCover[row_col[1]] = 0;
				} else {
					zero_RC[0] = row_col[0];
					zero_RC[1] = row_col[1];
					done = true;
					step = 3;
				}
			}
		}

		return step;
	}

	private int[] findUncoveredZero(int[] row_col) {
		row_col[0] = -1;
		row_col[1] = 0;

		int i = 0;
		boolean done = false;
		while (done == false) {
			int j = 0;
			while (j < cost[i].length) {
				if (cost[i][j] == 0 && rowCover[i] == 0 && colCover[j] == 0) {
					row_col[0] = i;
					row_col[1] = j;
					done = true;
				}
				j = j + 1;
			}
			i = i + 1;
			if (i >= cost.length) {
				done = true;
			}
		}

		return row_col;
	}

	private int step3() {

		int count = 0;
		path[count][0] = zero_RC[0];
		path[count][1] = zero_RC[1];

		boolean done = false;
		while (done == false) {
			int r = findStarInCol(path[count][1]);
			if (r >= 0) {
				count = count + 1;
				path[count][0] = r;
				path[count][1] = path[count - 1][1];
			} else {
				done = true;
			}

			if (done == false) {
				int c = findPrimeInRow(path[count][0]);
				count = count + 1;
				path[count][0] = path[count - 1][0];
				path[count][1] = c;
			}
		}

		convertPath(count);
		clearCovers();
		erasePrimes();

		return 1;

	}

	private int findStarInCol(int col) {
		int r = -1;
		for (int i = 0; i < mask.length; i++) {
			if (mask[i][col] == 1) {
				r = i;
			}
		}

		return r;
	}

	private int findPrimeInRow(int row) {
		int c = -1;
		for (int j = 0; j < mask[row].length; j++) {
			if (mask[row][j] == 2) {
				c = j;
			}
		}

		return c;
	}

	private void convertPath(int count) {
		for (int i = 0; i <= count; i++) {
			if (mask[path[i][0]][path[i][1]] == 1) {
				mask[path[i][0]][path[i][1]] = 0;
			} else {
				mask[path[i][0]][path[i][1]] = 1;
			}
		}
	}

	private void erasePrimes() {
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[i].length; j++) {
				if (mask[i][j] == 2) {
					mask[i][j] = 0;
				}
			}
		}
	}

	private void clearCovers() {
		for (int i = 0; i < rowCover.length; i++) {
			rowCover[i] = 0;
		}
		for (int j = 0; j < colCover.length; j++) {
			colCover[j] = 0;
		}
	}

	private int step4() {
		double minval = findSmallest();

		for (int i = 0; i < rowCover.length; i++) {
			for (int j = 0; j < colCover.length; j++) {
				if (rowCover[i] == 1) {
					cost[i][j] = cost[i][j] + minval;
				}
				if (colCover[j] == 0) {
					cost[i][j] = cost[i][j] - minval;
				}
			}
		}

		return 2;
	}

	private double findSmallest() {
		double minval = Double.POSITIVE_INFINITY;
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost[i].length; j++) {
				if (rowCover[i] == 0 && colCover[j] == 0
						&& (minval > cost[i][j])) {
					minval = cost[i][j];
				}
			}
		}

		return minval;
	}

	public Boolean oneStep() {
		boolean done = false;
		int step = 1;
		step = step1();
		while (done == false) {
			switch (step) {
			case 1:
				return false;
			case 2:
				step = step2();
				break;
			case 3:
				step = step3();
				break;
			case 4:
				step = step4();
				break;
			case 5:
				done = true;
				break;
			}
		}
		return done;
	}

	public void finish() {
		boolean done = false;
		while (done == false) {
			done = this.oneStep();
		}
	}
	
	public void save() {
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[i].length; j++) {
				if (i < happiness.length && j < happiness[0].length && mask[i][j] == 1) {
					Student student = this.indexStudents.get(i);
					Professor professor = this.indexProfessors.get(j);
					if ( !student.getResult().contains(professor) )
						if ( !professor.getResult().contains(student) ) {
							student.getResult().add(professor);
							professor.getResult().add(student);
						}
				}
			}
		}
	}
}
