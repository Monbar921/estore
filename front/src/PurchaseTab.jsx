import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const PurchaseTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [fromSortDate, setFromSortDate] = useState("");
  const [toSortDate, setToSortDate] = useState("");
  const [purchases, setPurchases] = useState([]);
  const [newPurchase, setNewPurchase] = useState({
    electroItem: "",
    employee: "",
    shop: "",
    purchaseDate: "",
    purchaseType: "",
  });

  const fetchData = async ({ queryKey }) => {
    const [, page, size] = queryKey;
    const response = await axios.get(`${config.API_BASE_URL}/estore/api/purchase/findAll?page=${page}&size=${size}`);
    setPurchases(response.data);
    console.log(purchases);
    return response.data;
  };

  const { data, error, isLoading } = useQuery({
    queryKey: ["purchaseTypes", page, size],
    queryFn: fetchData,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const addMutation = useMutation({
    mutationFn: async (entity) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/purchase/add`, entity, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["purchases"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/purchase/delete?id=${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["purchases"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const fetchSortAll = async ([fromSortDate, toSortDate, sortType]) => { 
    const response = await axios.get(
      `${config.API_BASE_URL}/estore/api/purchase/sort_by_dates?from=${fromSortDate}&to=${toSortDate}&isAsc=${sortType}&page=${page}&size=${size}`
    );
    console.log(response);
    return response.data;
  };

  const handleAdd = () => {
    addMutation.mutate(newPurchase);
  };

  const handleDateChange = (e) => {
    const inputDate = e.target.value; // Get the value in yyyy-mm-dd format
    const formattedDate = formatDate(inputDate);

    setNewPurchase({
      ...newPurchase,
      purchaseDate: formattedDate, // Keep it in yyyy-mm-dd format
    });
  };

  const handleFromSortDateChange = (e) => {
    const inputDate = e.target.value; // Get the value in yyyy-mm-dd format
    const formattedDate = formatDate(inputDate);

    setFromSortDate(formattedDate);
  };

  const handleToSortDateChange = (e) => {
    const inputDate = e.target.value; // Get the value in yyyy-mm-dd format
    const formattedDate = formatDate(inputDate);

    setToSortDate(formattedDate);
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

  const handleSortFetching = async (sortType) => { 
    try {
      // Pass an array directly
      const result = await fetchSortAll([fromSortDate, toSortDate, sortType]);
      setPurchases(result);
    } catch (error) {
      console.error("Error fetching best sellers:", error);
    }
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>Purchases</h2>

      <button onClick={() => setIsAddModalOpen(true)}>Add</button>
      <div>Choose sort</div>
      <table className="type-table">
        <thead>
            <tr>
              <th>From date</th>
              <th>To date</th>
              <th>Sort Asc</th>
              <th>Sort Desc</th>
            </tr>
          </thead>
        <tbody>
          <tr >
            <td>
              <input type="date" value={convertToInputFormat(fromSortDate)} onChange={handleFromSortDateChange} />
            </td>
            <td>
              <input type="date" value={convertToInputFormat(toSortDate)} onChange={handleToSortDateChange} />
            </td>
            <td>
              <button onClick={() => handleSortFetching(true)}>Get</button>
            </td>
            <td>
              <button onClick={() => handleSortFetching(false)}>Get</button>
            </td>
          </tr>
        </tbody>
      </table>

      <table className="type-table">
        <thead>
          <tr>
            <th>id</th>
            <th>ElectroItem</th>
            <th>Employee</th>
            <th>Shop</th>
            <th>PurchaseDate</th>
            <th>PurchaseType</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {purchases.map((purchase) => (
            <tr key={purchase.id}>
              <td>{purchase.id}</td>
              <th>{purchase.electroItem}</th>
              <th>{purchase.employee}</th>
              <th>{purchase.shop}</th>
              <th>{purchase.purchaseDate}</th>
              <th>{purchase.purchaseType}</th>
              <td>
                <button onClick={() => deleteMutation.mutate(purchase.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

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

      {isAddModalOpen && (
        <div className="modal">
          <h3>Add New</h3>
          <input type="number" placeholder="ElectroItem" value={newPurchase.electroItem} onChange={(e) => setNewPurchase({ ...newPurchase, electroItem: e.target.value })} />
          <input type="number" placeholder="Employee" value={newPurchase.employee} onChange={(e) => setNewPurchase({ ...newPurchase, employee: e.target.value })} />
          <input type="number" placeholder="Shop" value={newPurchase.shop} onChange={(e) => setNewPurchase({ ...newPurchase, shop: e.target.value })} />
          <input type="date" value={convertToInputFormat(newPurchase.purchaseDate)} onChange={handleDateChange} />
          <input type="number" placeholder="PurchaseType" value={newPurchase.purchaseType} onChange={(e) => setNewPurchase({ ...newPurchase, purchaseType: e.target.value })} />
          <button onClick={handleAdd}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default PurchaseTab;
