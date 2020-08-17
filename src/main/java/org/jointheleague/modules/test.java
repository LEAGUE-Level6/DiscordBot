package org.jointheleague.modules;

import javax.swing.JOptionPane;

public class test {
	public static void main(String[] args) {
		int[] a = new int[2];
		
		String cords = JOptionPane.showInputDialog("Enter numebrs");
		String newStr = cords.replaceAll("[!cord(),]", " ");
		String[] strs = newStr.trim().split("\\s+");

		for (int i = 0; i < strs.length; i++) {

			a[i] = Integer.parseInt(strs[i]);
		}

		int[][] array = new int[3][5];
		int xCord = a[0];
		int yCord = a[1];

		array[xCord][yCord] = 1;

		for (int[] x : array) {
			for (int y : x) {
				System.out.print(y + " ");
			}
			System.out.println();
		}

	}

}
