package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.Controller.ArticleController;

public class App {
	private Scanner sc;

	public App() {

	}

	public App(Scanner sc) {
		this.sc = sc;
	}

	public void run() {
		ArticleController articleController = new ArticleController();

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("article write")) {
				articleController.write(cmd, sc);
			} else if (cmd.equals("article list")) {
				articleController.list(cmd);
			} else if (cmd.startsWith("article modify")) {
				articleController.modify(cmd, sc);
			} else if (cmd.startsWith("article detail")) {
				articleController.detail(cmd, sc);
			} else if (cmd.startsWith("article delete")) {
				articleController.delete(cmd,sc);
			}
		}
	}
}
