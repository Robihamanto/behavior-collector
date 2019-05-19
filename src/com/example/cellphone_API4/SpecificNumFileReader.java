package com.example.cellphone_API4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;

public class SpecificNumFileReader {
	private BufferedReader reader;
	private SpecificNumSet upDownNum;
	private SpecificNumSet downUpNum;
	private SpecificNumSet leftRightNum;
	private SpecificNumSet rightLeftNum;
	private int status = 1;

	public SpecificNumFileReader() {
		upDownNum = new SpecificNumSet();
		downUpNum = new SpecificNumSet();
		leftRightNum = new SpecificNumSet();
		rightLeftNum = new SpecificNumSet();

		// 判斷SD卡是否存在
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_REMOVED)) {
			try {
				// 取得SD卡路徑，點擊數字檔案放置於根目錄下
				File SDCardpath = Environment.getExternalStorageDirectory();
				reader = new BufferedReader(new FileReader(
						SDCardpath.getAbsolutePath() + "/specific_num.txt"));

				//處理點擊數字檔案
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains("#")) {
						int totalSessionNum;
						int totalSpecificNum;
						int[][] specificNumMetrix = null;
						String type = line.replace("#", "");

						line = reader.readLine();
						String[] dimensionList = line.split(",");
						totalSessionNum = Integer.parseInt(dimensionList[0]);
						totalSpecificNum = Integer.parseInt(dimensionList[1]);

						specificNumMetrix = new int[totalSessionNum][totalSpecificNum];
						int rowCount = 0;
						while ((line = reader.readLine()) != null) {
							String[] specificNumList = line.split(",");
							for (int i = 0; i < totalSpecificNum; i++) {
								specificNumMetrix[rowCount][i] = Integer
										.parseInt(specificNumList[i]);
							}
							rowCount++;
							if (rowCount == totalSessionNum) {
								break;
							}
						}

						if (type.equals("UpDown")) {
							upDownNum = new SpecificNumSet(totalSessionNum,
									totalSpecificNum, specificNumMetrix);
						} else if (type.equals("DownUp")) {
							downUpNum = new SpecificNumSet(totalSessionNum,
									totalSpecificNum, specificNumMetrix);
						} else if (type.equals("LeftRight")) {
							leftRightNum = new SpecificNumSet(totalSessionNum,
									totalSpecificNum, specificNumMetrix);
						} else if (type.equals("RightLeft")) {
							rightLeftNum = new SpecificNumSet(totalSessionNum,
									totalSpecificNum, specificNumMetrix);
							break;
						}
					}

				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				status = -1;
			} catch (IOException e) {
				e.printStackTrace();
				status = -1;
			}

		}
	}

	public int getTotalSessionNum(String mode) {
		if (status != -1) {
			if (mode.equals("UpDown")) {
				return upDownNum.getTotalSessionNum();
			} else if (mode.equals("DownUp")) {
				return downUpNum.getTotalSessionNum();
			} else if (mode.equals("LeftRight")) {
				return leftRightNum.getTotalSessionNum();
			} else if (mode.equals("RightLeft")) {
				return rightLeftNum.getTotalSessionNum();
			}
		}
		return -1;
	}

	public int getTotalSpecificNum(String type) {
		if (status != -1) {
			if (type.equals("UpDown")) {
				return upDownNum.getTotalSpecificNum();
			} else if (type.equals("DownUp")) {
				return downUpNum.getTotalSpecificNum();
			} else if (type.equals("LeftRight")) {
				return leftRightNum.getTotalSpecificNum();
			} else if (type.equals("RightLeft")) {
				return rightLeftNum.getTotalSpecificNum();
			}
		}
		return -1;
	}

	public int[][] getSpecificNumMetrix(String type) {
		if (status != -1) {
			if (type.equals("UpDown")) {
				return upDownNum.getSpecificNumMetrix();
			} else if (type.equals("DownUp")) {
				return downUpNum.getSpecificNumMetrix();
			} else if (type.equals("LeftRight")) {
				return leftRightNum.getSpecificNumMetrix();
			} else if (type.equals("RightLeft")) {
				return rightLeftNum.getSpecificNumMetrix();
			}
		}
		return null;
	}

}
