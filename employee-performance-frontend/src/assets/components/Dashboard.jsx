import React, { useEffect, useState } from "react";
import axios from "axios";
import { Pie, Line } from "react-chartjs-2";
import "chart.js/auto"; // Ensures Chart.js works with React

function App() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/performance-appraisal/calculate") // Replace with your backend URL
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching data:", err);
        setError("Failed to fetch data");
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!data) {
    return <div>No data available</div>;
  }

  // Prepare data for Pie Chart
  const pieChartData = {
    labels: Object.keys(data).filter((key) => key !== "Employees to Revise"),
    datasets: [
      {
        data: Object.values(data)
          .filter((value) => value?.actualPercentage)
          .map((value) => parseFloat(value.actualPercentage)),
        backgroundColor: ["#4caf50", "#2196f3", "#ff9800", "#f44336", "#9c27b0"],
        hoverBackgroundColor: ["#66bb6a", "#42a5f5", "#ffb74d", "#e57373", "#ba68c8"],
      },
    ],
  };

  // Prepare data for Bell Curve Graph
  const bellCurveLabels = Object.keys(data).filter((key) => key !== "Employees to Revise");
  const bellCurveData = {
    labels: bellCurveLabels,
    datasets: [
      {
        label: "Standard (%)",
        data: [10, 20, 40, 20, 10], // Standard percentages
        borderColor: "#4caf50",
        backgroundColor: "rgba(76, 175, 80, 0.2)",
        tension: 0.4,
      },
      {
        label: "Actual (%)",
        data: Object.values(data)
          .filter((value) => value?.actualPercentage)
          .map((value) => parseFloat(value.actualPercentage)),
        borderColor: "#2196f3",
        backgroundColor: "rgba(33, 150, 243, 0.2)",
        tension: 0.4,
      },
    ],
  };

  const employees = data["Employees to Revise"] || [];

  return (
    <div>
      <h1 className="text-center">Performance Appraisal Dashboard</h1>

      {/* Flex container for Pie Chart and Bell Curve */}
      <div style={{ display: "flex", justifyContent: "space-between", gap: "30px", flexWrap: "wrap", alignItems: "flex-start" }}>
        
        {/* Pie Chart */}
        <div style={{ flex: "1 1 300px", maxWidth: "600px", height: "600px" }}>
          <Pie data={pieChartData} width={600} height={600} />
        </div>

        {/* Employees to Revise Section */}
        <div id="main-page" className="bg-white p-6 rounded-lg shadow-lg border border-gray-300 mt-6" style={{ flex: "1 1 300px", maxWidth: "500px", height: "500px" }}>
            <h2 className="text-lg font-medium text-gray-800 mb-4 text-center">Employees to Revise</h2>
            {employees.length > 0 ? (
              <div className="overflow-x-auto text-slate-500">
                <table className="min-w-full border-collapse table-auto">
                  <thead>
                    <tr className="bg-gray-100">
                      <th className="border px-4 py-2 text-sm font-semibold text-gray-600">Employee ID</th>
                      <th className="border px-4 py-2 text-sm font-semibold text-gray-600">Name</th>
                      <th className="border px-4 py-2 text-sm font-semibold text-gray-600">Current Rating</th>
                    </tr>
                  </thead>
                  <tbody>
                    {employees.map((employee, index) => (
                      <tr key={index} className="text-center">
                        <td className="border px-4 py-2 text-sm text-gray-700">{employee["Employee ID"]}</td>
                        <td className="border px-4 py-2 text-sm text-gray-700">{employee.Name}</td>
                        <td className="border px-4 py-2 text-sm text-gray-700">{employee["Current Rating"]}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <p className="text-gray-600 text-center">No employees need revision</p>
            )}
        </div>

        {/* Bell Curve and Deviation Data */}
        <div style={{ flex: "1 1 300px", maxWidth: "700px", height: "600px" }}>
          <div className="bg-white p-4 rounded-lg shadow-lg border border-gray-200" style={{ height: "300px" }}>
            <h2 className="text-lg font-medium text-gray-800 mb-4 text-center">Standard vs Actual Performance (Bell Curve)</h2>
            <div className="w-full h-full">
              <Line data={bellCurveData} />
            </div>
          </div>

        {/* Deviation Data */}
<div className="deviation-data-container">
  <h3 className="deviation-data-title">Deviation Data</h3>
  <div className="deviation-item-container">
    {Object.keys(data)
      .filter((key) => key !== "Employees to Revise")
      .map((category) => (
        <div key={category} className="deviation-item">
          <span>{category}</span>
          <span>{data[category].actualPercentage}</span>
          <span>Deviation: {data[category].deviation}</span>
        </div>
      ))}
  </div>
</div>

        </div>
      </div>
    </div>
  );
}

export default App;
