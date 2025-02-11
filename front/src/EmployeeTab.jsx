import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const fetchEmployees = async ({ queryKey }) => {
  const [, page, size] = queryKey;
  const response = await axios.get(`${config.API_BASE_URL}/estore/api/employee/findAll?page=${page}&size=${size}`);
  return response.data;
};

const EmployeeTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [isShowBestEmployeesCount, setIsShowBestEmployeesCount] = useState(false);
  const [isShowBestEmployeesSum, setIsShowBestEmployeesSum] = useState(false);
  const [isShowTheBestEmployee, setIsShowTheBestEmployee] = useState(false);
  const [position, setPosition] = useState(1);
  const [itemType, setItemType] = useState(1);
  const [year, setYear] = useState(2025);
  const [bestEmployees, setBestEmployees] = useState([]);
  const [newEmployee, setNewEmployee] = useState({
    lastName: "",
    firstName: "",
    patronymic: "",
    birthDate: "",
    positionType: "",
    shop: "",
    gender: "",
  });

  const { data, error, isLoading } = useQuery({
    queryKey: ["employees", page, size],
    queryFn: fetchEmployees,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const fetchBestSellersBySellCount = async ([position, year]) => { 
    console.log(position);
    console.log(year);
    const response = await axios.get(
      `${config.API_BASE_URL}/estore/api/employee/best_quantity_sell?positionTypeId=${position}&year=01/01/${year}`
    );
    return response.data;
  };

  const fetchBestSellersBySellSum = async ([position, year]) => { 
    console.log(position);
    console.log(year);
    const response = await axios.get(
      `${config.API_BASE_URL}/estore/api/employee/best_sum_sell?positionTypeId=${position}&year=01/01/${year}`
    );
    return response.data;
  };

  const fetchTheBestSeller = async ([position, itemType]) => { 
    const response = await axios.get(
      `${config.API_BASE_URL}/estore/api/employee/best_etype_sell?positionTypeId=${position}&electroTypeId=${itemType}`
    );
    console.log(response);
    return response.data;
  };

  const addMutation = useMutation({
    mutationFn: async (employee) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/employee/add`, employee, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["employees"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (employeeId) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/employee/delete?id=${employeeId}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["employees"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting employee:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const handleAddEmployee = () => {
    addMutation.mutate(newEmployee);
  };

  const handleDateChange = (e) => {
    const inputDate = e.target.value; // Get the value in yyyy-mm-dd format
    const formattedDate = formatDate(inputDate);

    setNewEmployee({
      ...newEmployee,
      birthDate: formattedDate, // Keep it in yyyy-mm-dd format
    });
  };

  const formatDate = (date) => {
    if (!date) return ''; // Return empty string if there's no date
    const [year, month, day] = date.split('-');
    return `${day}/${month}/${year}`; // Format as dd/mm/yyyy
  };

  const convertToInputFormat = (formattedDate) => {
    const [day, month, year] = formattedDate.split('/');
    return `${year}-${month}-${day}`; // Convert to yyyy-mm-dd for the input
  };

  const handleFetchBestSellersCount = async () => { 
    try {
      // Pass an array directly
      const result = await fetchBestSellersBySellCount([position, year]);
      setBestEmployees(result);
      setIsShowBestEmployeesCount(true);
      setIsShowBestEmployeesSum(false);
      setIsShowTheBestEmployee(false);
      console.log("Best Sellers:", result);
    } catch (error) {
      console.error("Error fetching best sellers:", error);
    }
  };

  const handleFetchBestSellersSum = async () => { 
    try {
      // Pass an array directly
      const result = await fetchBestSellersBySellSum([position, year]);
      setBestEmployees(result);
      console.log("Best Sellers:", bestEmployees);
      setIsShowBestEmployeesCount(false);
      setIsShowBestEmployeesSum(true);
      setIsShowTheBestEmployee(false);
    } catch (error) {
      console.error("Error fetching best sellers:", error);
    }
  };

  const handleFetchTheBestSeller = async () => { 
    try {
      // Pass an array directly
      const result = await fetchTheBestSeller([position, itemType]);
      setBestEmployees([result]);
      console.log("Best Sellers:", bestEmployees);
      setIsShowBestEmployeesCount(false);
      setIsShowBestEmployeesSum(false);
      setIsShowTheBestEmployee(true);
    } catch (error) {
      console.error("Error fetching best sellers:", error);
    }
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>Employees</h2>

      {/* Add Employee Button */}
      <button onClick={() => setIsAddModalOpen(true)}>Add</button>
      <div>The best employees by:</div>
      <table className="type-table">
        <thead>
          <tr>
            <th>Parameters</th>
            <th>Sold items</th>
            <th>Sold sum</th>
            <th>The Best by position and item type</th>
          </tr>
        </thead>
        <tbody>
          <tr >
            <td>
              <div>Position</div>
              <input 
                type="number"
                placeholder="Enter position"
                value={position} onChange={(e) => setPosition(e.target.value)}
              />
              <div>Year</div>
              <input 
                type="number" 
                placeholder="Enter year (e.g. 2025)" 
                value={year} onChange={(e) => setYear(e.target.value)}
              />
              <div>Item type</div>
              <input 
                type="number" 
                placeholder="Item type" 
                value={itemType} onChange={(e) => setItemType(e.target.value)}
              />
            </td>
            <td>
              <button onClick={handleFetchBestSellersCount}>Get</button>
            </td>
            <td>
              <button onClick={handleFetchBestSellersSum}>Get</button>
            </td>
            <td>
              <button onClick={handleFetchTheBestSeller}>Get</button>
            </td>
          </tr>
        </tbody>
      </table>

      {(isShowBestEmployeesCount || isShowTheBestEmployee) && (
      <table className="type-table">
      <thead>
        <tr>
          <th>id</th>
          <th>Last Name</th>
          <th>First Name</th>
          <th>Patronymic</th>
          <th>Sold items</th>
        </tr>
      </thead>
      <tbody>
        {bestEmployees.map((employee) => (
          <tr key={employee.id}>
            <td>{employee.id}</td>
            <td>{employee.lastName}</td>
            <td>{employee.firstName}</td>
            <td>{employee.patronymic}</td>
            <td>{employee.purchaseCount}</td>
          </tr>
        ))}
      </tbody>
    </table>
    )}
      
    {isShowBestEmployeesSum && (
      <table className="type-table">
      <thead>
        <tr>
          <th>id</th>
          <th>Last Name</th>
          <th>First Name</th>
          <th>Patronymic</th>
          <th>Sold sum</th>
        </tr>
      </thead>
      <tbody>
        {bestEmployees.map((employee) => (
          <tr key={employee.id}>
            <td>{employee.id}</td>
            <td>{employee.lastName}</td>
            <td>{employee.firstName}</td>
            <td>{employee.patronymic}</td>
            <td>{employee.purchaseSum}</td>
          </tr>
        ))}
      </tbody>
    </table>
    )}
      {!isShowBestEmployeesCount && !isShowBestEmployeesSum && !isShowTheBestEmployee && (
      <table className="type-table">
        <thead>
          <tr>
            <th>id</th>
            <th>Last Name</th>
            <th>First Name</th>
            <th>Patronymic</th>
            <th>Birthdate</th>
            <th>Position</th>
            <th>Shop</th>
            <th>Gender</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {data?.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.id}</td>
              <td>{employee.lastName}</td>
              <td>{employee.firstName}</td>
              <td>{employee.patronymic}</td>
              <td>{employee.birthDate}</td>
              <td>{employee.positionType}</td>
              <td>{employee.shop}</td>
              <td>{employee.gender ? "М" : "Ж"}</td>
              <td>
                <button onClick={() => deleteMutation.mutate(employee.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      )}
      {/* Pagination Controls */}
      <button disabled={page === 0} onClick={() => setPage(page - 1)}>Previous</button>
      <button onClick={() => setPage(page + 1)}>Next</button>
      <select onChange={(e) => setSize(Number(e.target.value))} value={size}>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
      </select>

      {/* Error Modal */}
      {isErrorModalOpen && <Modal message={errorMessage} onClose={() => setIsErrorModalOpen(false)} />}

      {/* Add Employee Modal */}
      {isAddModalOpen && (
        <div className="modal">
          <h3>Add New</h3>
          <input type="text" placeholder="Last Name" value={newEmployee.lastName} onChange={(e) => setNewEmployee({ ...newEmployee, lastName: e.target.value })} />
          <input type="text" placeholder="First Name" value={newEmployee.firstName} onChange={(e) => setNewEmployee({ ...newEmployee, firstName: e.target.value })} />
          <input type="text" placeholder="Patronymic" value={newEmployee.patronymic} onChange={(e) => setNewEmployee({ ...newEmployee, patronymic: e.target.value })} />
          <input type="date" value={convertToInputFormat(newEmployee.birthDate)} onChange={handleDateChange} />
          <input type="number" placeholder="Position" value={newEmployee.position} onChange={(e) => setNewEmployee({ ...newEmployee, positionType: e.target.value })} />
          <input type="number" placeholder="Shop ID" value={newEmployee.shop} onChange={(e) => setNewEmployee({ ...newEmployee, shop: e.target.value })} />
          <select value={newEmployee.gender} onChange={(e) => setNewEmployee({ ...newEmployee, gender: e.target.value === "true" })}>
            <option value="">Select Gender</option>
            <option value="true">Male</option>
            <option value="false">Female</option>
          </select>
          <button onClick={handleAddEmployee}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};


export default EmployeeTab;
