package com.example.cellphone_API4;

import java.io.FileWriter;
import java.io.IOException;

public class WriterThread extends Thread {
	private String record;
	private FileWriter txtFileWriter;

	public WriterThread(FileWriter fileWriter, String record) {
		this.txtFileWriter = fileWriter;
		this.record = record;
	}

	@Override
	public void run() {
		super.run();
		try {
			synchronized (txtFileWriter) {
				txtFileWriter.write(record + "\n");
				txtFileWriter.flush();
			}
		} catch (IOException e) {
			System.out.println("Read failed");
		}
	}
}
