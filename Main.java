package com.thumbtack.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Database db = new Database();
		String output = "";
		while (true) {
			try {
				String[] arr;
				arr = br.readLine().split(" ");
				if (arr[0].equalsIgnoreCase("GET")) {
					try {
						output += db.get(arr[1]) + "\n";
					} catch (NullPointerException n) {
						output += "NULL\n";
					}
				} else if (arr[0].equalsIgnoreCase("SET")) {
					db.set(arr[1], Integer.parseInt(arr[2]));
				}else if (arr[0].equalsIgnoreCase("NUMEQUALTO")) {
					try {
						output += db.numEqualTo(Integer.parseInt(arr[1])) + "\n";
					} catch (NullPointerException n) {
						output += "0\n";
					}
				}else if (arr[0].equalsIgnoreCase("UNSET")) {
					db.unset(arr[1]);
				}else if (arr[0].equalsIgnoreCase("BEGIN")) {
					db.begin();
				}else if (arr[0].equalsIgnoreCase("ROLLBACK")) {
					output += db.rollback();
				}else if (arr[0].equalsIgnoreCase("COMMIT")) {
					output += db.commit();
				}else if (arr[0].equalsIgnoreCase("END")) {
					break;
				}else {
					output += "Invalid command : " + arr[0] + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(output);
	}

}

