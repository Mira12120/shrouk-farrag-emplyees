package sirmaTask;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class Logic {

	public void startApp() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {

		// Create and set up the window.
		final JFrame frame = new JFrame("Centered");

		// Display the window.
		frame.setSize(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set flow layout for the frame
		frame.getContentPane().setLayout(new FlowLayout());

		JButton button = new JButton("Choose file/directory");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				File file = createFileChooser(frame);

				if (file != null) // Check if user selected a file
					startEngine(file);
			}
		});

		frame.getContentPane().add(button);

	}

	private void startEngine(File file) {

		List<Employee> listofEmployees = new ArrayList();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String st;
		try {
			while ((st = br.readLine()) != null) {

				System.out.println(st);
				String[] arrOfLine = st.split(",");

				Date today = new Date();

				Employee employee = new Employee(Integer.valueOf(arrOfLine[0]), Integer.valueOf(arrOfLine[1].trim()),
						arrOfLine[2].trim().equals("NULL") ? today
								: new SimpleDateFormat("yyyy-MM-dd").parse(arrOfLine[2]),
						arrOfLine[3].trim().equals("NULL") ? today
								: new SimpleDateFormat("yyyy-MM-dd").parse(arrOfLine[3]));

				listofEmployees.add(employee);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prinitngMap(getLonegestTimeEMployees(groupByProjectID(listofEmployees)));

	}

	private Map<Integer, Set<GridModel>> getLonegestTimeEMployees(Map<Integer, List<Employee>> listofEmployees) {

		Map<Integer, Set<GridModel>> longestEmployee = new HashMap();
		Set<GridModel> longestTimeEmployeeList = new HashSet();
		for (Map.Entry<Integer, List<Employee>> entry : listofEmployees.entrySet()) {

			long maxDaysWorked = 0;

			List<Employee> employeesListOfSameProject = entry.getValue();
			if (employeesListOfSameProject.size() > 1) {

				// longestTimeEmployeeList = new HashSet();
				for (int i = 0; i < employeesListOfSameProject.size(); i++) {

					for (int j = i + 1; j < employeesListOfSameProject.size(); j++) {

						Date startDateTogether = employeesListOfSameProject.get(i).getDateFrom()
								.compareTo(employeesListOfSameProject.get(j).getDateFrom()) > 0
										? employeesListOfSameProject.get(i).getDateFrom()
										: employeesListOfSameProject.get(j).getDateFrom();

						Date endDateTogether = employeesListOfSameProject.get(i).getDateTo()
								.compareTo(employeesListOfSameProject.get(j).getDateTo()) > 0
										? employeesListOfSameProject.get(j).getDateTo()
										: employeesListOfSameProject.get(i).getDateTo();


						if (endDateTogether.compareTo(startDateTogether) >= 0) {
							long diff = endDateTogether.getTime() - startDateTogether.getTime();
							long daysWorkedTogether = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
							System.out.println("Days Worked Together: " + daysWorkedTogether);

							if (daysWorkedTogether >= maxDaysWorked) {
								if (daysWorkedTogether > maxDaysWorked)
									longestTimeEmployeeList = new HashSet();

								maxDaysWorked = daysWorkedTogether;
								GridModel gridModel = new GridModel(
										String.valueOf(employeesListOfSameProject.get(i).getEmpID()),
										String.valueOf(employeesListOfSameProject.get(j).getEmpID()),
										String.valueOf(employeesListOfSameProject.get(j).getProjectID()),
										String.valueOf(maxDaysWorked));

								longestTimeEmployeeList.add(gridModel);
							}
						}
					}
				}
//				System.out.println("longist size" + longestTimeEmployeeList.size());
//				for (GridModel e : longestTimeEmployeeList) {
//					System.out.println(e.getEmpolyeeIDOne());
//					System.out.println(e.getEmployeeIDTwo());
//					System.out.println(e.getWorkedDays());
//				}
				if (longestTimeEmployeeList.size() > 0) {
					longestEmployee.put(entry.getKey(), longestTimeEmployeeList);

				}
			}

		}
		return longestEmployee;

	}

	private void prinitngMap(Map<Integer, Set<GridModel>> listofEmployees) {
		System.out.println("Printing ....");

		List<String> row;
		List<List<String>> rows = new ArrayList();
		for (Entry<Integer, Set<GridModel>> entry : listofEmployees.entrySet()) {
			for (GridModel gridModel : entry.getValue()) {
				row = new ArrayList();
				row.add(gridModel.getEmpolyeeIDOne());
				row.add(gridModel.getEmployeeIDTwo());
				row.add(gridModel.getProjectID());
				row.add(gridModel.getWorkedDays());
				rows.add(row);
			}
		}
		createTable(rows);
	}

	private void createTable(List<List<String>> mainList) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Longes Time Employees Worked together", TitledBorder.CENTER, TitledBorder.TOP));

		if (mainList.size() > 0) {
			String[][] rec = new String[mainList.size()][mainList.get(0).size()];

			for (int i = 0; i < mainList.size(); i++) {
				for (int j = 0; j < mainList.get(i).size(); j++) {
					rec[i][j] = mainList.get(i).get(j);

				}
			}

			String[] header = { "Employee One ID", "Employee Two ID", "Project ID", "Days Worked" };
			JTable table = new JTable(rec, header);
			table.setShowGrid(false);
			table.setShowHorizontalLines(true);
			table.setGridColor(Color.orange);
			frame.setLocationRelativeTo(null);
			panel.add(new JScrollPane(table));
			frame.add(panel);

			frame.setSize(550, 400);
			frame.setVisible(true);
		}
	}

	private File createFileChooser(final JFrame frame) {

		String filename = File.separator + "tmp";
		JFileChooser fileChooser = new JFileChooser(new File(filename));

		// pop up an "Open File" file chooser dialog
		fileChooser.showOpenDialog(frame);

		System.out.println("File to open: " + fileChooser.getSelectedFile());

		File file = fileChooser.getSelectedFile();

		return file;

	}

	public static Map<Integer, List<Employee>> groupByProjectID(List<Employee> listofEmployees) {
		Map<Integer, List<Employee>> employeesGroupedByIDMap = listofEmployees.stream()
				.collect(Collectors.groupingBy(w -> w.getProjectID()));
		return employeesGroupedByIDMap;

	}
}
