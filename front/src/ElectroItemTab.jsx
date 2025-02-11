import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const fetchData = async ({ queryKey }) => {
  const [, page, size] = queryKey;
  const response = await axios.get(`${config.API_BASE_URL}/estore/api/electro_item/findAll?page=${page}&size=${size}`);
  return response.data;
};

const ElectroItemTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [newElectroItem, setNewElectroItem] = useState({
    name: "",
    electroType: "",
    electroType: "",
    price: "",
    count: "",
    archived: "",
    description: "",
  });

  const { data, error, isLoading } = useQuery({
    queryKey: ["electroItems", page, size],
    queryFn: fetchData,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const addMutation = useMutation({
    mutationFn: async (data) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/electro_item/add`, data, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["electroItems"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/electro_item/delete?id=${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["electroItems"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const handleAdd = () => {
    addMutation.mutate(newElectroItem);
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>ElectroItems</h2>

      {/* Add Button */}
      <button onClick={() => setIsAddModalOpen(true)}>Add</button>

      {/* Employee Table */}
      <table className="type-table">
        <thead>
          <tr>
            <th>id</th>
            <th>Name</th>
            <th>ElectroType</th>
            <th>Price</th>
            <th>Count</th>
            <th>Archived</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {data?.map((entity) => (
            <tr key={entity.id}>
              <td>{entity.id}</td>
              <td>{entity.name}</td>
              <td>{entity.electroType}</td>
              <td>{entity.price}</td>
              <td>{entity.count}</td>
              <td>{entity.archived}</td>
              <td>{entity.description}</td>
              <td>
                <button onClick={() => deleteMutation.mutate(entity.id)}>Delete</button>
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

      {/* Add Employee Modal */}
      {isAddModalOpen && (
        <div className="modal">
          <h3>Add New</h3>
          <input type="text" placeholder="Name" value={newElectroItem.name} onChange={(e) => setNewElectroItem({ ...newElectroItem, name: e.target.value })} />
          <input type="number" placeholder="ElectroType" value={newElectroItem.electroType} onChange={(e) => setNewElectroItem({ ...newElectroItem, electroType: e.target.value })} />
          <input type="number" placeholder="Price" value={newElectroItem.price} onChange={(e) => setNewElectroItem({ ...newElectroItem, price: e.target.value })} />
          <input type="number" placeholder="Count" value={newElectroItem.count} onChange={(e) => setNewElectroItem({ ...newElectroItem, count: e.target.value })} />
          <select value={newElectroItem.archived} onChange={(e) => setNewElectroItem({ ...newElectroItem, archived: e.target.value === "true" })}>
            <option value="">Select availability</option>
            <option value="true">Out of stock</option>
            <option value="false">In stock</option>
          </select>
          <input type="text" placeholder="Description" value={newElectroItem.description} onChange={(e) => setNewElectroItem({ ...newElectroItem, description: e.target.value })} />
          <button onClick={handleAdd}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default ElectroItemTab;
